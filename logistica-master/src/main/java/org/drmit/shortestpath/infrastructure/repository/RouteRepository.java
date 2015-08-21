package org.drmit.shortestpath.infrastructure.repository;

import java.util.List;

import org.drmit.shortestpath.domain.model.Route;
import org.drmit.shortestpath.domain.model.Leg;

/**
 * This interface defines a Route repository.
 * 
 * @author Leandro Ferreira
 */
public interface RouteRepository {

	/**
	 * Saves a Leg into the repository. If the Leg already exists in the
	 * Repository, it will be updated.
	 * 
	 * @param leg
	 *            the leg to be saved.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public void saveLeg(Leg leg) throws RepositoryExeption;

	/**
	 * Saves all given Legs into the repository. If a Leg already exists in the
	 * Repository, it will be updated.
	 * 
	 * @param legs
	 *            the list of legs to be saved.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public void saveLegs(List<Leg> legs) throws RepositoryExeption;

	/**
	 * Tries to find the shortest route between the origin and the destination.
	 * 
	 * @param origin
	 *            the route origin.
	 * @param destination
	 *            the route destination.
	 * @return the shortest route between the origin and the destination.
	 * @throws RouteNotFoundRepositoryExeption
	 *             if no route could be found between the origin and
	 *             destination.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	public Route findShortestRoute(String origin, String destination)
			throws RouteNotFoundRepositoryExeption, RepositoryExeption;

}
