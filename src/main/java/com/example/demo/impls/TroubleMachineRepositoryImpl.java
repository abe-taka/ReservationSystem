package com.example.demo.impls;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.TroubleMachineCustomRepository;
import com.example.demo.entities.TroubleMachineEntity;


@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class TroubleMachineRepositoryImpl implements TroubleMachineCustomRepository {
	
	@Autowired
	EntityManager entityManager;
	
	// 座席が仮故障中かチェックする
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfMachineIsTentativelyInTrouble(String machinecode, String machineNumber) {     
		String jpql = "SELECT * FROM t18_trouble_machine WHERE t18_machine_code = :machinecode AND t18_machine_no = :machineNumber AND t18_state='0'"; 
			
		TypedQuery<TroubleMachineEntity> query = (TypedQuery<TroubleMachineEntity>) entityManager.createNativeQuery(jpql, TroubleMachineEntity.class);
		query.setParameter("machinecode", machinecode);
		query.setParameter("machineNumber", machineNumber);
	      
		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}
	
	// 座席が故障中かチェックする
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfMachineIsInTroubled(String machinecode, String machineNumber) {     
		String jpql = "SELECT * FROM t18_trouble_machine WHERE t18_machine_code = :machinecode AND t18_machine_no = :machineNumber AND t18_state<>'3' AND t18_state<>'0'"; 
		
		TypedQuery<TroubleMachineEntity> query = (TypedQuery<TroubleMachineEntity>) entityManager.createNativeQuery(jpql, TroubleMachineEntity.class);
		query.setParameter("machinecode", machinecode);
		query.setParameter("machineNumber", machineNumber);
	      
		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}

}
