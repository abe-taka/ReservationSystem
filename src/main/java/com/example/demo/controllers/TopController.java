package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.components.SessionForm;
import com.example.demo.forms.StudentForm;

//　トップ
@Controller
@SessionAttributes(types=SessionForm.class)
public class TopController {
	
	@Autowired
	SessionForm sessionForm;
	
	String session_data = null;
	
	@GetMapping(value = "/top")
	public String Get_Top(Model model) {
		//セッション確認
		session_data = sessionForm.getSession_code();
		if(session_data!= null) {
			model.addAttribute("session_data", session_data);
			model.addAttribute("studentForm", new StudentForm());
			return "top";
		}else {
			return "redirect:/";
		}	
	}

	// Post(トップページ)
	@PostMapping(value = "/top")
	public String Post_Top(StudentForm studentForm, Model model) {
		//セッション確認
		session_data = sessionForm.getSession_code();
		if(session_data!= null) {
			model.addAttribute("session_data", session_data);
			model.addAttribute("studentForm", new StudentForm());
			return "top";
		}else {
			System.out.println("session_data"+session_data);
			model.addAttribute("studentForm", new StudentForm());
			return "redirect:/";
		}	
	}
}
