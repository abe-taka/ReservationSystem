package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@Autowired
	LoginRepository loginRepository;

	//Get(ログインページ
	@GetMapping(value="/login")
	public String Get_Login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	//Post(トップページ)
	@PostMapping(value="/top")
	public String Post_Top(LoginForm loginForm,Model model) {
		
		model.addAttribute("loginForm",loginForm);
		return "top";
	}
	
	//ログイン認証
	@PostMapping(value="login_process")
	public String Login_Process(LoginForm loginForm) {
		
		int form_code = loginForm.getStudentcode();
		String form_pass = loginForm.getStudentpassword();
		
		LoginEntity loginEntity = loginRepository.findByStudentcodeAndStudentpassword(form_code,form_pass);
		if(loginEntity == null) {
			return "redirect:/login";
		}else {
			return "redirect:/top";
		}
	}
}