package com.tree.gdhealth.dto;

import lombok.Data;

@Data
public class Answer {
	private Integer answerNo;
	private int questionNo;
	private int employeeNo;
	private String employeeId;
	private String answerContent;
	private String createdate;
	private String updatedate;
}
