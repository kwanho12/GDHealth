package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 직원을 찾을 수 없는 경우에 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmpNotFoundException extends RuntimeException {
	public EmpNotFoundException(String message) {
		super(message);
	}
}
