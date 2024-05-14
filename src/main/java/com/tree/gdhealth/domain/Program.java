package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class Program {
	private int programNo;
	private int employeeNo;
	private String programName;
	private String programDetail;
	private Integer programMaxCustomer;
	private String programActive;
	private String createdate;
	private String updatedate;
	
}
