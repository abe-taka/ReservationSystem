package com.example.demo.immediateUse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　即時利用
@Controller
public class ImmediateUseController {

	@GetMapping(value="/immediateUse")
	public String Get_ImmediateUse(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "immediateUse";
	}
	
}
