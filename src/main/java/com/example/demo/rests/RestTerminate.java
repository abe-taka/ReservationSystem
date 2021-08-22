package com.example.demo.rests;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.components.UtilComponent;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TroubleMachineEntity;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TroubleMachineRepository;

@RestController
public class RestTerminate {

	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	SessionForm sessionForm;
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	
	// 故障機報告(REST)
	@RequestMapping(value="/restterminate", method=RequestMethod.POST)
	public String TerminateProcess(@RequestParam("js_troubleValue") String js_troubleValue, @RequestParam("js_machinecode") String js_machinecode, @RequestParam("js_machinenum") String js_machinenum, @RequestParam("reservationNumber") int reservationNumber) {
		// マシン・学生エンティティの取得
		MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
		String session_data = sessionForm.getSession_code();
		StudentEntity studentEntity = studentRepository.findByStudentcode(session_data);
		
		// 故障機報告
		TroubleMachineEntity troubleMachineEntity = new TroubleMachineEntity();
		troubleMachineEntity.setMachine(machineEntity);
		troubleMachineEntity.setMachineNo(js_machinenum);
		troubleMachineEntity.setReportDate(new Date());
		troubleMachineEntity.setStudent(studentEntity);
		troubleMachineEntity.setState("0");
		troubleMachineEntity.setTroublePattern(js_troubleValue);
		troubleMachineEntity = troubleMachineRepository.save(troubleMachineEntity);

		// 利用終了
		if (troubleMachineEntity != null && checkIfCanTerminateUse(reservationNumber)) {
			seatStatusRepository.deleteById(reservationNumber);
			
			// ログ保存
			utilComponent.saveToLog(null, null, session_data, "故障機報告");
			utilComponent.saveToLog(null, null, session_data, "利用終了");
			
			return "故障機報告を完了し、利用を終了しました！";
		}
		return "故障機報告に失敗しました。";
	}
	
	// 利用終了(REST)
	@RequestMapping(value="/restuseoff", method=RequestMethod.POST)
	public String UseOffProcess(@RequestParam("reservationNumber") int reservationNumber) {
		// ユーザ番号を取得
		String session_data = sessionForm.getSession_code();
		
		if (checkIfCanTerminateUse(reservationNumber)) {
			seatStatusRepository.deleteById(reservationNumber);
			
			// ログ保存
			utilComponent.saveToLog(null, null, session_data, "利用終了");
			
			return "利用を終了しました！";
		}
		return "利用終了に失敗しました。";
	}
	
	// 利用終了する関数
	private boolean checkIfCanTerminateUse(int reservationNumber) {
		if (seatStatusRepository.findByNumber(reservationNumber) != null) {
			return true;
		}
		return false;
	}
}