package com.example.demo.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//多様な処理で活用されるコンポーネント
@Component
public class UtilComponent {
	
	@Autowired
	ObjectMapper objectMapper;
	
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
}
