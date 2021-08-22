package com.example.demo.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.UtilComponent;
import com.example.demo.entities.CancelWaitEntity;
import com.example.demo.repositories.CancelWaitRepository;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRepository;

//非同期処理(マシン解放待ち解除機能)
@RestController
public class RestCancelWait {
	
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	CancelWaitRepository cancelWaitRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	StudentRepository studentRepository;

	// マシン解放待ち解除
	@RequestMapping(value="/cancel_wait", method=RequestMethod.POST)
	public String Post_CancelWait(@RequestParam("machineCode") String machineCode, @RequestParam("usercode") String usercode, @RequestParam("reservationNumber") int reservationNumber) {	
		// マシン解放待ちデータを取得する
		CancelWaitEntity waitEntity = cancelWaitRepository.findByStudentAndMachineAndSeatStatus(studentRepository.findByStudentcode(usercode), machineRepository.findByMachinecode(machineCode), seatStatusRepository.findByNumber(reservationNumber));
		
		if (waitEntity != null) {
			// マシン解放待ちデータを削除する
			cancelWaitRepository.delete(waitEntity);
			
			// 座席状態データを削除する
			seatStatusRepository.deleteById(reservationNumber);
			
			// ログ保存
			utilComponent.saveToLog(null, null, usercode, "解放待ち登録解除");
			return "マシン解放待ちを解除しました！";
		}
		return "マシン解放待ち解除に失敗しました。";
	}
	
}
