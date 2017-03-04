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

import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.compiler.util.ScannerHelper;
import org.eclipse.php.internal.core.search.StringOperation;

/**
 * Class SearchPattern is a utility class for any search utility that has to
 * find matches for search wildcards patterns.
 * 
 * @author shalom
 * 
 */
public class SearchPattern {

	// Rules for pattern matching: (exact, prefix, pattern) [ | case sensitive]
	/**
	 * Match rule: The search pattern matches exactly the search result, that
	 * is, the source of the search result equals the search pattern.
	 */
	public static final int R_EXACT_MATCH = 0;
	/**
	 * Match rule: The search pattern is a prefix of the search result.
	 */
	public static final int R_PREFIX_MATCH = 0x0001;
	/**
	 * Match rule: The search pattern contains one or more wild cards ('*' or
	 * '?'). A '*' wild-card can replace 0 or more characters in the search
	 * result. A '?' wild-card replaces exactly 1 character in the search
	 * result.
	 */
	public static final int R_PATTERN_MATCH = 0x0002;
	/**
	 * Match rule: The search pattern contains a regular expression.
	 */
	public static final int R_REGEXP_MATCH = 0x0004;
	/**
	 * Match rule: The search pattern matches the search result only if cases
	 * are the same. Can be combined to previous rules, e.g.
	 * {@link #R_EXACT_MATCH} | {@link #R_CASE_SENSITIVE}
	 */
	public static final int R_CASE_SENSITIVE = 0x0008;
	/**
	 * Match rule: The search pattern matches search results as
	 * raw/parameterized types/methods with same erasure. This mode has no
	 * effect on otherscriptelements search.<br>
	 * Type search example:
	 * <ul>
	 * <li>pattern: <code>List&lt;Exception&gt;</code></li>
	 * <li>match: <code>List&lt;Object&gt;</code></li>
	 * </ul>
	 * Method search example:
	 * <ul>
	 * <li>declaration: <code>&lt;T&gt;foo(T t)</code></li>
	 * <li>pattern: <code>&lt;Exception&gt;foo(new Exception())</code></li>
	 * <li>match: <code>&lt;Object&gt;foo(new Object())</code></li>
	 * </ul>
	 * Can be combined to all other match rules, e.g. {@link #R_CASE_SENSITIVE}
	 * | {@link #R_ERASURE_MATCH} This rule is not activated by default, so raw
	 * types or parameterized types with same erasure will not be found for
	 * pattern List&lt;String&gt;, Note that with this pattern, the match
	 * selection will be only on the erasure even for parameterized types.
	 * 
	 * 
	 */
	public static final int R_ERASURE_MATCH = 0x0010;
	/**
	 * Match rule: The search pattern matches search results as
	 * raw/parameterized types/methods with equivalent type parameters. This
	 * mode has no effect on otherscriptelements search.<br>
	 * Type search example:
	 * <ul>
	 * <li>pattern: <code>List&lt;Exception&gt;</code></li>
	 * <li>match:
	 * <ul>
	 * <li><code>List&lt;? extends Throwable&gt;</code></li>
	 * <li><code>List&lt;? super RuntimeException&gt;</code></li>
	 * <li><code>List&lt;?&gt;</code></li>
	 * </ul>
	 * </li>
	 * </ul>
	 * Method search example:
	 * <ul>
	 * <li>declaration: <code>&lt;T&gt;foo(T t)</code></li>
	 * <li>pattern: <code>&lt;Exception&gt;foo(new Exception())</code></li>
	 * <li>match:
	 * <ul>
	 * <li><code>&lt;? extends Throwable&gt;foo(new Exception())</code></li>
	 * <li><code>&lt;? super RuntimeException&gt;foo(new Exception())</code></li>
	 * <li><code>foo(new Exception())</code></li>
	 * </ul>
	 * </ul>
	 * Can be combined to all other match rules, e.g. {@link #R_CASE_SENSITIVE}
	 * | {@link #R_EQUIVALENT_MATCH} This rule is not activated by default, so
	 * raw types or equivalent parameterized types will not be found for pattern
	 * List&lt;String&gt;, This mode is overridden by {@link #R_ERASURE_MATCH}
	 * as erasure matches obviously include equivalent ones. That means that
	 * pattern with rule set to {@link #R_EQUIVALENT_MATCH} |
	 * {@link #R_ERASURE_MATCH} will return same results than rule only set with
	 * {@link #R_ERASURE_MATCH}.
	 * 
	 * 
	 */
	public static final int R_EQUIVALENT_MATCH = 0x0020;
	/**
	 * Match rule: The search pattern matches exactly the search result, that
	 * is, the source of the search result equals the search pattern.
	 * 
	 * 
	 */
	public static final int R_FULL_MATCH = 0x0040;
	/**
	 * Match rule: The search pattern contains a Camel Case expression. <br>
	 * Examples:
	 * <ul>
	 * <li><code>NPE</code> type string pattern will match
	 * <code>NullPointerException</code> and <code>NpPermissionException</code>
	 * types,</li>
	 * <li><code>NuPoEx</code> type string pattern will only match
	 * <code>NullPointerException</code> type.</li>
	 * </ul>
	 * 
	 * @see CharOperation#camelCaseMatch(char[], char[]) for a detailed
	 *      explanation of Camel Case matching. <br>
	 *      Can be combined to {@link #R_PREFIX_MATCH} match rule. For example,
	 *      when prefix match rule is combined with Camel Case match rule,
	 *      <code>"nPE"</code> pattern will match <code>nPException</code>. <br>
	 *      Match rule {@link #R_PATTERN_MATCH} may also be combined but both
	 *      rules will not be used simultaneously as they are mutually
	 *      exclusive. Used match rule depends on whether string pattern
	 *      contains specific pattern characters (e.g. '*' or '?') or not. If it
	 *      does, then only Pattern match rule will be used, otherwise only
	 *      Camel Case match will be used. For example, with <code>"NPE"</code>
	 *      string pattern, search will only use Camel Case match rule, but with
	 *      <code>N*P*E*</code> string pattern, it will use only Pattern match
	 *      rule.
	 * 
	 * 
	 */
	public static final int R_CAMELCASE_MATCH = 0x0080;

