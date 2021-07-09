package com.example.demo.forms;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

public class StudentForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/* バリデーション優先順位をグループ化 */
	public interface Group1 {
	}

	public interface Group2 {
	}

	@GroupSequence({ Group1.class, Group2.class })
	public interface All {
	}
	
	// 学籍番号
	@NotBlank(groups = Group1.class)
	private String studentcode;

	// パスワード
	@NotBlank(groups = Group1.class)
	private String studentpassword;

	// 名前
	private String studentname;

	// ボランティア委員かのフラグ
	private String studentflag;

	// ？
	private String studentstat;

	
	
	// ゲッター、セッター
	public String getStudentcode() {
		return studentcode;
	}

	public void setStudentcode(String studentcode) {
		this.studentcode = studentcode;
	}

	public String getStudentpassword() {
		return studentpassword;
	}

	public void setStudentpassword(String studentpassword) {
		this.studentpassword = studentpassword;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getStudentflag() {
		return studentflag;
	}

	public void setStudentflag(String studentflag) {
		this.studentflag = studentflag;
	}

	public String getStudentstat() {
		return studentstat;
	}

	public void setStudentstat(String studentstat) {
		this.studentstat = studentstat;
	}
}
