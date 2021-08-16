package com.example.demo.components;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.LogEntity;
import com.example.demo.entities.ReservationLogEntity;
import com.example.demo.entities.UseLogEntity;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.ReservationLogRepository;
import com.example.demo.repositories.UseLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//多様な処理で活用されるコンポーネント
@Component
public class UtilComponent {
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	LogRepository logRepository;
	@Autowired
	UseLogRepository useLogRepository;
	@Autowired
	ReservationLogRepository reservationLogRepository;
	
	// リストをJSON形式で変換する
	public String listToJSON(List<?> list) {
		// json変換取得変数
		String json = null;

		// 変換処理
		try {
			json = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			System.err.println(e);
			System.out.println("getJson_Reservation():fail");
		}
		return json;
	}
	
	// ログテーブルにデータを追加
	public void saveToLog(String teacherCode, String targetTeacherCode, String studentCode, String taskData) {
		LogEntity entity = new LogEntity();
		
		if (teacherCode != null) entity.setTeacherCode(teacherCode);
		if (targetTeacherCode != null) entity.setTargetTeacherCode(targetTeacherCode);
		if (studentCode != null) entity.setStudentCode(studentCode);
		entity.setDate(new Date());
		entity.setTaskData(taskData);
		logRepository.save(entity);
	}
	
	// 利用ログテーブルにデータを追加
	public void saveToUseLog(Date date, String hour, String machineCode, String machineNo) {
		UseLogEntity entity = new UseLogEntity();
				
		entity.setDate(date);
		entity.setHourCode(hour);
		entity.setMachineCode(machineCode);
		entity.setMachineNo(machineNo);
		useLogRepository.save(entity);
	}
	
	// 予約ログテーブルにデータを追加
	public void saveToReservationLog(String machineCode, String machineNo, String studentCode, String teacherCode, Date useYmd, String usePeriod, String taskData) {
		ReservationLogEntity entity = new ReservationLogEntity();
		
		entity.setMachineCode(machineCode);
		entity.setMachineNo(machineNo);
		if (studentCode != null) entity.setStudentCode(studentCode);
		if (teacherCode != null) entity.setTeacherCode(teacherCode);
		entity.setUseYmd(useYmd);
		entity.setUsePeriod(usePeriod);
		entity.setTaskData(taskData);
		entity.setDate(new Date());
		
		reservationLogRepository.save(entity);
	}
}
