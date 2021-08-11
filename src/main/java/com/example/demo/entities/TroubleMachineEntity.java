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
		
	//
	@Column(name="t18_machine_no")
	private String machineNo;
	
	// 
	@Column(name="t18_report_date")
	private Date reportDate;
	
	//
	@Column(name="t18_report_student_code")
	private String reportStudentCode;
	
	// 
	@Column(name="t18_register_date")
	private Date registerDate;
	
	// 
	@Column(name="t18_register_teacher_code")
	private String registerTeacherCode;
	
	// 
	@Column(name="t18_register_student_code")
	private String registerStudentCode;
	
	// 
	@Column(name="t18_state")
	private String state;
	
	// 
	@Column(name="t18_trouble_pattern")
	private String troublePattern;
	
	// 
	@Column(name="t18_trouble_parts")
	private String troubleParts;
	
	// 
	@Column(name="t18_other")
	private String other;
	
	// 
	@Column(name="t18_repair_start_date")
	private Date repairStartDate;
	
	// 
	@Column(name="t18_repair_end_date")
	private Date repairEndDate;
	
	// 
	@Column(name="t18_update_date")
	private Date updateDate;
	
	// 
	@Column(name="t18_Consignment_name")
	private String consignmentName;
	
	// 
	@Column(name="t18_Consignment_date")
	private Date consignmentDate;
	
	// 
	@Column(name="t18_Product_number")
	private String productNumber;
	
	// 
	@Column(name="t18_work_limit")
	private Date workLimit;
	
	// 
	@Column(name="t18_collection_day")
	private Date collectionDay;
	
	// 
	@Column(name="t18Type_number")
	private String typeNumber;
	
}
