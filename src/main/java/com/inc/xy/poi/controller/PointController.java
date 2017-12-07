package com.inc.xy.poi.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inc.xy.poi.annotation.SelfLink;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.service.PointService;

@RestController
@RequestMapping("/points")
public class PointController {

	@Autowired
	private PointService service;

	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Point findById(@PathVariable(name = "id") Long id) {
		return service.findById(id);
	}

	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Point save(@Valid @RequestBody Point point) {
		return service.save(point);
	}

	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.GET)
	public List<Point> findAll(@RequestParam(name = "withCenterInX", required = false) BigDecimal xCenterCoordinate,
			@RequestParam(name = "withCenterInY", required = false) BigDecimal yCenterCoordinate,
			@RequestParam(name = "withRadiusLength", required = false) BigDecimal radiusLength) {

		if (xCenterCoordinate != null || yCenterCoordinate != null || radiusLength != null) {
			return service.findInRadius(new Point("center", xCenterCoordinate, yCenterCoordinate), radiusLength);
		} else {
			return service.findAll();
		}
	}
}
