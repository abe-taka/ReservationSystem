package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　座席予約
@Controller
public class ReservationController {
	
	@GetMapping(value="/reservation")
	public String Get_Reservation(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "reservation";
	}
}
