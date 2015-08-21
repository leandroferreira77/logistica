package org.drmit.shortestpath.infrastructure.repository.neo4j;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * AbstractNeo4jRouteRepository implementation using the Dijkstra algorithm.
 * 
 * @author Leandro Ferreira
 */
@Repository
public class DijkstraNeo4jRouteRepository extends AbstractNeo4jRouteRepository {

	/** Distance evaluator */
	final CostEvaluator<Double> costEvaluator = CommonEvaluators
			.doubleCostEvaluator(DISTANCE_PROPERTY_KEY);

	/**
	 * Path finder which uses the Dijkstra algorithm to find the cheapest path
	 * between two nodes.
	 */
	final PathFinder<WeightedPath> pathFinder = GraphAlgoFactory.dijkstra(
			PathExpanders.forTypeAndDirection(LocationRelationshipType.CONNECTED,
					Direction.OUTGOING), costEvaluator);
	
	/**
	 * Constructs a DijkstraNeo4jRouteRepository object.
	 * 
	 * @param graphDatabase
	 *            the graphDatabase to use.
	 * @throws IllegalArgumentException
	 *             if graphDatabase is null.
	 */
	@Autowired
	public DijkstraNeo4jRouteRepository(GraphDatabaseService graphDatabase) {
		super(graphDatabase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.drmit.poc.dijkstra.infrastructure.repository.neo4j.
	 * AbstractNeo4jRouteRepository#getPathFinder()
	 */
	@Override
	protected PathFinder<? extends Path> getPathFinder() {
		return pathFinder;
	}

}
