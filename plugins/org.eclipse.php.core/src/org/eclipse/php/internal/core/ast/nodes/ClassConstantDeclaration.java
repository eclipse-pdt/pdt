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

import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a class constant declaration
 * <pre>e.g.<pre> const MY_CONST = 5;
 * const MY_CONST = 5, YOUR_CONSTANT = 8;
 */
public class ClassConstantDeclaration extends Statement {

	private final Identifier[] variableNames;
	private final Expression[] constantValues;

	public ClassConstantDeclaration(int start, int end, List variablesAndDefaults) {
		super(start, end);

		assert variablesAndDefaults != null && variablesAndDefaults.size() > 0;

		this.variableNames = new Identifier[variablesAndDefaults.size()];
		this.constantValues = new Expression[variablesAndDefaults.size()];

		int i = 0;
		for (Iterator iter = variablesAndDefaults.iterator(); iter.hasNext(); i++) {
			ASTNode[] element = (ASTNode[]) iter.next();
			this.variableNames[i] = (Identifier) element[0];
			this.constantValues[i] = (Expression) element[1];

			element[0].setParent(this);
			element[1].setParent(this);
		}
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < variableNames.length; i++) {
			variableNames[i].accept(visitor);
			constantValues[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < variableNames.length; i++) {
			variableNames[i].traverseTopDown(visitor);
			constantValues[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < variableNames.length; i++) {
			variableNames[i].traverseBottomUp(visitor);
			constantValues[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassConstantDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (int i = 0; i < variableNames.length; i++) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			variableNames[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = constantValues[i];
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ClassConstantDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CLASS_CONSTANT_DECLARATION;
	}

	public Expression[] getConstantValues() {
		return constantValues;
	}

	public Identifier[] getVariableNames() {
		return variableNames;
	}
}
