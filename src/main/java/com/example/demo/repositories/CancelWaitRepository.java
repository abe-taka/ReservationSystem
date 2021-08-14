package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customRepositories.CancelWaitCustomRepository;
import com.example.demo.entities.CancelWaitEntity;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentEntity;

public abstract interface CancelWaitRepository extends JpaRepository<CancelWaitEntity,Integer>, CancelWaitCustomRepository<CancelWaitEntity> {
		
	public CancelWaitEntity findByStudentAndMachineAndSeatStatus(StudentEntity student, MachineEntity machine, SeatStatusEntity seatStatus);
	
	public CancelWaitEntity findBySeatStatus(SeatStatusEntity seatStatus);

}
