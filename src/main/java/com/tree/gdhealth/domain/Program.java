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
public class Program {
	private Integer programNo;
	private Integer employeeNo;
	private String programName;
	private String programDetail;
	private Integer programMaxCustomer;
	private String programActive;
	private String createdate;
	private String updatedate;
}
