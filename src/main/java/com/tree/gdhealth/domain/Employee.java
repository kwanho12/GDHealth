package com.tree.gdhealth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
	private Integer employeeNo;
	private Integer branchNo;
	private String employeeId;
	private String employeePw;
	private String employeeActive;
	private String employeePosition;
	private String createdate;
	private String updatedate;
	
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public void setEmployeePw(String employeePw) {
		this.employeePw = employeePw;
	}
}
