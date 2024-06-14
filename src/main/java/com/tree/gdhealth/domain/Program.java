package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
