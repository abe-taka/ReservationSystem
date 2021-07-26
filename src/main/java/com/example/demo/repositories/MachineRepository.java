package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MachineEntity;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, Integer> {

	// 階層検索(重複省く、所属クラスが使える機種のみ)
	@Query(value = "SELECT DISTINCT m06_floor FROM m05_class_usable_machine x,m06_machine y WHERE x.m05_class_code = :classcode AND x.m05_machine_code = y.m06_machine_code", nativeQuery = true)
	public List<Integer> findByFloor(@Param("classcode") String classcode);

	// 階層検索
	public List<MachineEntity> findByFloor(int floor);

	// 機種コード検索
	public MachineEntity findByMachinecode(String machinecode);
}