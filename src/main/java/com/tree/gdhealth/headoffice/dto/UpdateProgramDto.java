package com.tree.gdhealth.headoffice.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.FutureDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProgramDto {
	
	private String originDate;
	
	private int programNo;
	
	private String filename;
	
	private MultipartFile programFile;
	
	@Max(value = 100, message = "수용 인원은 최대 100명까지 가능합니다.")
	@Min(value = 1, message = "수용 인원은 1명 이상이어야 합니다.")
	@NotNull(message = "수용 인원을 입력해주세요.")
	private Integer programMaxCustomer;
	
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식이 올바르지 않습니다.")
	@FutureDate
	private String programDate;
	
	@Size(min = 3, max = 40, message = "프로그램 제목은 3~40자로 입력 가능합니다.")
	private String programName;
	
	@Size(min = 5, max = 1000, message = "프로그램 내용은 5~1000자로 입력 가능합니다.")
	private String programDetail;
	
}
