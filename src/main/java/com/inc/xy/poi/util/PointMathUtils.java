package com.inc.xy.poi.util;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import com.inc.xy.poi.model.Point;

public final class PointMathUtils {

	private PointMathUtils() {
	}

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
