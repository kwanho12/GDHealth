package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
