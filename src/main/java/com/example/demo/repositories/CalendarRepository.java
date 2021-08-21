package com.example.demo.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.CalendarEntity;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity,String>{

	//日付検索
	@Query(value="SELECT * FROM m13_calendar WHERE m13_date= :date",nativeQuery=true)
	public CalendarEntity findByDate(String date);
}
