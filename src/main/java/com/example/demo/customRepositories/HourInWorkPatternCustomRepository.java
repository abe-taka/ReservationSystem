package com.example.demo.customRepositories;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.HourInWorkPatternEntity;

public interface HourInWorkPatternCustomRepository<T> {

	List<HourInWorkPatternEntity> findNotWorkingHour(String hour, Date date);
	
	int findMaxHour(String patternCode);
}
