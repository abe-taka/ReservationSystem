package com.example.demo.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StudentEntity;

@Repository
public interface LoginRepository extends JpaRepository<StudentEntity,Integer>{

	//学籍番号、パスワード検索
	public StudentEntity findByStudentcodeAndStudentpassword(String s_code,String s_password);
	
	//学籍番号検索
	public StudentEntity findByStudentcode(String s_code);
}