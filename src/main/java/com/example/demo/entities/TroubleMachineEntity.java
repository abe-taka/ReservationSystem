package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//故障機
@Entity
@Table(name="t18_trouble_machine")
public class TroubleMachineEntity {
	
	//id(連番)
	@Id
	@Column(name="t18_trouble_machine_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int troubleMachineId;
	
	//
	@ManyToOne
	@JoinColumn(name="t18_machine_code")
	@JsonBackReference("Unit11")
	private MachineEntity machine;
		
	//
	@Column(name="t18_machine_no")
	private String machineNo;
	
	// 
	@Column(name="t18_report_date")
	private Date reportDate;
	
	//
	@ManyToOne
	@JoinColumn(name="t18_report_student_code")
	@JsonBackReference("Unit6")
	private StudentEntity student;
	
	// 
	@Column(name="t18_register_date")
	private Date registerDate;
	
	//
	@ManyToOne
	@JoinColumn(name="t18_register_teacher_code")
	@JsonBackReference("Unit9")
	private TeacherEntity teacher;
	
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

	public int getTroubleMachineId() {
		return troubleMachineId;
	}

	public void setTroubleMachineId(int troubleMachineId) {
		this.troubleMachineId = troubleMachineId;
	}

	public MachineEntity getMachine() {
		return machine;
	}

	public void setMachine(MachineEntity machine) {
		this.machine = machine;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public String getRegisterStudentCode() {
		return registerStudentCode;
	}

	public void setRegisterStudentCode(String registerStudentCode) {
		this.registerStudentCode = registerStudentCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTroublePattern() {
		return troublePattern;
	}

	public void setTroublePattern(String troublePattern) {
		this.troublePattern = troublePattern;
	}

	public String getTroubleParts() {
		return troubleParts;
	}

	public void setTroubleParts(String troubleParts) {
		this.troubleParts = troubleParts;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getRepairStartDate() {
		return repairStartDate;
	}

	public void setRepairStartDate(Date repairStartDate) {
		this.repairStartDate = repairStartDate;
	}

	public Date getRepairEndDate() {
		return repairEndDate;
	}

	public void setRepairEndDate(Date repairEndDate) {
		this.repairEndDate = repairEndDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}

	public Date getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(Date consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public Date getWorkLimit() {
		return workLimit;
	}

	public void setWorkLimit(Date workLimit) {
		this.workLimit = workLimit;
	}

	public Date getCollectionDay() {
		return collectionDay;
	}

	public void setCollectionDay(Date collectionDay) {
		this.collectionDay = collectionDay;
	}

	public String getTypeNumber() {
		return typeNumber;
	}

	public void setTypeNumber(String typeNumber) {
		this.typeNumber = typeNumber;
	}

	
}
