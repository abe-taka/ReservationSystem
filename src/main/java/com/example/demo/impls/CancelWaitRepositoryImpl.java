package com.example.demo.impls;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.CancelWaitCustomRepository;
import com.example.demo.entities.CancelWaitEntity;

@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class CancelWaitRepositoryImpl implements CancelWaitCustomRepository {
	
	@Autowired
	EntityManager entityManager;

	// マシン解放待ちリストの取得
	@SuppressWarnings("unchecked")
	@Override
	public List<CancelWaitEntity> getWaitings(@Param("studentcode") String studentcode, @Param("date") Date date, @Param("hour") String hour) {     
		String jpql = "SELECT * FROM t16_cancel_wait WHERE t16_student_code = :studentcode AND t16_number IN (SELECT t09_number FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND (t09_checkin_flag = '3' OR t09_checkin_flag = '4'))"; 
		
		TypedQuery<CancelWaitEntity> query = (TypedQuery<CancelWaitEntity>) entityManager.createNativeQuery(jpql, CancelWaitEntity.class);
		query.setParameter("studentcode", studentcode);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		
		return query.getResultList();
	}
	
}
