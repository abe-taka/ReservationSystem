package com.example.demo.termination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　利用終了
@Controller
public class TerminationController {

	@GetMapping(value="/termination")
	public String Get_Termination(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "termination";
	}
}
