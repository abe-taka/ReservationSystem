package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.entities.composites.ClassUsableMachineId;
import com.fasterxml.jackson.annotation.JsonBackReference;

//各クラスが使える機種
@Entity
@IdClass(ClassUsableMachineId.class)
@Table(name="m05_class_usable_machine")
public class UsableMachineEntity {
		
	//クラス記号
	@Id
	@ManyToOne
	@JoinColumn(name="m05_class_code")
	@JsonBackReference("Unit6")
	private ClassEntity classEntity;
	
	//そのクラスが使用できるマシン
	@Id
	@ManyToOne
	@JoinColumn(name="m05_machine_code")
	@JsonBackReference("Unit")
	private MachineEntity machine;
	

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