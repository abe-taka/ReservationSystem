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

//　時限あたり稼働パターン
@Entity
@Table(name="m12_hour_in_work_pattern")
public class HourInWorkPatternEntity {

	//id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="m12_hour_in_work_pattern_id")
	private int hourInWorkPatternId;
	
	//稼働パターンコード
	@ManyToOne
	@JoinColumn(name="m12_work_pattern_code")
	@JsonBackReference("Unit2")
	private WorkPatternEntity workPattern;
		
	//時限コード
	@ManyToOne
	@JoinColumn(name="m12_hour_code")
	@JsonBackReference("Unit3")
	private HourEntity hour;
		
	//稼働フラグ
	@Column(name="m12_work_flag")
	private String workFlag;

	
	public int getHourInWorkPatternId() {
		return hourInWorkPatternId;
	}

	public void setHourInWorkPatternId(int hourInWorkPatternId) {
		this.hourInWorkPatternId = hourInWorkPatternId;
	}

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
