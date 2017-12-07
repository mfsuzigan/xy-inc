package com.inc.xy.poi.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.inc.xy.poi.model.Point;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1999602196603553016L;
	private final transient Set<ConstraintViolation<Point>> constraintViolations;
	private final String invalidValue;

	public ValidationException(String message, Object invalidValue) {
		super(message);
		this.constraintViolations = null;
		this.invalidValue = invalidValue == null ? null : invalidValue.toString();
	}

	public ValidationException(Set<ConstraintViolation<Point>> constraintViolations) {
		this.constraintViolations = constraintViolations;
		this.invalidValue = null;
	}

	public Set<ConstraintViolation<Point>> getConstraintViolations() {
		return constraintViolations;
	}

	public String getInvalidValue() {
		return invalidValue;
	}

}
