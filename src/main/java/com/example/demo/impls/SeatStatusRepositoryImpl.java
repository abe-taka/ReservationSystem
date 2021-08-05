package com.example.demo.impls;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.SeatStatusCustomRepository;
import com.example.demo.entities.SeatStatusEntity;


@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class SeatStatusRepositoryImpl implements SeatStatusCustomRepository {

	@Autowired
	EntityManager entityManager;

	// 予約確定かを調査する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> findIfAlreadyReserved(Date date, String hour, String studentcode) {     
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_student_code = :studentcode AND t09_checkin_flag='1'"; 
		
		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql, SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
      
		return query.getResultList();
	}
	
	// 仮予約かを調査する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> findIfAlreadyTentativelyReserved(Date date, String hour, String studentcode) {     
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_student_code = :studentcode AND t09_checkin_flag='0'"; 
	      
		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql, SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
	      
		return query.getResultList();
	}
	
	// 予約リストを取得する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> getReservations(Date date, String hour, String studentcode) {     
		String jpql = "SELECT * FROM t09_seat_status WHERE ((t09_date = :date AND t09_checkin_hour >= :hour) OR (t09_date > :date)) AND t09_student_code = :studentcode AND t09_checkin_flag = '1' ORDER BY t09_date ASC, t09_checkin_hour ASC;"; 
		      
		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql, SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
		      
		return query.getResultList();
	}
	
	// 予約IDを基に予約を取得する
	@SuppressWarnings("unchecked")
	@Override
	public SeatStatusEntity getReservationByNumber(Date date, String hour, String studentcode, int reservationNumber) {     
		String jpql = "SELECT * FROM t09_seat_status WHERE ((t09_date = :date AND t09_checkin_hour >= :hour) OR (t09_date > :date)) AND t09_student_code = :studentcode AND t09_number = :reservationNumber AND t09_checkin_flag = '1' ORDER BY t09_date ASC, t09_checkin_hour ASC;"; 
			      
		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql, SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
		query.setParameter("reservationNumber", reservationNumber);
			      
		return query.getSingleResult();
	}

}