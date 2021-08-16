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

//マシン解放待ち
@Entity
@Table(name="t16_cancel_wait")
public class CancelWaitEntity {
	
	//id(連番)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="t16_cancel_wait_id")
	private int cancelWaitId;

	// 座席状態ID(FK)
	@ManyToOne
	@JoinColumn(name="t16_student_code")
	@JsonBackReference("Unit2")
	private StudentEntity student;
	
	// 機種コード(FK)
	@ManyToOne
	@JoinColumn(name="t16_machine_code")
	@JsonBackReference("Unit2")
	private MachineEntity machine;
	
	// 座席状態ID(FK)
	@ManyToOne
	@JoinColumn(name="t16_number")
	@JsonBackReference("Unit2")
	private SeatStatusEntity seatStatus;
	
	// 更新時間
	@Column(name="t16_update_date")
	private Date updateDate;

	
	public int getCancelWaitId() {
		return cancelWaitId;
	}

	public void setCancelWaitId(int cancelWaitId) {
		this.cancelWaitId = cancelWaitId;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}

	public SeatStatusEntity getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatusEntity seatStatus) {
		this.seatStatus = seatStatus;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
