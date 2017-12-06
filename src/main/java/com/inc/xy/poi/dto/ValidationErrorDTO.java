package com.inc.xy.poi.dto;

import javax.validation.ConstraintViolation;

import org.springframework.validation.FieldError;

public class ValidationErrorDTO {
	private String message;
	private String invalidValue;

	public ValidationErrorDTO(String message, String invalidValue) {
		super();
		this.message = message;
		this.invalidValue = invalidValue;
	}

	public ValidationErrorDTO(FieldError fieldError) {

		if (fieldError != null) {
			this.message = fieldError.getDefaultMessage();
			this.invalidValue = fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString();
		}
	}

	public ValidationErrorDTO(ConstraintViolation<?> constraintViolation) {

		if (constraintViolation != null) {
			this.message = constraintViolation.getMessage();
			this.invalidValue = constraintViolation.getInvalidValue() == null ? null
					: constraintViolation.getInvalidValue().toString();
		}
	}

	public String getMessage() {
		return message;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

}
