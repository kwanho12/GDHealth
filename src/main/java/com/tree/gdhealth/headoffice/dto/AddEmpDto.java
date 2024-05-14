package com.tree.gdhealth.headoffice.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.ValidFile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmpDto {

	@Pattern(regexp = "^[a-z]+[a-z0-9]{5,14}$", message = "아이디는 영어 소문자로 시작하고 영어 소문자+숫자 조합의 6~15자로 이루어져야 합니다.")
	private String employeeId;

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{6,15}$", message = "비밀번호는 숫자와 영문자, 특수문자를 혼용하여 6자 이상 입력해야 합니다.")
	private String employeePw;

	@Size(min = 2, max = 20, message = "이름은 2~20자로 입력 가능합니다.")
	@Pattern(regexp = "^[a-zA-Zㄱ-힣\\s]+$", message = "이름은 영문, 한글 또는 공백만 허용됩니다.")
	private String employeeName;

	@Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "유효하지 않은 전화번호 형식입니다.")
	private String employeePhone;

	@Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
	private String employeeEmail;

	@Pattern(regexp = "^(m|f)$", message = "올바른 성별이 아닙니다.")
	private String employeeGender;

	@Positive(message = "지점 번호는 양수여야 합니다.")
	private Integer branchNo;

	@Pattern(regexp = "^(trainer|branch_manager|head_office_manager)$", message = "올바른 직책이 아닙니다.")
	private String employeePosition;

	@ValidFile
	private MultipartFile employeeFile;

}
