package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Employee {
	private Integer employeeNo;
	private Integer branchNo;
	private String employeeId;
	private String employeePw;
	private String employeeActive;
	private String employeePosition;
	private String createdate;
	private String updatedate;
}
