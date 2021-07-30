package com.example.demo.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

//1週間の日付を取得するクラス
@Component
public class Realtime_manage {

	static Calendar calendar = Calendar.getInstance();

	// 「月/日」取得
	public TreeMap<String, String> Get_Monthdate(TreeMap<String, String> current_md) {

		// 現在日付を取得する
		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		Date dateObj = new Date();
		calendar.setTime(dateObj);

		// 現在日付を基に10日分の日付と曜日を取得
		for (int i = 0; i < 10; i++) {
			String week = null;
			if (i != 0) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			dateObj = calendar.getTime();
			week = getDayOfTheWeekShort();
			current_md.put(format.format(dateObj), week);
		}
		return current_md;
	}

	// 今日の「年/月/日」取得
	public String Get_CurrentYmd(String current_ymd) {

		// 現在の年月日を取得する
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date dateObj = new Date();
		calendar.setTime(dateObj);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		current_ymd = format.format(dateObj);
		
		return current_ymd;
	}

	// 10日目の 「年/月/日」取得
	public String Get_SevenAfterYmd(String sevenafter_ymd) {

		// 現在の年月日を取得する
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date dateObj = new Date();
		calendar.setTime(dateObj);
		calendar.add(Calendar.DAY_OF_MONTH, 9);
		dateObj = calendar.getTime();
		sevenafter_ymd = format.format(dateObj);
		System.out.println("*****"+sevenafter_ymd);

		return sevenafter_ymd;
	}

	// 曜日取得
	public static String getDayOfTheWeekShort() {
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			return "(日)";
		case Calendar.MONDAY:
			return "(月)";
		case Calendar.TUESDAY:
			return "(火)";
		case Calendar.WEDNESDAY:
			return "(水)";
		case Calendar.THURSDAY:
			return "(木)";
		case Calendar.FRIDAY:
			return "(金)";
		case Calendar.SATURDAY:
			return "(土)";
		}
		throw new IllegalStateException();
	}
}