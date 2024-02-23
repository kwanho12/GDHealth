package com.tree.gdhealth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDetail {
	
	private int employeeNo;
	
	@Size(min = 2, max = 20, message = "이름은 2~20자로 입력 가능합니다.")
    @Pattern(regexp = "^[a-zA-Zㄱ-힣\\s]+$", 
    					message = "이름은 영문, 한글 또는 공백만 허용됩니다.")
	private String employeeName;
	
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", 
    					message = "유효하지 않은 전화번호 형식입니다.")
	private String employeePhone;
	
    // @Email 애노테이션은 null을 허용하기 때문에 사용x 
	@Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", 
						message = "올바른 이메일 형식이 아닙니다.")	
	private String employeeEmail;
	
	@Pattern(regexp = "^(m|f)$", message = "올바른 성별이 아닙니다.")
	private String employeeGender;
	
	private String createdate;
	private String updatedate;
}
