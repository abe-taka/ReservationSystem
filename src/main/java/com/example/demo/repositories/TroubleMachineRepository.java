package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.customRepositories.TroubleMachineCustomRepository;
import com.example.demo.entities.TroubleMachineEntity;

public abstract interface TroubleMachineRepository extends JpaRepository<TroubleMachineEntity, String>, TroubleMachineCustomRepository<TroubleMachineEntity> {
	
	// 故障中のマシンの台数を取得
	@Query(value = "SELECT COUNT(t18_machine_code) AS troublemachine FROM t18_trouble_machine WHERE t18_machine_code = :machinecode AND t18_state<>'3' AND t18_state<>'0'", nativeQuery = true)
	public Integer getTroubleMachine(@Param("machinecode") String machinecode);
}
