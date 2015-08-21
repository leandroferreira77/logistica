/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Leandro Ferreira
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.drmit.shortestpath.infrastructure.repository.neo4j;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.drmit.shortestpath.domain.model.Route;
import org.drmit.shortestpath.domain.model.Leg;
import org.drmit.shortestpath.infrastructure.repository.RepositoryExeption;
import org.drmit.shortestpath.infrastructure.repository.RouteNotFoundRepositoryExeption;
import org.drmit.shortestpath.infrastructure.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This class provides a skeletal implementation of the RouteRepository
 * interface using the Neo4j database.
 * 
 * @author Leandro Ferreira
 * @see <a href="http://www.neo4j.org/">Neo4j - The World's Leading Graph
 *      Database</a>
 */
@Repository
public abstract class AbstractNeo4jRouteRepository implements RouteRepository {

	/** Leg index name. */
	public static final String LEGS_INDEX_NAME = "legs";

	/** Name property key. */
	public static final String NAME_PROPERTY_KEY = "name";

	/** Distance property key. */
	public static final String DISTANCE_PROPERTY_KEY = "distance";

	/** Relationship types between locations. */
	public static enum LocationRelationshipType implements RelationshipType {
		CONNECTED
	}

	/** Underlying graph database. */
	private GraphDatabaseService graphDatabase = null;

	/** Index for legs lookup and querying. */
	private Index<Node> legsIndex = null;

	/**
	 * Constructs a AbstractNeo4jRouteRepository object.
	 * 
	 * @param graphDatabase
	 *            the graphDatabase to use.
	 * @throws IllegalArgumentException
	 *             if graphDatabase is null.
	 */
	@Autowired
	public AbstractNeo4jRouteRepository(GraphDatabaseService graphDatabase) {
		// Sets the graph database
		setGraphDatabase(graphDatabase);

		try (final Transaction tx = graphDatabase.beginTx()) {
			// Gets the index for Nodes with the specified name
			setLegsIndex(graphDatabase.index().forNodes(LEGS_INDEX_NAME));

			// Commits the transaction
			tx.success();
		}
	}

	/**
	 * Gets the underlying graph database.
	 * 
	 * @return a reference to the underlying graph database.
	 */
	public GraphDatabaseService getGraphDatabase() {
		return graphDatabase;
	}

	/**
	 * Sets the underlying graph database.
	 * 
	 * @param graphDatabase
	 *            the graphDatabase to set.
	 * @throws IllegalArgumentException
	 *             if graphDatabase is null.
	 */
	protected void setGraphDatabase(GraphDatabaseService graphDatabase) {
		if (graphDatabase == null) {
			throw new IllegalArgumentException("graphDatabase is null");
		}
		this.graphDatabase = graphDatabase;
	}

	/**
	 * Gets the index for legs lookup and querying.
	 * 
	 * @return the index for legs lookup and querying.
	 */
	public Index<Node> getLegsIndex() {
		return legsIndex;
	}

