package com.inc.xy.poi.dto;

import javax.validation.ConstraintViolation;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ValidationErrorDTO {
	private String message;
	private String invalidValue;

	public ValidationErrorDTO(String message, String invalidValue) {
		super();
		this.message = message;
		this.invalidValue = invalidValue;
	}

	public ValidationErrorDTO(String message) {
		super();
		this.message = message;
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
