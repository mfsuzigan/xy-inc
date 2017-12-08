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
import org.springframework.hateoas.ResourceSupport;

import com.inc.xy.poi.util.PointMathUtils;

@Entity
public class Point extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;

	@NotNull(message = "{msg.point.name.required}")
	@Size(min = 1, message = "{msg.point.name.required}")
	private String name;

	@NotNull(message = "{msg.null.x.coordinate}")
	@Digits(integer = 6, fraction = 0, message = "{msg.invalid.coordinate}")
	@Range(min = 0, message = "{msg.invalid.coordinate}")
	private BigDecimal xCoordinate;

	@NotNull(message = "{msg.null.y.coordinate}")
	@Digits(integer = 6, fraction = 0, message = "{msg.invalid.coordinate}")
	@Range(min = 0, message = "{msg.invalid.coordinate}")
	private BigDecimal yCoordinate;

	public Point() {
		super();
	}

	public Point(String name, BigDecimal xCoordinate, BigDecimal yCoordinate) {
		this.name = name;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public Long getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((xCoordinate == null) ? 0 : xCoordinate.hashCode());
		result = prime * result + ((yCoordinate == null) ? 0 : yCoordinate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Point other = (Point) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (pid == null) {
			if (other.pid != null) {
				return false;
			}
		} else if (!pid.equals(other.pid)) {
			return false;
		}
		if (xCoordinate == null) {
			if (other.xCoordinate != null) {
				return false;
			}
		} else if (!xCoordinate.equals(other.xCoordinate)) {
			return false;
		}
		if (yCoordinate == null) {
			if (other.yCoordinate != null) {
				return false;
			}
		} else if (!yCoordinate.equals(other.yCoordinate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String xCoordStr = xCoordinate == null ? null : xCoordinate.toString();
		String yCoordStr = xCoordinate == null ? null : yCoordinate.toString();
		return String.format("['%s', (%s, %s)]", name, xCoordStr, yCoordStr);
	}

	public boolean hasCoordinates() {
		return xCoordinate != null && yCoordinate != null;
	}

	public BigDecimal getDistanceFrom(Point anotherPoint) {
		return PointMathUtils.getDistanceBetween(this, anotherPoint);
	}
}
