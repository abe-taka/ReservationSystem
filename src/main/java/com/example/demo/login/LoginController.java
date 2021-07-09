package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.component.Session_manage;
import com.example.demo.entity.StudentEntity;
import com.example.demo.form.StudentForm;

@Controller
public class LoginController {
	
	@Autowired
	LoginRepository loginRepository;
	@Autowired
	Session_manage session_manage;
	
	//セッションデータ用
	String session_data = null;

	//Get(ログインページ
	@GetMapping(value="/")
	public String Get_Login(Model model) {
		model.addAttribute("studentForm", new StudentForm());
		return "login";
	}
	
	//ログイン認証
	@PostMapping(value="/login_process")
	public String Login_Process(@Validated(StudentForm.All.class) StudentForm studentForm, BindingResult result,Model model) {
		
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
			return "redirect:/";
		}
		//入力チェック
		else if(studentEntity == null) {
			System.out.println("DBに存在しない");
			//エラー表示は、LDAPが終わってから
			return "redirect:/";
		}	
		//正常
		else {
			session_data = studentEntity.getStudentcode();
			session_manage.setSession_data(session_data);
			return "redirect:/top";
		}
	}
}