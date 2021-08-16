package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.HourInWorkPatternCustomRepository;
import com.example.demo.entities.HourInWorkPatternEntity;
import com.example.demo.entities.composites.HourInWorkPatternId;


public interface HourInWorkPatternRepository extends JpaRepository<HourInWorkPatternEntity, HourInWorkPatternId>, HourInWorkPatternCustomRepository<HourInWorkPatternEntity> {
}
