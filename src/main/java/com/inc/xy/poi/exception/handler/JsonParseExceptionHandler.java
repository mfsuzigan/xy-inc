package com.inc.xy.poi.exception.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Intercepta {@link JsonParseException}s para construcao de
 * {@link ResponseEntity} tratadas.
 * 
 * @author Michel F. Suzigan
 *
 */
@ControllerAdvice
public class JsonParseExceptionHandler {

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Trata a {@link ResponseEntity} para casos de {@link JsonParseException}s
	 * lancadas
	 */
	@ExceptionHandler(value = { JsonParseException.class })
	public ResponseEntity<ValidationDTO> handleJsonParse(JsonParseException e) throws IOException {

		return new ResponseEntity<>(
				new ValidationDTO(new ValidationErrorDTO(messageUtils.get("msg.invalid.argument.type"),
						e.getProcessor().getCurrentName())),
				HttpStatus.BAD_REQUEST);
	}

}
