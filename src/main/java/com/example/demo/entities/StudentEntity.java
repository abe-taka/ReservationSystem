package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//学生
@Entity
@Table(name = "m01_student")
public class StudentEntity {

	// 学籍番号
	@Id
	@Column(name = "m01_student_code")
	private String studentcode;

	// パスワード(ローカル用) //本来のテーブルにはない
	@Column(name = "m01_student_password")
	private String studentpassword;

	// 学生の名前
	@Column(name = "m01_student_name")
	private String studentname;

	// アシススタントスタッフのフラグ
	@Column(name = "m01_student_flag")
	private int studentflag;

	// 管理用
	@Column(name = "m01_student_stat")
	private int studentstat;

	// t14テーブル(ReservationEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit7")
	private List<ReservationEntity> reservationEntity;

	
	// ゲッター、セッター
	public String getStudentcode() {
		return studentcode;
	}

	public void setStudentcode(String studentcode) {
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

	public int getStudentflag() {
		return studentflag;
	}

	public void setStudentflag(int studentflag) {
		this.studentflag = studentflag;
	}

	public int getStudentstat() {
		return studentstat;
	}

	public void setStudentstat(int studentstat) {
		this.studentstat = studentstat;
	}

	public List<ReservationEntity> getReservationEntity() {
		return reservationEntity;
	}

	public void setReservationEntity(List<ReservationEntity> reservationEntity) {
		this.reservationEntity = reservationEntity;
	}
}
