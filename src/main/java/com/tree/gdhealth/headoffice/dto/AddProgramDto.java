package com.tree.gdhealth.headoffice.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.FutureDates;
import com.tree.gdhealth.utils.customvalidation.ListPattern;
import com.tree.gdhealth.utils.customvalidation.ValidFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProgramDto {
	
	private int employeeNo;
	
	@Size(min = 3, max = 40, message = "프로그램 제목은 3~40자로 입력 가능합니다.")
	private String programName;
	
	@Size(min = 5, max = 1000, message = "프로그램 내용은 5~1000자로 입력 가능합니다.")
	private String programDetail;
	
	@Max(value = 100, message = "수용 인원은 최대 100명까지 가능합니다.")
	@Min(value = 1, message = "수용 인원은 1명 이상이어야 합니다.")
	@NotNull(message = "수용 인원을 입력해주세요.")
	private Integer programMaxCustomer;
	
	@ListPattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
	@FutureDates
	private List<String> programDates;
	
	@ValidFile
	private MultipartFile programFile;
}
