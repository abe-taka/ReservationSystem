package com.example.demo.rests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.Realtime_manage;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.HourEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.MachineSoftEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.SoftEntity;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.repositories.HourRepository;
import com.example.demo.repositories.HourInWorkPatternRepository;
import com.example.demo.repositories.TroubleMachineRepository;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.MachineSoftRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.SoftRepository;
import com.example.demo.repositories.StudentRegistRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//非同期処理(予約機能)
@RestController
public class RestReservation {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	SessionForm sessionForm;
	@Autowired
	Realtime_manage realtime_manage;
	@Autowired
	HourRepository hourRepository;
	@Autowired
	HourInWorkPatternRepository hourInWorkPatternRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	SoftRepository softRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	MachineSoftRepository machineSoftRepository;
	@Autowired
	StudentRegistRepository studentRegistRepository;

	// 機種コード取得[階層]
	@GetMapping("/get_machine")
	public String Get_Machine(@RequestParam("js_floor") int js_floor) {
		// セッションを取得
		String session_data = sessionForm.getSession_code();

		// 取得してきた値(階層)を元にDBに検索し、機種コードを取得する
		List<MachineEntity> machineEntity = new ArrayList<MachineEntity>();

		// 所属クラスを取得
		StudentRegistEntity studentRegistEntity = studentRegistRepository.findByStudentcode(session_data);
		String class_code = studentRegistEntity.getClassEntity().getClasscode();

		try {
			machineEntity = machineRepository.findByFloor(js_floor, class_code);
		} catch (Exception e) {
			System.out.println("RestReservation_Get_Machine():fail");
		}
		// JSONに変換し返却
		return getJson_Machine(machineEntity);
	}

	// ソフト取得[階層]
	@GetMapping("/get_soft")
	public String Get_Soft(@RequestParam("js_floor") int js_floor) {
		// 取得してきた値(階層)を元にDBに検索し、ソフトを取得する
		List<SoftEntity> softEntity = new ArrayList<SoftEntity>();
		try {
			softEntity = softRepository.findBySoftcode(js_floor);
		} catch (Exception e) {
			System.out.println("RestReservation_Get_Soft():fail");
		}
		// JSONに変換し返却
		return getJson_Soft(softEntity);
	}

	// 機種コード取得[チェックボックス]
	@GetMapping("/get_machine/fromsoft")
	public String Get_MachineFromSoft(@RequestParam("js_softcode") String js_softcode, @RequestParam("js_floor") int js_floor) {

		// セッションを取得
		String session_data = sessionForm.getSession_code();

		// 所属クラスを取得
		StudentRegistEntity studentRegistEntity = studentRegistRepository.findByStudentcode(session_data);
		String class_code = studentRegistEntity.getClassEntity().getClasscode();

		// チェックしたソフトが入っている + 所属クラスが使える機種コードを取得
		List<MachineSoftEntity> machineSoftEntity_list = new ArrayList<>();
		List<String> machinecode_list = new ArrayList<>();

		// DB検索
		machineSoftEntity_list = machineSoftRepository.query(js_softcode, js_floor, class_code);

		// List<String>に取得した機種コードをセット
		for (int j = 0; j < machineSoftEntity_list.size(); j++) {
			machinecode_list.add(machineSoftEntity_list.get(j).getMachine().getMachinecode());
		}

		// JSONに変換し返却
		return getJson_MachineFromSoft(machinecode_list);
	}

