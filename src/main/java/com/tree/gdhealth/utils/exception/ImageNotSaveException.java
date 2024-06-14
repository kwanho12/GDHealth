package com.tree.gdhealth.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * 이미지를 저장하지 못할 경우에 발생하는 예외 클래스
 * 
 * @author 진관호
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageNotSaveException extends RuntimeException{
	public ImageNotSaveException(String message) {
		super(message);
	}
}
