package org.drmit.shortestpath.presentation.converter;

import java.util.List;

import org.drmit.shortestpath.domain.model.Leg;

/**
 * Logistics network converter.
 * 
 * @author Leandro Ferreira
 */
public interface LogisticsNetworkConverter {

	/**
	 * Converts the string argument into a list of legs.
	 * 
	 * @param lexicalLegs
	 *            a string containing the lexical representation of the list of
	 *            legs.
	 * @return a list of legs value represented by the string argument.
	 * @throws IllegalArgumentException
	 *             if string parameter does not conform to lexical the lexical
	 *             representation of the list of legs.
	 */
	public List<Leg> parseLegs(String lexicalLegs);

}
