package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.components.UtilComponent;
import com.example.demo.entities.CancelWaitEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.repositories.CancelWaitRepository;
import com.example.demo.repositories.HourRepository;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TroubleMachineRepository;

//非同期処理(即時利用機能)
@RestController
public class RestStart {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	CancelWaitRepository cancelWaitRepository;
	@Autowired
	HourRepository hourRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	
	// 座席情報取得
	@GetMapping("/get_seats")
	public String Get_Seats(@RequestParam("js_mcode") String js_mcode) {
		// 学籍番号を取得
		String studentcode = sessionForm.getSession_code();

		// 機種エンティティを取得
		MachineEntity machine = machineRepository.findByMachinecode(js_mcode);

		// 座席情報を格納するリスト宣言
		List<String> list_seats = new ArrayList<String>();
		
		//現在日を取得		
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		Date todayDate = new Date();
		todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
				
		//現在時限を取得
		String hour = dateTimeComponent.getCurrentHour();
		
		// 照会するマシンの台数を取得する
		int totalAmounts = machine.getCount();
		
		// 階数・時限・機種コードを基に座席リストを取得する
		for (int i = 1; i < totalAmounts + 1; i++) {			
			// 座席番号・色の取得
			String seatNumber = setSeatNumber(i);
			String seatColor = checkSeatCurrentStatus(todayDate, hour, js_mcode, seatNumber, studentcode);
			
			// 結果リストに座席情報を格納する
			list_seats.add(seatNumber + "-" + seatColor);
		}
			
		System.out.println("Done!: ");
		System.out.println("resultList: " + list_seats);

		return utilComponent.listToJSON(list_seats);
	}

