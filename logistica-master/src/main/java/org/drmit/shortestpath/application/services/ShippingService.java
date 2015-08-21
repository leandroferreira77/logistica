package org.drmit.shortestpath.application.services;

import org.drmit.shortestpath.domain.model.LogisticsNetwork;
import org.drmit.shortestpath.domain.model.ShippingDetails;

/**
 * Shipping service.
 * 
 * @author Leandro Ferreira
 */
public interface ShippingService {

	/**
	 * Adds a new logistics network used for shipping route selection.
	 * 
	 * @param logisticsNetwork
	 *            the logistics network.
	 * @throws ServiceException
	 *             if a service access error occurs.
	 */
	public void addLogisticsNetwork(LogisticsNetwork logisticsNetwork)
			throws ServiceException;

	/**
	 * Gets an order shipping details.
	 * 
	 * @param origin
	 *            the origin of shipment.
	 * @param destination
	 *            the destination of shipment.
	 * @param vehicleMileage
	 *            vehicle mileage (in Kilometers per liter or KMPL).
	 * @param fuelPrice
	 *            fuel price per liter.
	 * @return the order shipping details.
	 * @throws NoShippingRouteServiceException
	 *             if no shipping route could be found between the origin and
	 *             destination.
	 * @throws ServiceException
	 *             if a service access error occurs.
	 */
	public ShippingDetails getShippingDetails(String origin,
			String destination, double vehicleMileage, double fuelPrice)
			throws NoShippingRouteServiceException, ServiceException;

}
