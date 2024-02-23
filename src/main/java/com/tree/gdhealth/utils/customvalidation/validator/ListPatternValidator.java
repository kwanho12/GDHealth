package com.tree.gdhealth.utils.customvalidation.validator;

import java.util.List;

import com.tree.gdhealth.utils.customvalidation.ListPattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link ListPattern} 애노테이션을 기반으로 하며, List<String> 형식의 값을 검증하는 유효성 검사 클래스
 * 
 * @author 진관호
 */
public class ListPatternValidator implements ConstraintValidator<ListPattern, List<String>> {

	private String regexp;

	/**
	 * {@link ListPattern} 애노테이션에서 정의한 정규 표현식을 가져와 저장합니다.
	 *
	 * @param constraintAnnotation ListPattern 애노테이션
	 */
	@Override
	public void initialize(ListPattern constraintAnnotation) {
		this.regexp = constraintAnnotation.regexp();
	}

	/**
	 * List<String> 형식의 값들을 유효성을 검사합니다.
	 *
	 * @param values  유효성을 검사할 값들의 목록
	 * @param context 제약 조건 검사를 위한 컨텍스트
	 * @return 모든 값들이 정규 표현식과 일치하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean isValid(List<String> values, ConstraintValidatorContext context) {
		if (values == null || values.isEmpty()) {
			return false;
		}

		for (String value : values) {
			if (value == null || !value.matches(regexp)) {
				return false;
			}
		}

		return true;
	}
}
