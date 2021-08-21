package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.forms.StudentForm;
import com.example.demo.repositories.SeatStatusRepository;

//　継続利用
@Controller
public class ReuseController {

	@Autowired
	SessionForm sessionForm;
	@Autowired
	SeatStatusRepository seatStatusRepository;

	// セッションデータ用
	String session_data = null;

	@GetMapping(value = "/reuse")
	public String Get_Reuse(Model model) {

		// セッションデータの取得
		session_data = sessionForm.getSession_code();
		// セッション確認
		if (session_data != null) {
			List<SeatStatusEntity> seatStatus = seatStatusRepository.getReservationByTerminate(session_data, "2");
			model.addAttribute("seatStatus", seatStatus);
			return "reuse";
		} else {
			model.addAttribute("studentForm", new StudentForm());
			return "redirect:/";
		}
	}
}
