package com.inc.xy.poi.exception.handler;

import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;
import com.inc.xy.poi.exception.ValidationException;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Intercepta {@link ValidationException}s para construcao de
 * {@link ResponseEntity} tratadas
 * 
 * @author Michel F. Suzigan
 *
 */
@ControllerAdvice
public class ValidationExceptionHandler {

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Trata a {@link ResponseEntity} para diferentes casos de
	 * {@link ValidationException}s lancadas
	 * 
	 * @param e
	 *            Excecao lancada
	 */
	@ExceptionHandler(value = { ValidationException.class })
	private ResponseEntity<ValidationDTO> handleInvalidPoint(ValidationException e) {

		ValidationDTO validation = new ValidationDTO();

		if (CollectionUtils.isNotEmpty(e.getConstraintViolations())) {
			validation.setErrors(
					e.getConstraintViolations().stream().map(ValidationErrorDTO::new).collect(Collectors.toList()));

		} else if (StringUtils.isNotBlank(e.getMessage())) {
			validation.addError(new ValidationErrorDTO(e.getMessage(), e.getInvalidValue()));

		} else {
			validation.addError(new ValidationErrorDTO(messageUtils.get("msg.invalid.argument"), null));
		}

		return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
	}

}
