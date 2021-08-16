package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.repositories.HourRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRegistRepository;

//　予約取消
@Controller
public class CancelReservationController {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	StudentRegistRepository studentregRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	HourRepository hourRepository;
	
	// セッションデータ用
	String session_data = null;
	
	static Calendar calendar = Calendar.getInstance();
	
	@GetMapping(value="/cancelReservation")
	public String Get_CancelReservation(Model model) {
		//セッションデータの取得
		session_data = sessionForm.getSession_code();
				
		//セッションを確認し、ない場合取得を試す
		if (session_data != null) {
			//所属クラスを取得
			String session_data = sessionForm.getSession_code();
			StudentRegistEntity studentreg = new StudentRegistEntity();
			studentreg = studentregRepository.findByStudentcode(session_data);
			String classcode = null;
					
			if (studentreg != null) {
				classcode = studentreg.getClassEntity().getClasscode();
			} else {
				System.out.println("所属クラスがない");
			}
			model.addAttribute("session_data", session_data);
			
			//現在日を取得		
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Date todayDate = new Date();
			todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
			
			//現在時限を取得
			String hour = dateTimeComponent.getCurrentHour();
			
			//現在日・現在時限・学籍番号を基に予約を取得
			List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
			list_reservations = seatStatusRepository.getReservations(todayDate, hour, session_data);
			model.addAttribute("list_reservations", list_reservations);
			
			return "cancelReservation";
		} else {
			return "redirect:/";
		}
	}
	
	
	
}
