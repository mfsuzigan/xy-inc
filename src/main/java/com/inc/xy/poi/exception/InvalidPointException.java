package com.inc.xy.poi.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.inc.xy.poi.model.Point;

public class InvalidPointException extends RuntimeException {

	private static final long serialVersionUID = 1999602196603553016L;
	private final Set<ConstraintViolation<Point>> constraintViolations;

	public InvalidPointException(Set<ConstraintViolation<Point>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}

	public Set<ConstraintViolation<Point>> getConstraintViolations() {
		return constraintViolations;
	}

}
