/*******************************************************************************
 * Copyright (c) 2000, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - Adapt to PDT
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.internal.corext.util.SearchUtils;
import org.eclipse.dltk.internal.corext.util.Strings;

/**
 * A pattern matcher can match strings against various kinds of patterns
 * supported by {@link SearchPattern}: Prefix, "*" and "?" patterns, camelCase,
 * exact match with " " or "<" at the end of the pattern.
 *
 * @since 3.8
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
			return SearchPattern.camelCaseMatch(fPattern, text);
		case SearchPattern.R_CAMELCASE_MATCH:
			if (SearchPattern.camelCaseMatch(fPattern, text)) {
				return true;
			}
			// fall back to prefix match if camel case failed (bug 137244)
			return Strings.startsWithIgnoreCase(text, fPattern);
		default:
			return Strings.startsWithIgnoreCase(text, fPattern);
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