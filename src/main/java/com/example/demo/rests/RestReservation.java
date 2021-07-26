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
import com.example.demo.entities.SoftEntity;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.SoftRepository;
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

	// 機種コード取得
	@GetMapping("/get_machine")
	public String Get_Machine(@RequestParam("js_floor") int js_floor) {

		// 取得してきた値を元にDBに検索する
		List<MachineEntity> machineEntity = new ArrayList<MachineEntity>();
		try {
			machineEntity = machineRepository.findByFloor(js_floor);
		} catch (Exception e) {
			System.out.println("機種コード取得失敗");
		}
		// JSONに変換し返却
		return getJson_Machine(machineEntity);
	}

	// ソフト取得
	@GetMapping("/get_soft")
	public String Get_Soft(@RequestParam("js_floor") int js_floor) {

		// 取得してきた値を元にDBに検索する
		List<SoftEntity> softEntity = new ArrayList<SoftEntity>();
		try {
			softEntity = softRepository.findBySoftid(js_floor);
		} catch (Exception e) {
			System.out.println("ソフト取得失敗");
		}
		// JSONに変換し返却
		return getJson_Soft(softEntity);
	}

	// 予約情報取得
	@GetMapping("/get_reservation")
	public String Get_Reservation(@RequestParam("js_mcode") String js_mcode) {

		// 機種の台数を取得
		MachineEntity machineEntity = new MachineEntity();
		machineEntity = machineRepository.findByMachinecode(js_mcode);
		System.out.println("台数" + machineEntity.getCount());

		// 機種の予約情報を取得
		// 受け取り用のMapを用意
		TreeMap<String, List<String>> reseravtion_data = new TreeMap<>();
		TreeMap<String, String> week_data = new TreeMap<>();
		week_data = realtime_manage.Realtime_process(week_data);
		int k = 0;
		for (String keyval : week_data.keySet()) {
			String str = null;
			str = "2021/" + keyval;
			System.out.println("????" + str);

			for(int i = 0; i < 9; i++) {
				String j = String.valueOf(i);
				
				List<Long> period_count = reservationRepository.countByMachineAndReservationstartdateAndPeriodcode(machineEntity,str,j);
				System.out.println("コマごとの予約数"+ period_count.toString());
				List<String> valueList = new ArrayList<String>();
				valueList.add(str);
				valueList.add(j);
				
				
				String ww = String.valueOf(machineEntity.getCount());
				String qq = String.valueOf(period_count);
				if(ww.equals(qq)) {
					System.out.println("ooooooooooo");
					valueList.add("1");
				}else {
					System.out.println("vggggg"+ period_count.toString());
					valueList.add("0");
				}
				String kk = String.valueOf(k);
				reseravtion_data.put(kk, valueList);
				System.out.println("Map値"+ reseravtion_data.toString());
				
				k++;
			}
		}

		return getJson_Reservation(reseravtion_data);
	}

	// 引数のオブジェクトをJSON文字列に変換
	// 機種コード
	private String getJson_Machine(List<MachineEntity> machineEntity) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(machineEntity);
			System.out.println("[機種コード]JSON変換正常");
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("[機種コード]JSON変換失敗");
		}
		System.out.println("[機種コード]json変換値：" + json_convert);
		return json_convert;
	}

	// ソフト
	private String getJson_Soft(List<SoftEntity> softEntity) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(softEntity);
			System.out.println("[ソフト]JSON変換正常");
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("[ソフト]JSON変換失敗");
		}
		System.out.println("[ソフト]json変換値：" + json_convert);
		return json_convert;
	}
	
	
	//予約情報
	private String getJson_Reservation(TreeMap<String, List<String>> reservation_data) {
		// json変換取得変数
		String json_convert = null;

		// 変換処理
		try {
			json_convert = objectMapper.writeValueAsString(reservation_data);
			System.out.println("[予約情報]JSON変換正常");
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("[予約情報]JSON変換失敗");
		}
		System.out.println("[予約情報]json変換値：" + json_convert);
		return json_convert;
	}
}