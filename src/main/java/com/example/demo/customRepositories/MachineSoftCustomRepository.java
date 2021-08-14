package com.example.demo.customRepositories;

import java.util.List;

import com.example.demo.entities.MachineSoftEntity;

public interface MachineSoftCustomRepository<T> {

	List<MachineSoftEntity> getMachineCodeBySoft(String softCode, String floor, String classCode);
	
}
