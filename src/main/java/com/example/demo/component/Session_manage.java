package com.example.demo.component;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//セッション情報の受け渡し
@Component
public class Session_manage {

	// HttpSession
	@Autowired
	HttpSession httpsession; 
	
	private String session_data;
	
	public String getSession_data() {
		return session_data;
	}

	public void setSession_data(String session_data) {
		this.session_data = session_data;
	}
	
	
	

	public void Set_SessionData(String data) {
		httpsession.setAttribute("data", data);
	}
	
	public String Get_SessionData(String data) {
		httpsession.getAttribute(data).toString();
		return data;
	}
	
	public String Get_SessionMail(String session_num) {
		
		/* 「Authenticationオブジェクト」 
		 * セッションIDを基に作成されたオブジェクト
		 * これがあるかどうかでSession管理は行われている
		 * */
		
		// SecurityContextHolder → 認証情報
		// getAuthentication() → アクセスする許可があるかどうかを判別。
		// getName() →認証ありはusernameの値、無ければanonymousUserが取得できる。
		session_num = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("## ユーザー情報 ##"+SecurityContextHolder.getContext().getAuthentication().toString());
		if(session_num == "anonymousUser") {
			System.out.println("認証なし");
		}else {
			System.out.println("認証あり");
		}
		
		return session_num;
	}
}