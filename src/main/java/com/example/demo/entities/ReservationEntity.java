package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t14_reservation")
public class ReservationEntity {

	// 予約id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "t14_reservation_no")
	private int reservationnum;

	// 学籍番号
	@ManyToOne
	@JoinColumn(name = "t14_student_code")
	private StudentEntity student;

	// 教師番号
	@ManyToOne
	@JoinColumn(name = "t14_teacher_code")
	private TeacherEntity teacher;

	// 利用開始日
	@Column(name = "t14_reservation_start_date")
	private String reservationstartdate;

	// 利用終了日
	@Column(name = "t14_reservation_end_date")
	private String reservationend;

	// コマ
	@Column(name = "t14_period_code")
	private String periodcode;

	// 機種コード
	@ManyToOne
	@JoinColumn(name = "t14_machine_code")
	private MachineEntity machine;

	// 予約日
	@Column(name = "t14_reservation_date")
	private String reservationdate;

	// 更新日
	@Column(name = "t14_update_date")
	private String updatedade;

	// @Column(name = "day_code")
	// private int daycode;

	// @Column(name = "week_code")
	// private int weekcode;
	

	// ゲッター、セッター
	public int getReservationnum() {
		return reservationnum;
	}

	public void setReservationnum(int reservationnum) {
		this.reservationnum = reservationnum;
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

	public String getReservationstartdate() {
		return reservationstartdate;
	}

	public void setReservationstartdate(String reservationstartdate) {
		this.reservationstartdate = reservationstartdate;
	}

	public String getReservationend() {
		return reservationend;
	}

	public void setReservationend(String reservationend) {
		this.reservationend = reservationend;
	}

	public String getPeriodcode() {
		return periodcode;
	}

	public void setPeriodcode(String periodcode) {
		this.periodcode = periodcode;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}

	public String getReservationdate() {
		return reservationdate;
	}

	public void setReservationdate(String reservationdate) {
		this.reservationdate = reservationdate;
	}

	public String getUpdatedade() {
		return updatedade;
	}

	public void setUpdatedade(String updatedade) {
		this.updatedade = updatedade;
	}
}