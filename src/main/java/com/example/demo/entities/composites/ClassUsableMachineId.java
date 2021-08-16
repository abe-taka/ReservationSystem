package com.example.demo.entities.composites;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class ClassUsableMachineId implements Serializable {

	@Column(name="m05_class_code")
	private String classEntity;
	
	@Column(name="m05_machine_code")
	private String machine;
	
	
	public ClassUsableMachineId() {
		
	}
	
    public ClassUsableMachineId(String classEntity, String machine) {
        this.classEntity = classEntity;
        this.machine = machine;
    }
	
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	ClassUsableMachineId classUsableMachineId = (ClassUsableMachineId) o;
    	return classEntity.equals(classUsableMachineId.classEntity) && machine.equals(classUsableMachineId.machine);
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(classEntity, machine);
    }
}
