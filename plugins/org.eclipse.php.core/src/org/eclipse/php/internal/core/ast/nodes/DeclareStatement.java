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

import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a declare statement
 * <pre>e.g.<pre> declare(ticks=1) { }
 * declare(ticks=2) { for ($x = 1; $x < 50; ++$x) {  }  }
 */
public class DeclareStatement extends Statement {

	private final Identifier[] directiveNames;
	private final Expression[] directiveValues;
	private final Statement action;

	private DeclareStatement(int start, int end, Identifier[] directiveNames, Expression[] directiveValues, Statement action) {
		super(start, end);

		assert directiveNames != null && directiveValues != null && directiveNames.length == directiveValues.length;
		this.directiveNames = directiveNames;
		this.directiveValues = directiveValues;
		this.action = action;

		for (int i = 0; i < directiveNames.length; i++) {
			directiveNames[i].setParent(this);
			directiveValues[i].setParent(this);
		}
		action.setParent(this);
	}

	public DeclareStatement(int start, int end, List directiveNames, List directiveValues, Statement action) {
		this(start, end, directiveNames == null ? null : (Identifier[]) directiveNames.toArray(new Identifier[directiveNames.size()]), directiveValues == null ? null : (Expression[]) directiveValues.toArray(new Expression[directiveValues.size()]), action);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	
	
	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < directiveNames.length; i++) {
			directiveNames[i].accept(visitor);
			directiveValues[i].accept(visitor);
		}
		action.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < directiveNames.length; i++) {
			directiveNames[i].traverseTopDown(visitor);
			directiveValues[i].traverseTopDown(visitor);
		}
		action.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < directiveNames.length; i++) {
			directiveNames[i].traverseBottomUp(visitor);
			directiveValues[i].traverseBottomUp(visitor);
		}
		action.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<DeclareStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<Directives>\n"); //$NON-NLS-1$
		for (int i = 0; i < directiveNames.length; i++) {
			buffer.append(tab).append(TAB).append(TAB).append("<Name>\n"); //$NON-NLS-1$
			directiveNames[i].toString(buffer, TAB + TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("</Name>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("<Value>\n"); //$NON-NLS-1$
			directiveValues[i].toString(buffer, TAB + TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append(TAB).append("</Value>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Directives>\n"); //$NON-NLS-1$
		action.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</DeclareStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.DECLARE_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public Identifier[] getDirectiveNames() {
		return directiveNames;
	}

	public Expression[] getDirectiveValues() {
		return directiveValues;
	}
}
