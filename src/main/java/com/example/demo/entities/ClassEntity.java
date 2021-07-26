package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="m03_class")
public class ClassEntity {

	//クラス記号
	@Id
	@Column(name="m03_class_code")
	private String classcode;
	
	//？
	@Column(name="m03_class_type")
	private String classtype;
	
	//m04(StudentRegistEntity)
	@OneToMany(mappedBy="classEntity")
	private List<StudentRegistEntity> studentregist;
	
	//m05(UsableMachineEntity)
	@OneToMany(mappedBy="classEntity")
	private List<UsableMachineEntity> usableMachineEntity;

	
	//ゲッター、セッター
	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public List<StudentRegistEntity> getStudentregist() {
		return studentregist;
	}

	public void setStudentregist(List<StudentRegistEntity> studentregist) {
		this.studentregist = studentregist;
	}

	public List<UsableMachineEntity> getUsableMachineEntity() {
		return usableMachineEntity;
	}

	public void setUsableMachineEntity(List<UsableMachineEntity> usableMachineEntity) {
		this.usableMachineEntity = usableMachineEntity;
	}
}