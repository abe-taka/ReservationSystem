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

	// t09テーブル(SeatStatusEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit7")
	private List<SeatStatusEntity> seatStatusEntity;

	// t14テーブル(ReservationEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit7")
	private List<ReservationEntity> reservationEntity;

	// t16テーブル(CancelWaitEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit4")
	private List<CancelWaitEntity> cancelWaitEntity;

	// t18テーブル(TroubleMachineEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit9")
	private List<TroubleMachineEntity> TroubleMachineEntity;


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

	public List<SeatStatusEntity> getSeatStatusEntity() {
		return seatStatusEntity;
	}

	public void setSeatStatusEntity(List<SeatStatusEntity> seatStatusEntity) {
		this.seatStatusEntity = seatStatusEntity;
	}

	public List<CancelWaitEntity> getCancelWaitEntity() {
		return cancelWaitEntity;
	}

	public void setCancelWaitEntity(List<CancelWaitEntity> cancelWaitEntity) {
		this.cancelWaitEntity = cancelWaitEntity;
	}
}
