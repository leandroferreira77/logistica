package org.drmit.shortestpath.application.services.impl;

/**
 * This exception is raised when no shipping route could be found between the
 * origin and destination.
 * 
 * @author Leandro Ferreira
 */

import static org.junit.Assert.assertEquals;

import org.drmit.shortestpath.application.services.ServiceException;
import org.drmit.shortestpath.application.services.ShippingRatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DefaultShippingRatingService test case.
 * 
 * @author Leandro Ferreira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DefaultShippingRatingServiceTest {

	/** Shipping rating service. */
	@Autowired
	private ShippingRatingService shippingRatingService;

	/**
	 * Test method for
	 * {@link org.drmit.shortestpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRateZeroDistance() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(0, 10, 2.50),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.drmit.shortestpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRateZeroPrice() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(25, 10, 0), 0.001);
	}

	/**
	 * Test method for
	 * {@link org.drmit.shortestpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetShippingRateZeroMileage() throws ServiceException {
		assertEquals(0, shippingRatingService.getShippingRate(25, 0, 2.5),
				0.001);
	}

	/**
	 * Test method for
	 * {@link org.drmit.shortestpath.application.services.impl.DefaultShippingRatingService#getShippingRate(double, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingRate() throws ServiceException {
		assertEquals(6.25, shippingRatingService.getShippingRate(25, 10, 2.50),
				0.001);
	}

}
