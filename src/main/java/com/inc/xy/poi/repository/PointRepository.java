package com.inc.xy.poi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inc.xy.poi.model.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
