package com.example.demo.impls;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.HourInWorkPatternCustomRepository;
import com.example.demo.entities.HourInWorkPatternEntity;

@Repository
@Transactional
public class HourInWorkPatternRepositoryImpl implements HourInWorkPatternCustomRepository {

	@Autowired
    EntityManager entityManager;

	//稼動パターンに対応しているかどうかをチェック
	@SuppressWarnings("unchecked")
	@Override
    public List<HourInWorkPatternEntity> findNotWorkingHour(String hour, Date date) {     
        String jpql = "SELECT * FROM m12_hour_in_work_pattern, m13_calendar WHERE m12_work_pattern_code = m13_pattern_code AND m12_hour_code = :hour AND m13_date = :date AND m12_work_flag='0'"; 
        
        TypedQuery<HourInWorkPatternEntity> query = (TypedQuery<HourInWorkPatternEntity>) entityManager.createNativeQuery(jpql, HourInWorkPatternEntity.class);
        query.setParameter("hour", hour);
        query.setParameter("date", date);
        
        return query.getResultList();
    }

}
