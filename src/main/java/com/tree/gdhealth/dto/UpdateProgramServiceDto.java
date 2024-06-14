package com.tree.gdhealth.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProgramServiceDto {
	private String originDate;
	private int programNo;
	private String filename;
	private MultipartFile programFile;
	private Integer programMaxCustomer;
	private String programDate;
	private String programName;
	private String programDetail;	
}
