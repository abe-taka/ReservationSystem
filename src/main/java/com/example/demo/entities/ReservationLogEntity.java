package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//予約ログ
@Entity
@Table(name="t27_reservation_log")
public class ReservationLogEntity {
	
	// id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="t27_log_no")
	private int logNo;
	
	// 機種
	@Column(name="t27_machine_code")
	private String machineCode;
		
	// 座番
	@Column(name="t27_machine_no")
	private String machineNo;
		
	// 学生コード
	@Column(name="t27_student_code")
	private String studentCode;
		
	// 教官コード
	@Column(name="t27_teacher_code")
	private String teacherCode;
	
	// 利用年月日
	@Column(name="t27_use_ymd")
	private Date useYmd;
	
	// 利用時限
	@Column(name="t27_use_period")
	private String usePeriod;
		
	// 作業内容
	@Column(name="t27_task_data")
	private String taskData;
	
	// 作業日付
	@Column(name="t27_date")
	private Date date;

	
	public int getlogNo() {
		return logNo;
	}

	public void setlogNo(int logNo) {
		this.logNo = logNo;
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

	public Date getUseYmd() {
		return useYmd;
	}

	public void setUseYmd(Date useYmd) {
		this.useYmd = useYmd;
	}

	public String getUsePeriod() {
		return usePeriod;
	}

	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
