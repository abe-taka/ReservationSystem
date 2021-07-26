package com.example.demo.components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//Session情報管理
@Component
@SessionScope
public class SessionForm {

	private String session_code;

	public String getSession_code() {
		return session_code;
	}

	public void setSession_code(String session_code) {
		this.session_code = session_code;
	}
}