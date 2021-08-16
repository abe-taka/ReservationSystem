package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.SeatStatusEntity;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.repositories.HourInWorkPatternRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRegistRepository;

// 利用開始か即時利用か判断するコントローラー
@Controller
public class SelectToStartController {

	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	StudentRegistRepository studentregRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	HourInWorkPatternRepository hourInWorkPatternRepository;
	
	// セッションデータ用
	String session_data = null;
	
	// 元プログラムの流れ
	// 1. マシン解放待ちに登録中かを調べる -> Y: "マシン解放待ちに登録中です。" N: 次へ
	// 2. (まだ、実装する予定なし)　複数台利用時に期間内にあるかチェック（単体の場合、常にtrue）
	// 3. マシン解放待ちにした機種が利用可能かどうか調べる
	// 4. 予約があるかどうかチェックする
	// 5. 全部該当しない場合、即時利用にする
	
	@GetMapping(value="/selectToStart")
	public String Get_SelectToStart(Model model) {
		// セッションデータの取得
		session_data = sessionForm.getSession_code();
						
		// セッションを確認し、ない場合取得を試す
		if (session_data != null) {
			//所属クラスを取得
			String session_data = sessionForm.getSession_code();
			StudentRegistEntity studentreg = new StudentRegistEntity();
			studentreg = studentregRepository.findByStudentcode(session_data);
			String classcode = null;
							
			if (studentreg != null) {
				classcode = studentreg.getClassEntity().getClasscode();
			} else {
				System.out.println("所属クラスがない");
			}
			model.addAttribute("session_data", session_data);
					
			// 現在日を取得		
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Date todayDate = new Date();
			todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
					
			// 現在時限を取得
			String hour = dateTimeComponent.getCurrentHour();
			
			// 現在が利用可能時間外の場合、利用開始画面に遷移しダイアログを表示する
			if (checkIfCurrentlyNotAvailable(hour, todayDate)) {
				model.addAttribute("notAvailable", "現在は利用可能時間外です。");
				return "/start";
			// 現在が利用可能時間内の場合、通常の処理を行う
			} else {
				// 1. マシン解放待ちに登録中かを調べる -> Y: 予約リストに移動し、"マシン解放待ちに登録中でまだ利用不可です。"のダイアログ表示後、トップに移動する N: 次へ
				if (checkIfCurrentlyWaiting(todayDate, hour, session_data)) {					
					model.addAttribute("notAvailable", "現在マシン解放待ち登録済みで、まだ利用不可です。");
					return "/selectToStart";
				// 2. マシン解放待ち登録中で利用可能の場合 -> 予約リスト表示
				} else if (checkIfCurrentlyWaitingAndCanCheckIn(todayDate, hour, session_data)) {			
					//現在日・現在時限・学籍番号を基に、現在日・現在時限で有効な予約のみを取得
					List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
					list_reservations = seatStatusRepository.getReservationByFlag(todayDate, hour, session_data, "4");
					
					model.addAttribute("currentTime", hour);
					model.addAttribute("list_reservations", list_reservations);
					return "/selectToStart";	
				// 3. 現在日・現在時限を基に、予約がある場合 -> 予約リスト表示
				} else if (checkIfReservationsExist(todayDate, hour, session_data)) {
					//現在日・現在時限・学籍番号を基に、現在日・現在時限で有効な予約のみを取得
					List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
					list_reservations = seatStatusRepository.getReservationByFlag(todayDate, hour, session_data, "1");
					
					model.addAttribute("currentTime", hour);
					model.addAttribute("list_reservations", list_reservations);
					return "/selectToStart";	
				}
				// 4. 予約がない場合 -> 即時利用
				return "redirect:/start";
			}
		} else {
			return "redirect:/";
		}
	}
	
	
	// 予約中の場合、予約リストから利用開始に遷移するためのプロキシコントローラー
	@GetMapping(value="/redirect_to_start")
    public String Get_RedirectToStart(HttpServletRequest request, HttpServletResponse httpResponse, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("reservDate", request.getParameter("reservDate"));
		redirectAttributes.addFlashAttribute("checkinHour", request.getParameter("checkinHour"));
		redirectAttributes.addFlashAttribute("machineCode", request.getParameter("machineCode"));
		redirectAttributes.addFlashAttribute("checkinFlag", request.getParameter("checkinFlag"));
		return "redirect:/start";
    }
	
	
	// 利用可能時間外または稼働時間外かをチェックする
	private boolean checkIfCurrentlyNotAvailable(String targetHour, Date date) {
		System.out.println("checkIfCurrentlyNotAvailable");
		// DBに設定されている時限コードを超えた数字になった場合、利用不可判定
		if (targetHour.equals("8")) {
			return true;
		}
		// DBから該当時限で検索し、非稼働であればtrueを返す
		if (hourInWorkPatternRepository.findNotWorkingHour(targetHour, date).size() > 0) {
			return true;
		}
		return false;
	}
	
	
	// 現在、マシン解放待ちに登録中かをチェック
	private boolean checkIfCurrentlyWaiting(Date todayDate, String hour, String studentcode) {
		// 現在日・現在時限・学籍番号を基に、現在日・現在時限でマシン解放待ち中のみを取得
		List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
		list_reservations = seatStatusRepository.getReservationByFlag(todayDate, hour, studentcode, "3");
			
		if (list_reservations.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	// 現在日・現在時限を基に、予約があるかチェック
	private boolean checkIfReservationsExist(Date todayDate, String hour, String studentcode) {
		// 現在日・現在時限・学籍番号を基に、現在日・現在時限で有効な予約のみを取得
		List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
		list_reservations = seatStatusRepository.getReservationByFlag(todayDate, hour, studentcode, "1");
			
		if (list_reservations.size() > 0) {
			return true;
		}
		return false;
	}
		
	
	// 現在、マシン解放待ちに登録中で利用開始できる状態かをチェック
	private boolean checkIfCurrentlyWaitingAndCanCheckIn(Date todayDate, String hour, String studentcode) {
		// 現在日・現在時限・学籍番号を基に、現在日・現在時限でマシン解放待ち中かつ利用開始できる状態のみを取得
		List<SeatStatusEntity> list_reservations = new ArrayList<SeatStatusEntity>();
		list_reservations = seatStatusRepository.getReservationByFlag(todayDate, hour, studentcode, "4");
				
		if (list_reservations.size() > 0) {
			return true;
		}
		return false;
	}
}
