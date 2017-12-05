package com.inc.xy.poi.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationDTO {

	private List<ValidationErrorDTO> validationErrors;

	public List<ValidationErrorDTO> getErrors() {
		return validationErrors;
	}

	public void setErrors(List<ValidationErrorDTO> errors) {
		this.validationErrors = errors;
	}

	public void addError(ValidationErrorDTO error) {

		if (validationErrors == null) {
			validationErrors = new ArrayList<>();
		}

		validationErrors.add(error);
	}

}
