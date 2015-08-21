package org.drmit.shortestpath.application.services.impl;

import org.drmit.shortestpath.application.services.ServiceException;
import org.drmit.shortestpath.application.services.ShippingRatingService;
import org.springframework.stereotype.Service;

/**
 * Default ShippingRatingService interface implementation.
 * 
 * @author Leandro Ferreira
 */
@Service
public class DefaultShippingRatingService implements ShippingRatingService {

	/**
	 * Constructs a new DefaultShippingRateService object.
	 */
	public DefaultShippingRatingService() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.drmit.shortestpath.application.services.ShippingRatingService#
	 * getShippingRate(double, double, double)
	 */
	@Override
	public double getShippingRate(double distance, double vehicleMileage,
			double fuelPrice) throws ServiceException {
		if (distance < 0) {
			throw new IllegalArgumentException("distance is null");
		}
		if (vehicleMileage <= 0) {
			throw new IllegalArgumentException("vehicleMileage is invalid");
		}
		if (fuelPrice < 0) {
			throw new IllegalArgumentException("fuelPrice is null");
		}

		return ((distance * fuelPrice) / vehicleMileage);
	}

}
