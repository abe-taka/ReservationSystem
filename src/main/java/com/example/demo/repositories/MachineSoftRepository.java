package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MachineSoftEntity;

@Repository
public interface MachineSoftRepository extends JpaRepository<MachineSoftEntity, Integer> {

	// 機種コード検索(チェックボックスで選択したソフトが入っている + 所属クラスが使える + ステップ１で選んだ階数内で検索する)
	@Query(value = "SELECT DISTINCT m08_machine_soft_id, m08_machine_code, m08_soft_code FROM m08_machine_soft WHERE m08_soft_code = :softcode AND m08_machine_code in (SELECT m06_machine_code FROM m06_machine WHERE m06_floor = :floor AND m06_machine_code in (SELECT m05_machine_code FROM m05_class_usable_machine WHERE m05_class_code = :classcode))", nativeQuery = true)
	public List<MachineSoftEntity> query(@Param("softcode") String soft_code, @Param("floor") int floor, @Param("classcode") String classcode);
}