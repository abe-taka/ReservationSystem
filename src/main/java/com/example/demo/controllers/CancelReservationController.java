package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　予約取消
@Controller
public class CancelReservationController {
	
	@GetMapping(value="/cancelReservation")
	public String Get_CancelReservation(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "cancelReservation";
	}
}
