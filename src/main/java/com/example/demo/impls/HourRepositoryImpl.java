package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.HourCustomRepository;
import com.example.demo.entities.HourEntity;

@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class HourRepositoryImpl implements HourCustomRepository {

	@Autowired
	EntityManager entityManager;

	// 現在の時間を基に現在の時限コードを取得
	@SuppressWarnings("unchecked")
	@Override
	public List<HourEntity> getHourCodeBetweenCheckinStartAndCheckoutLimit(@Param("date") String date) {     
		String jpql = "SELECT * FROM m10_hour WHERE m10_checkin_start_time <= :date AND m10_checkout_limit_time >= :date"; 
		
		TypedQuery<HourEntity> query = (TypedQuery<HourEntity>) entityManager.createNativeQuery(jpql, HourEntity.class);
		query.setParameter("date", date);
      
		return query.getResultList();
	}
	
	// 現在の時間を基に利用開始できるかをチェック
	@SuppressWarnings("unchecked")
	@Override
	public List<HourEntity> checkIfCurrentTimeIsBetweenStartAndLimit(@Param("date") String date, @Param("targetHour") String targetHour) {     
		String jpql = "SELECT * FROM m10_hour WHERE m10_checkin_start_time <= :date AND m10_checkin_limit_time >= :date AND m10_hour_code = :targetHour"; 
		
		TypedQuery<HourEntity> query = (TypedQuery<HourEntity>) entityManager.createNativeQuery(jpql, HourEntity.class);
		query.setParameter("date", date);
		query.setParameter("targetHour", targetHour);
		
		return query.getResultList();
	}
	
}
