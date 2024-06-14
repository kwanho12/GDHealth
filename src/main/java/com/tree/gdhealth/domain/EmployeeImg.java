package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeImg {
	private Integer employeeImgNo;
	private Integer employeeNo;
	private String employeeImgOriginName;
	private String employeeImgFilename;
	private long employeeImgSize;
	private String employeeImgType;
	private String createdate;
	private String updatedate;	
}
