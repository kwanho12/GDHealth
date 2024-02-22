package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.FutureDatesValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDatesValidator.class)
@Documented
public @interface FutureDates {

	// 유효성 검사가 실패했을 때 사용될 기본 에러 메시지
	String message() default "선택한 날짜는 오늘 날짜 이후여야 합니다.";

	// Jakarta Bean Validation에서 그룹 기반 유효성 검사를 할 때 사용
	Class<?>[] groups() default {};

	// 페이로드 : 특별한 정보를 전달할 때 사용
	Class<? extends Payload>[] payload() default {};
}
