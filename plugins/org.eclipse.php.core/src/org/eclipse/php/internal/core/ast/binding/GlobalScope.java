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
 * Global scope   
 */
public class GlobalScope extends ScopeBase {

	public final Scope parentScope;
	public GlobalScope childScope;

	protected GlobalScope(int start, Scope parentScope, GlobalScope childScope, ProgramScope programScope) {
		super(start, programScope);
		assert parentScope != null;

		this.parentScope = parentScope;
	}

	public static GlobalScope createRootScope(int start, ProgramScope programScope) {
		final GlobalScope globalScope = new GlobalScope(start, programScope, null, programScope);
		return globalScope;
	}

	public GlobalScope enterScope(int start) {
		final GlobalScope globalScope = new GlobalScope(start, this, null, programScope);
		this.childScope = globalScope;
		this.length = start - this.start;
		return globalScope;
	}

	public Scope getChild() {
		return childScope;
	}

	public Scope getParent() {
		return parentScope;
	}

	public Scope getScope(int offset) {
		switch (compareTo(offset)) {
			case 0:
				return this;
			case -1:
				return this.getChild();
			case 1:
				return this.getParent();
		}
		throw new IllegalArgumentException();
	}

	protected int compareTo(int offset) {
		return this.start <= offset ? (this.start + this.length > offset ? 0 : 1) : -1;
	}

	public ScopeType getScopeType() {
		return ScopeType.GLOBAL_SCOPE;
	}

	public Attribute lookup(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Scope");
		appendInterval(buffer);
		buffer.append(">");

		super.toString(buffer, tab + TAB);
		buffer.append("\n");

		final Scope child = getChild();
		if (child != null) {
			child.toString(buffer, tab + TAB);
		}

		buffer.append(tab).append("</Scope>\n");
	}
}
