package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.HourEntity;

@Repository
public interface HourRepository extends JpaRepository<HourEntity, String> {
	
}
