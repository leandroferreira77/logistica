package org.drmit.shortestpath.application.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.drmit.shortestpath.application.services.NoShippingRouteServiceException;
import org.drmit.shortestpath.application.services.ServiceException;
import org.drmit.shortestpath.application.services.ShippingService;
import org.drmit.shortestpath.domain.model.Leg;
import org.drmit.shortestpath.domain.model.LogisticsNetwork;
import org.drmit.shortestpath.domain.model.Route;
import org.drmit.shortestpath.domain.model.ShippingDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DefaultShippingService test case.
 * 
 * @author Leandro Ferreira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DefaultShippingServiceTest {

	/** Shipping service. */
	@Autowired
	private ShippingService shippingService;

	/**
	 * Sets up the fixture.
	 * 
	 * @throws Exception
	 *             if an error occurs.
	 */
	@Before
	public void setUp() throws Exception {
		// Creates the route legs
		final List<Leg> legs = new LinkedList<Leg>();
		legs.add(new Leg("A", "B", 10));
		legs.add(new Leg("B", "D", 15));
		legs.add(new Leg("A", "C", 20));
		legs.add(new Leg("C", "D", 20));
		legs.add(new Leg("B", "E", 50));
		legs.add(new Leg("D", "E", 50));

		// Creates the logistics network
		final LogisticsNetwork logisticsNetwork = new LogisticsNetwork(
				"Sample Logistics Network", legs);

		// Registers the logistics network
		shippingService.addLogisticsNetwork(logisticsNetwork);
	}

	/**
	 * Test method for
	 * {@link org.drmit.shortestpath.application.services.impl.DefaultShippingService#getShippingDetails(java.lang.String, java.lang.String, double, double)}
	 * .
	 */
	@Test
	public void testGetShippingDetails()
			throws NoShippingRouteServiceException, ServiceException {
		// Gets the order shipping details
		final ShippingDetails shippingDetails = shippingService
				.getShippingDetails("A", "D", 10, 2.5);

		// Expected legs
		final List<Leg> expectedLegs = new LinkedList<Leg>();
		expectedLegs.add(new Leg("A", "B", 10));
		expectedLegs.add(new Leg("B", "D", 15));

		// Expected route
		final Route expectedRoute = new Route("A", "D", expectedLegs);

		// Asserts that the expected and the actual values are equals
		assertEquals(6.25, shippingDetails.getShippingRate(), 0.001);
		assertEquals(expectedRoute, shippingDetails.getShippingRoute());
	}

}
