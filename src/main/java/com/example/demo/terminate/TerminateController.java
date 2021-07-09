package com.example.demo.terminate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　利用終了
@Controller
public class TerminateController {

	@GetMapping(value="/terminate")
	public String Get_Termination(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "terminate";
	}
}
