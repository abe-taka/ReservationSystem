package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



//稼働日程
@Entity
@Table(name="m13_calendar")
public class CalendarEntity {
<<<<<<< HEAD

	//稼働日程コード
	@Id
	@Column(name="m13_calendar_code")
	private String calendarCode;
=======
>>>>>>> a7deea17c725e708ca7ac00fd16ca57b35bdc164
		
	//日付
	@Id
	@Column(name="m13_date")
	private Date date;
		
	//稼働コード
	@ManyToOne
	@JoinColumn(name="m13_pattern_code")
	private WorkPatternEntity workPattern;

	
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
