package com.example.demo.entities.composites;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
public class HourInWorkPatternId implements Serializable {

	@JoinColumn(name="m12_work_pattern_code")
	private String workPattern;
	
	@JoinColumn(name="m12_hour_code")
	private String hour;
	
	public HourInWorkPatternId() {
		
	}
	
    public HourInWorkPatternId(String workPattern, String hour) {
        this.workPattern = workPattern;
        this.hour = hour;
    }
	
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	HourInWorkPatternId hourInWorkPatternId = (HourInWorkPatternId) o;
    	return workPattern.equals(hourInWorkPatternId.workPattern) && hour.equals(hourInWorkPatternId.hour);
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(workPattern, hour);
    }
}
