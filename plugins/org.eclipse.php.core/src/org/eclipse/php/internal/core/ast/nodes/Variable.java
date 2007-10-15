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

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Holds a variable. 
 * note that the variable name can be expression, 
 * <pre>e.g.<pre> $a
 *
 * Subclasses: {@link ArrayAccess}, {@link ReflectionVariable}, {@link StaticFieldAccess}
 */
public class Variable extends VariableBase {

	private final Expression variableName;
	private final boolean isDollared;

	protected Variable(int start, int end, Expression variableName, boolean isDollared) {
		super(start, end);

		assert variableName != null;
		this.variableName = variableName;
		this.isDollared = isDollared;

		variableName.setParent(this);
	}

	protected Variable(int start, int end, Expression variableName) {
		this(start, end, variableName, false);
	}

	/**
	 * A simple variable (like $a) can be constructed with a string
	 * The string is wraped by an identifier
	 * @param start
	 * @param end
	 * @param variableName
	 */
	public Variable(int start, int end, String variableName) {
		this(start, end, createIdentifier(start, end, variableName), checkIsDollared(variableName));
	}

	private static boolean checkIsDollared(String variableName) {
		return variableName.indexOf('$') == 0;
	}

	private static Identifier createIdentifier(int start, int end, String idName) {
		if (checkIsDollared(idName)) {
			idName = idName.substring(1);
			// the start position move after the the dollar mark
			start++;
		}
		return new Identifier(start, end, idName);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		variableName.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		variableName.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		variableName.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Variable"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isDollared='").append(isDollared).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		variableName.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</Variable>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.VARIABLE;
	}

	public boolean isDollared() {
		return isDollared;
	}

	public Expression getVariableName() {
		return variableName;
	}
}
