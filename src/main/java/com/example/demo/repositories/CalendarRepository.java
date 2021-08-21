package com.example.demo.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.CalendarEntity;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity,Date>{

	//日付検索
	public CalendarEntity findByDate(Date date);
}
