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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;

/**
 * Interface for scope information and actions
 */
public class ProgramScope extends AbstractVisitor implements Scope {

	private GlobalScope rootGlobalScope;
	/**
	 * <Identifier, ClassScope>
	 */
	private final Map classScopes = new HashMap();
	/**
	 * <Identifier, FunctionScope>
	 */
	private final Map functionScopes = new HashMap();

	private GlobalScope currentGlobalScope;

	public ProgramScope(Program program) {
		// root global scope
		rootGlobalScope = GlobalScope.createRootScope(program.getStart(), this);
		currentGlobalScope = rootGlobalScope;

		program.accept(this);
	}

	private void attachAttributes(int start, ListVariable list, Attribute attribute) {

		VariableBase[] variables = list.getVariables();
		Attribute[] attributes = new Attribute[variables.length];
		if (attribute.getType() == AttributeType.ARRAY_ATTRIBUTE) {
			ArrayAttribute arrayAttribute = (ArrayAttribute) attribute;
			int i = 0;
			for (Iterator iter = arrayAttribute.getAttrributes().iterator(); iter.hasNext();) {
				Attribute element = (Attribute) iter.next();
				attributes[i] = element;
				i++;
			}
		} else {
			attributes[0] = attribute;
		}

		for (int i = 0; i < variables.length; i++) {
			VariableBase variable = variables[i];
			attachVariableBase(start, variable, attributes[i]);
		}
	}

	private void attachIndexedVariable(int start, Attribute attribute, final ArrayAccess variable) {
		Expression variableName = variable.getVariableName();
		if (variableName.getType() == ASTNode.IDENTIFIER) {
			String identifier = ((Identifier) variableName).getName();
			final ArrayAttribute arrayAttribute = new ArrayAttribute();
			arrayAttribute.addAttribute(attribute);
			updateScope(start, identifier, arrayAttribute);
		}
	}

	private void attachVariable(int start, Attribute attribute, final Variable variable) {
		Expression variableName = variable.getVariableName();
		if (variableName.getType() == ASTNode.IDENTIFIER) {
			String identifier = ((Identifier) variableName).getName();
			updateScope(start, identifier, attribute);
		}
	}

	private void attachVariableBase(int start, final VariableBase variable, Attribute attribute) {
		if (attribute == null) {
			attribute = Attribute.NULL_ATTRIBUTE;
		}

		switch (variable.getType()) {
			case ASTNode.LIST_VARIABLE:
				attachAttributes(start, (ListVariable) variable, attribute);
				break;
			case ASTNode.METHOD_INVOCATION:
				break;
			case ASTNode.FIELD_ACCESS:
				break;
			case ASTNode.ARRAY_ACCESS:
				attachIndexedVariable(start, attribute, (ArrayAccess) variable);
				break;
			case ASTNode.REFLECTION_VARIABLE:
				break;
			case ASTNode.STATIC_FIELD_ACCESS:
				break;
			case ASTNode.VARIABLE:
				attachVariable(start, attribute, (Variable) variable);
				break;
			default:
				throw new IllegalArgumentException("Bad assignment"); //$NON-NLS-1$
		}
	}

	public Scope getChild() {
		return null;
	}

	/**
	 * @see {@link Scope#getLength()}  
	 */
	public int getLength() {
		return Integer.MAX_VALUE;
	}

	public Scope getParent() {
		return null;
	}

	public Scope getScope(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public ScopeType getScopeType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see {@link Scope#getStart()}
	 */
	public int getStart() {
		return 0;
	}

	public Attribute lookup(StaticConstantAccess classConstant) {
		// TODO : complete method
		return Attribute.NULL_ATTRIBUTE;
	}

	public Attribute lookup(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public Attribute lookup(VariableBase variable) {
		// TODO Auto-generated method stub
		return null;
	}

	public Attribute probe(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	private Attribute resolveAttribute(Expression value) {
		return TypeResolver.resolve(currentGlobalScope, value);
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		toString(buffer, ""); //$NON-NLS-1$
		return buffer.toString();
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ProgramScope>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<RootGlobalScope>\n"); //$NON-NLS-1$
		rootGlobalScope.toString(buffer, TAB + TAB + tab);
		buffer.append(TAB).append(tab).append("</RootGlobalScope>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<ClassScopes>\n"); //$NON-NLS-1$
		for (Iterator iter = classScopes.values().iterator(); iter.hasNext();) {
			Scope scope = (Scope) iter.next();
			scope.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</ClassScopes>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<FunctionScopes>\n"); //$NON-NLS-1$
		for (Iterator iter = functionScopes.values().iterator(); iter.hasNext();) {
			Scope scope = (Scope) iter.next();
			scope.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</FunctionScopes>\n"); //$NON-NLS-1$

		buffer.append(tab).append("</ProgramScope>"); //$NON-NLS-1$
	}

	private void updateScope(int start, String identifier, Attribute attribute) {
		Attribute found = currentGlobalScope.lookup(identifier);
		if ((found == null || !found.equals(attribute)) && start != currentGlobalScope.start) {
			// open new scope
			currentGlobalScope = currentGlobalScope.enterScope(start);
		}
		currentGlobalScope.insertIdentifier(identifier, attribute);
	}

	public void visit(Assignment assignment) {
		Attribute attribute = resolveAttribute(assignment.getValue());
		attachVariableBase(assignment.getStart(), assignment.getVariable(), attribute);
	}

	public void visit(BackTickExpression expression) {
		// TODO Auto-generated method stub

	}

	public void visit(Program program) {
		super.visit(program);
	}

	public void visit(StaticMethodInvocation invocation) {
		// TODO Auto-generated method stub

	}
}
