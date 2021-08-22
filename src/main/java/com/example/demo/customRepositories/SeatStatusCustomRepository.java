package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.SeatStatusEntity;


public interface SeatStatusCustomRepository<T> {
	
	List<SeatStatusEntity> findIfAlreadyReserved(Date date, String hour, String studentcode);

	List<SeatStatusEntity> findIfAlreadyTentativelyReserved(Date date, String hour, String studentcode);
	
	List<SeatStatusEntity> getReservations(Date date, String hour, String studentcode);
	
	List<SeatStatusEntity> getReservationByFlag(Date date, String hour, String studentcode, String checkinFlag);
	
	SeatStatusEntity getReservationByNumber(Date date, String hour, String studentcode, int reservationNumber);
	
	boolean checkIfSeatIsUsingByMyself(Date date, String hour, String machineCode, String machineNumber, String studentcode);
	
	boolean checkIfSeatIsInStateByStatusCode(Date date, String hour, String machineCode, String machineNumber, String checkinFlag);
	
	boolean checkIfUserAlreadyReserved(String date, String hour, String studentcode, String checkinFlag, String checkinFlag2);

	boolean checkIfSeatIsReservedByMyself(String date, String hour, String machineCode, String machineNumber, String studentCode, String checkinFlag,String checkinFlag2);
	
	boolean checkIfSeatIsReservedByOthers(String date, String hour, String machineCode, String machineNumber, String studentCode, String checkinFlag,String checkinFlag2);
	
	public int countReservedMachine(Date date, String hour, String machinecode);
	
	public int countReuseReservedMachine(String date, String hour, String machinecode);
	
	List<SeatStatusEntity> getReservationForReusing(String studentcode, String checkinFlag, String hour, Date date);
	
	SeatStatusEntity getReservationByMachinecode(String machinecode, String studentcode, String checkinFlag, String hour);
}
