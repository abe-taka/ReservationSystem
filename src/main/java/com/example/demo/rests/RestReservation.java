package com.example.demo.rests;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.Realtime_manage;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.MachineSoftEntity;
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
}