package com.example.demo.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TroubleMachineEntity;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.TroubleMachineRepository;

@RestController
public class RestTerminate {

	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	SessionForm sessionForm;

	@GetMapping("/restterminate")
	public void TerminateProcess(@RequestParam("js_troubleValue") String js_troubleValue,@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_machinenum") String js_machinenum) {
		
		// 故障機報告
		TroubleMachineEntity troubleMachineEntity = new TroubleMachineEntity();
		troubleMachineEntity.setTroublePattern(js_troubleValue);
		MachineEntity machineEntity = new MachineEntity();
		machineEntity.setMachinecode(js_machinecode);
		troubleMachineEntity.setMachine(machineEntity);
		troubleMachineEntity.setMachineNo(js_machinenum);
		troubleMachineRepository.save(troubleMachineEntity);

		
		// 利用終了
		String session_data = sessionForm.getSession_code();
		
		SeatStatusEntity seatStatusEntity = new SeatStatusEntity();
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setStudentcode(session_data);
		seatStatusEntity.setStudent(studentEntity);
		
		seatStatusEntity.setCheckinFlag("2");
		
		MachineEntity machineEntity2 = new MachineEntity();
		machineEntity2.setMachinecode(js_machinecode);
		seatStatusEntity.setMachine(machineEntity2);
		
		seatStatusEntity.setMachineNo(js_machinenum);
		
		seatStatusRepository.deleteByStudentAndCheckinFlagAndMachineAndMachineNo(studentEntity, "2", machineEntity2, js_machinenum);
	}
	
	@GetMapping("/restuseoff")
	public void UseOffProcess(@RequestParam("js_machinecode") String js_machinecode,@RequestParam("js_machinenum") String js_machinenum) {

		System.out.println("機種コード"+js_machinecode);
		System.out.println("機種No"+js_machinenum);
				
		// 利用終了
		String session_data = sessionForm.getSession_code();
		
		SeatStatusEntity seatStatusEntity = new SeatStatusEntity();
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setStudentcode(session_data);
		seatStatusEntity.setStudent(studentEntity);
		
		seatStatusEntity.setCheckinFlag("2");
		
		MachineEntity machineEntity2 = new MachineEntity();
		machineEntity2.setMachinecode(js_machinecode);
		seatStatusEntity.setMachine(machineEntity2);
		
		seatStatusEntity.setMachineNo(js_machinenum);
		
		seatStatusRepository.deleteByStudentAndCheckinFlagAndMachineAndMachineNo(studentEntity, "2", machineEntity2, js_machinenum);
	}
	
}