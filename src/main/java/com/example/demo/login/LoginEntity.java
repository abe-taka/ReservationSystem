package com.example.demo.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m01_student")
public class LoginEntity {

	//学籍番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "m01_student_code")
	private int studentcode;
	
	//パスワード(ローカル用)
	@Column(name = "m01_student_password")
	private String studentpassword;
	
	//名前
	@Column(name = "m01_student_name")
	private String studentname;
	
	//ボランティア委員かのフラグ
	@Column(name = "m01_student_flag")
	private String studentflag;
	
	//？
	@Column(name = "m01_student_stat")
	private String studentstat;


	//ゲッター、セッター
	public int getStudentcode() {
		return studentcode;
	}

	public void setStudentcode(int studentcode) {
		this.studentcode = studentcode;
	}

	public String getStudentpassword() {
		return studentpassword;
	}

	public void setStudentpassword(String studentpassword) {
		this.studentpassword = studentpassword;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getStudentflag() {
		return studentflag;
	}

	public void setStudentflag(String studentflag) {
		this.studentflag = studentflag;
	}

	public String getStudentstat() {
		return studentstat;
	}

	public void setStudentstat(String studentstat) {
		this.studentstat = studentstat;
	}
	
}
