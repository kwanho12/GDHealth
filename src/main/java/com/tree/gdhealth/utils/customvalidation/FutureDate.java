package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.FutureDateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link FutureDateValidator} 클래스를 기반으로 하며, 날짜가 오늘 날짜 혹은 그 이후인지를 검증하는 애노테이션
 * 
 * @author 진관호
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
@Documented
public @interface FutureDate {

	String message() default "선택한 날짜는 오늘 날짜 이후여야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
