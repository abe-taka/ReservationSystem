package com.example.demo.queue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　待ち状態管理
@Controller
public class QueueController {

	@GetMapping(value="/queue")
	public String Get_Queue(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "queue";
	}
}
