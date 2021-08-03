package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.ReservationCustomRepository;

import com.example.demo.entities.ReservationEntity;


public abstract interface ReservationRepository extends JpaRepository<ReservationEntity, Integer>, ReservationCustomRepository<ReservationEntity> {	
}