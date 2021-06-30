package com.example.demo.reportBroken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　故障機報告
@Controller
public class ReportBrokenController {
	
	@GetMapping(value="/reportBroken")
	public String Get_ReportBroken(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "reportBroken";
	}

}
