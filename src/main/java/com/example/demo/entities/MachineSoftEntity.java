package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.entities.composites.MachineSoftId;
import com.fasterxml.jackson.annotation.JsonBackReference;

//ソフトに対応している機種
@Entity
@IdClass(MachineSoftId.class)
@Table(name="m08_machine_soft")
public class MachineSoftEntity {
	
	//機種コード
	@Id
	@ManyToOne
	@JoinColumn(name="m08_machine_code")
	@JsonBackReference("Unit2")
	private MachineEntity machine;
	
	//ソフトコード
	@Id
	@ManyToOne
	@JoinColumn(name="m08_soft_code")
	@JsonBackReference("Unit3")
	private SoftEntity soft;
	
	//階数コード
	@ManyToOne
	@JoinColumn(name="m08_floor_code")
	@JsonBackReference("Unit4")
	private FloorEntity floor;
	

	//ゲッター、セッター
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

	public FloorEntity getFloor() {
		return floor;
	}

	public void setFloor(FloorEntity floor) {
		this.floor = floor;
	}

}