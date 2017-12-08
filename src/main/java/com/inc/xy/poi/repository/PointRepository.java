package com.inc.xy.poi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inc.xy.poi.model.Point;

/**
 * Repositorio para operacoes relacionadas a {@link Point}
 * 
 * @author Michel F. Suzigan
 *
 */
public interface PointRepository extends JpaRepository<Point, Long> {
}
