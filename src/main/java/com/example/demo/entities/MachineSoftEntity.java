package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m8_machine_soft")
public class MachineSoftEntity {

	//機種コード
	@Id
	@Column(name="m08_machine_code")
	private String machinecode;
	
	//ソフトコード
	@Column(name="m08_soft_code")
	private String softcode;

	//ゲッター、セッター
	public String getMachinecode() {
		return machinecode;
	}

	public void setMachinecode(String machinecode) {
		this.machinecode = machinecode;
	}

	public String getSoftcode() {
		return softcode;
	}

	public void setSoftcode(String softcode) {
		this.softcode = softcode;
	}
}