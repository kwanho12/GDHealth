package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * 이미지 형식의 확장자가 아닐 경우에 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class ExtensionNotMatchException extends RuntimeException {
	public ExtensionNotMatchException(String message) {
		super(message);
	}
}
