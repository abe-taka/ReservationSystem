package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.forms.StudentForm;
import com.example.demo.repositories.CalendarRepository;
import com.example.demo.repositories.HourInWorkPatternRepository;
import com.example.demo.repositories.SeatStatusRepository;

//　継続利用
@Controller
public class ReuseController {

	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	HourInWorkPatternRepository hourInWorkPatternRepository;
	@Autowired
	CalendarRepository calendarRepository;

	// セッションデータ用
	String session_data = null;

	@GetMapping(value = "/reuse")
	public String Get_Reuse(Model model) {

		// セッションデータの取得
		session_data = sessionForm.getSession_code();
		
		// セッション確認
		if (session_data != null) {
			// 現在日を取得		
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Date todayDate = new Date();
			todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
						
			// 現在時限を取得			
			String currentHour = dateTimeComponent.getCurrentHour();
			
			// 時間外の場合
			if (checkIfNotAvailable(currentHour, todayDate)) {
				model.addAttribute("notAvailable", "利用可能時間外または継続利用申込不可状態です。");
			// 継続利用できる場合
			} else {
				List<SeatStatusEntity> seatStatus = seatStatusRepository.getReservationForReusing(session_data, "2", currentHour, todayDate);
				model.addAttribute("seatStatus", seatStatus);
			}

			return "/reuse";
		} else {
			model.addAttribute("studentForm", new StudentForm());
			return "redirect:/";
		}
	}
	
	
	// 利用可能時間外または稼働時間外かをチェックする
	private boolean checkIfNotAvailable(String targetHour, Date date) {
		// DBに設定されている時限コードを超えた数字になった場合、継続利用不可判定
		if (targetHour.equals("00")) {
			return true;
		}
		
		// 現在日の稼働コードを元に次の時限が存在しない場合、継続利用不可判定
		String patternCode = calendarRepository.findByDate(date).getWorkPattern().getWorkPatternCode();
		if (Integer.parseInt(targetHour) + 1 > hourInWorkPatternRepository.findMaxHour(patternCode)) {
			return true;
		}
		
		// DBから該当時限で検索し、非稼働であればtrueを返す
		if (hourInWorkPatternRepository.findNotWorkingHour(targetHour, date).size() > 0) {
			return true;
		}
		return false;
	}
}
