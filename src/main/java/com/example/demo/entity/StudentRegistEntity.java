package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dbo_m04_student_regist")
public class StudentRegistEntity {

	//学籍番号
	@Id
	@Column(name = "m04_student_code")
	private String studentcode;
	
	//クラス記号
	@Column(name="m04_class_code")
	private String classcode;
	
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

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public int getClassnum() {
		return classnum;
	}

	public void setClassnum(int classnum) {
		this.classnum = classnum;
	}
	
}