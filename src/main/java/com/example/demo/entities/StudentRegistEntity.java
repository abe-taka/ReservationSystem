package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//所属クラスと出席番号
@Entity
@Table(name="m04_student_regist")
public class StudentRegistEntity {

	//学籍番号
	@Id
	@Column(name = "m04_student_code")
	private String studentcode;
	
	//クラス記号
	@ManyToOne
	@JoinColumn(name="m04_class_code")
	@JsonBackReference("Unit5")
	private ClassEntity classEntity;
	
	//出席番号
	@Column(name="m04_class_no")
	private int classnum;

	
	//ゲッター、セッター
	public String getStudentcode() {
		return studentcode;
	}

	public void setStudentcode(String studentcode) {
		this.studentcode = studentcode;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public int getClassnum() {
		return classnum;
	}

	public void setClassnum(int classnum) {
		this.classnum = classnum;
	}
	
}