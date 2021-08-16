package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



//稼働日程
@Entity
@Table(name="m13_calendar")
public class CalendarEntity {

	//稼働日程コード
	@Id
	@Column(name="m13_calendar_code")
	private String calendarCode;
		
	//日付
	@Column(name="m13_date")
	private Date date;
		
	//稼働コード
	@ManyToOne
	@JoinColumn(name="m13_pattern_code")
	private WorkPatternEntity workPattern;

	
	public String getCalendarCode() {
		return calendarCode;
	}

	public void setCalendarCode(String calendarCode) {
		this.calendarCode = calendarCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public WorkPatternEntity getWorkPattern() {
		return workPattern;
	}

	public void setWorkPattern(WorkPatternEntity workPattern) {
		this.workPattern = workPattern;
	}

}
