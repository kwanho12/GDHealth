package com.tree.gdhealth.utils.customvalidation.validator;

import java.util.List;

import com.tree.gdhealth.utils.customvalidation.ListPattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 이 클래스는 ListPattern 애노테이션을 기반으로 하며, List<String> 형식의 값을 검증한다.
public class ListPatternValidator implements ConstraintValidator<ListPattern, List<String>> {

	// 애노테이션에서 정의된 정규 표현식을 저장하는 변수
	private String regexp;

	// @ListPattern에서 정의한 정규 표현식을 가져와 regexp 변수에 저장한다.
	@Override
	public void initialize(ListPattern constraintAnnotation) {
		this.regexp = constraintAnnotation.regexp();
	}

	/*
	 * 실제 유효성 검사 로직을 구현 List<String> 형식의 값들과 ConstraintValidatorContext를 받아서 유효성을
	 * 판단한다.
	 */
	@Override
	public boolean isValid(List<String> values, ConstraintValidatorContext context) {
		if (values == null || values.isEmpty()) {
			return false; // list가 null이거나 비어있는 리스트는 유효하지 않다고 간주
		}

		for (String value : values) {
			if (value == null || !value.matches(regexp)) {
				// matches : 정규식으로 이루어진 특정 패턴의 문자열을 포함하는지 확인
				// 리스트의 각 요소를 순회하면서 각 요소가 정규 표현식과 일치하는지 검사
				// 하나라도 일치하지 않는 요소가 있다면 유효하지 않다고 판단
				return false; // 패턴에 맞지 않는 요소가 있다면 유효하지 않음
			}
		}

		// 모든 요소가 정규 표현식과 일치하면 유효하다고 판단하여 true를 반환
		return true;
	}
}
