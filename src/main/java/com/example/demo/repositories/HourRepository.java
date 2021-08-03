package com.example.demo.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.HourEntity;

@Repository
public interface HourRepository extends JpaRepository<HourEntity, String> {
	
	//すべての時限開始時刻を取得
	//@Query(name = "SELECT m10_hour_start_time FROM m10_hour", nativeQuery = true)
	//public Date findHourStartTime();
	
	// 現在の時間を基に現在の時限コードを取得
	// 作業中
	@Query(value = "SELECT m10_hour_code FROM m10_hour WHERE m10_checkin_start_time <= :date AND m10_checkout_limit_time >= :date", nativeQuery = true)
	public HourEntity findHourCode(@Param("date") String date);
}
