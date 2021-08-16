package com.example.demo.entities.composites;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.JoinColumn;


@SuppressWarnings("serial")
public class MachineSoftId implements Serializable {
	
	@JoinColumn(name="m08_machine_code")
	private String machine;
	
	@JoinColumn(name="m08_soft_code")
	private String soft;
	
	
	public MachineSoftId() {
		
	}
	
    public MachineSoftId(String machine, String soft) {
        this.machine = machine;
        this.soft = soft;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	MachineSoftId machineSoftId = (MachineSoftId) o;
    	return machine.equals(machineSoftId.machine) && soft.equals(machineSoftId.soft);
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(machine, soft);
    }
}
