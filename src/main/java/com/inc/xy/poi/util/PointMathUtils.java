package com.inc.xy.poi.util;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import com.inc.xy.poi.model.Point;

/**
 * Classe utilitaria para calculos relacionados a {@link Point}s
 * 
 * @author Michel F. Suzigan
 *
 */
public final class PointMathUtils {

	private PointMathUtils() {
	}

	/**
	 * Retorna a distancia entre dois {@link Point}s com coordenadas nao-nulas
	 * ou <code>null</code> em caso contrario
	 * 
	 * @param point
	 * @param anotherPoint
	 */
	public static BigDecimal getDistanceBetween(Point point, Point anotherPoint) {
		BigDecimal distance = null;

		if (point != null && point.hasCoordinates() && anotherPoint != null && anotherPoint.hasCoordinates()) {

			BiFunction<BigDecimal, BigDecimal, Double> differenceToPowerOfTwo = (coordinate1, coordinate2) -> {
				Double delta = coordinate1.doubleValue() - coordinate2.doubleValue();
				return delta * delta;
			};

			double squaredSum = Math
					.sqrt(differenceToPowerOfTwo.apply(point.getxCoordinate(), anotherPoint.getxCoordinate())
							+ differenceToPowerOfTwo.apply(point.getyCoordinate(), anotherPoint.getyCoordinate()));

			distance = BigDecimal.valueOf(squaredSum);
		}

		return distance;
	}
}
