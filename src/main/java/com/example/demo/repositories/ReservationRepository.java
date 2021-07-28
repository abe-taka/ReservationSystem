package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

	// 機種コード検索
	@Query(name="SELECT DISTINCT t14_reservation_date FROM t14_reservation",nativeQuery = true)
	public List<ReservationEntity> findByMachine(MachineEntity machinecode);
	
	public List<Long> countByMachineAndReservationstartdateAndPeriodcode(MachineEntity machinecode,String startdate,String period);
	
	public List<ReservationEntity> findByReservationstartdate(String date);
}