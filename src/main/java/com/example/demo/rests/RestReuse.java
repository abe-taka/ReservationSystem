package com.example.demo.rests;

import java.text.SimpleDateFormat;
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
	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_machinenum") String js_machinenum, @RequestParam("js_hour") String js_hour, @RequestParam("js_date") String js_date) {

		// メッセージ
		String response_text = null;
		//セッション取得
		String session_data = sessionForm.getSession_code();
		//Dateを取得
		SeatStatusEntity seatStatus = seatStatusRepository.getReservationMachinecodeByTerminate(js_machinecode ,session_data, "2",js_hour);
		Date date = seatStatus.getDate(); 
		
		// 夜間チェック
		if (!(js_hour.equals("7"))) {
			
			//夜間がやっているかのチェック
			CalendarEntity calendarEntity = calendarRepository.findByDate(js_date);
			String patterncode = calendarEntity.getWorkPattern().getWorkPatternCode();
			if(patterncode.equals("00") && !(js_hour.equals("6"))) {
				
				//同じ座席の予約チェック
				//次の時限の値設置
				int int_hour = Integer.parseInt(js_hour);
				js_hour = String.valueOf(int_hour + 1);
				
				//既に他の予約をしている場合
				boolean alreadyflag = seatStatusRepository.checkIfAlreadySeatIsInReservationByStatusCode(js_date, js_hour, session_data,"1","2");
				if(alreadyflag == false){
					
					boolean flag = seatStatusRepository.checkIfSeatIsInReservationByStatusCode(js_date, js_hour, js_machinecode, js_machinenum, "1","2");
					if(flag == false) {
						
						// 次の時限の予約数取得
						int i = seatStatusRepository.countReuseReservedMachine(js_date, js_hour, js_machinecode);
						System.out.println("予約数" + i);
						
						// 機種の台数を取得
						MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
						System.out.println("台数" + machineEntity.getCount());
						
						// 満席チェック
						if (i < machineEntity.getCount()) {
							/* 予約データを追加する */
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
							seatStatus.setMachineNo(js_machinenum);
							//日付
							System.out.println("++++++"+date.toString());
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
					}
					//次の時限に同じ座席の予約がある
					else {
						response_text = "既に次の時限に「座席:"+js_machinenum +"」に予約があるため継続利用不可";
					}	
				//既に予約あり
				}else {
					response_text = "既にあなたはその時限に予約をしているため継続利用不可";
				}	
			}
			//夜間が無い 、現在が6限目
			else {
				response_text = "夜間がないため継続利用不可";
			}
		}
		//夜間
		else {
			response_text = "夜間のため継続利用不可";
		}
		return response_text;
	}
}