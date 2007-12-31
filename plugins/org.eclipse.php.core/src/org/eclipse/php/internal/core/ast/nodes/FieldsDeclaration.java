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
 * Represents a fields declaration
 * <pre>e.g.<pre> var $a, $b;
 * public $a = 3;
 * final private static $var;
 */
public class FieldsDeclaration extends BodyDeclaration {

	private final Variable[] variableNames;
	private final Expression[] initialValues;

	public FieldsDeclaration(int start, int end, int modifier, List variablesAndDefaults) {
		super(start, end, modifier);

		assert variablesAndDefaults != null && variablesAndDefaults.size() > 0;

		this.variableNames = new Variable[variablesAndDefaults.size()];
		this.initialValues = new Expression[variablesAndDefaults.size()];

		int i = 0;
		for (Iterator iter = variablesAndDefaults.iterator(); iter.hasNext(); i++) {
			ASTNode[] element = (ASTNode[]) iter.next();
			this.variableNames[i] = (Variable) element[0];
			this.initialValues[i] = (Expression) element[1];

			element[0].setParent(this);
			if (element[1] != null) {
				element[1].setParent(this);
			}
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
			Expression expr = initialValues[i];
			if (expr != null) {
				expr.accept(visitor);
			}
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < variableNames.length; i++) {
			variableNames[i].traverseTopDown(visitor);
			Expression expr = initialValues[i];
			if (expr != null) {
				expr.traverseTopDown(visitor);
			}
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < variableNames.length; i++) {
			variableNames[i].traverseBottomUp(visitor);
			Expression expr = initialValues[i];
			if (expr != null) {
				expr.traverseBottomUp(visitor);
			}
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FieldsDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; i < variableNames.length; i++) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			variableNames[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = initialValues[i];
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</FieldsDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FIELD_DECLARATION;
	}

	public Expression[] getInitialValues() {
		return initialValues;
	}

	public Variable[] getVariableNames() {
		return variableNames;
	}
}
