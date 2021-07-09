package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m07_soft")
public class SoftEntity {

	//ソフトコード
	@Id
	@Column(name="m07_soft_code")
	private int softcode;
	
	//ソフト名
	@Column(name="m07_soft_name")
	private int softname;
	
	//メーカー？
	@Column(name="m07_maker")
	private String maker;

	//ゲッター、セッター
	public int getSoftcode() {
		return softcode;
	}

	public void setSoftcode(int softcode) {
		this.softcode = softcode;
	}

	public int getSoftname() {
		return softname;
	}

	public void setSoftname(int softname) {
		this.softname = softname;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}
}