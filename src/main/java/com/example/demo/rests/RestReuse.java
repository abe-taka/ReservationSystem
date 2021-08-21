package com.example.demo.rests;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.CalendarEntity;
import com.example.demo.entities.HourEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.repositories.CalendarRepository;
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
	@Autowired
	CalendarRepository calendarRepository;

	@RequestMapping(value="/restresue", method = RequestMethod.POST)
	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_hour") String js_hour, @RequestParam("js_date") String js_date) {

		// テキスト
		String response_text = null;
		//セッション取得
		String session_data = sessionForm.getSession_code();
		
		//継続利用したい機種のデータ取得
		SeatStatusEntity seatStatus = seatStatusRepository.getReservationMachinecodeByTerminate(js_machinecode,session_data, "2");
		
		//日付
		Date date = seatStatus.getDate();
		//座席 
		String machinenum = seatStatus.getMachineNo();
		
		// 最終時限チェック
		if (!(js_hour.equals("7"))) {
			
			//夜間がやっているかのチェック
			CalendarEntity calendarEntity = calendarRepository.findByDate(date);
			String patterncode = calendarEntity.getWorkPattern().getWorkPatternCode();
			if(patterncode.equals("00") && !(js_hour.equals("6"))) {
				
				boolean flag = seatStatusRepository.checkIfSeatIsInReservationByStatusCode(date, js_hour, js_machinecode, machinenum, session_data,"1");
				if(flag == false) {
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
						/* 予約データを追加する */
						//座席番号保持
						String seatnum = seatStatus.getMachineNo();
						//初期化
						seatStatus = new SeatStatusEntity();
						// フラグ
						seatStatus.setCheckinFlag("2");
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
						//予約可
						response_text = "継続利用ができました";
					}
					//満席
					else {
						response_text = "満席のため継続利用不可";
					}
				//次の時限に同じ座席の予約がある場合
				}else {
					response_text = "次の時限に座席:"+machinenum +"に予約があるため継続利用不可";
				}	
			}
			//夜間が無い 、現在が6限目の場合
			else {
				response_text = "夜間がないため継続利用不可";
			}
		}
		//最終時限
		else {
			response_text = "最終時限のため継続利用不可";
		}
		return response_text;
	}
}