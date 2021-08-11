package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.forms.StudentForm;

//　トップ
@Controller
public class TopController {
	
	@Autowired
	SessionForm sessionForm;
	
	// セッションデータ用
	String session_data = null;
	
	@GetMapping(value = "/top")
	public String Get_Top(Model model, HttpServletRequest request) {
		
		//セッションデータの取得
		session_data = sessionForm.getSession_code();
		
		//HTTPセッションの宣言
		HttpSession session = request.getSession();
	
		//利用開始で使ったセッションデータを削除する
		session.removeAttribute("floor");
		session.removeAttribute("checkinHour");
		session.removeAttribute("machineCode");
		session.removeAttribute("reservDate");

		//セッション確認
		if (session_data != null) {
			model.addAttribute("session_data", session_data);
			model.addAttribute("studentForm", new StudentForm());
			return "top";
		} else {
			return "redirect:/";
		}	
	}

	// Post(トップページ)
	@PostMapping(value = "/top")
	public String Post_Top(StudentForm studentForm, Model model) {
		
		//セッションデータの取得
		session_data = sessionForm.getSession_code();
		
		//セッション確認
		if (session_data != null) {
			model.addAttribute("session_data", session_data);
			model.addAttribute("studentForm", new StudentForm());
			return "top";
		} else {
			return "redirect:/";
		}	
	}
}
