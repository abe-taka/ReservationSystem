package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.MachineEntity;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.SeatStatusRepository;
import com.example.demo.repositories.StudentRegistRepository;
import com.example.demo.repositories.TroubleMachineRepository;

// 利用開始
@Controller
public class StartController {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	StudentRegistRepository studentregRepository;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	SeatStatusRepository seatStatusRepository;
	@Autowired
	TroubleMachineRepository troubleMachineRepository;
	
	// セッションデータ用
	String session_data = null;
		
	@GetMapping(value="/start")
	public String Get_Start(Model model, HttpServletRequest request) {
		//セッションデータの取得
		session_data = sessionForm.getSession_code();
		
		//HTTPセッションの宣言
		HttpSession session = request.getSession();
						
		//セッションを確認し、ない場合取得を試す
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
			
			// 予約から利用開始になった場合、時限と機種データをセッションで格納する
			if (model.getAttribute("checkinHour") != null && model.getAttribute("machineCode") != null && model.getAttribute("reservDate") != null && model.getAttribute("checkinFlag") != null) {
				session.setAttribute("checkinHour", model.getAttribute("checkinHour"));
				session.setAttribute("machineCode", model.getAttribute("machineCode"));
				session.setAttribute("reservDate", model.getAttribute("reservDate"));
				session.setAttribute("checkinFlag", model.getAttribute("checkinFlag"));
			}
			
			// セッションに時限と機種・日付データがある場合、次の画面に渡す階数リスト・時限・機種データを保持し、セッションデータは削除する
			if (session.getAttribute("checkinHour") != null && session.getAttribute("machineCode") != null && session.getAttribute("reservDate") != null && session.getAttribute("checkinFlag") != null) {
				// マシンエンティティを取得
				MachineEntity machine = machineRepository.findByMachinecode((String) session.getAttribute("machineCode"));
				
				// 結果用リスト宣言
				List<String> list_seats = new ArrayList<String>();
				
				// 照会するマシンの台数を取得する
				int totalAmounts = machine.getCount();
				
				// 満席フラグの設定
				boolean isFull = true;
				
				// 階数・時限・機種コードを基に座席リストを作る
				for (int i = 1; i < totalAmounts + 1; i++) {
					// 日付データの操作
					String strReservDate = (String) session.getAttribute("reservDate");
					Date date = dateTimeComponent.strDateToDate(strReservDate.split("\\(")[0], "yyyy/MM/dd hh:mm:ss");
					
					// 座席-座席色の文字列を生成
					String seatNumber = setSeatNumber(i);
					String seatColor = checkSeatCurrentStatus(date, (String) session.getAttribute("checkinHour"), (String) session.getAttribute("machineCode"), seatNumber, session_data);
					
					// もし利用可能な席の場合、満席フラグをfalseにする
					if (seatColor.equals("WHITE") || seatColor.equals("YELLOW")) {
						isFull = false;
					}
					
					// 結果リストに代入する
					list_seats.add(seatNumber + "-" + seatColor);
				}
				
				// 次のページに渡すデータの設定
				model.addAttribute("floor", machine.getFloor());
				model.addAttribute("checkinHour", session.getAttribute("checkinHour"));
				model.addAttribute("machineCode", session.getAttribute("machineCode"));
				model.addAttribute("reservDate", session.getAttribute("reservDate"));
				model.addAttribute("checkinFlag", session.getAttribute("checkinFlag"));
				model.addAttribute("isFull", isFull);
				model.addAttribute("list_seats", list_seats);
			} else {
				//機種の階層を取得(所属クラスが使える + 重複無し)
				List<Integer> list_machine = new ArrayList<Integer>();
				list_machine = machineRepository.findByFloor(classcode);
				
				// 現在日を取得
				String todayDate = dateTimeComponent.dateToStrDateWithDay(new Date());
				
				// 次のページに渡すデータの設定
				model.addAttribute("list_machine", list_machine);
				model.addAttribute("checkinHour", dateTimeComponent.getCurrentHour());
				model.addAttribute("reservDate", todayDate);	
			}			
			
			return "start";
		} else {
			return "redirect:/";
		}
	}

	
	// 座席状態をチェックし、色をつける -> 利用中(PURPLE), 予約・仮予約中(BLUE), 故障報告(YELLOW), 故障(RED)
	private String checkSeatCurrentStatus(Date date, String hour, String machineCode, String machineNumber, String studentcode) {
		// 座席状態に合わせて色を返す
		if (seatStatusRepository.checkIfSeatIsUsingByMyself(date, hour, machineCode, machineNumber, studentcode)) {
			return "PURPLE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "2")) {
			return "PURPLE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "1")) {
			return "BLUE";
		} else if (seatStatusRepository.checkIfSeatIsInStateByStatusCode(date, hour, machineCode, machineNumber, "0")) {
			return "BLUE";
		} else if (troubleMachineRepository.checkIfMachineIsTentativelyInTrouble(machineCode, machineNumber)) {
			return "YELLOW";
		} else if (troubleMachineRepository.checkIfMachineIsInTroubled(machineCode, machineNumber)) {
			return "RED";
		}
		return "WHITE";
	}
	
	
	// 座席番号を3桁の文字列で変換する
	private String setSeatNumber(int seatNumber) {
		String strSeatNumber = Integer.toString(seatNumber);
		
		if (strSeatNumber.length() == 1) {
			return "00" + strSeatNumber;
		} else if (strSeatNumber.length() == 2) {
			return "0" + strSeatNumber;
		}
		return strSeatNumber;
	}
}
