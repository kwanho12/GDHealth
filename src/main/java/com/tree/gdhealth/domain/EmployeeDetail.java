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
public class EmployeeDetail {
	private Integer employeeNo;
	private String employeeName;
	private String employeePhone;
	private String employeeEmail;
	private String employeeGender;
	private String createdate;
	private String updatedate;
}
