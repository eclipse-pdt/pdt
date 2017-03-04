/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.internal.corext.util.SearchUtils;

/**
 * A pattern matcher can match strings against various kinds of patterns
 * supported by {@link SearchPattern}: Prefix, "*" and "?" patterns, camelCase,
 * exact match with " " or "<" at the end of the pattern.
 * 
 * @since 5.0
 */
public class PatternMatcher {

	private String fPattern;
	private int fMatchKind;
	private StringMatcher fStringMatcher;

	private static final char END_SYMBOL = '<';
	private static final char ANY_STRING = '*';
	private static final char BLANK = ' ';

	public PatternMatcher(String pattern) {
		this(pattern, SearchPattern.R_EXACT_MATCH | SearchPattern.R_PREFIX_MATCH | SearchPattern.R_PATTERN_MATCH
				| SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH);
	}

	private PatternMatcher(String pattern, int allowedModes) {
		initializePatternAndMatchKind(pattern);
		fMatchKind = fMatchKind & allowedModes;
		if (fMatchKind == SearchPattern.R_PATTERN_MATCH) {
			fStringMatcher = new StringMatcher(fPattern, true, false);
		}
	}

	public String getPattern() {
		return fPattern;
	}

	public int getMatchKind() {
		return fMatchKind;
	}

	public boolean matches(String text) {
		switch (fMatchKind) {
		case SearchPattern.R_PATTERN_MATCH:
			return fStringMatcher.match(text);
		case SearchPattern.R_EXACT_MATCH:
			return fPattern.equalsIgnoreCase(text);
		case SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH:
			return org.eclipse.php.internal.ui.util.SearchPattern.camelCaseMatch(fPattern, text, true);
		case SearchPattern.R_CAMELCASE_MATCH:
			if (SearchPattern.camelCaseMatch(fPattern, text)) {
				return true;
			}
			// fall back to prefix match if camel case failed (bug 137244)
			return StringUtils.startsWithIgnoreCase(text, fPattern);
		default:
			return StringUtils.startsWithIgnoreCase(text, fPattern);
		}
	}

	private void initializePatternAndMatchKind(String pattern) {
		int length = pattern.length();
		if (length == 0) {
			fMatchKind = SearchPattern.R_EXACT_MATCH;
			fPattern = pattern;
			return;
		}
		char last = pattern.charAt(length - 1);

		if (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1) {
			fMatchKind = SearchPattern.R_PATTERN_MATCH;
			switch (last) {
			case END_SYMBOL:
			case BLANK:
				fPattern = pattern.substring(0, length - 1);
				break;
			case ANY_STRING:
				fPattern = pattern;
				break;
			default:
				fPattern = pattern + ANY_STRING;
			}
			return;
		}

		if (last == END_SYMBOL || last == BLANK) {
			fPattern = pattern.substring(0, length - 1);
			if (SearchPattern.validateMatchRule(fPattern,
					SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH) == SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH) {
				fMatchKind = SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH;
			} else {
				fMatchKind = SearchPattern.R_EXACT_MATCH;
			}
			return;
		}

		if (SearchUtils.isCamelCasePattern(pattern)) {
			fMatchKind = SearchPattern.R_CAMELCASE_MATCH;
			fPattern = pattern;
			return;
		}

		fMatchKind = SearchPattern.R_PREFIX_MATCH;
		fPattern = pattern;
	}
}
