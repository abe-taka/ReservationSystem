package com.example.demo.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.AdminEntity;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.HourRepository;

//日付・時間に関するコンポーネント
@Component
public class DateTimeComponent {

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	HourRepository hourRepository;
	
	static Calendar calendar = Calendar.getInstance();

	// 「月/日」取得
	public TreeMap<String, String> Get_Monthdate(TreeMap<String, String> current_md) {

		// 現在日付を取得する
		SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd");
		Date dateObj = new Date();
		calendar.setTime(dateObj);
		
		AdminEntity admin = new AdminEntity();
		admin = adminRepository.findTopByOrderByAdminIdDesc();
		
		// 現在日付とアドミンの予約可能な最大日数を基に日付を取得
		for (int i = 0; i < admin.getMaxReservationDate(); i++) {
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
	
	// String型の日付をDate型に変換する
	public Date strDateToDate(String strDate, String dateFormat) {
		Date date = null;
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat);
			date = sdFormat.parse(strDate + " 00:00:00");
		} catch (ParseException e) {
	        e.printStackTrace();
	    }
			
		return date;
	}
	
	// Date型の日付をString型に変換する
	public String dateToStrDateWithDay(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd(E)").format(date);
	}
		
	// Date型の日付をString型に変換する
	public String dateToStrDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
		
	// Date型の時間をString型に変換する
	public String dateToStrTime(Date date) {
		return new SimpleDateFormat("HH:mm:ss:S").format(date);
	}
	
	// 現在時刻を基に現在時限を返還する
	public String getCurrentHour() {
		String currentHour = null;
			
		// 現在時刻を取得
		Date currentTime = new Date(System.currentTimeMillis());
						
		// DB比較用の臨時文字列を生成
		String tempTimeStr = "1900/01/01 " + dateToStrTime(currentTime);
						
		if (hourRepository.getHourCodeBetweenCheckinStartAndCheckoutLimit(tempTimeStr).size() > 0) {
			// DBから時限コードを取得
			currentHour = hourRepository.getHourCodeBetweenCheckinStartAndCheckoutLimit(tempTimeStr).get(0).getHourCode();
		} else {
			// 現在時刻がDBに登録した時限の時間外の場合、00を返還
			currentHour = "00";
		}
			
		return currentHour;
	}
	
	// 現在時刻を基に現在時限を返還する
	public boolean checkIsTargetHourBeforeCurrentHour(String targetHour) {				
		// 現在時刻を取得
		Date currentTime = new Date(System.currentTimeMillis());
							
		// DB比較用の臨時文字列を生成
		String tempTimeStr = "1900/01/01 " + dateToStrTime(currentTime);
						
		if (hourRepository.getHourCodeBetweenCheckinStartAndCheckoutLimit(tempTimeStr).size() > 0) {
			// DBから時限コードを取得
			String currentHour = hourRepository.getHourCodeBetweenCheckinStartAndCheckoutLimit(tempTimeStr).get(0).getHourCode();
			
			// 処理しようとする時限と取得した時限コードの時限を比べ、現在時刻より前の時限をtrueにする
			if (Integer.parseInt(targetHour) < Integer.parseInt(currentHour)) {
				return true;
			}
			return false;
		} else {
			// 現在時刻がDBに登録した時限の時間外の場合、trueを返す
			return true;
		}
	}
	
	// 現在時刻を基に利用開始できるかをチェック
	public boolean checkIfMachineCanBeStarted(String targetHour) {
		// 現在時刻を取得
		Date currentTime = new Date(System.currentTimeMillis());
									
		// DB比較用の臨時文字列を生成
		String tempTimeStr = "1900/01/01 " + dateToStrTime(currentTime);
		
		// 現在の時間が開始と制限時間の間にある場合
		if (hourRepository.checkIfCurrentTimeIsBetweenStartAndLimit(tempTimeStr, targetHour).size() > 0) {
			return true;
		}	
		// 現在時刻がDBに登録した時限の時間外の場合、falseを返す	
		return false;
	}
}