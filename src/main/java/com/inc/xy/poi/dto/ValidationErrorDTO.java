package com.inc.xy.poi.dto;

public class ValidationErrorDTO {
	private String message;
	private Object invalidValue;

	public ValidationErrorDTO(String message, Object invalidValue) {
		super();
		this.message = message;
		this.invalidValue = invalidValue;
	}

	public String getMessage() {
		return message;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

}
