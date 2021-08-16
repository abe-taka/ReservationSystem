package com.example.demo.impls;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.MachineSoftCustomRepository;
import com.example.demo.entities.MachineSoftEntity;

@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class MachineSoftRepositoryImpl implements MachineSoftCustomRepository {

	@Autowired
	EntityManager entityManager;
	
	// 現在の時間を基に現在の時限コードを取得
	@SuppressWarnings("unchecked")
	@Override
	public List<MachineSoftEntity> getMachineCodeBySoft(@Param("softCode") String softCode, @Param("floor") String floor, @Param("classCode") String classCode) {
		String jpql = "SELECT DISTINCT * FROM m08_machine_soft WHERE m08_floor_code = :floor AND m08_soft_code = :softCode AND m08_machine_code in (SELECT m06_machine_code FROM m06_machine WHERE m06_machine_code in (SELECT m05_machine_code FROM m05_class_usable_machine WHERE m05_class_code = :classCode))"; 
		
		TypedQuery<MachineSoftEntity> query = (TypedQuery<MachineSoftEntity>) entityManager.createNativeQuery(jpql, MachineSoftEntity.class);
		query.setParameter("softCode", softCode);
		query.setParameter("floor", floor);
		query.setParameter("classCode", classCode);
      
		return query.getResultList();
	}
}
