package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class Employee {
	private int employeeNo;
	private Integer branchNo;
	private String employeeId;
	private String employeePw;
	private String employeeActive;
	private String employeePosition;
	private String createdate;
	private String updatedate;
}
