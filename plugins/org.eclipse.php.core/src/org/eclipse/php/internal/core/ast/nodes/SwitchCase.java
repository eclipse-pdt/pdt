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
 * Represents a case statement.
 * A case statement is part of switch statement
 * <pre>e.g.<pre> 
 * case expr:
 *   statement1;
 *   break;,
 * 
 * default:
 *   statement2; 
 */
public class SwitchCase extends Statement {

	private final Expression value;
	private final Statement[] actions;
	private final boolean isDefault;

	public SwitchCase(int start, int end, Expression value, Statement[] actions, boolean isDefault) {
		super(start, end);

		assert actions != null;
		this.value = value;
		this.actions = actions;
		this.isDefault = isDefault;

		if (value != null) {
			value.setParent(this);
		}
		for (int i = 0; i < actions.length; i++) {
			actions[i].setParent(this);
		}
	}

	public SwitchCase(int start, int end, Expression value, List actions, boolean isDefault) {
		this(start, end, value, actions == null ? null : (Statement[]) actions.toArray(new Statement[actions.size()]), isDefault);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		if (value != null) {
			value.accept(visitor);
		}
		for (int i = 0; i < actions.length; i++) {
			actions[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (value != null) {
			value.traverseTopDown(visitor);
		}
		for (int i = 0; i < actions.length; i++) {
			actions[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		if (value != null) {
			value.traverseBottomUp(visitor);
		}
		for (int i = 0; i < actions.length; i++) {
			actions[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<SwitchCase"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isDefault='").append(isDefault).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
		if (value != null) {
			value.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$
		for (int i = 0; i < actions.length; i++) {
			actions[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</SwitchCase>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.SWITCH_CASE;
	}

	public Statement[] getActions() {
		return actions;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public Expression getValue() {
		return value;
	}
}
