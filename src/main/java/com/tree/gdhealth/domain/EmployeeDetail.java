package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//@Data
@Getter
@Builder
@AllArgsConstructor
public class EmployeeDetail {
	private int employeeNo;
	private String employeeName;
	private String employeePhone;
	private String employeeEmail;
	private String employeeGender;
	private String createdate;
	private String updatedate;
}
