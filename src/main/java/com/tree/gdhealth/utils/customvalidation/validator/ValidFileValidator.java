package com.tree.gdhealth.utils.customvalidation.validator;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.ValidFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link ValidFile} 애노테이션을 기반으로 하며, {@link MultipartFile} 형식의 파일 유효성을 검사하는 유효성
 * 검사 클래스
 */
public class ValidFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

	/**
	 * 주어진 {@link MultipartFile} 객체가 유효한지 검사합니다.
	 *
	 * @param file    검사할 {@link MultipartFile} 객체
	 * @param context 제약 조건 검사를 위한 컨텍스트
	 * @return 주어진 파일이 null이 아니면서 비어있지 않으면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

		return (file != null && !file.isEmpty());
	}

}
