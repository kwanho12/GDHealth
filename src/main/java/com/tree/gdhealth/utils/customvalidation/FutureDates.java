package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.FutureDatesValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link FutureDatesValidator} 클래스를 기반으로 하며, 날짜가 오늘 날짜 혹은 그 이후인지를 검증하는 애노테이션
 * 
 * @author 진관호
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDatesValidator.class)
@Documented
public @interface FutureDates {

	String message() default "선택한 날짜들은 오늘 날짜 이후여야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
