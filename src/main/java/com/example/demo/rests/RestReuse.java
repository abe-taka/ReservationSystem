package com.example.demo.rests;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.HourEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentEntity;
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

	@RequestMapping(value="/restresue", method = RequestMethod.POST)
	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_hour") String js_hour, @RequestParam("js_date") String js_date) {

		// フラグ(予約可能or予約不可)
		String seat_flag = null;
		//セッション取得
		String session_data = sessionForm.getSession_code();
		
		//継続利用したい機種のデータ取得
		SeatStatusEntity seatStatus = seatStatusRepository.getReservationMachinecodeByTerminate(js_machinecode,session_data, "2");
		
		//日付
		Date date = seatStatus.getDate();
		
		// 次の時限の空席確認
		// 最終時限チェック
		if (!(js_hour.equals("7"))) {
			//次の時限の値設置
			int int_hour = Integer.parseInt(js_hour);
			js_hour = String.valueOf(int_hour + 1);
			
			// 次の時限の予約数取得
			int i = seatStatusRepository.countReservedMachine(date, js_hour, js_machinecode);
			System.out.println("予約数" + i);
			
			// 機種の台数を取得
			MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
			System.out.println("台数" + machineEntity.getCount());
			
			// 満席チェック
			if (i < machineEntity.getCount()) {
				//予約可
				seat_flag = "0";
				/* 予約データを追加する */
				//座席番号保持
				String seatnum = seatStatus.getMachineNo();
				//初期化
				seatStatus = new SeatStatusEntity();
				// フラグ
				seatStatus.setCheckinFlag("1");
				// 学籍番号
				StudentEntity student = new StudentEntity();
				student.setStudentcode(session_data);
				seatStatus.setStudent(student);
				// 機種コード
				MachineEntity machine = new MachineEntity();
				machine.setMachinecode(js_machinecode);
				seatStatus.setMachine(machine);
				// 時限
				HourEntity hourEntity = new HourEntity();
				hourEntity.setHourCode(js_hour);
				seatStatus.setHour(hourEntity);
				// 座席番号
				seatStatus.setMachineNo(seatnum);
				//日付
				seatStatus.setDate(date);
				//追加
				seatStatusRepository.save(seatStatus);
			}
			//満席
			else {
				seat_flag = "1";
			}
		}
		//最終時限
		else {
			seat_flag = "1";
		}
		return seat_flag;
	}
}