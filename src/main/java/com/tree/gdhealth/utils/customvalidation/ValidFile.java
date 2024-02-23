package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.ValidFileValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link ValidFileValidator} 클래스를 기반으로 하며, 이미지 파일이 필수로 추가되어야 함을 검증하는 애너테이션
 * 
 * @author 진관호
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFileValidator.class)
@Documented
public @interface ValidFile {

	String message() default "이미지 파일은 필수로 추가해야 합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
