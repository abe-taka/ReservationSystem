package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.entities.composites.HourInWorkPatternId;
import com.fasterxml.jackson.annotation.JsonBackReference;

//　時限あたり稼働パターン
@Entity
@IdClass(HourInWorkPatternId.class)
@Table(name="m12_hour_in_work_pattern")
public class HourInWorkPatternEntity {
	
	//稼働パターンコード
	@Id
	@ManyToOne
	@JoinColumn(name="m12_work_pattern_code")
	@JsonBackReference("Unit2")
	private WorkPatternEntity workPattern;
		
	//時限コード
	@Id
	@ManyToOne
	@JoinColumn(name="m12_hour_code")
	@JsonBackReference("Unit3")
	private HourEntity hour;
		
	//稼働フラグ
	@Column(name="m12_work_flag")
	private String workFlag;


	public WorkPatternEntity getWorkPattern() {
		return workPattern;
	}

	public void setWorkPattern(WorkPatternEntity workPattern) {
		this.workPattern = workPattern;
	}

	public HourEntity getHour() {
		return hour;
	}

	public void setHour(HourEntity hour) {
		this.hour = hour;
	}

	public String getWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}

}
