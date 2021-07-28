package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MachineSoftEntity;

@Repository
public interface MachineSoftRepository extends JpaRepository<MachineSoftEntity, Integer> {

	// 機種コード検索(チェックボックスで選択したソフトが入っている + 所属クラスが使える)
	@Query(value = "SELECT DISTINCT m08_machine_soft_id,m08_machine_code,m08_soft_code FROM m08_machine_soft x,m05_class_usable_machine y,m06_machine z WHERE x.m08_soft_code = :softcode AND exists (select * from m05_class_usable_machine where m05_class_code = :classcode)", nativeQuery = true)
	public List<MachineSoftEntity> query(@Param("softcode") String soft_code, @Param("classcode") String classcode);
}