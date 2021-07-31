package com.example.demo.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.AdminEntity;
import com.example.demo.repositories.AdminRepository;

//1週間の日付を取得するクラス
@Component
public class Realtime_manage {

	@Autowired
	AdminRepository adminRepository;
	
	static Calendar calendar = Calendar.getInstance();
	
	public TreeMap<String, String> Realtime_process(TreeMap<String, String> real_time) {
		
		//現在日付を取得する
		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		Date dateObj = new Date();
		calendar.setTime(dateObj);
		
		AdminEntity admin = new AdminEntity();
		admin = adminRepository.findTopByOrderByAdminIdDesc();
		
		// 現在日付とアドミンの予約可能な最大日数を基に日付を取得
		for (int i = 0; i < admin.getMaxReservationDate(); i++) {
			String week = null;
			if(i != 0) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			dateObj = calendar.getTime();
			week = getDayOfTheWeekShort();
			real_time.put(format.format(dateObj), week);
		}
		return real_time;
	}
	
	//曜日取得
	public static String getDayOfTheWeekShort() { 
	    switch (calendar.get(Calendar.DAY_OF_WEEK)) {
	        case Calendar.SUNDAY: return "(日)";
	        case Calendar.MONDAY: return "(月)";
	        case Calendar.TUESDAY: return "(火)";
	        case Calendar.WEDNESDAY: return "(水)";
	        case Calendar.THURSDAY: return "(木)";
	        case Calendar.FRIDAY: return "(金)";
	        case Calendar.SATURDAY: return "(土)";
	    }
	    throw new IllegalStateException();
	}	
}