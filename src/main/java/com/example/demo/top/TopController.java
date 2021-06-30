package com.example.demo.top;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　メイン
@Controller
public class TopController {
	@GetMapping(value="/top")
	public String Get_Top(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "top";
	}
}
