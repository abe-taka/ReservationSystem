package com.example.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//機種
@Entity
@Table(name = "m06_machine")
public class MachineEntity {

	// 機種コード
	@Id
	@Column(name = "m06_machine_code")
	private String machinecode;

	// 機種名
	@Column(name = "m06_machine_name")
	private String machinename;

	// 台数
	@Column(name = "m06_count")
	private int count;

	// 階層
	@Column(name = "m06_floor")
	private int floor;

	// m04テーブル(UsableMachineEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit")
	private List<UsableMachineEntity> usableMachineEntity;

	// m08テーブル(MachineSoftEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit2")
	private List<MachineSoftEntity> machineSoftEntity;

	// t09テーブル(SeatStatusEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit7")
	private List<SeatStatusEntity> seatStatusEntity;

	// t14テーブル(ReservationEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit4")
	private List<ReservationEntity> reservationEntity;

	// t16テーブル(CancelWaitEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit4")
	private List<CancelWaitEntity> cancelWaitEntity;

	// t18テーブル(TroubleMachineEntity)
	@OneToMany(mappedBy = "machine")
	@JsonBackReference("Unit11")
	private List<TroubleMachineEntity> troubleMachineEntity;

	// ゲッター、セッター
	public String getMachinecode() {
		return machinecode;
	}

	public void setMachinecode(String machinecode) {
		this.machinecode = machinecode;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public List<UsableMachineEntity> getUsableMachineEntity() {
		return usableMachineEntity;
	}

	public void setUsableMachineEntity(List<UsableMachineEntity> usableMachineEntity) {
		this.usableMachineEntity = usableMachineEntity;
	}

	public List<MachineSoftEntity> getMachineSoftEntity() {
		return machineSoftEntity;
	}

	public void setMachineSoftEntity(List<MachineSoftEntity> machineSoftEntity) {
		this.machineSoftEntity = machineSoftEntity;
	}

	public List<SeatStatusEntity> getSeatStatusEntity() {
		return seatStatusEntity;
	}

	public void setSeatStatusEntity(List<SeatStatusEntity> seatStatusEntity) {
		this.seatStatusEntity = seatStatusEntity;
	}

	public List<CancelWaitEntity> getCancelWaitEntity() {
		return cancelWaitEntity;
	}

	public void setCancelWaitEntity(List<CancelWaitEntity> cancelWaitEntity) {
		this.cancelWaitEntity = cancelWaitEntity;
	}

	public List<ReservationEntity> getReservationEntity() {
		return reservationEntity;
	}

	public void setReservationEntity(List<ReservationEntity> reservationEntity) {
		this.reservationEntity = reservationEntity;
	}
}