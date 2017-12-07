package com.inc.xy.poi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.service.PointService;

@RestController
@RequestMapping("/points")
public class PointController {

	@Autowired
	private PointService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Point> findAll() {
		return addLinks(service.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Point findById(@PathVariable(name = "id") Long id) {
		return addLinks(service.findById(id));

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Point save(@Valid @RequestBody Point point) {
		return addLinks(service.save(point));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/radius")
	public List<Point> findInRadius(@RequestParam(name = "fromX", required = true) BigDecimal fromX,
			@RequestParam(name = "fromY", required = true) BigDecimal fromY,
			@RequestParam(name = "length", required = true) BigDecimal length) {

		return addLinks(service.findInRadius(new Point("center", fromX, fromY), length));
	}

	private Point addLinks(Point point) {

		if (point != null && point.getPid() != null) {
			point.add(linkTo(methodOn(PointController.class).findById(point.getPid())).withSelfRel());
		}

		return point;
	}

	private List<Point> addLinks(List<Point> points) {

		if (CollectionUtils.isNotEmpty(points)) {
			points.forEach(this::addLinks);
		}

		return points;
	}
}
