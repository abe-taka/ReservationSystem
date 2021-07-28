package com.example.demo.logout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.SessionForm;

//ログアウト
@Controller
public class LogoutController {

	@Autowired
	SessionForm sessionForm;
	
	//ログアウト処理
	@GetMapping(value="/logout")
	public String Logout() {
		sessionForm.setSession_code(null);
		
		return "redirect:/";
	}
}
