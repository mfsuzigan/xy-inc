package com.inc.xy.poi.exception.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Intercepta {@link IllegalArgumentException}s para construcao de
 * {@link ResponseEntity} tratadas.
 * 
 * @author Michel F. Suzigan
 *
 */
@ControllerAdvice
public class IllegalArgumentExceptionHandler {

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Trata a {@link ResponseEntity} para os diferentes casos de
	 * {@link IllegalArgumentException}s lancadas
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<ValidationDTO> handleIllegalArgument(IllegalArgumentException e) {

		String message = StringUtils.isBlank(e.getMessage()) ? messageUtils.get("msg.invalid.argument")
				: e.getMessage();

		return new ResponseEntity<>(new ValidationDTO(new ValidationErrorDTO(message)), HttpStatus.BAD_REQUEST);
	}

}
