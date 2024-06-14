package com.tree.gdhealth.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmpServiceDto {
	private String employeeId;
	private String employeePw;
	private String employeeName;
	private String employeePhone;
	private String employeeEmail;
	private String employeeGender;
	private Integer branchNo;
	private String employeePosition;
	private MultipartFile employeeFile;
}
