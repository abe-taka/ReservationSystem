package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.SoftEntity;

@Repository
public interface SoftRepository extends JpaRepository<SoftEntity, String>{

	// ソフトコード検索(指定した階のマシーンのソフト)
	@Query(value = "SELECT DISTINCT z.m07_soft_code,z.m07_maker,z.m07_soft_name FROM m06_machine x,m08_machine_soft y,m07_soft z WHERE x.m06_floor_code = :floor AND x.m06_machine_code = y.m08_machine_code AND y.m08_soft_code = z.m07_soft_code", nativeQuery = true)
	public List<SoftEntity> findBySoftcode(@Param("floor") int floor);
	
	// ソフトコード検索
	public SoftEntity findBySoftcode(String softcode);
}