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

/**
 * Operacoes de negocio relacionadas a pontos de interesse
 * 
 * @author Michel F. Suzigan
 *
 */
@Service
public class PointService {

	@Autowired
	private PointRepository repository;

	@Autowired
	private Validator validator;

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Lista todos os pontos de interesse cadastrados
	 * 
	 */
	public List<Point> findAll() {
		return repository.findAll();
	}

	/**
	 * Cadastra um ponto de interesse valido (com nome e coordenadas inteiras
	 * nao-negativas)
	 * 
	 * @param point
	 *            Ponto de interesse
	 */
	public Point save(Point point) {
		validatePointForNullity(point);
		validatePointConstraints(point);
		return repository.save(point);
	}

	/**
	 * Exclui um ponto de interesse se for informado um identificador. Se o
	 * identificador informado nao corresponder a um registro, lanca uma
	 * {@link IllegalArgumentException}
	 * 
	 * @param id
	 *            Identificador do ponto de interesse
	 */
	public void delete(Long id) {

		if (id != null) {
			try {
				repository.delete(id);

			} catch (EmptyResultDataAccessException e) {
				throw new IllegalArgumentException(messageUtils.get("msg.point.not.found"), e);
			}
		}
	}

	/**
	 * Dado um ponto de referencia P valido (com coordenadas) e uma distancia D
	 * (nao-negativa), retorna todos os pontos de interesse dentro de uma
	 * circunferencia com centro em P e raio igual a D
	 * 
	 * @param center
	 *            Ponto de referencia
	 * @param radiusLength
	 *            Distancia
	 */
	public List<Point> findInRadius(Point center, BigDecimal radiusLength) {
		validatePointForNullity(center);
		validatePointCoordinates(center);

		if (radiusLength == null || BigDecimal.ZERO.compareTo(radiusLength) > 0) {
			throw new ValidationException(messageUtils.get("msg.invalid.distance"), radiusLength);
		}

		return findAll().stream().filter(point -> point.getDistanceFrom(center).compareTo(radiusLength) <= 0)
				.collect(Collectors.toList());
	}

	/**
	 * Retorna, se houver, um ponto de interesse com o identificador informado
	 * 
	 * @param id
	 *            Identificador do ponto de interesse
	 */
	public Point findById(Long id) {

		if (id == null) {
			throw new IllegalArgumentException(messageUtils.get("msg.point.id.required"));
		}

		return repository.findOne(id);
	}

	/**
	 * Valida a nulidade do ponto informado, lancando uma
	 * {@link ValidationException} em caso de falha
	 * 
	 */
	private void validatePointForNullity(Point point) {

		if (point == null) {
			throw new ValidationException(messageUtils.get("msg.null.point"), null);
		}
	}

	/**
	 * Valida se, para o ponto nao-nulo informado, ambas as coordenadas sao
	 * nao-nulas. Desconsidera sinal ou se sao inteiras/decimais
	 * 
	 */
	private void validatePointCoordinates(Point point) {

		if (point != null && (point.getxCoordinate() == null || point.getyCoordinate() == null)) {
			throw new ValidationException(messageUtils.get("msg.null.coordinates"), null);
		}
	}

	/**
	 * Invoca todas Bean validations da entidade {@link Point}, lancando uma
	 * {@link ValidationException} com as violacoes encontradas
	 * 
	 */
	private void validatePointConstraints(Point point) {
		Set<ConstraintViolation<Point>> constraintViolations = validator.validate(point);

		if (!constraintViolations.isEmpty()) {
			throw new ValidationException(constraintViolations);
		}
	}
}
