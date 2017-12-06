package com.inc.xy.poi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inc.xy.poi.exception.InvalidPointException;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.repository.PointRepository;

@Service
public class PointService {

	@Autowired
	private PointRepository repository;

	@Autowired
	private Validator validator;

	public List<Point> findAll() {
		return repository.findAll();
	}

	public Point save(Point point) {
		validate(point);
		return repository.save(point);
	}

	public List<Point> findInRadius(Point center, BigDecimal radiusLength) {
		validate(center);
		return findAll().stream().filter(point -> point.getDistanceFrom(center).compareTo(radiusLength) <= 0)
				.collect(Collectors.toList());
	}

	public Point findById(Long id) {

		if (id == null) {
			throw new IllegalArgumentException("Identificador de ponto de interesse obrigatório");
		}

		return repository.findOne(id);
	}

	private void validate(Point point) {
		Set<ConstraintViolation<Point>> constraintViolations = validator.validate(point);
		if (!constraintViolations.isEmpty()) {
			throw new InvalidPointException(constraintViolations);
		}
	}
}
