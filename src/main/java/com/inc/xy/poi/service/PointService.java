package com.inc.xy.poi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
		validatePointForNullity(point);
		validatePointConstraints(point);
		return repository.save(point);
	}

	public void delete(Long id) {

		if (id != null) {
			try {
				repository.delete(id);

			} catch (EmptyResultDataAccessException e) {
				throw new IllegalArgumentException(messageUtils.get("msg.point.not.found"), e);
			}
		}
	}

	public List<Point> findInRadius(Point center, BigDecimal radiusLength) {
		validatePointForNullity(center);
		validatePointCoordinates(center);

		if (radiusLength == null || BigDecimal.ZERO.compareTo(radiusLength) > 0) {
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

	private void validatePointForNullity(Point point) {

		if (point == null) {
			throw new ValidationException(messageUtils.get("msg.null.point"), null);
		}
	}

	private void validatePointCoordinates(Point point) {

		if (point != null && (point.getxCoordinate() == null || point.getyCoordinate() == null)) {
			throw new ValidationException(messageUtils.get("msg.null.coordinates"), null);
		}
	}

	private void validatePointConstraints(Point point) {
		Set<ConstraintViolation<Point>> constraintViolations = validator.validate(point);

		if (!constraintViolations.isEmpty()) {
			throw new ValidationException(constraintViolations);
		}
	}
}
