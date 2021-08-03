package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.SeatStatusEntity;

public interface SeatStatusCustomRepository<T> {
	
	List<SeatStatusEntity> findIfAlreadyReserved(Date date, String hour, String studentcode);

	List<SeatStatusEntity> findIfAlreadyTentativelyReserved(Date date, String hour, String studentcode);
}
