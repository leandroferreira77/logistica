package org.drmit.shortestpath.application.services;

/**
 * Shipping rating service.
 * 
 * @author Leandro Ferreira
 */
public interface ShippingRatingService {

	/**
	 * Gets the order shipping rate.
	 * 
	 * @param distance
	 *            the route distance.
	 * @param vehicleMileage
	 *            vehicle mileage (in Kilometers per liter or KMPL).
	 * @param fuelPrice
	 *            fuel price per liter.
	 * @return the shipping rate.
	 * @throws ServiceException
	 *             if a service access error occurs.
	 */
	public double getShippingRate(double distance, double vehicleMileage,
			double fuelPrice) throws ServiceException;

}
