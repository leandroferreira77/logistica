package org.drmit.shortestpath.domain.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Represents a single leg of the journey from the origin to the destination in
 * the calculated route.
 * 
 * @author Leandro Ferreira
 */
public class Leg implements Serializable {

	/** Serial version id. */
	private static final long serialVersionUID = 1L;

	/** Origin. */
	private String origin;

	/** Destination. */
	private String destination;

	/** The distance covered by this leg. */
	private double distance;

	/**
	 * Creates a new Leg object.
	 * 
	 * @param origin
	 *            the leg origin.
	 * @param destination
	 *            the leg destination.
	 * @param distance
	 *            the distance covered by this leg.
	 */
	public Leg(String origin, String destination, double distance) {
		setOrigin(origin);
		setDestination(destination);
		setDistance(distance);
	}

	/**
	 * Gets the leg origin.
	 * 
	 * @return the leg origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the leg origin.
	 * 
	 * @param origin
	 *            the origin to set.
	 * @throws IllegalArgumentException
	 *             if origin is null.
	 */
	private void setOrigin(String origin) {
		if (origin == null) {
			throw new IllegalArgumentException("origin is null");
		}
		this.origin = origin;
	}

	/**
	 * Gets the leg destination.
	 * 
	 * @return the leg destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the leg destination.
	 * 
	 * @param destination
	 *            the destination to set.
	 * @throws IllegalArgumentException
	 *             if destination is null.
	 */
	private void setDestination(String destination) {
		if (destination == null) {
			throw new IllegalArgumentException("destination is null");
		}
		this.destination = destination;
	}

	/**
	 * Gets the distance covered by this leg.
	 * 
	 * @return the distance covered by this leg.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance covered by this leg.
	 * 
	 * @param distance
	 *            the distance to set.
	 * @throws IllegalArgumentException
	 *             if distance is negative.
	 */
	private void setDistance(double distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("distance is negative");
		}
		this.distance = distance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(origin).append(destination)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Leg rhs = (Leg) obj;
		return new EqualsBuilder().append(origin, rhs.origin)
				.append(destination, rhs.destination).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
