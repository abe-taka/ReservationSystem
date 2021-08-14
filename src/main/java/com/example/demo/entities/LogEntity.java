package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// 元プログラム -> 先生・学生テーブルとの関係無し
// ログ
@Entity
@Table(name="t20_log")
public class LogEntity {

	// id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="t20_log_no")
	private int logNo;

	// 日時
	@Column(name="t20_date")
	private Date date;
	
	// 教職員コード
	@Column(name="t20_teacher_code")
	private String teacherCode;
	
	// ターゲット教職員コード
	@Column(name="t20_target_teacher_code")
	private String targetTeacherCode;
	
	// 学生コード
	@Column(name="t20_student_code")
	private String studentCode;
	
	// ログ内容
	@Column(name="t20_task_data")
	private String taskData;

	
	public int getLogNo() {
		return logNo;
	}

	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getTargetTeacherCode() {
		return targetTeacherCode;
	}

	public void setTargetTeacherCode(String targetTeacherCode) {
		this.targetTeacherCode = targetTeacherCode;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	
}
