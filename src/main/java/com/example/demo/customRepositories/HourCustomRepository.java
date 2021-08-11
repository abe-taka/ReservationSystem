package com.example.demo.customRepositories;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.HourEntity;

public interface HourCustomRepository<T> {
	
	List<HourEntity> getHourCodeBetweenCheckinStartAndCheckoutLimit(@Param("date") String date);
	
	List<HourEntity> checkIfCurrentTimeIsBetweenStartAndLimit(@Param("date") String date, @Param("targetHour") String targetHour);

}
