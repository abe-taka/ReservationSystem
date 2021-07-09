package com.example.demo.selectToStart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//利用開始か即時利用か判断する
@Controller
public class SelectToStartController {

	@GetMapping(value="/selectToStart")
	public String Get_SelectToStart(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "selectToStart";
	}
	
}
