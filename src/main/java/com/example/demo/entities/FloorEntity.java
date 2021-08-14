package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//階数
@Entity
@Table(name="v01_floor")
public class FloorEntity {
	
	//階数コード
	@Id
	@Column(name="v01_floor_code")
	private String floorCode;
	
	//m06テーブル(MachineEntity)
	@OneToMany(mappedBy = "floor")
	@JsonBackReference("Unit")
	private List<MachineEntity> machineEntity;
	
	//m08テーブル(MachineSoftEntity)
	@OneToMany(mappedBy = "floor")
	@JsonBackReference("Unit")
	private List<MachineSoftEntity> machineSoftEntity;
	

	public String getFloorCode() {
		return floorCode;
	}

	public void setFloorCode(String floorCode) {
		this.floorCode = floorCode;
	}

	public List<MachineEntity> getMachineEntity() {
		return machineEntity;
	}

	public void setMachineEntity(List<MachineEntity> machineEntity) {
		this.machineEntity = machineEntity;
	}

	public List<MachineSoftEntity> getMachineSoftEntity() {
		return machineSoftEntity;
	}

	public void setMachineSoftEntity(List<MachineSoftEntity> machineSoftEntity) {
		this.machineSoftEntity = machineSoftEntity;
	}
	
}
