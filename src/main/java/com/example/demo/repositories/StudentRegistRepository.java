package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.StudentRegistEntity;

@Repository
public interface StudentRegistRepository extends JpaRepository<StudentRegistEntity,String>{

	//学籍番号検索
	public StudentRegistEntity findByStudentcode(String studentcode);
}