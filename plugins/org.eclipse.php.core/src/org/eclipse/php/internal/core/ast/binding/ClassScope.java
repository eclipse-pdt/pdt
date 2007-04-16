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
 * Description:   
 */
public class ClassScope extends ScopeBase {

	public ClassScope(int start, ProgramScope programScope) {
		super(start, programScope);
		// TODO Auto-generated constructor stub
	}

	public Scope getChild() {
		return null;
	}

	public Scope getParent() {
		return null;
	}

	public Scope getScope(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public ScopeType getScopeType() {
		return ScopeType.CLASS_SCOPE;
	}

	public Attribute lookup(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toString(StringBuffer buffer, String tab) {
		// TODO Auto-generated method stub

	}
}
