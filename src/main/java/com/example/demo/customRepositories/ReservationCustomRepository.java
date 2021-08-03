package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.ReservationEntity;

public interface ReservationCustomRepository<T> {
		
	String addReservation(Date date, String hour, String machinecode, String studentcode);

}
