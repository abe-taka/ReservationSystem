package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//ソフトに対応している機種
@Entity
@Table(name="m08_machine_soft")
public class MachineSoftEntity {

	//id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="m08_machine_soft_id")
	private int machinesoftid;
	
	//機種コード
	@ManyToOne
	@JoinColumn(name="m08_machine_code")
	@JsonBackReference("Unit2")
	private MachineEntity machine;
	
	//ソフトコード
	@ManyToOne
	@JoinColumn(name="m08_soft_code")
	@JsonBackReference("Unit3")
	private SoftEntity soft;
	

	//ゲッター、セッター
	public int getMachinesoftid() {
		return machinesoftid;
	}

	public void setMachinesoftid(int machinesoftid) {
		this.machinesoftid = machinesoftid;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}

	public SoftEntity getSoft() {
		return soft;
	}

	public void setSoft(SoftEntity soft) {
		this.soft = soft;
	}

}