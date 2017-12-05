package com.inc.xy.poi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
public class Point {

	private Long id;

	@NotNull(message = "{message.point.name.required}")
	@Size(min = 1, message = "{message.point.name.required}")
	private String name;

	@Digits(integer = 6, fraction = 0, message = "{message.point.invalid.coordinate}")
	@Range(min = 0, message = "{message.point.invalid.coordinate}")
	private Double xCoordinate;

	@Digits(integer = 6, fraction = 0, message = "{message.point.invalid.coordinate}")
	@Range(min = 0, message = "{message.point.invalid.coordinate}")
	private Double yCoordinate;

	public Point() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getxCoordinate() {
		return xCoordinate;
	}

	public Double getyCoordinate() {
		return yCoordinate;
	}

	public void setxCoordinate(Double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public void setyCoordinate(Double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public static boolean isValid(Point point) {
		return point != null && point.getxCoordinate() != null && point.getyCoordinate() != null;
	}

	public Double getDistanceFrom(Point anotherPoint) {
		Double distance = null;

		if (isValid(this) && isValid(anotherPoint)) {
			Double deltaX = Math.pow(xCoordinate - (anotherPoint.getxCoordinate()), 2.0);
			Double deltaY = Math.pow(yCoordinate - (anotherPoint.getyCoordinate()), 2.0);
			distance = Math.sqrt(deltaX + deltaY);
		}

		return distance;
	}
}
