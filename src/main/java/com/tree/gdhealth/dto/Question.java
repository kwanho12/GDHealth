package com.tree.gdhealth.dto;

import lombok.Data;

@Data
public class Question {
	private Integer customerNo;
	private Integer questionNo;
	private Integer branchNo;
	private String branchName;
	private String customerId;
	private String questionTitle;
	private String questionContent;
	private String createdate;
	private String updatedate;
	private String isSecret;
	private int count;
}
