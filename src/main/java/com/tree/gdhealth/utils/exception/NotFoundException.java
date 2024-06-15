package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * 요청한 자원을 찾을 수 없을 때 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@NoArgsConstructor
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}
