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

import org.eclipse.php.internal.core.ast.nodes.VariableBase;

/**
 * Interface for scope information and actions
 */
public interface Scope {

	public static final String TAB = "\t"; //$NON-NLS-1$

	/**
	 * @return the parent Scope, null for the enclosing global scope 
	 */
	public abstract Scope getParent();

	/**
	 * @return the child Scope, null for the last scope 
	 */
	public abstract Scope getChild();

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope 
	 *         and the scope above
	 */
	public abstract Attribute lookup(VariableBase variable);

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope 
	 *         and the scope above
	 */
	public abstract Attribute lookup(String identifier);

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope (only) 
	 */
	public abstract Attribute probe(String identifier);

	/**
	 * @param offset
	 * @return the scope for the specified offset 
	 */
	public abstract Scope getScope(int offset);

	/**
	 * @return the start offset  
	 */
	public abstract int getStart();

	/**
	 * @return the length offset  
	 */
	public abstract int getLength();

	/**
	 * @return the scope's type
	 */
	public abstract ScopeType getScopeType();

	/**
	 * Buffers the actual node information
	 * @param buffer - buffer to write the content to
	 * @param tab - indentation
	 */
	public void toString(StringBuffer buffer, String tab);
}
