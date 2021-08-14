package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UseLogEntity;

@Repository
public interface UseLogRepository extends JpaRepository<UseLogEntity,Integer> {

}
