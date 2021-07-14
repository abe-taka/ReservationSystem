package com.example.demo.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　座席予約
@Controller
public class ReservationController {
	
	//予約
	@GetMapping(value="/reservation")
	public String Get_Reservation(Model model) {
		return "reservation";
	}
}
