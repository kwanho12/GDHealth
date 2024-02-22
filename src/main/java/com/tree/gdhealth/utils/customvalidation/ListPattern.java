package com.tree.gdhealth.utils.customvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tree.gdhealth.utils.customvalidation.validator.ListPatternValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD }) // 제약의 적용 범위(필드에만 적용 가능)
@Retention(RetentionPolicy.RUNTIME) // 이 제약이 런타임까지 유지되어야 함을 나타낸다.
//custom 제약 정의(ListPatternValidator 클래스에 의해 검증된다.)
@Constraint(validatedBy = ListPatternValidator.class)
@Documented // Javadoc에 이 애노테이션이 포함되어야 한다.
public @interface ListPattern {

	/*
	 * @Target : 해당 사용자가 만든 어노테이션이 부착될 수 있는 타입을 지정한다.
	 * 
	 * @Retention : 애노테이션의 라이프 사이클 즉, 애노테이션이 언제까지 살아 남아 있을지를 정한다.
	 * 
	 * @Constraint : 사용자가 원하는 Constraint와 Validation을 만들어 이를 적용할 수 있다.
	 * 
	 * @Documented : 애노테이션에 대한 정보가 javadoc으로 작성한 문서에 포함되도록 한다.
	 */

	// 유효성 검사가 실패했을 때 사용될 기본 에러 메시지를 지정
	String message() default "날짜 형식이 올바르지 않습니다.";

	// Jakarta Bean Validation에서 그룹 기반 유효성 검사를 할 때 사용된다.
	Class<?>[] groups() default {};

	// 페이로드를 나타내며, 특별한 정보를 전달할 때 사용된다.
	Class<? extends Payload>[] payload() default {};

	// 리스트의 각 요소에 대한 검증에 사용될 정규 표현식을 나타낸다.
	String regexp();
}