	// 予約情報取得
	@GetMapping("/get_reservation")
	public String Get_Reservation(@RequestParam("js_mcode") String js_mcode) {
		// セッションを取得
		String studentcode = sessionForm.getSession_code();

		// 機種の台数を取得
		MachineEntity machineEntity = machineRepository.findByMachinecode(js_mcode);

		// 10日×(1日付+7コマ)分の予約情報受け取り用のList
		List resultList = new ArrayList();
		
		// (1日付+7コマ)の受け取り用のリスト
		List seatStatusPerDay = new ArrayList();
		
		// 時限データをDBから取得
		List<HourEntity> listHours = new ArrayList<HourEntity>();
		listHours = hourRepository.findAll();
		
		// 10日間の日付データを取得
		TreeMap<String, String> periodData = new TreeMap<>();
		periodData = realtime_manage.Get_Monthdate(periodData);
		
		for (int i = 0; i < periodData.size(); i++) {
			seatStatusPerDay = new ArrayList();
			
			// 日付データの日・曜日を取得し、1日付+7コマのリストの最初に入れる
			String key = (String) periodData.keySet().toArray()[i];
			String value = periodData.get(key);		
			seatStatusPerDay.add(key + value);
			
			for (int j = 0; j < listHours.size(); j++) {
				String hour = listHours.get(j).getHourCode();
				System.out.println("hour: " + listHours.get(j).getHourCode());
				
				Date date = strDateToDate(key + " 00:00:00");			
				System.out.println("date: " + date);
				
				// 現在の時限より前かどうかをチェック
				if (checkIsBeforeCurrentTime(hour, date)) {
					// RED!!
					System.out.println("BEFORE: RED!!");
					seatStatusPerDay.add("RED-NONE");
				// 稼動パターンに対応しているかどうかをチェック
				} else if (checkIsMachineRoomNotWorking(hour, date)) {
					// RED!!
					System.out.println("NOT WORKING: RED!!");
					seatStatusPerDay.add("RED-NONE");
				} else {
					// ユーザが確定予約を持っている場合
					if (checkIsMachineReserved(date, hour, studentcode)) {
						// BLUE!!
						String reservedMachineCode = getReservedMachineCode(date, hour, studentcode);
						System.out.println("BLUE!! " + reservedMachineCode);
						seatStatusPerDay.add("BLUE-" + reservedMachineCode);
					// 確定予約ではない場合
					} else {
						// 仮予約の場合
						if (checkIsMachineTentativelyReserved(date, hour, studentcode)) {
							// YELLOW!!
							String tentativelyReservedMachineCode = getTentativelyReservedMachineCode(date, hour, studentcode);
							System.out.println("YELLOW!! " + tentativelyReservedMachineCode);
							seatStatusPerDay.add("YELLOW-" + tentativelyReservedMachineCode);
						// 仮予約ではない場合
						} else {
							//残席がない場合
							if (checkLimitSeatPersonal(js_mcode, hour, date)) {
								// RED!!
								System.out.println("RED!!");
								seatStatusPerDay.add("RED-NONE");
							//すべてのケースに該当しないため、予約可能にする	
							} else {
								// WHITE!!
								System.out.println("WHITE!!");
								seatStatusPerDay.add("WHITE-NONE");
							}
						}
					}
				}
			}
			// 結果リストに座席状態データを追加する
			resultList.add(seatStatusPerDay);
		}
		
		System.out.println("Done!: ");
		System.out.println("resultList: " + resultList);

		return getJson_Reservation(resultList);
	}
	
	
	// 予約確定処理
	@PostMapping("/make_reservation")
	public String Post_Reservation(@RequestParam("date") String date, @RequestParam("hour") String hour, @RequestParam("machinecode") String machinecode, @RequestParam("studentcode") String studentcode) {
		
		// 予約テーブルにデータを追加する(X)
		
		// 座席状態テーブルにデータを追加する
		
	}
	
	// String型の日付をDate型に変換する
	private Date strDateToDate(String strDate) {
		Date date = null;
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			date = sdFormat.parse(strDate + " 00:00:00");	
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return date;
	}
	
	// Date型の日付をString型に変換する
	private String dateToStrDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	// Date型の時間をString型に変換する
	private String dateToStrTime(Date date) {
		return new SimpleDateFormat("HH:mm:ss:S").format(date);
	}
	
	// 現在の時限より前かどうかチェック()
	private boolean checkIsBeforeCurrentTime(String targetHour, Date date) {
		System.out.println("checkIsBeforeCurrentTime");
		
		// 現在時刻を取得
		Date currentTime = new Date(System.currentTimeMillis());
		
		// DB比較用の臨時文字列を生成
		String tempTimeStr = "1900/01/01 " + dateToStrTime(currentTime);
		
		// 現在の時刻を文字列化する
		String currentStrDate = dateToStrDate(currentTime);
		
		// 対象日と現在日が一緒であれば
		if (dateToStrDate(date).equals(currentStrDate)) {
			try {
				// DBから時限コードを取得
				String currentHour = hourRepository.findHourCode(tempTimeStr).getHourCode();
				// 処理しようとする時限と取得した時限コードの時限を比べ、現在時刻より前の時限をtrueにする
				if (Integer.parseInt(targetHour) < Integer.parseInt(currentHour)) {
					return true;
				}
				return false;
			// 現在時刻がDBに登録した時限の時間外の場合、trueを返す
			} catch (NullPointerException e) {
				return true;
			}
		}
		return false;
	}
	
