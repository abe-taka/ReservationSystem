package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.MachineSoftCustomRepository;
import com.example.demo.entities.MachineSoftEntity;
import com.example.demo.entities.composites.MachineSoftId;

public abstract interface MachineSoftRepository extends JpaRepository<MachineSoftEntity, MachineSoftId>, MachineSoftCustomRepository<MachineSoftEntity> {
}