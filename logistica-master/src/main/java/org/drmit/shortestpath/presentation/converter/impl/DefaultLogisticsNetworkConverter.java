package org.drmit.shortestpath.presentation.converter.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drmit.shortestpath.domain.model.Leg;
import org.drmit.shortestpath.presentation.converter.LogisticsNetworkConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Leandro Ferreira
 */
@Component
public class DefaultLogisticsNetworkConverter implements
		LogisticsNetworkConverter {

	/** Lines delimiters. */
	private static final String LINE_DELIMITERS = "\r\n";

	/** Leg pattern. */
	private static final Pattern LEG_PATTERN = Pattern
			.compile("\\s*(\\w+)\\s*(\\w+)\\s*(\\d+)\\s*");

	/**
	 * Constructs a DefaultLogisticsNetworkConverter object.
	 */
	public DefaultLogisticsNetworkConverter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.drmit.shortestpath.presentation.converter.LegConverter#parseLegs(
	 * java.lang.String)
	 */
	@Override
	public List<Leg> parseLegs(String lexicalLegs) {
		if (lexicalLegs == null) {
			throw new IllegalArgumentException("lexicalLegs is null");
		}

		// Lines tokenizer
		final StringTokenizer linesTokenizer = new StringTokenizer(lexicalLegs,
				LINE_DELIMITERS);

		// The legs list
		final List<Leg> legs = new LinkedList<Leg>();

		// Iterates over each line
		for (int lineNumber = 1; linesTokenizer.hasMoreTokens(); lineNumber++) {
			// Returns the next token in this string tokenizer's string
			final String line = linesTokenizer.nextToken();

			// Gets a matcher that will match the input against the pattern
			final Matcher matcher = LEG_PATTERN.matcher(line);

			// Checks if the line formatted correctly
			if (!matcher.matches()) {
				throw new IllegalArgumentException(String.format(
						"Invalid leg format at line %d", lineNumber));
			}

			// Parses the current line
			legs.add(new Leg(matcher.group(1), matcher.group(2), Double
					.valueOf(matcher.group(3))));
		}

		return legs;
	}

}
