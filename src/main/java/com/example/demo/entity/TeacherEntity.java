package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name="m02_teacher")
public class TeacherEntity {

	//教師番号
	@Id
	@Column(name="m02_teacher_code")
	private String teachercode;

	//先生の名前
	@Column(name="m02_teacher_name")
	private String teachername;

	//ゲッター、セッター
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
}