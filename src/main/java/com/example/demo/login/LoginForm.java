package com.example.demo.login;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class LoginForm {

	// 学籍番号
	private int studentcode;

	// パスワード(ローカル用)
	private String studentpassword;

	// 名前
	private String studentname;

	// ボランティア委員かのフラグ
	private String studentflag;

	// ？
	private String studentstat;

	// ゲッター、セッター
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
