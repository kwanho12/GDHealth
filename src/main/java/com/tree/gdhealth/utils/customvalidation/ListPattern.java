package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.ListPatternValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 리스트의 각 요소에 대한 유효성 검사를 위한 {@link ListPatternValidator} 클래스를 지정하는 애너테이션
 * 
 * @author 진관호
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListPatternValidator.class)
@Documented
public @interface ListPattern {

	String message() default "날짜 형식이 올바르지 않습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String regexp();
}
