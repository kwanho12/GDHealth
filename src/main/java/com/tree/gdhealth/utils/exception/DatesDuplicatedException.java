package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * 선택한 프로그램 날짜들 중 중복된 날짜가 존재할 경우에 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class DatesDuplicatedException extends RuntimeException {
	public DatesDuplicatedException(String message) {
		super(message);
	}
}
