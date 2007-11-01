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
 * Represents a base class for method invocation and field access
 * <pre>e.g.<pre> $a->$b,
 * foo()->bar(),
 * $myClass->foo()->bar(),
 * A::$a->foo()
 */
public abstract class Dispatch extends VariableBase {

	private final VariableBase dispatcher;

	public Dispatch(int start, int end, VariableBase dispatcher) {
		super(start, end);

		assert dispatcher != null;
		this.dispatcher = dispatcher;

		dispatcher.setParent(this);
	}

	public VariableBase getDispatcher() {
		return dispatcher;
	}

	/**
	 * @return the property of the diaptch
	 */
	public abstract VariableBase getMember();
}
