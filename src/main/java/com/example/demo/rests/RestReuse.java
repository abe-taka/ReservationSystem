package com.example.demo.rests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.components.UtilComponent;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.repositories.CalendarRepository;
import com.example.demo.repositories.HourInWorkPatternRepository;
import com.example.demo.repositories.HourRepository;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRepository;

@RestController
public class RestReuse {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	UtilComponent utilComponent;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	CalendarRepository calendarRepository;
	@Autowired
	HourInWorkPatternRepository hourInWorkPatternRepository;
	@Autowired
	HourRepository hourRepository;
	@Autowired
	StudentRepository studentRepository;

	@RequestMapping(value="/restresue", method = RequestMethod.POST)
	public String ReuseProcess(@RequestParam("js_machinecode") String js_machinecode, @RequestParam("js_machinenum") String js_machinenum, @RequestParam("js_hour") String js_hour, @RequestParam("js_date") String js_date) {
		// メッセージの初期化
		String response_text = null;
		
		// セッション取得
		String session_data = sessionForm.getSession_code();
				
		// 現在日を取得		
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		Date todayDate = new Date();
		todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
								
		// 現在時限を取得			
		String currentHour = dateTimeComponent.getCurrentHour();
		
		// 継続利用時間内かをチェック
		if (!checkIfNotAvailable(currentHour, todayDate)) {
			// 次の時限の値設置
			int int_hour = Integer.parseInt(js_hour);
			String nextHour = String.valueOf(int_hour + 1);
			
			// 継続利用などで既に同じ機種・座席番号に対してユーザが予約していない場合
			if (!seatStatusRepository.checkIfSeatIsReservedByMyself(js_date, nextHour, js_machinecode, js_machinenum, session_data, "1", "2")) {
				// 誰かが同じマシンの同じ座席を次の時限に予約していない場合
				if (!seatStatusRepository.checkIfSeatIsReservedByOthers(js_date, nextHour, js_machinecode, js_machinenum, session_data, "1", "2")) {
					// 次の時限の予約数取得
					int i = seatStatusRepository.countReuseReservedMachine(js_date, nextHour, js_machinecode);
							
					// 機種の総台数を取得
					MachineEntity machineEntity = machineRepository.findByMachinecode(js_machinecode);
							
					// 満席でなければ
					if (i < machineEntity.getCount()) {
						// 予約データを追加する
						SeatStatusEntity seatStatusEntity = new SeatStatusEntity();
						seatStatusEntity.setCheckinFlag("2");
						seatStatusEntity.setHour(hourRepository.findByHourCode(nextHour));
						seatStatusEntity.setDate(todayDate);
						seatStatusEntity.setMachine(machineRepository.findByMachinecode(js_machinecode));
						seatStatusEntity.setMachineCount(1);
						seatStatusEntity.setMachineNo(js_machinenum);
						seatStatusEntity.setStudent(studentRepository.findByStudentcode(session_data));
						seatStatusEntity.setUpdateDate(todayDate);
						seatStatusEntity = seatStatusRepository.save(seatStatusEntity);
						
						// データが追加されたら
						if (seatStatusEntity != null) {
							// ログ保存
							utilComponent.saveToLog(null, null, session_data, "継続利用");
							
							// 予約ログ保存
							utilComponent.saveToReservationLog(js_machinecode, js_machinenum, session_data, "", todayDate, nextHour, "継続利用");
							
							// 利用ログ保存
							utilComponent.saveToUseLog(todayDate, nextHour, js_machinecode, js_machinenum);
							
							response_text = "継続利用の申込みに成功しました！";
						}
					// 満席の場合
					} else {
						response_text = "次の時限に満席のため、継続利用できません。";
					}
				// 他のユーザが同じマシンの同じ座席を次の時限に予約している場合
				} else {
					response_text = "「機種：" + js_machinecode + "」「座席番号：" + js_machinenum + "」に対して、次の時限に他のユーザが予約をしているため、継続利用できません。";
				}
			// 継続利用などで既に同じ機種・座席番号に対してユーザが予約している状況の場合
			} else {
				response_text = "既に継続利用申込を完了しましたので、現在継続利用の申込はできません。";
			}	
		// 継続利用可能時間外の場合
		} else {
			response_text = "利用可能時間外または継続利用申込不可状態のため、継続利用できません。";
		}

		return response_text;
	}
	
	// 利用可能時間外または稼働時間外かをチェックする
	private boolean checkIfNotAvailable(String targetHour, Date date) {
		// DBに設定されている時限コードを超えた数字になった場合、継続利用不可判定
		if (targetHour.equals("00")) {
			return true;
		}
			
		// 現在日の稼働コードを元に次の時限が存在しない場合、継続利用不可判定
		String patternCode = calendarRepository.findByDate(date).getWorkPattern().getWorkPatternCode();
		if (Integer.parseInt(targetHour) + 1 > hourInWorkPatternRepository.findMaxHour(patternCode)) {
			return true;
		}
			
		// DBから該当時限で検索し、非稼働であればtrueを返す
		if (hourInWorkPatternRepository.findNotWorkingHour(targetHour, date).size() > 0) {
			return true;
		}
		return false;
	}
}