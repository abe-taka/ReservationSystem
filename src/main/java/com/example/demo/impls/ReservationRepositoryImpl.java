package com.example.demo.impls;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.ReservationCustomRepository;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.SeatStatusEntity;


@Repository
@Transactional
public class ReservationRepositoryImpl implements ReservationCustomRepository {
	
	@Autowired
	EntityManager entityManager;

	// 予約を確定する
	@SuppressWarnings("unchecked")
	@Override
	public String addReservation(Date date, String hour, String machinecode, String studentcode) {     
		String jpql = "INSERT INTO t09_seat_status (t09_date,t09_checkin_hour,t09_machine_code,t09_machine_count,t09_number,t09_student_code,t09_checkin_flag,t09_update_date) VALUES(:date, :hour, :machinecode, '1', , :studentcode, '0')"; 
	      
		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql, SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
	      
		return query.getResultList();
	}

}
