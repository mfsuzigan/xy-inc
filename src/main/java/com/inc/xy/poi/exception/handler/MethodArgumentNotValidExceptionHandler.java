package com.inc.xy.poi.exception.handler;

import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Intercepta {@link MethodArgumentNotValidException}s para construcao de
 * {@link ResponseEntity} tratadas.
 * 
 * @author Michel F. Suzigan
 *
 */
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Trata a {@link ResponseEntity} para diferentes casos de
	 * {@link MethodArgumentNotValidException}s lancadas
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ValidationDTO validation = new ValidationDTO();

		if (e.getBindingResult() != null && CollectionUtils.isNotEmpty(e.getBindingResult().getAllErrors())) {
			validation.setErrors(e.getBindingResult().getAllErrors().stream()
					.filter(error -> error.getClass().isAssignableFrom(FieldError.class))
					.map(error -> new ValidationErrorDTO((FieldError) error)).collect(Collectors.toList()));

		} else {
			validation.addError(new ValidationErrorDTO(messageUtils.get("msg.invalid.argument"), null));
		}

		return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
	}

}
