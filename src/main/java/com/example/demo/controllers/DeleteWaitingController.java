package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.components.DateTimeComponent;
import com.example.demo.components.SessionForm;
import com.example.demo.entities.CancelWaitEntity;
import com.example.demo.repositories.CancelWaitRepository;

//　待ち状態解除
@Controller
public class DeleteWaitingController {
	
	@Autowired
	SessionForm sessionForm;
	@Autowired
	DateTimeComponent dateTimeComponent;
	@Autowired
	CancelWaitRepository cancelWaitRepository;
	
	// セッションデータ用
	String session_data = null;
	
	@GetMapping(value="/deleteWaiting")
	public String Get_DeleteWaiting(Model model) {
		// セッションデータの取得
		session_data = sessionForm.getSession_code();
				
		// セッションを確認し、ない場合取得を試す
		if (session_data != null) {
			// セッションデータの受け渡し設定
			String session_data = sessionForm.getSession_code();	
			model.addAttribute("session_data", session_data);
			
			//現在日を取得		
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Date todayDate = new Date();
			todayDate = dateTimeComponent.strDateToDate(format.format(todayDate), "yyyy-MM-dd hh:mm:ss");
			
			//現在時限を取得
			String hour = dateTimeComponent.getCurrentHour();
			
			// 結果リストの生成
			List<CancelWaitEntity> list_waitings = new ArrayList<CancelWaitEntity>();
			
			// 現在の日付・時限・学籍番号を基にマシン解放待ちのデータを取得し、結果リストに入れる
			list_waitings = cancelWaitRepository.getWaitings(session_data, todayDate, hour);
			model.addAttribute("list_waitings", list_waitings);
			
			return "deleteWaiting";
		} else {
			return "redirect:/";
		}
	}
}
