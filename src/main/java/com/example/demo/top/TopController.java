package com.example.demo.top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.component.Session_manage;
import com.example.demo.form.StudentForm;

//　メイン
@Controller
public class TopController {
	
	@Autowired
	Session_manage session_manage;
	
	String session_data = null;
	String session_mail = null;
	
	@GetMapping(value = "/top")
	public String Get_Top(Model model) {
		session_data = session_manage.getSession_data();
		System.out.println("session_mail"+session_data);
		model.addAttribute("session_data", session_data);
		model.addAttribute("studentForm", new StudentForm());
		return "top";
	}

	// Post(トップページ)
	@PostMapping(value = "/top")
	public String Post_Top(StudentForm studentForm, Model model) {
		session_data = session_manage.getSession_data();
		System.out.println("session_mail"+session_data);
		model.addAttribute("session_data", session_data);
		model.addAttribute("studentForm", studentForm);
		return "top";
	}
}
