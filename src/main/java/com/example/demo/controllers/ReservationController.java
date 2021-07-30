package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.Realtime_manage;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.StudentRegistEntity;
import com.example.demo.forms.StudentForm;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.repositories.StudentRegistRepository;

//　座席予約
@Controller
public class ReservationController {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	MachineRepository machineRepository;
	@Autowired
	StudentRegistRepository studentregRepository;
	@Autowired
	Realtime_manage realtime_manage;
	
	// セッションデータ用
	String session_data = null;
	
	@GetMapping(value="/reservation")
	public String Get_Reservation(Model model) {
		
		//セッションデータの取得
		session_data = sessionForm.getSession_code();
		//セッション確認
		if(session_data!= null) {
			//所属クラスを取得
			String session_data = sessionForm.getSession_code();
			StudentRegistEntity studentreg = new StudentRegistEntity();
			studentreg = studentregRepository.findByStudentcode(session_data);
			String classcode = null;
			
			if(studentreg != null) {
				classcode = studentreg.getClassEntity().getClasscode();
			}else {
				System.out.println("所属クラスがない");
			}
			
			//機種の階層を取得(所属クラスが使える + 重複無し)
			List<Integer> list_machine = new ArrayList<Integer>();
			list_machine = machineRepository.findByFloor(classcode);
			model.addAttribute("list_machine", list_machine);
			
			//現在日付から1週間のデータを取得
			TreeMap<String, String> week_data = new TreeMap<>();
			week_data = realtime_manage.Get_Monthdate(week_data);
			model.addAttribute("week_data", week_data);
			
			//セッションデータ
			model.addAttribute("session_data", session_data);
			
			return "reservation";
		}
		else {
			return "redirect:/";
		}
	}
}
