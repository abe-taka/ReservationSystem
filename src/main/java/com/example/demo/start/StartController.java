package com.example.demo.start;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　利用開始
@Controller
public class StartController {
	
	@GetMapping(value="/start")
	public String Get_Start(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "start";
	}
	
}
