/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.search;

/**
 * <p>
 * Describes a search query by giving a textual pattern to search for.
 * </p>
 */
public class PatternQuerySpecification extends QuerySpecification {
	private int fSearchFor;
	private boolean fCaseSensitive;

	/**
	 * @param pattern
	 *            The string that the query should search for.
	 * @param searchFor
	 *            What kind of php element the query should search for.
	 * @param caseSensitive
	 *            Whether the query should be case sensitive.
	 * @param limitTo
	 *            The kind of occurrence the query should search for.
	 * @param scope
	 *            The scope to search in.
	 * @param scopeDescription
	 *            A human readable description of the search scope.
	 * 
	 * @see org.eclipse.jdt.core.search.SearchPattern#createPattern(java.lang.String, int, int, int)
	 */
	public PatternQuerySpecification(String pattern, int searchFor, boolean caseSensitive, IPHPSearchScope scope, String scopeDescription) {
		super(scope, scopeDescription, pattern);
		fSearchFor = searchFor;
		fCaseSensitive = caseSensitive;
	}

	/**
	 * Whether the query should be case sensitive.
	 * @return Whether the query should be case sensitive.
	 */
	public boolean isCaseSensitive() {
		return fCaseSensitive;
	}

	/**
	 * Returns what kind of php element the query should search for.
	 * 
	 * @return The kind of php element to search for (e.g. Class, function, etc.)
	 * 
	 * @see IPHPSearchConstants#CLASS
	 * @see IPHPSearchConstants#FUNCTION
	 * @see IPHPSearchConstants#CONSTANT
	 * @see IPHPSearchConstants#VARIABLE
	 */
	public int getSearchFor() {
		return fSearchFor;
	}
}