	/**
	 * Sets the index for legs lookup and querying.
	 * 
	 * @param legsIndex
	 *            the legsIndex to set.
	 * @throws IllegalArgumentException
	 *             if legsIndex is null.
	 */
	private void setLegsIndex(Index<Node> legsIndex) {
		if (legsIndex == null) {
			throw new IllegalArgumentException("legsIndex is null");
		}
		this.legsIndex = legsIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.drmit.poc.dijkstra.infrastructure.repository.RouteRepository#saveLeg
	 * (org.drmit.poc.dijkstra.domain.model.RouteLeg)
	 */
	@Override
	public void saveLeg(Leg leg) throws RepositoryExeption {
		if (leg == null) {
			throw new IllegalArgumentException("leg is null");

		}

		try (final Transaction tx = graphDatabase.beginTx()) {
			// Persists the Leg into the repository
			persist(leg);

			// Commits the transaction
			tx.success();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.drmit.poc.dijkstra.infrastructure.repository.RouteRepository#saveLegs
	 * (java.util.List)
	 */
	@Override
	public void saveLegs(List<Leg> legs) throws RepositoryExeption {
		if (legs == null) {
			throw new IllegalArgumentException("leg is null");
		}

		try (final Transaction tx = graphDatabase.beginTx()) {
			// Persists all given Legs into the repository
			for (Leg leg : legs) {
				persist(leg);
			}

			// Commits the transaction
			tx.success();
		}
	}

	/**
	 * Persists a Leg into the repository. If the Leg already exists in the
	 * Repository, it will be updated.
	 * 
	 * @param leg
	 *            the leg to be saved.
	 * @throws RepositoryExeption
	 *             if a repository access error occurs.
	 */
	protected void persist(Leg leg) throws RepositoryExeption {
		if (leg == null) {
			throw new IllegalArgumentException("leg is null");
		}

		// Creates a relationship between the origin and the destination
		final Relationship relationship = getNode(leg.getOrigin())
				.createRelationshipTo(getNode(leg.getDestination()),
						LocationRelationshipType.CONNECTED);
		relationship.setProperty(DISTANCE_PROPERTY_KEY, leg.getDistance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.drmit.shortestpath.infrastructure.repository.RouteRepository#
	 * findShortestRoute(java.lang.String, java.lang.String)
	 */
	@Override
	public Route findShortestRoute(String origin, String destination)
			throws RouteNotFoundRepositoryExeption, RepositoryExeption {
		if (origin == null) {
			throw new IllegalArgumentException("origin is null");

		}
		if (destination == null) {
			throw new IllegalArgumentException("destination is null");
		}

		try (final Transaction tx = graphDatabase.beginTx()) {
			// Finds the Logistica between the origin and the destination
			final Path path = getPathFinder().findSinglePath(getNode(origin),
					getNode(destination));
			if (path == null) {
				throw new RouteNotFoundRepositoryExeption(String.format(
						"No route could be found between the %s and %s",
						origin, destination));
			}

			// Commits the transaction
			tx.success();

			// Returns a route constructed from the specified path
			return toRoute(path);
		}
	}

	/**
	 * Returns a route constructed from the specified path.
	 * 
	 * @param path
	 *            path for which a route is required.
	 * @return a route constructed from the specified path.
	 * @throws IllegalArgumentException
	 *             if path is null.
	 */
	protected Route toRoute(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}

		final List<Leg> legs = new LinkedList<Leg>();
		for (Relationship relationship : path.relationships()) {
			legs.add(toRouteLeg(relationship));
		}
		return new Route((String) path.startNode().getProperty(
				NAME_PROPERTY_KEY), (String) path.endNode()
				.getProperty(NAME_PROPERTY_KEY).toString(), legs);
	}

	/**
	 * Returns a route leg constructed from the specified relationship.
	 * 
	 * @param relationship
	 *            relationship for which a route leg is required.
	 * @return a route leg constructed from the relationship.
	 * @throws IllegalArgumentException
	 *             if relationship is null.
	 */
	protected Leg toRouteLeg(Relationship relationship) {
		if (relationship == null) {
			throw new IllegalArgumentException("relationship is null");
		}

		return new Leg((String) relationship.getStartNode().getProperty(
				NAME_PROPERTY_KEY), (String) relationship.getEndNode()
				.getProperty(NAME_PROPERTY_KEY),
				(double) relationship.getProperty(DISTANCE_PROPERTY_KEY));
	}

	/**
	 * Returns the node referred to by this name. If the node does not exist,
	 * then it will be created.
	 * 
	 * @param name
	 *            the node name.
	 * @return the node instance.
	 * @throws IllegalArgumentException
	 *             if name is null.
	 */
	protected Node getNode(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		Node node = null;

		try (final Transaction tx = graphDatabase.beginTx()) {
			// Returns exact matches from this index, given the key/value pair
			node = legsIndex.get(NAME_PROPERTY_KEY, name).getSingle();
			if (node == null) {
				// Creates a new node
				node = graphDatabase.createNode();
				node.setProperty(NAME_PROPERTY_KEY, name);
				legsIndex.add(node, NAME_PROPERTY_KEY, name);
			}

			// Commits the transaction
			tx.success();
		}

		return node;
	}

	/**
	 * Gets the path finder used to find the cheapest path between two nodes.
	 * 
	 * @return the path finder used to find the cheapest path between two nodes.
	 */
	protected abstract PathFinder<? extends Path> getPathFinder();

}
