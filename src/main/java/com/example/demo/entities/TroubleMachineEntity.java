package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//故障機
@Entity
@Table(name="t18_trouble_machine")
public class TroubleMachineEntity {
	
	//id(連番)
	@Id
	@Column(name="t18_trouble_machine_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int troubleMachineId;
	
	//故障機コード
	@Column(name="t18_machine_code")
	private String machinecode;
		
	//座番
	@Column(name="t18_machine_no")
	private String machineNo;
	
	//報告日
	@Column(name="t18_report_date")
	private Date reportDate;
	
	//報告学生コード
	@Column(name="t18_report_student_code")
	private String reportStudentCode;
	
	//登録日
	@Column(name="t18_register_date")
	private Date registerDate;
	
	//登録教官コード
	@Column(name="t18_register_teacher_code")
	private String registerTeacherCode;
	
	//登録学生コード
	@Column(name="t18_register_student_code")
	private String registerStudentCode;
	
	//状態
	@Column(name="t18_state")
	private String state;
	
	//故障分類
	@Column(name="t18_trouble_pattern")
	private String troublePattern;
	
	//故障箇所
	@Column(name="t18_trouble_parts")
	private String troubleParts;
	
	//その他・備考
	@Column(name="t18_other")
	private String other;
	
	//修理開始日
	@Column(name="t18_repair_start_date")
	private Date repairStartDate;
	
	//修理終了日
	@Column(name="t18_repair_end_date")
	private Date repairEndDate;
	
	//更新日
	@Column(name="t18_update_date")
	private Date updateDate;
	
	//委託業者名
	@Column(name="t18_Consignment_name")
	private String consignmentName;
	
	//委託日
	@Column(name="t18_Consignment_date")
	private Date consignmentDate;
	
	//製品番号
	@Column(name="t18_Product_number")
	private String productNumber;
	
	//型番
	@Column(name="t18_work_limit")
	private Date workLimit;
	
	//作業期限日
	@Column(name="t18_collection_day")
	private Date collectionDay;
	
	//業者回収日
	@Column(name="t18Type_number")
	private String typeNumber;
	
}
