package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
