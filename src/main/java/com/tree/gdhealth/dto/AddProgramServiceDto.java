package com.tree.gdhealth.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProgramServiceDto {
	private Integer employeeNo;
	private String programName;
	private String programDetail;
	private Integer programMaxCustomer;
	private List<String> programDates;
	private MultipartFile programFile;
}
