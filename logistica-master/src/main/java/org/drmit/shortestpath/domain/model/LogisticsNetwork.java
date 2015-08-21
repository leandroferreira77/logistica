package org.drmit.shortestpath.domain.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * This class represents a logistics network.
 * 
 * @author Leandro Ferreira
 */
public class LogisticsNetwork {

	/** Network name. */
	private String name = null;

	/** Shipping legs. */
	private List<Leg> legs = null;

	/**
	 * Constructs a LogisticsNetwork object.
	 * 
	 * @param name
	 *            network name.
	 * @param legs
	 *            the shipping legs this network consist in.
	 * @throws IllegalArgumentException
	 *             if name or legs is null.
	 */
	public LogisticsNetwork(String name, List<Leg> legs) {
		setName(name);
		setLegs(legs);
	}

	/**
	 * Gets the network name.
	 * 
	 * @return the network name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the network name.
	 * 
	 * @param name
	 *            the name to set.
	 * @throws IllegalArgumentException
	 *             if name is null.
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}
		this.name = name;
	}

	/**
	 * Gets an unmodifiable view of the shipping legs this network consist in.
	 * 
	 * @return an unmodifiable view of the shipping legs this network consist
	 *         in.
	 */
	public List<Leg> getLegs() {
		return Collections.unmodifiableList(legs);
	}

	/**
	 * Sets the shipping legs this network consist in.
	 * 
	 * @param legs
	 *            the legs to set.
	 * @throws IllegalArgumentException
	 *             if legs is null.
	 */
	private void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).toHashCode();
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
		final LogisticsNetwork rhs = (LogisticsNetwork) obj;
		return new EqualsBuilder().append(name, rhs.name).isEquals();
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
