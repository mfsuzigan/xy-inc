package com.inc.xy.poi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inc.xy.poi.exception.ValidationException;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.repository.PointRepository;
import com.inc.xy.poi.util.MessageUtils;

@Service
public class PointService {

	@Autowired
	private PointRepository repository;

	@Autowired
	private Validator validator;

	@Autowired
	private MessageUtils messageUtils;

	public List<Point> findAll() {
		return repository.findAll();
	}

	public Point save(Point point) {
		validate(point);
		return repository.save(point);
	}

	public List<Point> findInRadius(Point center, BigDecimal radiusLength) {
		validate(center);

		if (radiusLength == null || radiusLength.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException(messageUtils.get("msg.invalid.distance"), radiusLength);
		}

		return findAll().stream().filter(point -> point.getDistanceFrom(center).compareTo(radiusLength) <= 0)
				.collect(Collectors.toList());
	}

	public Point findById(Long id) {

		if (id == null) {
			throw new IllegalArgumentException(messageUtils.get("msg.point.id.required"));
		}

		return repository.findOne(id);
	}

	private void validate(Point point) {

		if (point == null) {
			throw new ValidationException(messageUtils.get("msg.null.point"), null);
		}

		Set<ConstraintViolation<Point>> constraintViolations = validator.validate(point);

		if (!constraintViolations.isEmpty()) {
			throw new ValidationException(constraintViolations);
		}
	}
}
