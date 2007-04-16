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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.VariableBase;

/**
 *     
 */
public class CompositeScope implements Scope {

	public final int start;
	public final int length;
	public final ProgramScope programScope;

	private Scope childScope;
	private Scope parentScope;

	/**
	 * List of ScopeBase each scope can be the head of scope chain
	 */
	private final List internalScopes = new LinkedList();

	public CompositeScope(final int start, final int length, ProgramScope programScope) {
		super();
		this.start = start;
		this.length = length;
		this.programScope = programScope;
	}

	public Scope getChild() {
		return childScope;
	}

	public Scope getParent() {
		return parentScope;
	}

	public Scope getScope(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public ScopeType getScopeType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Attribute lookup(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public Attribute probe(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addScope(Scope scope) {
		internalScopes.add(scope);
	}

	public Attribute lookup(VariableBase variable) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see {@link Scope}
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @see {@link Scope}  
	 */
	public int getLength() {
		return length;
	}

	public void toString(StringBuffer buffer, String tab) {
		// TODO Auto-generated method stub

	}
}
