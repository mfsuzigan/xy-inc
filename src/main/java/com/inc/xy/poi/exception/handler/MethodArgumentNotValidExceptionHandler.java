package com.inc.xy.poi.exception.handler;

import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
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

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler
		implements MessageSourceAware {

	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ValidationDTO validation = new ValidationDTO();

		if (e.getBindingResult() != null && CollectionUtils.isNotEmpty(e.getBindingResult().getAllErrors())) {

			validation.setErrors(e.getBindingResult().getAllErrors().stream()
					.filter(error -> error.getClass().isAssignableFrom(FieldError.class)).map(error -> {
						FieldError fieldError = (FieldError) error;
						return new ValidationErrorDTO(fieldError.getDefaultMessage(), fieldError.getRejectedValue());

					}).collect(Collectors.toList()));

		} else {
			validation.addError(new ValidationErrorDTO(messageSource., null));
		}

		return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
