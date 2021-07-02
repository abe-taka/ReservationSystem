package com.example.demo.deleteWaiting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//　待ち状態解除
@Controller
public class DeleteWaitingController {

	@GetMapping(value="/deleteWaiting")
	public String Get_DeleteWaiting(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
		return "deleteWaiting";
	}
}
