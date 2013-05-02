/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

public class DefaultExpression implements Expression {

	private String[] name;
	private String fullName;
	private ExpressionValue expressionValue;

	/**
	 * Creates new DefaultExpression
	 */
	public DefaultExpression(String expression) {
		name = new String[] { expression.trim() };
		fullName = expression;
		setValue(null);
	}

	public String[] getName() {
		return name;
	}

	public String getLastName() {
		return name[name.length - 1];
	}

	public String getFullName() {
		return fullName;
	}

	public void setValue(ExpressionValue value) {
		if (value == null) {
			value = ExpressionValue.NULL_VALUE;
		}
		this.expressionValue = value;
	}

	public ExpressionValue getValue() {
		return expressionValue;
	}

	public String toString() {
		return getLastName() + " = " + getValue().getValueAsString(); //$NON-NLS-1$
	}

	public Expression createChildExpression(String endName,
			String endRepresentation) {
		return new DefaultExpression(this, endName, endRepresentation);
	}

	protected DefaultExpression(Expression parent, String name,
			String representation) {
		String[] parentName = parent.getName();
		this.name = new String[parentName.length + 1];
		System.arraycopy(parentName, 0, this.name, 0, parentName.length);
		this.name[parentName.length] = name;
		fullName = parent.getFullName() + representation;
		setValue(null);
	}

}