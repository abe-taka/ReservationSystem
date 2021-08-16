package com.example.demo.rests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;


@RestController
public class RestReuse {

	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	SessionForm sessionForm;
	@Autowired
	MachineRepository machineRepository;
	
	@GetMapping("/restresue")
	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_hour") String js_hour,@RequestParam("js_date") String js_date) {
		
		//空席があるかのフラグ
		String seat_flag = null;
		
		String session_data = sessionForm.getSession_code();
		SeatStatusEntity seatStatus = seatStatusRepository.getReservationByTerminate(session_data, "2");
        Date date = seatStatus.getDate();
        System.out.println("日付"+ date);
		
		//次の時限の空席確認
		//夜間2ではないか
		if(!(js_hour.equals("8"))) {
			//予約数取得
			int i = seatStatusRepository.countReservedMachine(date, js_hour, js_machinecode);
			System.out.println("予約数"+ i);
			// 機種の台数を取得
			MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
			System.out.println("台数"+ machineEntity.getCount());
			//満席かを確認
			if(i < machineEntity.getCount()) {
				seat_flag = "0";
			}else {
				seat_flag = "1";
			}
		}else {
			seat_flag = "1";
		}
		System.out.println(seat_flag);
		return seat_flag;
	}
}