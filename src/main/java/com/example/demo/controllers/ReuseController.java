package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　継続利用
@Controller
public class ReuseController {

	@GetMapping(value="/reuse")
	public String Get_Reuse(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "reuse";
	}
}