	// 利用開始処理
	@RequestMapping(value="/make_start", method=RequestMethod.POST)
	public String Post_Start(@RequestParam("date") String date, @RequestParam("hour") String hour, @RequestParam("machineCode") String machineCode, @RequestParam("studentcode") String studentcode, @RequestParam("machineNumber") String machineNumber, @RequestParam("processType") int processType, @RequestParam("checkinFlag") String checkinFlag) {
		System.out.println(date + hour + machineCode + studentcode + machineNumber + checkinFlag);
		// タイプ変換
		Date dateDate = dateTimeComponent.strDateToDate(date, "yyyy/MM/dd hh:mm:ss");
		System.out.println(dateDate);
		
		// 現在時刻の取得
		Date todayDate = new Date();
		
		// 予約リストから利用開始の場合
		if (processType == 1) {
			// 座席状態テーブルに予約データが格納されているか確認するために取得
			List<SeatStatusEntity> reservations = seatStatusRepository.getReservationForStart(dateDate, hour, studentcode, checkinFlag);
					
			System.out.println(reservations);
				
			// 該当機種に残席があるかチェック
			if (reservations.size() > 0) {
				// 座席状態テーブルにデータを更新する
				SeatStatusEntity entity = reservations.get(0);
				entity.setMachineNo(machineNumber);
				entity.setCheckinFlag("2");
				entity.setPopupFlag("1");
				entity.setUpdateDate(todayDate);
				entity = seatStatusRepository.save(entity);
				
				System.out.println(entity.getNumber());
				// マシン解放待ちデータがあれば削除する
				CancelWaitEntity waitEntity = cancelWaitRepository.findBySeatStatus(seatStatusRepository.findByNumber(entity.getNumber()));
				System.out.print(waitEntity);			
				if (waitEntity != null) {
					cancelWaitRepository.delete(waitEntity);
				}
				
				// ログ保存
				utilComponent.saveToLog(null, null, studentcode, "利用開始");
				System.out.println(machineCode);
				
				// 予約ログ保存
				utilComponent.saveToReservationLog(machineCode, machineNumber, studentcode, "", dateDate, hour, "利用開始");
				
				// 利用ログ保存
				utilComponent.saveToUseLog(dateDate, hour, machineCode, machineNumber);
				
				return "利用開始を確定しました！";
			}
			return "利用開始に失敗しました。";
			
		// 即時利用の場合	
		} else if (processType == 2) {
			SeatStatusEntity entity = new SeatStatusEntity();
			entity.setCheckinFlag("2");
			entity.setHour(hourRepository.findByHourCode(hour));
			entity.setDate(dateDate);
			entity.setMachine(machineRepository.findByMachinecode(machineCode));
			entity.setMachineCount(1);
			entity.setMachineNo(machineNumber);
			entity.setPopupFlag("1");
			entity.setStudent(studentRepository.findByStudentcode(studentcode));
			entity.setUpdateDate(todayDate);
			entity = seatStatusRepository.save(entity);
			
			// ログ保存
			utilComponent.saveToLog(null, null, studentcode, "利用開始");
			
			// 予約ログ保存
			utilComponent.saveToReservationLog(machineCode, machineNumber, studentcode, "", dateDate, hour, "利用開始");
			
			// 利用ログ保存
			utilComponent.saveToUseLog(dateDate, hour, machineCode, machineNumber);
			
			return "利用開始を確定しました！";
		}
		return "利用開始に失敗しました。";
	}
	
	
	// マシン解放待ち登録
	@RequestMapping(value="/make_wait", method=RequestMethod.POST)
	public String Post_CancelWait(@RequestParam("date") String date, @RequestParam("hour") String hour, @RequestParam("machineCode") String machineCode, @RequestParam("studentcode") String studentcode) {
		System.out.println(date + hour + machineCode + studentcode);
		// タイプ変換
		Date dateDate = dateTimeComponent.strDateToDate(date, "yyyy/MM/dd hh:mm:ss");
		System.out.println(dateDate);
		
		// 現在時刻の取得
		Date todayDate = new Date();
		
		// 座席状態テーブルにキャンセル待ち中(flag:3)として登録(キャンセル待ちで利用可能(flag:4)になるのは元プログラムの「AT」が遂行)
		SeatStatusEntity seatStatusEntity = new SeatStatusEntity();
		seatStatusEntity.setCheckinFlag("3");
		seatStatusEntity.setHour(hourRepository.findByHourCode(hour));
		seatStatusEntity.setDate(dateDate);
		seatStatusEntity.setMachine(machineRepository.findByMachinecode(machineCode));
		seatStatusEntity.setMachineCount(1);
		seatStatusEntity.setStudent(studentRepository.findByStudentcode(studentcode));
		seatStatusEntity.setUpdateDate(todayDate);
		seatStatusEntity = seatStatusRepository.save(seatStatusEntity);
		System.out.println(seatStatusEntity);
		
		// マシン解放待ちテーブルにテータを追加
		CancelWaitEntity cancelWaitEntity = new CancelWaitEntity();
		cancelWaitEntity.setStudent(studentRepository.findByStudentcode(studentcode));
		cancelWaitEntity.setMachine(machineRepository.findByMachinecode(machineCode));
		cancelWaitEntity.setSeatStatus(seatStatusEntity);
		cancelWaitEntity.setUpdateDate(todayDate);
		cancelWaitEntity = cancelWaitRepository.save(cancelWaitEntity);
		System.out.println(cancelWaitEntity);
		
		if (seatStatusEntity != null && cancelWaitEntity != null) {
			// ログ保存
			utilComponent.saveToLog(null, null, studentcode, "解放待ち登録");
			
			return "マシン解放待ちに登録しました！";
		}
		return "マシン解放待ちに失敗しました。";
	}
	
	
	// 현재상황(색깔) -> 자기가 이용3(보라), 다른 사람이 이용중(연보라), 예약중(파랑), 가예약중(파랑), 가고장중(노), 고장(빨)
	private String checkSeatCurrentStatus(Date date, String hour, String machineCode, String machineNumber, String studentcode) {
		// 座席状態に合わせて色を返す
		if (seatStatusRepository.checkIfSeatIsUsingByMyself(date, hour, machineCode, machineNumber, studentcode)) {
			return "PURPLE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "2")) {
			return "PURPLE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "1")) {
			return "BLUE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "0")) {
			return "BLUE";
		} else if (troubleMachineRepository.checkIfMachineIsTentativelyInTrouble(machineCode, machineNumber)) {
			return "YELLOW";
		} else if (troubleMachineRepository.checkIfMachineIsInTroubled(machineCode, machineNumber)) {
			return "RED";
		}
		return "WHITE";
	}
	
	
	// 座席番号を3桁の文字列で変換する
	private String setSeatNumber(int seatNumber) {
		String strSeatNumber = Integer.toString(seatNumber);
			
		if (strSeatNumber.length() == 1) {
			return "00" + strSeatNumber;
		} else if (strSeatNumber.length() == 2) {
			return "0" + strSeatNumber;
		}
		return strSeatNumber;
	}
}
