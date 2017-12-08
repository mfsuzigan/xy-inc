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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "Pontos de Interesse" })
@RestController
@RequestMapping("/points")
public class PointController {

	@Autowired
	private PointService service;

	@ApiOperation(value = "Consulta por identificador", notes = "Consulta um ponto de interesse com base em um identificador")
	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Point findById(@ApiParam("Identificador de um ponto de interesse") @PathVariable(name = "id") Long id) {
		return service.findById(id);
	}

	@ApiOperation(value = "Consulta", notes = "Consulta todos os pontos de interesses cadastrados ou aqueles dentro de um raio de abrangência com base nos parâmetros informados")
	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.GET)
	public List<Point> findAll(
			@ApiParam(allowEmptyValue = true, value = "Coordenada X para consulta de pontos de interesse dentro de um raio de abrangência") @RequestParam(name = "withCenterInX", required = false) BigDecimal xCenterCoordinate,
			@ApiParam(allowEmptyValue = true, value = "Coordenada Y para consulta de pontos de interesse dentro de um raio de abrangência") @RequestParam(name = "withCenterInY", required = false) BigDecimal yCenterCoordinate,
			@ApiParam(allowEmptyValue = true, value = "Distância máxima para consulta de pontos de interesse dentro de um raio de abrangência") @RequestParam(name = "withRadiusLength", required = false) BigDecimal radiusLength) {

		if (xCenterCoordinate != null || yCenterCoordinate != null || radiusLength != null) {
			return service.findInRadius(new Point("center", xCenterCoordinate, yCenterCoordinate), radiusLength);

		} else {
			return service.findAll();
		}
	}

	@ApiOperation(value = "Cadastro", notes = "Cadastra um ponto de interesse")
	@SelfLink(controllerIdMethodName = "findById", entityIdMethodName = "getPid")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Point save(@ApiParam("Ponto de interesse para cadastro") @Valid @RequestBody Point point) {
		return service.save(point);
	}

	@ApiOperation(value = "Exclusão", notes = "Exclui um ponto de interesse")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public void delete(@ApiParam("Identificador de um ponto de interesse") @PathVariable(name = "id") Long id) {
		service.delete(id);
	}

}
