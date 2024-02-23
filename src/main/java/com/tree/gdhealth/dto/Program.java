package com.tree.gdhealth.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Program {
	
	/*
		@Size : 문자열, 배열, Collection, Map 등의 크기를 검증할 때 사용된다.
		@Max, @Min : 숫자 타입 (int, long 등)의 값이 최대값을 초과하거나 최소값 미만이 되지 않도록 검증할 때 사용된다.
	*/
	
	private int programNo;
	
	private int employeeNo;
	
	@Size(min = 3, max = 40, message = "프로그램 제목은 3~40자로 입력 가능합니다.")
	private String programName;
	
	@Size(min = 5, max = 1000, message = "프로그램 내용은 5~1000자로 입력 가능합니다.")
	private String programDetail;
	
	@Max(value = 100, message = "수용 인원은 최대 100명까지 가능합니다.")
	@Min(value = 1, message = "수용 인원은 1명 이상이어야 합니다.")
	@NotNull(message = "수용 인원을 입력해주세요.")
	private Integer programMaxCustomer;
	
	private String programActive;
	private String createdate;
	private String updatedate;
	
}
