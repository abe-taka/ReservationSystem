package com.example.demo.rests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.components.UtilComponent;
import com.example.demo.entities.HourEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.MachineSoftEntity;
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
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

// 非同期処理(予約機能)
@RestController
public class RestReservation {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	HourRepository hourRepository;
	@Autowired
	HourInWorkPatternRepository hourInWorkPatternRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	@Autowired
	StudentRepository studentRepository;
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
		return utilComponent.listToJSON(machineEntity);
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
		return utilComponent.listToJSON(softEntity);
	}

	// 機種コード取得[チェックボックス]
	@GetMapping("/get_machine/fromsoft")
	public String Get_MachineFromSoft(@RequestParam("js_softcode") String js_softcode, @RequestParam("js_floor") String js_floor) {

		// セッションを取得
		String session_data = sessionForm.getSession_code();

		// 所属クラスを取得
		StudentRegistEntity studentRegistEntity = studentRegistRepository.findByStudentcode(session_data);
		String class_code = studentRegistEntity.getClassEntity().getClasscode();

		// チェックしたソフトが入っている + 所属クラスが使える機種コードを取得
		List<MachineSoftEntity> machineSoftEntity_list = new ArrayList<>();
		List<String> machinecode_list = new ArrayList<>();
		
		// DB検索
		machineSoftEntity_list = machineSoftRepository.getMachineCodeBySoft(js_softcode, js_floor, class_code);

		// List<String>に取得した機種コードをセット
		for (int j = 0; j < machineSoftEntity_list.size(); j++) {
			machinecode_list.add(machineSoftEntity_list.get(j).getMachine().getMachinecode());
		}

		// JSONに変換し返却
		return utilComponent.listToJSON(machinecode_list);
	}

	// 予約情報取得
	@GetMapping("/get_reservation")
	public String Get_Reservation(@RequestParam("js_mcode") String js_mcode) {
		// セッションを取得
		String studentcode = sessionForm.getSession_code();

		// 10日×(1日付+7コマ)分の予約情報受け取り用のList
		List<List<String>> resultList = new ArrayList<List<String>>();
		
		// (1日付+7コマ)の受け取り用のリスト
		List<String> seatStatusPerDay = null;
		
		// 時限データをDBから取得
		List<HourEntity> listHours = new ArrayList<HourEntity>();
		listHours = hourRepository.findAll();
		
		// 10日間の日付データを取得
		TreeMap<String, String> periodData = new TreeMap<>();
		periodData = dateTimeComponent.Get_Monthdate(periodData);
		
		for (int i = 0; i < periodData.size(); i++) {
			seatStatusPerDay = new ArrayList<String>();
			
			// 日付データの日・曜日を取得し、1日付+7コマのリストの最初に入れる
			String key = (String) periodData.keySet().toArray()[i];
			String value = periodData.get(key);		
			seatStatusPerDay.add(key + value);
			
			for (int j = 0; j < listHours.size(); j++) {
				String hour = listHours.get(j).getHourCode();
				Date date = dateTimeComponent.strDateToDate(key + " 00:00:00", "yyyy/MM/dd hh:mm:ss");
				
				// 現在の時限より前かどうかをチェック
				if (checkIsBeforeCurrentTime(hour, date)) {
					// RED!!
					seatStatusPerDay.add("RED-NONE");
				// 稼動パターンに対応しているかどうかをチェック
				} else if (checkIsMachineRoomNotWorking(hour, date)) {
					// RED!!
					seatStatusPerDay.add("RED-NONE");
				} else {
					// ユーザが確定予約を持っている場合
					if (checkIsMachineReserved(date, hour, studentcode)) {
						// BLUE!!
						String reservedMachineCode = getReservedMachineCode(date, hour, studentcode);
						seatStatusPerDay.add("BLUE-" + reservedMachineCode);
					// 確定予約ではない場合
					} else {
						// 仮予約の場合
						if (checkIsMachineTentativelyReserved(date, hour, studentcode)) {
							// YELLOW!!
							String tentativelyReservedMachineCode = getTentativelyReservedMachineCode(date, hour, studentcode);
							seatStatusPerDay.add("YELLOW-" + tentativelyReservedMachineCode);
						// 仮予約ではない場合
						} else {
							// 残席がない場合
							if (checkLimitSeatPersonal(js_mcode, hour, date)) {
								// RED!!
								seatStatusPerDay.add("RED-NONE");
							//すべてのケースに該当しないため、予約可能にする	
							} else {
								// WHITE!!
								seatStatusPerDay.add("WHITE-NONE");
							}
						}
					}
				}
			}
			// 結果リストに座席状態データを追加する
			resultList.add(seatStatusPerDay);
		}

		return utilComponent.listToJSON(resultList);
	}
	
	
	// 予約確定処理
	@RequestMapping(value="/make_reservation", method=RequestMethod.POST)
	public String Post_Reservation(@RequestParam("date") String date, @RequestParam("hour") String hour, @RequestParam("machinecode") String machinecode, @RequestParam("studentcode") String studentcode) {
		// タイプ変換
		Date dateDate = dateTimeComponent.strDateToDate(date, "yyyy/MM/dd hh:mm:ss");
		
		// 現在時刻の取得
		Date todayDate = new Date();
		
		// 該当機種に残席があるかチェック
		if (!checkLimitSeatPersonal(machinecode, hour, dateDate)) {
			// 座席状態テーブルにデータを追加する
			SeatStatusEntity entity = new SeatStatusEntity(dateDate, hourRepository.findByHourCode(hour), machineRepository.findByMachinecode(machinecode), 1, studentRepository.findByStudentcode(studentcode), "1", todayDate);
			entity = seatStatusRepository.save(entity);
			
			// ログ保存
			utilComponent.saveToLog(null, null, studentcode, "予約登録");
			
			// 予約ログ保存
			utilComponent.saveToReservationLog(machinecode, "", studentcode, "", dateDate, hour, "予約登録");
		} else {
			return "残席がありません。";
		}
		return "予約を確定しました！";
	}
	
	
	// 現在の時限より前かどうかチェック
	private boolean checkIsBeforeCurrentTime(String targetHour, Date date) {	
		// 現在時刻を取得
		Date currentTime = new Date(System.currentTimeMillis());
		
		// 現在の時刻を文字列化する
		String currentStrDate = dateTimeComponent.dateToStrDate(currentTime);
		
		// 対象日と現在日が一緒であれば
		if (dateTimeComponent.dateToStrDate(date).equals(currentStrDate)) {
			return dateTimeComponent.checkIsTargetHourBeforeCurrentHour(targetHour);
		}
		return false;
	}
	
	// マシンルームが非稼働かどうかチェック(非稼働：true, 稼働：false)
	private boolean checkIsMachineRoomNotWorking(String targetHour, Date date) {
		// DBから該当時限で検索し、非稼働であればtrueを返す
		if (hourInWorkPatternRepository.findNotWorkingHour(targetHour, date).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 予約確定かを調査する(確定：true, 未定：false)
	private boolean checkIsMachineReserved(Date date, String targetHour, String studentcode) {
		// DBから日付・時限・学籍番号で検索し、その日時に該当学生がどれかのマシーンを予約していればtrueを返す
		if (seatStatusRepository.findIfAlreadyReserved(date, targetHour, studentcode).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 予約確定の機種コードを取得する
	private String getReservedMachineCode(Date date, String targetHour, String studentcode) {
		List<SeatStatusEntity> entities = seatStatusRepository.findIfAlreadyReserved(date, targetHour, studentcode);
		if (entities.size() > 0) {
			return entities.get(0).getMachine().getMachinecode();
		}
		return "";
	}
	
	// 仮予約かを調査する(仮予約：true, 未定：false)
	private boolean checkIsMachineTentativelyReserved(Date date, String targetHour, String studentcode) {
		if (seatStatusRepository.findIfAlreadyTentativelyReserved(date, targetHour, studentcode).size() > 0) {
			return true;
		}
		return false;
	}
	
	// 仮予約の機種コードを取得する
	private String getTentativelyReservedMachineCode(Date date, String targetHour, String studentcode) {
		List<SeatStatusEntity> entities = seatStatusRepository.findIfAlreadyTentativelyReserved(date, targetHour, studentcode);
		if (entities.size() > 0) {
			return entities.get(0).getMachine().getMachinecode();
		}
		return "";
	}
	
	// 該当機種に残席があるかチェック(有：true, 無：false)
	private boolean checkLimitSeatPersonal(String machinecode, String hour, Date date) {
		// マシンの総台数を取得
        int int_limit_seat = machineRepository.getSeatCountSelectMachine(machinecode);

        // 故障機数の取得(故障確定のみ)
        int int_trouble_machine = troubleMachineRepository.getTroubleMachine(machinecode);

        // 予約・利用中の台数の取得
        int int_reservation_machine = seatStatusRepository.countReservedMachine(date, hour, machinecode);
        
        int int_seat_count = int_limit_seat - int_trouble_machine - int_reservation_machine;
        
        // 空席数が0以上の場合、予約可能なのでfalseを返す
        if (int_seat_count > 0) {
        	return false;
        }
        return true;
	}
}