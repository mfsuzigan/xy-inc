package com.inc.xy.poi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.repository.PointRepository;

@Service
public class PointService {

	@Autowired
	private PointRepository repository;

	public List<Point> findAll() {
		return repository.findAll();
	}

	public Point save(Point point) {
		repository.save(point);
		return point;
	}
}
