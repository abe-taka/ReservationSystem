package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.SeatStatusEntity;


public interface SeatStatusCustomRepository<T> {
	
	List<SeatStatusEntity> findIfAlreadyReserved(Date date, String hour, String studentcode);

	List<SeatStatusEntity> findIfAlreadyTentativelyReserved(Date date, String hour, String studentcode);
	
	List<SeatStatusEntity> getReservations(Date date, String hour, String studentcode);
	
	List<SeatStatusEntity> getReservationForStart(Date date, String hour, String studentcode, String checkinFlag);
	
	SeatStatusEntity getReservationByNumber(Date date, String hour, String studentcode, int reservationNumber);
	
	boolean checkIfSeatIsUsingByMyself(Date date, String hour, String machineCode, String machineNumber, String studentcode);
	
	boolean checkIfSeatIsInStateByStatusCode(Date date, String hour, String machineCode, String machineNumber, String checkinFlag);
	
	public int countReservedMachine(Date date, String hour, String machinecode);
	
}
