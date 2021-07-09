package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m03_class")
public class ClassEntity {

	//クラス記号
	@Id
	@Column(name="m03_class_code")
	private String classcode;
	
	//？
	@Column(name="m03_class_type")
	private String classtype;

	//ゲッター、セッター
	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}
}