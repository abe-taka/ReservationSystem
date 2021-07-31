package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.ReservationEntity;

@Repository
public abstract interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

	// 機種コード検索
	@Query(name = "SELECT DISTINCT t14_reservation_date FROM t14_reservation", nativeQuery = true)
	public List<ReservationEntity> findByMachine(MachineEntity machinecode);

	//
	@Query(name = "SELECT * FROM t14_reservation x WHERE x.machine.machinecode = :machinecode t14_reservation_start_date BETWEEN :currentday AND :tenafterday order by t14_reservation_start_date", nativeQuery = true)
	public List<ReservationEntity> findByMachineAndReservationstartdateBetweenOrderByReservationstartdate(@Param("machinecode") MachineEntity machineEntity,@Param("currentday") String currentday,@Param("tenafterday") String tenafterday);
}