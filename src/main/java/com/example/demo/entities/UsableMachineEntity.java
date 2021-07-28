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

//各クラスが使える機種
@Entity
@Table(name="m05_class_usable_machine")
public class UsableMachineEntity {
	
	//id(連番)
	@Id
	@Column(name="m05_usable_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int usableid;
	
	//クラス記号
	@ManyToOne
	@JoinColumn(name="m05_class_code")
	@JsonBackReference("Unit6")
	private ClassEntity classEntity;
	
	//そのクラスが使用できるマシン
	@ManyToOne
	@JoinColumn(name="m05_machine_code")
	@JsonBackReference("Unit")
	private MachineEntity machine;
	
	
	//ゲッター、セッター
	public void setUsableid(int usableid) {
		this.usableid = usableid;
	}

	public int getUsableid() {
		return usableid;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}
}