package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.StudentEntity;

@Repository
public interface LoginRepository extends JpaRepository<StudentEntity,Integer>{

	//学籍番号、パスワード検索(ログイン認証)
	public StudentEntity findByStudentcodeAndStudentpassword(String s_code,String s_password);
}