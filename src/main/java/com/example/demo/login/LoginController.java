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

	//Get(ログインページ)
	@GetMapping(value="/login")
	public String Get_Login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	//Post(トップページ)
	@PostMapping(value="/top")
	public String Post_Login(LoginForm loginForm,Model model) {
	
		//送信されたデータをDBに保存
		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setId(loginForm.getId());
		loginEntity.setName(loginForm.getName());
		loginRepository.save(loginEntity);
		
		model.addAttribute("loginForm",loginForm);
		return "top";
	}
}