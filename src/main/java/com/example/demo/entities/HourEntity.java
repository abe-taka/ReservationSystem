package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//時限
@Entity
@Table(name="m10_hour")
public class HourEntity {

	//時限コード
	@Id
	@Column(name="m10_hour_code")
	private String hourcode;
		
	//時限名
	@Column(name="m10_hour_name")
	private String hourname;
	
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
	
	public String getHourname() {
		return hourname;
	}
}
