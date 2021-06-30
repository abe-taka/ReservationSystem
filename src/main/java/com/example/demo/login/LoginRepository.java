package com.example.demo.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity,Integer>{

	//学籍番号、パスワード検索
	public LoginEntity findByStudentcodeAndStudentpassword(int s_code,String s_password);
}