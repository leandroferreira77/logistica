package org.drmit.shortestpath.infrastructure.repository.neo4j;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.drmit.shortestpath.domain.model.Route;
import org.drmit.shortestpath.domain.model.Leg;
import org.drmit.shortestpath.infrastructure.repository.RepositoryExeption;
import org.drmit.shortestpath.infrastructure.repository.RouteNotFoundRepositoryExeption;
import org.drmit.shortestpath.infrastructure.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DijkstraNeo4jRouteRepository test case.
 * 
 * @author Leandro Ferreira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DijkstraNeo4jRouteRepositoryTest {

	/** Location repository. */
	@Autowired
	private RouteRepository routeRepository;

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

		// Saves all given Legs into the repository
		routeRepository.saveLegs(legs);
	}

	/**
	 * Test method for
	 * {@link org.drmit.poc.dijkstra.infrastructure.repository.neo4j.AbstractNeo4jRouteRepository#findShortestRoute(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidOrigin() throws RouteNotFoundRepositoryExeption,
			RepositoryExeption {
		// Finds the shortest route between the origin and the destination
		routeRepository.findShortestRoute(null, "D");
	}

	/**
	 * Test method for
	 * {@link org.drmit.poc.dijkstra.infrastructure.repository.neo4j.AbstractNeo4jRouteRepository#findShortestRoute(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDestination()
			throws RouteNotFoundRepositoryExeption, RepositoryExeption {
		// Finds the shortest route between the origin and the destination
		routeRepository.findShortestRoute("A", null);
	}

	/**
	 * Test method for
	 * {@link org.drmit.poc.dijkstra.infrastructure.repository.neo4j.AbstractNeo4jRouteRepository#findShortestRoute(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testSameLocation() throws RouteNotFoundRepositoryExeption,
			RepositoryExeption {
		// Finds the shortest route between the origin and the destination
		final Route actualRoute = routeRepository.findShortestRoute("A", "A");

		// Expected legs
		final List<Leg> expectedLegs = Collections.emptyList();

		// Expected route
		final Route expectedRoute = new Route("A", "A", expectedLegs);

		// Asserts that the expected and the actual values are equals
		assertEquals(expectedRoute, actualRoute);
		assertEquals(expectedRoute.getLength(), 0, 0.001);
	}

	/**
	 * Test method for
	 * {@link org.drmit.poc.dijkstra.infrastructure.repository.neo4j.AbstractNeo4jRouteRepository#findShortestRoute(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testFindShortestRouteAToD() throws RouteNotFoundRepositoryExeption,
			RepositoryExeption {
		// Finds the shortest route between the origin and the destination
		final Route actualRoute = routeRepository.findShortestRoute("A", "D");

		// Expected legs
		final List<Leg> expectedLegs = new LinkedList<Leg>();
		expectedLegs.add(new Leg("A", "B", 10));
		expectedLegs.add(new Leg("B", "D", 15));

		// Expected route
		final Route expectedRoute = new Route("A", "D", expectedLegs);

		// Asserts that the expected and the actual values are equals
		assertEquals(expectedRoute, actualRoute);
		assertEquals(expectedRoute.getLength(), 25, 0.001);
	}

	/**
	 * Test method for
	 * {@link org.drmit.poc.dijkstra.infrastructure.repository.neo4j.AbstractNeo4jRouteRepository#findShortestRoute(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = RouteNotFoundRepositoryExeption.class)
	public void testNoRoute() throws RouteNotFoundRepositoryExeption,
			RepositoryExeption {
		// Finds the shortest route between the origin and the destination
		routeRepository.findShortestRoute("A", "X");
	}

}
