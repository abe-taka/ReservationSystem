package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.HourInWorkPatternCustomRepository;
import com.example.demo.entities.HourInWorkPatternEntity;


public interface HourInWorkPatternRepository extends JpaRepository<HourInWorkPatternEntity, Integer>, HourInWorkPatternCustomRepository<HourInWorkPatternEntity> {
}
