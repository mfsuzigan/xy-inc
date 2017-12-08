package com.inc.xy.poi.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto auxiliar para transporte entre camadas de informacoes de validacao
 * 
 * @author Michel F. Suzigan
 *
 */
public class ValidationDTO {

	private List<ValidationErrorDTO> validationErrors;

	public ValidationDTO() {
		super();
	}

	public ValidationDTO(ValidationErrorDTO validationError) {
		addError(validationError);
	}

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
