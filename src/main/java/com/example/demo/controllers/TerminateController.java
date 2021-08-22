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
import com.example.demo.repositories.SeatStatusRepository;

//　利用終了
@Controller
public class TerminateController {

	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;

	// セッションデータ用
	String session_data = null;

	@GetMapping(value = "/terminate")
	public String Get_Termination(Model model) {
		// セッションデータの取得
		session_data = sessionForm.getSession_code();
		
		// セッション確認
		if (session_data != null) {
			//セッションデータの受け渡し設定
			String session_data = sessionForm.getSession_code();
			model.addAttribute("session_data", session_data);
			
			// 現在日を取得		
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Date todayDate = new Date();
			todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
					
			// 現在時限を取得
			String hour = dateTimeComponent.getCurrentHour();
			
			List<SeatStatusEntity> use_list = seatStatusRepository.getReservationByFlag(todayDate, hour, session_data, "2");
			model.addAttribute("todayDate", todayDate);
			model.addAttribute("hour", hour);
			model.addAttribute("use_list", use_list);

			return "terminate";
		} else {
			return "redirect:/";
		}
		
	}
}
