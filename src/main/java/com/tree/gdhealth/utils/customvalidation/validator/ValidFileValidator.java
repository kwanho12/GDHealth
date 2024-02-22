package com.tree.gdhealth.utils.customvalidation.validator;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.customvalidation.ValidFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

		// file이 null이 아니면서 비어있지 않으면 true 반환
		return (file != null && !file.isEmpty());
	}

}
