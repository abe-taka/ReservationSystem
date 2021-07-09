package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m05_class_usable_machine")
public class UsableMachineEntity {
	
	//クラス記号
	@Id
	@Column(name="m05_class_code")
	private String classcode;
	
	//そのクラスが使用できるマシン名
	@Column(name="m05_machine_code")
	private String machinecode;

	//ゲッター、セッター
	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getMachinecode() {
		return machinecode;
	}

	public void setMachinecode(String machinecode) {
		this.machinecode = machinecode;
	}	
}