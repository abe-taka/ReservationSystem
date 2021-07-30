package com.example.demo.rests;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.Realtime_manage;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.MachineSoftEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.SoftEntity;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.MachineSoftRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.SoftRepository;
import com.example.demo.repositories.StudentRegistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//非同期処理(予約機能)
@RestController
public class RestReservation {

	@Autowired
	MachineRepository machineRepository;
	@Autowired
	SoftRepository softRepository;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	SessionForm sessionForm;
	@Autowired
	Realtime_manage realtime_manage;
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
		String session_data = sessionForm.getSession_code();

		// 機種の台数を取得
		MachineEntity machineEntity = machineRepository.findByMachinecode(js_mcode);
		System.out.println("台数" + machineEntity.getCount());

		// 10日×7コマ分の予約情報受け取り用のList (jsに渡す変数)
		// bigdata_listの中身 → 日付、コマ、予約状況のフラグ、席状況のフラグ
		// 予約状況のフラグ → 送信者の予約なし:0 予約あり:1
		// 予約状況のフラグ → 空席有り:0 満席:1
		List<TreeMap> bigdata_list = new ArrayList<TreeMap>();

		// 1日7コマ分の受け取り用のMap
		TreeMap<String, List<String>> reseravtion_data = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data2 = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data3 = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data4 = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data5 = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data6 = new TreeMap<>();
		TreeMap<String, List<String>> reseravtion_data7 = new TreeMap<>();

		// コマ、予約状況のフラグ、席状況のフラグの受け取り用のリスト
		List<String> reservation_list = new ArrayList<>();

		// 10日間のデータ取得
		TreeMap<String, String> week_data = new TreeMap<>();
		week_data = realtime_manage.Get_Monthdate(week_data);

		// 今日の「年/月/日」取得
		String current_ymd = null;
		current_ymd = realtime_manage.Get_CurrentYmd(current_ymd);

		// 10日目の 「年/月/日」取得
		String tenafter_ymd = null;
		tenafter_ymd = realtime_manage.Get_SevenAfterYmd(tenafter_ymd);

		// 予約情報を取得(指定した機種 + 今日から10日以内の予約)
		List<ReservationEntity> reser = reservationRepository.findByMachineAndReservationstartdateBetweenOrderByReservationstartdate(machineEntity, current_ymd,tenafter_ymd);

		// 日別でループ処理
		for (String key : week_data.keySet()) {
			//コマ別でループ処理
			for (int p = 1; p < 8; p++) {
				// リストの初期化
				reservation_list = new ArrayList<>();
				// 予約数のカウント
				int reservation_count = 0;
				// 送信者が既に予約しているかのフラグ
				int sender_flag = 0;
				// コマをリストに追加
				reservation_list.add(String.valueOf(p));
				// DBの日付置換用
				String db_monthdate;
				// コマ
				String pp;
				// 予約情報をループ
				for (int i = 0; i < reser.size(); i++) {
					// DBに取得した日付を置換する(2021-01-01 → 01/01)
					db_monthdate = null;
					db_monthdate = reser.get(i).getReservationstartdate().substring(5).replace("-", "/");

					// コマ
					pp = null;
					pp = String.valueOf(p);
					// 1日ずつ検証
					if (key.equals(db_monthdate) && pp.equals(reser.get(i).getPeriodcode())) {
						reservation_count++;
						// 既に予約している場合1をセット
						// 学生
						try {
							if (session_data.equals(reser.get(i).getStudent().getStudentcode()) && sender_flag == 0) {
								reservation_list.add("1");
								sender_flag++;
							}
						} catch (Exception e) {
						}

						// 教師
						try {
							if (session_data.equals(reser.get(i).getTeacher().getTeachercode()) && sender_flag == 0) {
								reservation_list.add("1");
								sender_flag++;
							}
						} catch (Exception e) {
						}
					}
				}
				System.out.println("予約数" + reservation_count);
				// 予約していない場合0をセット
				if (sender_flag == 0) {
					reservation_list.add("0");
				}
				// 予約数
				if (machineEntity.getCount() == reservation_count) {
					// 満席の場合1をセット
					reservation_list.add("1");
				} else {
					// 満席の場合0をセット
					reservation_list.add("0");
				}
				// 日付
				if (p == 1) {
					reseravtion_data.put(key, reservation_list);
					bigdata_list.add(reseravtion_data);
				} else if (p == 2) {
					reseravtion_data2.put(key, reservation_list);
					bigdata_list.add(reseravtion_data2);
				} else if (p == 3) {
					reseravtion_data3.put(key, reservation_list);
					bigdata_list.add(reseravtion_data3);
				} else if (p == 4) {
					reseravtion_data4.put(key, reservation_list);
					bigdata_list.add(reseravtion_data4);
				} else if (p == 5) {
					reseravtion_data5.put(key, reservation_list);
					bigdata_list.add(reseravtion_data5);
				} else if (p == 6) {
					reseravtion_data6.put(key, reservation_list);
					bigdata_list.add(reseravtion_data6);
				} else if (p == 7) {
					reseravtion_data7.put(key, reservation_list);
					bigdata_list.add(reseravtion_data7);
				}
				// カウントリセット
				reservation_count = 0;
				sender_flag = 0;
			}
		}
		System.out.println("bigdata_list" + bigdata_list);
		return getJson_Reservation(bigdata_list);
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
	private String getJson_Reservation(List<TreeMap> bigdata_list) {
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