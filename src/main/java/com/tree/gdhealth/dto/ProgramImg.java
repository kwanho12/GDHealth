package com.tree.gdhealth.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.ValidFile;

import lombok.Data;

@Data
public class ProgramImg {

	private int programImgNo;
	private int programNo;
	private String originName;
	private String filename;
	private long programImgSize;
	private String programImgType;
	private String createdate;
	private String updatedate;
	
	@ValidFile
	private MultipartFile programFile;
	
}
