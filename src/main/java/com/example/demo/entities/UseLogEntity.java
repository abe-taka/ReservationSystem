package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//利用ログ
@Entity
@Table(name="t24_uselog")
public class UseLogEntity {

	// id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="t24_uselog_no")
	private int useLogNo;
	
	// 利用日時
	@Column(name="t24_date")
	private Date date;
	
	// 利用時限
	@Column(name="t24_hour_code")
	private String hourCode;
	
	// 利用機種
	@Column(name="t24_machine_code")
	private String machineCode;
		
	// 利用座番
	@Column(name="t24_machine_no")
	private String machineNo;

	
	public int getUseLogNo() {
		return useLogNo;
	}

	public void setUseLogNo(int useLogNo) {
		this.useLogNo = useLogNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHourCode() {
		return hourCode;
	}

	public void setHourCode(String hourCode) {
		this.hourCode = hourCode;
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

}
