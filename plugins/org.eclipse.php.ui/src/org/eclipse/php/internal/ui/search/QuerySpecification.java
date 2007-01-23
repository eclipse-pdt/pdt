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
package org.eclipse.php.internal.ui.search;

/**
 * <p>
 * Describes a PHP search query. 
 * A query is described by giving a string and what kind of element to search
 * for (class, function, etc).
 * </p>
 * 
 * <p>
 * This class is not intended to be instantiated or subclassed by clients.
 * </p>
 * 
 */
public abstract class QuerySpecification {
	
	private IPHPSearchScope fScope;
	private String fScopeDescription;
	private String query;

	QuerySpecification(IPHPSearchScope scope, String scopeDescription, String query) {
		fScope= scope;
		fScopeDescription= scopeDescription;
		this.query = query;
	}

	/**
	 * Returns the search scope to be used in the query.
	 * @return The search scope.
	 */
	public IPHPSearchScope getScope() {
		return fScope;
	}
	
	/**
	 * Returns a human readable description of the search scope.
	 * @return A description of the search scope. 
	 * @see QuerySpecification#getScope()
	 */
	public String getScopeDescription() {
		return fScopeDescription;
	}
	
	/**
	 * Returns the query string
	 * 
	 * @return The query string
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query string
	 * 
	 * @param query The query string
	 */
	public void setQuery(String query) {
		this.query = query;
	}
}
