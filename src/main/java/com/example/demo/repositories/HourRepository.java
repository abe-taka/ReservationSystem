package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.HourCustomRepository;
import com.example.demo.entities.HourEntity;

public abstract interface HourRepository extends JpaRepository<HourEntity, String>, HourCustomRepository<HourEntity> {
	
	public HourEntity findByHourCode(String hour);
	
	public HourEntity findFirstByOrderByHourEndTimeDesc();

}
