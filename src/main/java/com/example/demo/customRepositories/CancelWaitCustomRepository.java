package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.CancelWaitEntity;


public interface CancelWaitCustomRepository<T> {
	
	List<CancelWaitEntity> getWaitings(@Param("studentcode") String studentcode, @Param("date") Date date, @Param("hour") String hour);

}
