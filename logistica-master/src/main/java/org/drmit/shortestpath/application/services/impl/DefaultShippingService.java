package org.drmit.shortestpath.application.services.impl;

import org.drmit.shortestpath.application.services.NoShippingRouteServiceException;
import org.drmit.shortestpath.application.services.ServiceException;
import org.drmit.shortestpath.application.services.ShippingRatingService;
import org.drmit.shortestpath.application.services.ShippingService;
import org.drmit.shortestpath.domain.model.LogisticsNetwork;
import org.drmit.shortestpath.domain.model.Route;
import org.drmit.shortestpath.domain.model.ShippingDetails;
import org.drmit.shortestpath.infrastructure.repository.RouteNotFoundRepositoryExeption;
import org.drmit.shortestpath.infrastructure.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default ShippingService interface implementation.
 * 
 * @author Leandro Ferreira
 */
@Service
public class DefaultShippingService implements ShippingService {

	/** The underlying route repository implementation. */
	private RouteRepository routeRepository = null;

	/** The underlying shipping rating service implementation. */
	private ShippingRatingService shippingRatingService = null;

	/**
	 * Constructs a new DefaultShippingService object.
	 * 
	 * @param routeRepository
	 *            the underlying route repository implementation.
	 * @param shippingRatingService
	 *            the underlying shipping rating service implementation.
	 * @throws IllegalArgumentExcpetion
	 *             if routeRepository or shippingRatingService is null.
	 */
	@Autowired
	public DefaultShippingService(RouteRepository routeRepository,
			ShippingRatingService shippingRatingService) {
		setRouteRepository(routeRepository);
		setShippingRatingService(shippingRatingService);
	}

	/**
	 * Gets the underlying route repository used by this service.
	 * 
	 * @return the underlying route repository used by this service.
	 */
	public RouteRepository getRouteRepository() {
		return routeRepository;
	}

	/**
	 * Sets the underlying route repository used by this service.
	 * 
	 * @param routeRepository
	 *            the repository to set.
	 * @throws IllegalArgumentException
	 *             if routeRepository is null.
	 */
	private void setRouteRepository(RouteRepository routeRepository) {
		if (routeRepository == null) {
			throw new IllegalArgumentException("routeRepository is null");
		}
		this.routeRepository = routeRepository;
	}

	/**
	 * Gets the underlying shipping rating service implementation.
	 * 
	 * @return the underlying shipping rating service implementation.
	 */
	public ShippingRatingService getShippingRatingService() {
		return shippingRatingService;
	}

	/**
	 * Sets the underlying shipping rating service implementation.
	 * 
	 * @param shippingRatingService
	 *            the rating service to set
	 * @throws IllegalArgumentException
	 *             if shippingRatingService is null.
	 */
	private void setShippingRatingService(
			ShippingRatingService shippingRatingService) {
		if (shippingRatingService == null) {
			throw new IllegalArgumentException("shippingRatingService is null");
		}
		this.shippingRatingService = shippingRatingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.drmit.shortestpath.application.services.ShippingService#
	 * addLogisticsNetwork(org.drmit.shortestpath.domain.model.LogisticsNetwork)
	 */
	@Override
	public void addLogisticsNetwork(LogisticsNetwork logisticsNetwork)
			throws ServiceException {
		if (logisticsNetwork == null) {
			throw new IllegalArgumentException("logisticsNetwork is null");
		}

		try {
			// Saves all given Legs into the route repository
			routeRepository.saveLegs(logisticsNetwork.getLegs());
		} catch (Exception e) {
			// Propagates the exception
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.drmit.shortestpath.application.services.ShippingService#
	 * getShippingDetails(java.lang.String, java.lang.String, double, double)
	 */
	@Override
	public ShippingDetails getShippingDetails(String origin,
			String destination, double vehicleMileage, double fuelPrice)
			throws NoShippingRouteServiceException, ServiceException {
		if (origin == null) {
			throw new IllegalArgumentException("origin is null");
		}
		if (destination == null) {
			throw new IllegalArgumentException("destination is null");
		}
		if (vehicleMileage <= 0) {
			throw new IllegalArgumentException("vehicleMileage is invalid");
		}
		if (fuelPrice < 0) {
			throw new IllegalArgumentException("fuelPrice is negative");
		}

		try {
			// Gets the shortest shipping route
			final Route route = routeRepository.findShortestRoute(origin,
					destination);

			// Returns the order shipping details
			return new ShippingDetails(shippingRatingService.getShippingRate(
					route.getLength(), vehicleMileage, fuelPrice), route);
		} catch (RouteNotFoundRepositoryExeption e) {
			// Propagates the exception
			throw new NoShippingRouteServiceException(e.getMessage(), e);
		} catch (Exception e) {
			// Propagates the exception
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
