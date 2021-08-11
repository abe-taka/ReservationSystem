package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//教師
@Table
@Entity(name = "m02_teacher")
public class TeacherEntity {

	// 教師番号
	@Id
	@Column(name = "m02_teacher_code")
	private String teachercode;

	// 先生の名前
	@Column(name = "m02_teacher_name")
	private String teachername;
	
	// t09テーブル(SeatStatusEntity)
	@OneToMany(mappedBy = "teacher")
	@JsonBackReference("Unit7")
	private List<SeatStatusEntity> seatStatusEntity;

	// t14テーブル(ReservationEntity)
	@OneToMany(mappedBy = "teacher")
	@JsonBackReference("Unit8")
	private List<ReservationEntity> reservationEntity;
	

	// ゲッター、セッター
	public String getTeachercode() {
		return teachercode;
	}

	public void setTeachercode(String teachercode) {
		this.teachercode = teachercode;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	
	public List<SeatStatusEntity> getSeatStatusEntity() {
		return seatStatusEntity;
	}

	public void setSeatStatusEntity(List<SeatStatusEntity> seatStatusEntity) {
		this.seatStatusEntity = seatStatusEntity;
	}

	public List<ReservationEntity> getReservationEntity() {
		return reservationEntity;
	}

	public void setReservationEntity(List<ReservationEntity> reservationEntity) {
		this.reservationEntity = reservationEntity;
	}
}