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

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import java.util.HashSet;
import java.util.Set;

public class DefaultExpression implements Expression {

	private String[] name;
	private String fullName;
	private ExpressionValue expressionValue;
	private Set<Facet> facets = new HashSet<Facet>();

	/**
	 * Creates new default expression.
	 * 
	 * @param expression
	 */
	public DefaultExpression(String expression) {
		this(expression, expression);
	}

	/**
	 * Creates new default expression.
	 * 
	 * @param expression
	 */
	public DefaultExpression(String expression, String fullName) {
		name = new String[] { expression.trim() };
		this.fullName = fullName;
		setValue(null);
	}

	/**
	 * Creates new default expression.
	 * 
	 * @param expression
	 * @param facets
	 */
	public DefaultExpression(String expression, Facet... facets) {
		this(expression, expression, facets);
	}

	/**
	 * Creates new default expression.
	 * 
	 * @param expression
	 * @param facets
	 */
	public DefaultExpression(String expression, String fullName, Facet... facets) {
		name = new String[] { expression.trim() };
		this.fullName = fullName;
		setValue(null);
		addFacets(facets);
	}

	/**
	 * Creates new default expression.
	 * 
	 * @param parent
	 * @param name
	 * @param representation
	 * @param facets
	 */
	protected DefaultExpression(Expression parent, String name, String representation, Facet... facets) {
		String[] parentName = parent.getName();
		this.name = new String[parentName.length + 1];
		System.arraycopy(parentName, 0, this.name, 0, parentName.length);
		this.name[parentName.length] = name;
		fullName = parent.getFullName() + representation;
		setValue(null);
		setChildFacets(name, facets);
		addFacets(facets);
	}

	private void setChildFacets(String name, Facet... facets) {
		for (Facet facet : facets) {
			if (facet == KIND_OBJECT_MEMBER) {
				if (name.startsWith("*::")) { //$NON-NLS-1$
					addFacets(MOD_PROTECTED);
				} else if (name.contains("::")) { //$NON-NLS-1$
					addFacets(MOD_PRIVATE);
				} else {
					addFacets(MOD_PUBLIC);
				}
			}
		}
	}

	@Override
	public void addFacets(Facet... facets) {
		for (Facet facet : facets)
			this.facets.add(facet);
	}

	@Override
	public Expression createChildExpression(String endName, String endRepresentation, Facet... facets) {
		return new DefaultExpression(this, endName, endRepresentation, facets);
	}

	@Override
	public String[] getName() {
		return name;
	}

	@Override
	public String getLastName() {
		return name[name.length - 1];
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public ExpressionValue getValue() {
		return expressionValue;
	}

	@Override
	public boolean hasFacet(Facet facet) {
		return facets.contains(facet);
	}

	@Override
	public void setValue(ExpressionValue value) {
		if (value == null) {
			value = ExpressionValue.NULL_VALUE;
		}
		this.expressionValue = value;
	}

	@Override
	public String toString() {
		return getLastName() + " = " + getValue().getValueAsString(); //$NON-NLS-1$
	}

}