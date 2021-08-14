package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ReservationLogEntity;

@Repository
public interface ReservationLogRepository extends JpaRepository<ReservationLogEntity,Integer> {

}
