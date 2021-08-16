package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.UtilComponent;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.repositories.SeatStatusRepository;

//非同期処理(予約取消機能)
@RestController
public class RestCancelReservation {
	
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;

	// 予約取消
	@RequestMapping(value="/cancel_reservation", method=RequestMethod.POST)
	public String Post_CancelReservation(@RequestParam("reservationNumber") int reservationNumber, @RequestParam("machineCode") String machineCode, @RequestParam("usercode") String usercode, @RequestParam("date") String date, @RequestParam("hour") String hour) {
		// タイプ変換
		Date dateDate = dateTimeComponent.strDateToDate(date, "yyyy/MM/dd hh:mm:ss");
				
		// 予約が存在するかチェックし、あれば削除する
		if (checkIfReservationExists(usercode, reservationNumber)) {
			seatStatusRepository.deleteById(reservationNumber);
			
			// ログ保存
			utilComponent.saveToLog(null, null, usercode, "予約取消");
			
			// 予約ログ保存
			utilComponent.saveToReservationLog(machineCode, "", usercode, "", dateDate, hour, "予約取消");
			
			return "予約を取り消しました。";
		}
		return "予約取消に失敗しました。";
	}
	
	
	// 予約がデータベースにあるかどうかチェック
	private boolean checkIfReservationExists(String usercode, int reservationNumber) {
		//現在日を取得		
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		Date todayDate = new Date();
		todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
		
		//現在時限を取得
		String hour = dateTimeComponent.getCurrentHour();
		
		//予約を取得してみる
		SeatStatusEntity reservation = seatStatusRepository.getReservationByNumber(todayDate, hour, usercode, reservationNumber);
		if (reservation != null) {
			return true;
		}
		return false;
	}
}
