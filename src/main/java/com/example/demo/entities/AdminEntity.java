package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//アドミン
@Entity
@Table(name="m19_admin")
public class AdminEntity {
	
	@Id
	@Column(name="m19_admin_id")
	private Integer adminId;

	@Column(name="m19_max_reservation_date")
	private Integer maxReservationDate;
	
	@Column(name="m19_max_reservation")
	private Integer maxReservation;
	
	@Column(name="m19_cancel_wait_timeout")
	private Integer cancelWaitTimeout;
	
	@Column(name="m19_first_term")
	private Date firstTerm;
	
	@Column(name="m19_first_term_end")
	private Date firstTermEnd;
	
	@Column(name="m19_letter_term")
	private Date letterTerm;
	
	@Column(name="m19_letter_term_end")
	private Date letterTermEnd;
	
	@Column(name="m19_update_date")
	private Date updateDate;
	
	@Column(name="m19_popup_time")
	private Integer popupTime;

	public Integer getMaxReservationDate() {
		return maxReservationDate;
	}

	public void setMaxReservationDate(Integer maxReservationDate) {
		this.maxReservationDate = maxReservationDate;
	}

}