	/**
	 * Match rule: The search pattern contains a Camel Case expression with a
	 * strict expected number of parts. <br>
	 * Examples:
	 * <ul>
	 * <li>'HM' type string pattern will match 'HashMap' and 'HtmlMapper' types,
	 * but not 'HashMapEntry'</li>
	 * <li>'HMap' type string pattern will still match previous 'HashMap' and
	 * 'HtmlMapper' types, but not 'HighMagnitude'</li>
	 * </ul>
	 *
	 * This rule is not intended to be combined with any other match rule. In
	 * case of other match rule flags are combined with this one, then match
	 * rule validation will return a modified rule in order to perform a better
	 * appropriate search request (see {@link #validateMatchRule(String, int)}
	 * for more details).
	 * <p>
	 * 
	 * @see CharOperation#camelCaseMatch(char[], char[], boolean) for a detailed
	 *      explanation of Camel Case matching.
	 *      <p>
	 * @since 3.4
	 */
	public static final int R_CAMELCASE_SAME_PART_COUNT_MATCH = 0x0100;

	/**
	 * Match rule: The search pattern contains a substring expression in a
	 * case-insensitive way.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>'bar' string pattern will match 'bar1', 'Bar' and 'removeBar'
	 * types,</li>
	 * </ul>
	 *
	 * This rule is not intended to be combined with any other match rule. In
	 * case of other match rule flags are combined with this one, then match
	 * rule validation will return a modified rule in order to perform a better
	 * appropriate search request (see {@link #validateMatchRule(String, int)}
	 * for more details).
	 * 
	 * <p>
	 * This is implemented only for code assist and not available for normal
	 * search.
	 *
	 * @since 3.12
	 */
	public static final int R_SUBSTRING_MATCH = 0x0200;

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
	public static boolean match(String pattern, String str, boolean simulateWildcard) {
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
			return match(pattern.substring(1), str.substring(1), simulateWildcard);
		case '*': // '*' we can match 0 letters or more: match the rest of the
					// pattern with str (0 letters match) OR the pattern with
					// the rest of str (1 or more letters match)
			return match(pattern.substring(1), str, simulateWildcard)
					|| match(pattern, str.substring(1), simulateWildcard);
		default: // we have a letter in the pattern: check that the letter match
					// and the rest of the pattern match the rest of str
			return str.charAt(0) == pattern.charAt(0)
					&& match(pattern.substring(1), str.substring(1), simulateWildcard);
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
	public static boolean match(String pattern, String str, boolean caseSensitive, boolean simulateWildcard) {
		if (pattern == null) {
			return str == null;
		}
		if (str == null) {
			return false;
		}
		if (caseSensitive) {
			return match(pattern, str, simulateWildcard);
		} else {
			return match(pattern.toLowerCase(), str.toLowerCase(), simulateWildcard);
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

	public static final boolean camelCaseMatch(String pattern, String name) {
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (name == null)
			return false; // null name cannot match
		return camelCaseMatch(pattern, 0, pattern.length(), name, 0, name.length());
	}

	public static final boolean camelCaseMatch(String pattern, String name, boolean samePartCount) {
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (name == null)
			return false; // null name cannot match

		return camelCaseMatch(pattern, 0, pattern.length(), name, 0, name.length(), samePartCount);
	}

	public static final boolean camelCaseMatch(String pattern, int patternStart, int patternEnd, String name,
			int nameStart, int nameEnd, boolean samePartCount) {
		return StringOperation.getCamelCaseMatchingRegions(pattern, patternStart, patternEnd, name, nameStart, nameEnd,
				samePartCount) != null;
	}

	public static final boolean camelCaseMatch(String pattern, int patternStart, int patternEnd, String name,
			int nameStart, int nameEnd) {
		if (name == null)
			return false; // null name cannot match
		if (pattern == null)
			return true; // null pattern is equivalent to '*'
		if (patternEnd < 0)
			patternEnd = pattern.length();
		if (nameEnd < 0)
			nameEnd = name.length();
		if (patternEnd <= patternStart)
			return nameEnd <= nameStart;
		if (nameEnd <= nameStart)
			return false;
		// check first pattern char
		if (name.charAt(nameStart) != pattern.charAt(patternStart)) {
			// first char must strictly match (upper/lower)
			return false;
		}
		char patternChar, nameChar;
		int iPattern = patternStart;
		int iName = nameStart;
		// Main loop is on pattern characters
		while (true) {
			iPattern++;
			iName++;
			if (iPattern == patternEnd) {
				// We have exhausted pattern, so it's a match
				return true;
			}
			if (iName == nameEnd) {
				// We have exhausted name (and not pattern), so it's not a match
				return false;
			}
			// For as long as we're exactly matching, bring it on (even if it's
			// a lower case character)
			if ((patternChar = pattern.charAt(iPattern)) == name.charAt(iName)) {
				continue;
			}
			// If characters are not equals, then it's not a match if
			// patternChar is lowercase
			if (patternChar < ScannerHelper.MAX_OBVIOUS) {
				if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[patternChar] & ScannerHelper.C_UPPER_LETTER) == 0) {
					return false;
				}
			} else if (Character.isJavaIdentifierPart(patternChar) && !Character.isUpperCase(patternChar)) {
				return false;
			}
			// patternChar is uppercase, so let's find the next uppercase in
			// name
			while (true) {
				if (iName == nameEnd) {
					// We have exhausted name (and not pattern), so it's not a
					// match
					return false;
				}
				nameChar = name.charAt(iName);
				if (nameChar < ScannerHelper.MAX_OBVIOUS) {
					if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[nameChar]
							& (ScannerHelper.C_LOWER_LETTER | ScannerHelper.C_SPECIAL | ScannerHelper.C_DIGIT)) != 0) {
						// nameChar is lowercase
						iName++;
						// nameChar is uppercase...
					} else if (patternChar != nameChar) {
						// .. and it does not match patternChar, so it's not a
						// match
						return false;
					} else {
						// .. and it matched patternChar. Back to the big loop
						break;
					}
				} else if (Character.isJavaIdentifierPart(nameChar) && !Character.isUpperCase(nameChar)) {
					// nameChar is lowercase
					iName++;
					// nameChar is uppercase...
				} else if (patternChar != nameChar) {
					// .. and it does not match patternChar, so it's not a match
					return false;
				} else {
					// .. and it matched patternChar. Back to the big loop
					break;
				}
			}
			// At this point, either name has been exhausted, or it is at an
			// uppercase letter.
			// Since pattern is also at an uppercase letter
		}
	}

	public static int validateMatchRule(String stringPattern, int matchRule) {

		// Verify Regexp match rule
		if ((matchRule & R_REGEXP_MATCH) != 0) {
			// regexp is not supported yet
			return -1;
		}

		// Verify Pattern match rule
		if (stringPattern != null) {
			int starIndex = stringPattern.indexOf('*');
			int questionIndex = stringPattern.indexOf('?');
			if (starIndex < 0 && questionIndex < 0) {
				// reset pattern match flag if any
				matchRule &= ~R_PATTERN_MATCH;
			} else {
				// force Pattern rule
				matchRule |= R_PATTERN_MATCH;
			}
		}
		if ((matchRule & R_PATTERN_MATCH) != 0) {
			// reset other incompatible flags
			matchRule &= ~R_CAMELCASE_MATCH;
			matchRule &= ~R_CAMELCASE_SAME_PART_COUNT_MATCH;
			matchRule &= ~R_PREFIX_MATCH;
			return matchRule;
		}

		// Verify Camel Case
		if ((matchRule & R_CAMELCASE_MATCH) != 0) {
			// reset other incompatible flags
			matchRule &= ~R_CAMELCASE_SAME_PART_COUNT_MATCH;
			matchRule &= ~R_PREFIX_MATCH;
			// validate camel case rule and modify it if not valid
			boolean validCamelCase = validateCamelCasePattern(stringPattern);
			if (!validCamelCase) {
				matchRule &= ~R_CAMELCASE_MATCH;
				matchRule |= R_PREFIX_MATCH;
			}
			return matchRule;
		}

		// Verify Camel Case with same count of parts
		if ((matchRule & R_CAMELCASE_SAME_PART_COUNT_MATCH) != 0) {
			// reset other incompatible flags
			matchRule &= ~R_PREFIX_MATCH;
			// validate camel case rule and modify it if not valid
			boolean validCamelCase = validateCamelCasePattern(stringPattern);
			if (!validCamelCase) {
				matchRule &= ~R_CAMELCASE_SAME_PART_COUNT_MATCH;
			}
			return matchRule;
		}

		// Return the validated match rule (modified if necessary)
		return matchRule;
	}

	private static boolean validateCamelCasePattern(String stringPattern) {
		if (stringPattern == null)
			return true;
		// verify sting pattern validity
		int length = stringPattern.length();
		boolean validCamelCase = true;
		boolean lowerCamelCase = false;
		int uppercase = 0;
		for (int i = 0; i < length && validCamelCase; i++) {
			char ch = stringPattern.charAt(i);
			validCamelCase = i == 0 ? ScannerHelper.isScriptIdentifierStart(ch)
					: ScannerHelper.isScriptIdentifierPart(ch);
			// at least one uppercase character is need in CamelCase pattern
			// (see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=136313)
			if (ScannerHelper.isUpperCase(ch))
				uppercase++;
			if (i == 0)
				lowerCamelCase = uppercase == 0;
		}
		if (validCamelCase) {
			validCamelCase = lowerCamelCase ? uppercase > 0 : uppercase > 1;
		}
		return validCamelCase;
	}

	public static final int[] getMatchingRegions(String pattern, String name, int matchRule) {
		if (name == null)
			return null;
		final int nameLength = name.length();
		if (pattern == null) {
			return new int[] { 0, nameLength };
		}
		final int patternLength = pattern.length();
		boolean countMatch = false;
		switch (matchRule) {
		case SearchPattern.R_EXACT_MATCH:
			if (patternLength == nameLength && pattern.equalsIgnoreCase(name)) {
				return new int[] { 0, patternLength };
			}
			break;
		case SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE:
			if (patternLength == nameLength && pattern.equals(name)) {
				return new int[] { 0, patternLength };
			}
			break;
		case SearchPattern.R_PREFIX_MATCH:
			if (patternLength <= nameLength && name.substring(0, patternLength).equalsIgnoreCase(pattern)) {
				return new int[] { 0, patternLength };
			}
			break;
		case SearchPattern.R_PREFIX_MATCH | SearchPattern.R_CASE_SENSITIVE:
			if (name.startsWith(pattern)) {
				return new int[] { 0, patternLength };
			}
			break;
		case SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH:
			countMatch = true;
			// $FALL-THROUGH$
		case SearchPattern.R_CAMELCASE_MATCH:
			if (patternLength <= nameLength) {
				int[] regions = StringOperation.getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0,
						nameLength, countMatch);
				if (regions != null)
					return regions;
				if (name.substring(0, patternLength).equalsIgnoreCase(pattern)) {
					return new int[] { 0, patternLength };
				}
			}
			break;
		case SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH | SearchPattern.R_CASE_SENSITIVE:
			countMatch = true;
			// $FALL-THROUGH$
		case SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_CASE_SENSITIVE:
			if (patternLength <= nameLength) {
				return StringOperation.getCamelCaseMatchingRegions(pattern, 0, patternLength, name, 0, nameLength,
						countMatch);
			}
			break;
		case SearchPattern.R_PATTERN_MATCH:
			return StringOperation.getPatternMatchingRegions(pattern, 0, patternLength, name, 0, nameLength, false);
		case SearchPattern.R_PATTERN_MATCH | SearchPattern.R_CASE_SENSITIVE:
			return StringOperation.getPatternMatchingRegions(pattern, 0, patternLength, name, 0, nameLength, true);
		case SearchPattern.R_SUBSTRING_MATCH:
			if (patternLength <= nameLength) {
				int next = CharOperation.indexOf(pattern.toCharArray(), name.toCharArray(), false);
				return next >= 0 ? new int[] { next, patternLength } : null;
			}
			break;
		}
		return null;
	}
}
