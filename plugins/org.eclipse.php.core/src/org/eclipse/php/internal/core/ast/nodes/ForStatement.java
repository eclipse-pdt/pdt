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

import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a for statement
 * <pre>e.g.<pre>
 * for (expr1; expr2; expr3)
 * 	 statement;
 * 
 * for (expr1; expr2; expr3):
 * 	 statement
 * 	 ...
 * endfor;
 */
public class ForStatement extends Statement {

	private final Expression[] initializations;
	private final Expression[] conditions;
	private final Expression[] increasements;
	private final Statement action;

	private ForStatement(int start, int end, Expression[] initializations, Expression[] conditions, Expression[] increasements, Statement action) {
		super(start, end);

		assert initializations != null && conditions != null && increasements != null && action != null;
		this.initializations = initializations;
		this.conditions = conditions;
		this.increasements = increasements;
		this.action = action;

		for (int i = 0; i < initializations.length; i++) {
			initializations[i].setParent(this);
		}
		for (int i = 0; i < conditions.length; i++) {
			conditions[i].setParent(this);
		}
		for (int i = 0; i < increasements.length; i++) {
			increasements[i].setParent(this);
		}
		action.setParent(this);
	}

	public ForStatement(int start, int end, List initializations, List conditions, List increasements, Statement action) {
		this(start, end, initializations == null ? null : (Expression[]) initializations.toArray(new Expression[initializations.size()]), conditions == null ? null : (Expression[]) conditions.toArray(new Expression[conditions.size()]), increasements == null ? null : (Expression[]) increasements
			.toArray(new Expression[increasements.size()]), action);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < initializations.length; i++) {
			initializations[i].accept(visitor);
		}
		for (int i = 0; i < conditions.length; i++) {
			conditions[i].accept(visitor);
		}
		for (int i = 0; i < increasements.length; i++) {
			increasements[i].accept(visitor);
		}
		action.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < initializations.length; i++) {
			initializations[i].traverseTopDown(visitor);
		}
		for (int i = 0; i < conditions.length; i++) {
			conditions[i].traverseTopDown(visitor);
		}
		for (int i = 0; i < increasements.length; i++) {
			increasements[i].traverseTopDown(visitor);
		}
		action.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < initializations.length; i++) {
			initializations[i].traverseBottomUp(visitor);
		}
		for (int i = 0; i < conditions.length; i++) {
			conditions[i].traverseBottomUp(visitor);
		}
		for (int i = 0; i < increasements.length; i++) {
			increasements[i].traverseBottomUp(visitor);
		}
		action.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ForStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Initializations>\n"); //$NON-NLS-1$
		for (int i = 0; i < initializations.length; i++) {
			initializations[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Initializations>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Conditions>\n"); //$NON-NLS-1$
		for (int i = 0; i < conditions.length; i++) {
			conditions[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Conditions>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Increasements>\n"); //$NON-NLS-1$
		for (int i = 0; i < increasements.length; i++) {
			increasements[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Increasements>\n"); //$NON-NLS-1$
		action.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ForStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FOR_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public Expression[] getConditions() {
		return conditions;
	}

	public Expression[] getIncreasements() {
		return increasements;
	}

	public Expression[] getInitializations() {
		return initializations;
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}
}
