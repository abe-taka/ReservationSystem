package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//時限
@Entity
@Table(name="m10_hour")
public class HourEntity {

	//時限コード
	@Id
	@Column(name="m10_hour_code")
	private String hourCode;
		
	//時限名
	@Column(name="m10_hour_name")
	private String hourName;
	
	//チェックインスタート時間
	@Column(name="m10_checkin_start_time")
	private Date checkinStartTime;
		
	//時限スタート時間
	@Column(name="m10_hour_start_time")
	private Date hourStartTime;
			
	//チェックインリミット時間
	@Column(name="m10_checkin_limit_time")
	private Date checkinLimitTime;
	
	//継続時間？
	@Column(name="m10_continue_time")
	private Date continueTime;
		
	//時限終了時間
	@Column(name="m10_hour_end_time")
	private Date hourEndTime;
			
	//チェックアウト制限時間
	@Column(name="m10_checkout_limit_time")
	private Date checkoutLimitTime;
	
	// m12テーブル
	@OneToMany(mappedBy = "hour")
	private List<HourInWorkPatternEntity> hourInWorkPatternEntity;

	
	public String getHourCode() {
		return hourCode;
	}

	public void setHourCode(String hourCode) {
		this.hourCode = hourCode;
	}

	public String getHourName() {
		return hourName;
	}

	public void setHourName(String hourName) {
		this.hourName = hourName;
	}

	public Date getCheckinStartTime() {
		return checkinStartTime;
	}

	public void setCheckinStartTime(Date checkinStartTime) {
		this.checkinStartTime = checkinStartTime;
	}

	public Date getHourStartTime() {
		return hourStartTime;
	}

	public void setHourStartTime(Date hourStartTime) {
		this.hourStartTime = hourStartTime;
	}

	public Date getCheckinLimitTime() {
		return checkinLimitTime;
	}

	public void setCheckinLimitTime(Date checkinLimitTime) {
		this.checkinLimitTime = checkinLimitTime;
	}

	public Date getContinueTime() {
		return continueTime;
	}

	public void setContinueTime(Date continueTime) {
		this.continueTime = continueTime;
	}

	public Date getHourEndTime() {
		return hourEndTime;
	}

	public void setHourEndTime(Date hourEndTime) {
		this.hourEndTime = hourEndTime;
	}

	public Date getCheckoutLimitTime() {
		return checkoutLimitTime;
	}

	public void setCheckoutLimitTime(Date checkoutLimitTime) {
		this.checkoutLimitTime = checkoutLimitTime;
	}

	public List<HourInWorkPatternEntity> getHourInWorkPatternEntity() {
		return hourInWorkPatternEntity;
	}

	public void setHourInWorkPatternEntity(List<HourInWorkPatternEntity> hourInWorkPatternEntity) {
		this.hourInWorkPatternEntity = hourInWorkPatternEntity;
	}
	
}
