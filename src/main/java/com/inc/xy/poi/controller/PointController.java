package com.inc.xy.poi.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.inc.xy.poi.dto.ValidationDTO;
import com.inc.xy.poi.dto.ValidationErrorDTO;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.service.PointService;

@RestController
@RequestMapping("/points")
public class PointController {

	@Autowired
	private PointService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Point> findAll() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Point findById(@PathVariable(name = "id") Long id) {
		return new Point();

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Point> save(@Valid @RequestBody Point point) {
		// return ResponseEntity.created(location)

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(point.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/radius")
	public List<Point> findInRadius(@RequestParam(name = "fromX", required = true) Integer fromX,
			@RequestParam(name = "fromY", required = true) Integer fromY,
			@RequestParam(name = "length", required = true) BigDecimal length) {
		return findAll();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationDTO> handleValidationException(MethodArgumentNotValidException e) {

		ValidationDTO validation = new ValidationDTO();

		if (e.getBindingResult() != null && CollectionUtils.isNotEmpty(e.getBindingResult().getAllErrors())) {
			validation.setErrors(e.getBindingResult().getAllErrors().stream()
					.filter(error -> error.getClass().isAssignableFrom(FieldError.class))
					.map(error -> new ValidationErrorDTO((FieldError) error)).collect(Collectors.toList()));
		}

		return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
	}
}
