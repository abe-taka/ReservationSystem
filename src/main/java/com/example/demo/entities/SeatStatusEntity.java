package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

//座席状態
@Component
@Entity
@Table(name="t09_seat_status")
public class SeatStatusEntity {

	//座席状態コード
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="t09_number")
	private Integer number;
			
	//
	@Column(name="t09_date")
	private Date date;
	
	//
	@Column(name="t09_checkin_hour")
	private String checkinHour;
	
	//
	@Column(name="t09_machine_code")
	private String machineCode;
	
	//
	@Column(name="t09_machine_no")
	private String machineNo;
	
	//
	@Column(name="t09_reservation_no")
	private Integer reservationNo;
	
	//
	@Column(name="t09_student_code")
	private String studentCode;
	
	//
	@Column(name="t09_teacher_code")
	private String teacherCode;
	
	//
	@Column(name="t09_checkin_flag")
	private String checkinFlag;
	
	//
	@Column(name="t09_popup_flag")
	private String popupFlag;
	
	//
	@Column(name="t09_update_date")
	private Date updateDate;
	
	//
	@Column(name="t09_machine_count")
	private Integer machineCount;


	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCheckinHour() {
		return checkinHour;
	}

	public void setCheckinHour(String checkinHour) {
		this.checkinHour = checkinHour;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public Integer getReservationNo() {
		return reservationNo;
	}

	public void setReservationNo(Integer reservationNo) {
		this.reservationNo = reservationNo;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getCheckinFlag() {
		return checkinFlag;
	}

	public void setCheckinFlag(String checkinFlag) {
		this.checkinFlag = checkinFlag;
	}

	public String getPopupFlag() {
		return popupFlag;
	}

	public void setPopupFlag(String popupFlag) {
		this.popupFlag = popupFlag;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}
	
}
