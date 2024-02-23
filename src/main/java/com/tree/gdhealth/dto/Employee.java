package com.tree.gdhealth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Employee {

	private int employeeNo;
	
	/*
		@NotNull : null을 전달 받을 경우 Exception이 발생하지만 ""나 "   "와 같은 빈값이 들어올 때는 
		null이 아닌 String으로 인식하기 때문에 유효성을 통과한다.
		
		@NotBlank : Null과 "", "   " 모두 허용하지 않는다. String 타입만 사용 가능하다.
	*/
	
	
	@Positive(message = "지점 번호는 양수여야 합니다.") // 양수만 가능하다.
	private Integer branchNo;
	
	@Pattern(regexp = "^[a-z]+[a-z0-9]{5,14}$", message = "아이디는 영어 소문자로 시작하고 영어 소문자+숫자 조합의 6~15자로 이루어져야 합니다.")
	private String employeeId;
	
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{6,15}$", message = "비밀번호는 숫자와 영문자, 특수문자를 혼용하여 6자 이상 입력해야 합니다.")
	private String employeePw;
	
	private String employeeActive;
	
	@Pattern(regexp = "^(trainer|branch_manager|head_office_manager)$", message = "올바른 직책이 아닙니다.")
	private String employeePosition;
	
	private String createdate;
	private String updatedate;
	
}
