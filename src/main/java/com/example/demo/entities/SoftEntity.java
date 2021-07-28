package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//ソフト
@Entity
@Table(name = "m07_soft")
public class SoftEntity {

	// ソフトコード
	@Id
	@Column(name = "m07_soft_code")
	private String softcode;

	// ソフト名
	@Column(name = "m07_soft_name")
	private String softname;

	// ？
	@Column(name = "m07_maker")
	private String maker;

	// m08テーブル
	@OneToMany(mappedBy = "soft")
	@JsonBackReference("Unit3")
	private List<MachineSoftEntity> machineSoftEntity;
	

	// ゲッター、セッター
	public String getSoftcode() {
		return softcode;
	}

	public void setSoftcode(String softcode) {
		this.softcode = softcode;
	}

	public String getSoftname() {
		return softname;
	}

	public void setSoftname(String softname) {
		this.softname = softname;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public List<MachineSoftEntity> getMachineSoftEntity() {
		return machineSoftEntity;
	}

	public void setMachineSoftEntity(List<MachineSoftEntity> machineSoftEntity) {
		this.machineSoftEntity = machineSoftEntity;
	}
	
}