	// マシンルームが非稼働かどうかチェック(非稼働：true, 稼働：false)
	private boolean checkIsMachineRoomNotWorking(String targetHour, Date date) {
		System.out.println("checkIsMachineRoomNotWorking");
		// DBから該当時限で検索し、非稼働であればtrueを返す
		if (hourInWorkPatternRepository.findNotWorkingHour(targetHour, date).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 予約確定かを調査する(確定：true, 未定：false)
	private boolean checkIsMachineReserved(Date date, String targetHour, String studentcode) {
		System.out.println("checkIsMachineReserved");
		// DBから日付・時限・学籍番号で検索し、その日時に該当学生がどれかのマシーンを予約していればtrueを返す
		if (seatStatusRepository.findIfAlreadyReserved(date, targetHour, studentcode).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 予約確定の機種コードを取得する
	private String getReservedMachineCode(Date date, String targetHour, String studentcode) {
		System.out.println("getReservedMachineCode");
		List<SeatStatusEntity> entities = seatStatusRepository.findIfAlreadyReserved(date, targetHour, studentcode);
		if (entities.size() > 0) {
			return entities.get(0).getMachineCode();
		}
		return "";
	}
	
	// 仮予約かを調査する(仮予約：true, 未定：false)
	private boolean checkIsMachineTentativelyReserved(Date date, String targetHour, String studentcode) {
		System.out.println("checkIsMachineTentativelyReserved");
		if (seatStatusRepository.findIfAlreadyTentativelyReserved(date, targetHour, studentcode).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 仮予約の機種コードを取得する
	private String getTentativelyReservedMachineCode(Date date, String targetHour, String studentcode) {
		System.out.println("getTentativelyReservedMachineCode");
		List<SeatStatusEntity> entities = seatStatusRepository.findIfAlreadyTentativelyReserved(date, targetHour, studentcode);
		if (entities.size() > 0) {
			return entities.get(0).getMachineCode();
		}
		return "";
	}
	
	// 該当機種に残席があるかチェック(有：true, 無：false)
	private boolean checkLimitSeatPersonal(String machinecode, String hour, Date date) {
		//マシンの総台数を取得
        int int_limit_seat = machineRepository.getSeatCountSelectMachine(machinecode);
        System.out.println("int_limit_seat: " + int_limit_seat);

        //故障機数の取得
        int int_trouble_machine = troubleMachineRepository.getTroubleMachine(machinecode);
        System.out.println("int_trouble_machine: " + int_trouble_machine);

        //予約・利用中の台数の取得
        int int_reservation_machine = seatStatusRepository.countReservedMachineByDateAndCheckinHourAndMachineCode(date, hour, machinecode);
        System.out.println("int_reservation_machine: " + int_reservation_machine);
        
        int int_seat_count = int_limit_seat - int_trouble_machine - int_reservation_machine;
        System.out.println("int_seat_count: " + int_seat_count);
        
        //空席数が0以上の場合、予約可能なのでfalseを返す
        if (int_seat_count > 0) {
        	return false;
        }
        return true;
	}

	/** 引数のオブジェクトをJSON文字列に変換 **/
	// 機種コード[階層]
	private String getJson_Machine(List<MachineEntity> machineEntity) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(machineEntity);
		} catch (JsonProcessingException e) {
			System.out.println("getJson_Machine():fail");
		}
		return json_convert;
	}

	// ソフト[階層]
	private String getJson_Soft(List<SoftEntity> softEntity) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(softEntity);
		} catch (JsonProcessingException e) {
			System.out.println("getJson_Soft():fail");
		}
		return json_convert;
	}

	// 機種コード[チェックボックス]
	private String getJson_MachineFromSoft(List<String> machine_list) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(machine_list);
		} catch (JsonProcessingException e) {
			System.out.println("getJson_MachineFromSoft():fail");
		}
		return json_convert;
	}

	// 予約情報
	private String getJson_Reservation(List bigdata_list) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(bigdata_list);
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("getJson_Reservation():fail");
		}
		return json_convert;
	}
}