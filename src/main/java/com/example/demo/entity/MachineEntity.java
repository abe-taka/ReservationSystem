package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dbo_m06_machine")
public class MachineEntity {

	//機種コード
	@Id
	@Column(name="m06_machine_code")
	private String machinecode;
	
	//機種名
	@Column(name="m06_machine_name")
	private String machinename;
	
	//台数
	@Column(name="m06_count")
	private int count;

	//ゲッター、セッター
	public String getMachinecode() {
		return machinecode;
	}

	public void setMachinecode(String machinecode) {
		this.machinecode = machinecode;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}