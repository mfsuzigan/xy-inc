package com.inc.xy.poi.dto;

import org.springframework.validation.FieldError;

public class ValidationErrorDTO {
	private String message;
	private Object invalidValue;

	public ValidationErrorDTO(FieldError fieldError) {
		if (fieldError != null) {
			this.message = fieldError.getDefaultMessage();
			this.invalidValue = fieldError.getRejectedValue();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

}
