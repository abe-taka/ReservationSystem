//package com.example.demo.rests;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.components.SessionForm;
//import com.example.demo.entities.HourEntity;
//import com.example.demo.entities.MachineEntity;
//import com.example.demo.entities.SeatStatusEntity;
//import com.example.demo.entities.StudentEntity;
//import com.example.demo.repositories.MachineRepository;
//import com.example.demo.repositories.SeatStatusRepository;
//
//@RestController
//public class RestReuse {
//
//	@Autowired
//	SeatStatusRepository seatStatusRepository;
//	@Autowired
//	SessionForm sessionForm;
//	@Autowired
//	MachineRepository machineRepository;
//
//	@GetMapping("/restresue")
//	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode,
//			@RequestParam("js_hour") String js_hour, @RequestParam("js_date") String js_date) {
//
//		System.out.println("******" + js_hour);
//		// 空席があるかのフラグ
//		String seat_flag = null;
//
//		String session_data = sessionForm.getSession_code();
//		List<SeatStatusEntity> seatStatus = seatStatusRepository.getReservationByTerminate(session_data, "2");
//		Date date = seatStatus.getDate();
//		System.out.println("日付" + date);
//		int seatnum = seatStatus.getNumber();
//		System.out.println("座席" + seatnum);
//		
//		// 次の時限の空席確認
//		// 夜間2ではないか
//		if (!(js_hour.equals("7"))) {
//			// 予約数取得
//			int i = seatStatusRepository.countReservedMachine(date, js_hour, js_machinecode);
//			System.out.println("予約数" + i);
//			// 機種の台数を取得
//			MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
//			System.out.println("台数" + machineEntity.getCount());
//			// 満席かを確認
//			if (i < machineEntity.getCount()) {
//				seat_flag = "0";
//				// データ登録
//				// フラグ
//				seatStatus = new SeatStatusEntity();
//				seatStatus.setCheckinFlag("2");
//				// 学籍番号
//				StudentEntity student = new StudentEntity();
//				student.setStudentcode(session_data);
//				seatStatus.setStudent(student);
//				// 機種コード
//				MachineEntity machine = new MachineEntity();
//				machine.setMachinecode(js_machinecode);
//				seatStatus.setMachine(machine);
//				// 時限
//				String replacehour = js_hour.replace("限目", "");
//				int hour = Integer.parseInt(replacehour);
//				hour += 1;
//				HourEntity hourEntity = new HourEntity();
//				String strhour = String.valueOf(hour);
//				hourEntity.setHourCode(strhour);
//				seatStatus.setHour(hourEntity);
//				// 座席番号
//				seatStatus.setMachineNo(String.valueOf(seatnum));
//				seatStatusRepository.save(seatStatus);
//				System.out.println("#########");
//			} else {
//				seat_flag = "1";
//			}
//		} else {
//			seat_flag = "1";
//		}
//		System.out.println(seat_flag);
//		return seat_flag;
//	}
//}