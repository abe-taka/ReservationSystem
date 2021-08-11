package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@ManyToOne
	@JoinColumn(name="t09_checkin_hour")
	@JsonBackReference("Unit2")
	private HourEntity hour;
	
	//
	@ManyToOne
	@JoinColumn(name="t09_machine_code")
	@JsonBackReference("Unit2")
	private MachineEntity machine;
	
	//
	@Column(name="t09_machine_no")
	private String machineNo;
	
	//
	@ManyToOne
	@JoinColumn(name="t09_reservation_no")
	@JsonBackReference("Unit2")
	private ReservationEntity reservation;
	
	//
	@ManyToOne
	@JoinColumn(name="t09_student_code")
	@JsonBackReference("Unit2")
	private StudentEntity student;
	
	//
	@ManyToOne
	@JoinColumn(name="t09_teacher_code")
	@JsonBackReference("Unit2")
	private TeacherEntity teacher;
	
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
	
	//t16テーブル(CancelWaitEntity)
	@OneToMany(mappedBy = "student")
	@JsonBackReference("Unit4")
	private List<CancelWaitEntity> cancelWaitEntity;
	
	
	public SeatStatusEntity() {
	}
	
	public SeatStatusEntity(Date date, HourEntity hour, MachineEntity machine, Integer machineCount, StudentEntity student, String checkinFlag, Date updateDate) {
		this.date = date;
        this.hour = hour;
        this.machine = machine;
        this.machineCount = machineCount;
        this.student = student;
        this.checkinFlag = checkinFlag;
        this.updateDate = updateDate;
	}

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

	public HourEntity getHour() {
		return hour;
	}

	public void setHour(HourEntity hour) {
		this.hour = hour;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public ReservationEntity getReservation() {
		return reservation;
	}

	public void setReservation(ReservationEntity reservation) {
		this.reservation = reservation;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
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

	public List<CancelWaitEntity> getCancelWaitEntity() {
		return cancelWaitEntity;
	}

	public void setCancelWaitEntity(List<CancelWaitEntity> cancelWaitEntity) {
		this.cancelWaitEntity = cancelWaitEntity;
	}
}
