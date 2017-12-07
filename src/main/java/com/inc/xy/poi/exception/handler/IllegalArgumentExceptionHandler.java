package com.inc.xy.poi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<ValidationDTO> handleIllegalArgument(IllegalArgumentException e) {
		return new ResponseEntity<>(new ValidationDTO(new ValidationErrorDTO(e.getMessage())), HttpStatus.BAD_REQUEST);
	}

}
