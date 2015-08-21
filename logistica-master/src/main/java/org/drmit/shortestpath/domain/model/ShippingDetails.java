package org.drmit.shortestpath.domain.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * This class contains an order shipping details.
 * 
 * @author Leandro Ferreira
 */
public class ShippingDetails {

	/** Shipping rate. */
	private double shippingRate = 0;

	/** Shipping route. */
	private Route shippingRoute = null;

	/**
	 * Constructs a ShippingDetails object.
	 * 
	 * @param shippingRate
	 *            the shipping rate.
	 * @param shippingRoute
	 *            the shipping rate.
	 * @throws IllegalArgumentException
	 *             if shippingRate is negative.
	 * @throws IllegalArgumentException
	 *             if shippingRoute is null.
	 */
	public ShippingDetails(double shippingRate, Route shippingRoute) {
		setShippingRate(shippingRate);
		setShippingRoute(shippingRoute);
	}

	/**
	 * Gets the shipping rate.
	 * 
	 * @return the shipping rate.
	 */
	public double getShippingRate() {
		return shippingRate;
	}

	/**
	 * Sets the shipping rate.
	 * 
	 * @param shippingRate
	 *            the rate to set
	 * @throws IllegalArgumentExcpetion
	 *             if shippingRate is negative.
	 */
	private void setShippingRate(double shippingRate) {
		if (shippingRate < 0) {
			throw new IllegalArgumentException("shippingRate is negative");
		}
		this.shippingRate = shippingRate;
	}

	/**
	 * Gets the shipping route.
	 * 
	 * @return the shipping route.
	 */
	public Route getShippingRoute() {
		return shippingRoute;
	}

	/**
	 * Sets the shipping route.
	 * 
	 * @param shippingRoute
	 *            the route to set.
	 * @throws IllegalArgumentException
	 *             if shippingRoute is negative.
	 */
	private void setShippingRoute(Route shippingRoute) {
		if (shippingRoute == null) {
			throw new IllegalArgumentException("shippingRoute is null");
		}
		this.shippingRoute = shippingRoute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(shippingRate)
				.append(shippingRoute).toHashCode();
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
		final ShippingDetails rhs = (ShippingDetails) obj;
		return new EqualsBuilder().append(shippingRate, rhs.shippingRate)
				.append(shippingRoute, rhs.shippingRoute).isEquals();
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
