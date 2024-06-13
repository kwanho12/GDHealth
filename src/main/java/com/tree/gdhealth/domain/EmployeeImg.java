package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeImg {
	private int employeeImgNo;
	private int employeeNo;
	private String employeeImgOriginName;
	private String employeeImgFilename;
	private long employeeImgSize;
	private String employeeImgType;
	private String createdate;
	private String updatedate;	
}
