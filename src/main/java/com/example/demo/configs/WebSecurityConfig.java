package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Spring Securityの設定
@Configuration
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 「WebSecurityのconfigureメソッドでは全体に対するセキュリティ設定を行う。」
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/image/**", "/css/**", "/javascript/**","/bootstrap/**","/webjars/**");
	}
	
	// 「HttpSecurityのconfigureメソッドではURLごとにセキュリティ設定を行う」
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		//http
			//アクセス許可に関する設定
			//.authorizeRequests()
				//.antMatchers("/","/login_process","/top").permitAll() // 「/」「/login」は認証不要でアクセス可能。 //permitAll() → 全ユーザー対象
				//.and()
				//.authorizeRequests().mvcMatchers("/admin").hasAuthority("Role_ADMIN") //Role_ADMINユーザーのみ管理者画面にアクセスできる
				//.anyRequest().authenticated() //それ以外は全て認証無しの場合はアクセス不許可
				//.and() //「=http.formLogin()」
			//ログイン認証に関する設定
			//.formLogin()
			// ログアウトに関する設定
//			.logout()
//				.logoutUrl("/logout") //ログアウトする際のパス
//				.logoutSuccessUrl("/")	//ログアウト後に遷移するパス
//				.deleteCookies("JSESSIONID")// ログアウト後、Cookieに保存されているセッションIDを削除。　保存されるセッションID名:JSESSIONID
//				.invalidateHttpSession(true)// true:ログアウト後セッションを無効にする、false:セッションを無効にしない
//				.and()
//				.rememberMe() //Remember-Meを有効にする
//				.and()
//				.exceptionHandling()
//				.accessDeniedPage("/access-denied"); //不正アクセスのハンドリング
	}

	// 「認証に関する設定を行う」
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(auth_service);
	}
}