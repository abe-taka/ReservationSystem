package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.components.SessionForm;
import com.example.demo.entities.StudentEntity;
import com.example.demo.forms.StudentForm;
import com.example.demo.repositories.LoginRepository;

@Controller
public class LoginController {
	
	@Autowired
	LoginRepository loginRepository;
	@Autowired
	SessionForm sessionForm;
	
	//セッションデータ用
	String session_data = null;

	//Get(ログインページ
	@GetMapping(value="/")
	public String Get_Login(Model model) {
		
		//セッション確認
		session_data = sessionForm.getSession_code();
		if(session_data!= null) {
			return "redirect:/top";
		}else {
			model.addAttribute("studentForm", new StudentForm());
			return "login";
		}	
	}
	
	//ログイン認証
	@PostMapping(value="/login_process")
	public String Login_Process(@Validated(StudentForm.All.class) StudentForm studentForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		
		//送信された情報を取得
		String form_code = studentForm.getStudentcode();
		String form_pass = studentForm.getStudentpassword();
		
		//DBに検索
		StudentEntity studentEntity = loginRepository.findByStudentcodeAndStudentpassword(form_code,form_pass);
		
		//バリデーションチェック
		if (result.hasErrors()) {
			// エラー(バリデーションチェック)
			System.out.println("バリデーションエラー");
			System.out.println("バリデーションログ"+result);
			redirectAttr.addFlashAttribute("err", "IDとパスワードを確認してください。");
			
			return "redirect:/";
		}
		
		//DBでのデータ存在チェック
		else if(studentEntity == null) {
			System.out.println("DBに存在しない");
			redirectAttr.addFlashAttribute("err", "IDとパスワードを確認してください。");
			//エラー表示は、LDAPが終わってから
			return "redirect:/";
		}
		
		//正常
		else {
			session_data = studentEntity.getStudentcode();
			sessionForm.setSession_code(session_data);
			return "redirect:/top";
		}
	}
}