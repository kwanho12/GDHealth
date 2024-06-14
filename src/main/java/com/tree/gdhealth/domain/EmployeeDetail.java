package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDetail {
	private Integer employeeNo;
	private String employeeName;
	private String employeePhone;
	private String employeeEmail;
	private String employeeGender;
	private String createdate;
	private String updatedate;
}
