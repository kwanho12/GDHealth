package com.tree.gdhealth.vo;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.ValidFile;

import lombok.Data;

@Data
public class EmployeeImg {
	
	private int employeeImgNo;
	private int employeeNo;
	private String employeeImgOriginName;
	private String employeeImgFilename;
	private long employeeImgSize;
	private String employeeImgType;
	private String createdate;
	private String updatedate;
	
	@ValidFile
	private MultipartFile employeeFile;
	
}
