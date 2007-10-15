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
 * Represents a catch clause (as part of a try statement)
 * <pre>e.g.<pre> catch (ClassName $e) { },
 * 
 */
public class CatchClause extends Statement {

	private final Identifier className;
	private final Variable variable;
	private final Block statement;

	public CatchClause(int start, int end, Identifier className, Variable variable, Block statement) {
		super(start, end);

		assert className != null && variable != null && statement != null;
		this.className = className;
		this.variable = variable;
		this.statement = statement;

		className.setParent(this);
		variable.setParent(this);
		statement.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		className.accept(visitor);
		variable.accept(visitor);
		statement.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		className.traverseTopDown(visitor);
		variable.traverseTopDown(visitor);
		statement.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		className.traverseBottomUp(visitor);
		variable.traverseBottomUp(visitor);
		statement.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<CatchClause"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$
		variable.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		statement.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</CatchClause>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CATCH_CLAUSE;
	}

	public Identifier getClassName() {
		return className;
	}

	public Block getStatement() {
		return statement;
	}

	public Variable getVariable() {
		return variable;
	}
}
