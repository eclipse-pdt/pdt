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

	private VariableBase field;

	public Dispatch(int start, int end, AST ast, VariableBase dispatcher) {
		super(start, end, ast);

		assert dispatcher != null;
		this.field = dispatcher;

		dispatcher.setParent(this, getDispatcherProperty());
	}

	/**
	 * The dispatcher component of this dispatch expression
	 * 
	 * @return dispatcher component of this dispatch expression 
	 */
	public VariableBase getDispatcher() {
		return field;
	}
	
	/**
	 * Sets the dispatcher expression of this field access.
	 * 
	 * @param dispatcher the new expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setDispatcher(VariableBase dispatcher) {
		if (dispatcher == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.field;
		preReplaceChild(oldChild, dispatcher, getDispatcherProperty());
		this.field = dispatcher;
		postReplaceChild(oldChild, dispatcher, getDispatcherProperty());
	}
	
	ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == getDispatcherProperty()) {
			if (get) {
				return getDispatcher();
			} else {
				setDispatcher((VariableBase) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}
	

	/**
	 * @return the property descriptor of the dispatcher variable
	 */
	abstract ChildPropertyDescriptor getDispatcherProperty();
	
	/**
	 * @return the property of the dispatch
	 */
	public abstract VariableBase getMember();
}
