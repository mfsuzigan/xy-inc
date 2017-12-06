package com.inc.xy.poi.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.inc.xy.poi.util.PointMathUtils;

@Entity
public class Point {

	private Long id;

	@NotNull(message = "{msg.point.name.required}")
	@Size(min = 1, message = "{msg.point.name.required}")
	private String name;

	@Digits(integer = 6, fraction = 0, message = "{msg.invalid.coordinate}")
	@Range(min = 0, message = "{msg.invalid.coordinate}")
	private BigDecimal xCoordinate;

	@Digits(integer = 6, fraction = 0, message = "{msg.invalid.coordinate}")
	@Range(min = 0, message = "{msg.invalid.coordinate}")
	private BigDecimal yCoordinate;

	public Point() {
	}

	public Point(String name, BigDecimal xCoordinate, BigDecimal yCoordinate) {
		this.name = name;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
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

	public BigDecimal getxCoordinate() {
		return xCoordinate;
	}

	public BigDecimal getyCoordinate() {
		return yCoordinate;
	}

	public void setxCoordinate(BigDecimal xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public void setyCoordinate(BigDecimal yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	@Override
	public String toString() {
		String xCoord = xCoordinate == null ? null : xCoordinate.toString();
		String yCoord = xCoordinate == null ? null : yCoordinate.toString();
		return String.format("['%s', %s, %s]", name, xCoord, yCoord);
	}

	public boolean hasCoordinates() {
		return xCoordinate != null && yCoordinate != null;
	}

	public BigDecimal getDistanceFrom(Point anotherPoint) {
		return PointMathUtils.getDistanceBetween(this, anotherPoint);
	}
}
