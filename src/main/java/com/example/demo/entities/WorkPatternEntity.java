package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// 稼働パターン
@Entity
@Table(name = "m11_work_pattern")
public class WorkPatternEntity {

	// パターンコード
	@Id
	@Column(name = "m11_work_pattern_code")
	private String workPatternCode;

	// パターン名
	@Column(name = "m11_work_pattern_name")
	private String workPatternName;
	
	// m12テーブル
	@OneToMany(mappedBy = "workPattern")
	private List<HourInWorkPatternEntity> hourInWorkPatternEntity;
	
	// m13テーブル
	@OneToMany(mappedBy = "workPattern")
	private List<CalendarEntity> calendarEntity;
	
}
