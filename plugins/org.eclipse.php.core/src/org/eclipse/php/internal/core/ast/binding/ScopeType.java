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
package org.eclipse.php.internal.core.ast.binding;

/**
 * A Scope type,
 * GLOBAL_SCOPE    global scope of the script
 * FUNCTION_SCOPE  function scope
 * CLASS_SCOPE     class scope
 */
public class ScopeType {

	/**
	 * Represents a global scope type
	 */
	public static final ScopeType GLOBAL_SCOPE = new ScopeType("global"); //$NON-NLS-1$

	/**
	 * Represents a function scope type
	 */
	public static final ScopeType FUNCTION_SCOPE = new ScopeType("function"); //$NON-NLS-1$

	/**
	 * Represents a class scope type 
	 */
	public static final ScopeType CLASS_SCOPE = new ScopeType("class"); //$NON-NLS-1$

	/**
	 * Represents a method scope type (class function)
	 */
	public static final ScopeType METHOD_SCOPE = new ScopeType("method"); //$NON-NLS-1$

	private final String toString;

	private ScopeType(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}
}
