/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

/**
 * Class SearchPattern is a utility class for any search utility that has to
 * find matches for search wildcards patterns.
 * 
 * @author shalom
 * 
 */
public class SearchPattern {

	private SearchPattern() {
		super();
	}

	/**
	 * Checks if a given string matches a given pattern. The pattern can contain
	 * the '*' symbol which can replace any series of carachters, and the '?'
	 * symbol which can replace any single character. If third argument is
	 * <code>true</code>, then pattern match behaves as if the given pattern
	 * ends with '*', thus, for example, matching 'aabbc' string with 'aabb'
	 * pattern will return a true result. The match is case sensitive.
	 * 
	 * @param pattern
	 *            The string pattern
	 * @param str
	 *            The string to match
	 * @param simulateWildcard
	 *            simulate a wildcard at the end of the pattern
	 * @return True, if there is a pattern match; False, otherwise.
	 * @see
	 */
	public static boolean match(String pattern, String str,
			boolean simulateWildcard) {
		if (pattern.length() == 0) {
			if (simulateWildcard) {
				return true;
			}
			return (str.length() == 0);
		}
		if (str.length() == 0) {
			// if the pattern doesn't begin with '*' (s = "") return false
			// otherwise check the rest of the pattern
			if (pattern.charAt(0) != '*') {
				return false;
			}
			return match(pattern.substring(1), str, simulateWildcard);
		}
		switch (pattern.charAt(0)) {
		case '?': // '?' we matched one letter - continue matching the rest of
					// the pattern with the rest of str
			return match(pattern.substring(1), str.substring(1),
					simulateWildcard);
		case '*': // '*' we can match 0 letters or more: match the rest of the
					// pattern with str (0 letters match) OR the pattern with
					// the rest of str (1 or more letters match)
			return match(pattern.substring(1), str, simulateWildcard)
					|| match(pattern, str.substring(1), simulateWildcard);
		default: // we have a letter in the pattern: check that the letter match
					// and the rest of the pattern match the rest of str
			return str.charAt(0) == pattern.charAt(0)
					&& match(pattern.substring(1), str.substring(1),
							simulateWildcard);
		}
	}

	/**
	 * Checks if a given string matches a given pattern. The pattern can contain
	 * the '*' symbol which can replace any series of carachters, and the '?'
	 * symbol which can replace any single character. If fourth argument is
	 * <code>true</code>, then pattern match behaves as if the given pattern
	 * ends with '*', thus, for example, matching 'aabbc' string with 'aabb'
	 * pattern will return a true result.
	 * 
	 * @param pattern
	 *            The string pattern
	 * @param str
	 *            The string to match
	 * @param caseSensitive
	 *            Indicate whether to match as case sensitive or not.
	 * @param simulateWildcard
	 *            simulate a wildcard at the end of the pattern
	 * @return True, if there is a pattern match; False, otherwise.
	 */
	public static boolean match(String pattern, String str,
			boolean caseSensitive, boolean simulateWildcard) {
		if (pattern == null) {
			return str == null;
		}
		if (str == null) {
			return false;
		}
		if (caseSensitive) {
			return match(pattern, str, simulateWildcard);
		} else {
			return match(pattern.toLowerCase(), str.toLowerCase(),
					simulateWildcard);
		}
	}

	/**
	 * Checks if a given string matches a given pattern. The pattern can contain
	 * the '*' symbol which can replace any series of carachters, and the '?'
	 * symbol which can replace any single character. The pattern match behaves
	 * as if the given pattern ends with '*', thus, for example, matching
	 * 'aabbc' string with 'aabb' pattern will return a true result. The search
	 * is case sensitive.
	 * 
	 * @param pattern
	 *            The string pattern
	 * @param str
	 *            The string to match
	 */
	public static boolean match(String pattern, String str) {
		return match(pattern, str, true, true);
	}
}
