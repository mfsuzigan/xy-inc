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

@Entity
public class Point {

	private Long id;

	@NotNull(message = "{message.point.name.required}")
	@Size(min = 1, message = "{message.point.name.required}")
	private String name;

	@Digits(integer = 6, fraction = 0, message = "{message.point.invalid.coordinate}")
	@Range(min = 0, message = "{message.point.invalid.coordinate}")
	private BigDecimal xCoordinate;

	@Digits(integer = 6, fraction = 0, message = "{message.point.invalid.coordinate}")
	@Range(min = 0, message = "{message.point.invalid.coordinate}")
	private BigDecimal yCoordinate;

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

}
