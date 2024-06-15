package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * 직원의 상세 정보를 찾을 수 없는 경우에 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@NoArgsConstructor
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmpNotFoundException extends NotFoundException {
	public EmpNotFoundException(String message) {
		super(message);
	}
}
