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
package org.eclipse.php.internal.core.ast.nodes;

/**
 * base class for all the static access
 */
public abstract class StaticDispatch extends VariableBase {

	private final Identifier className;

	public StaticDispatch(int start, int end, Identifier className) {
		super(start, end);

		assert className != null;
		this.className = className;

		className.setParent(this);
	}

	public Identifier getClassName() {
		return className;
	}

	public abstract ASTNode getProperty();
}
