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
 * Represents a block of statements
 * <pre>e.g.<pre>
 * {
 *   statement1;
 *   statement2;
 * },
 * :
 *   statement1;
 *   statement2;
 * ,
 */
public class Block extends Statement {

	private final Statement[] statements;
	private final boolean isCurly;

	private Block(int start, int end, Statement[] statements, boolean isCurly) {
		super(start, end);

		assert statements != null;
		this.statements = statements;
		this.isCurly = isCurly;

		// set the child nodes' parent
		for (int i = 0; i < statements.length; i++) {
			statements[i].setParent(this);
		}
	}

	public Block(int start, int end, List statements, boolean isCurly) {
		this(start, end, statements == null ? null : (Statement[]) statements.toArray(new Statement[statements.size()]), isCurly);
	}

	public Block(int start, int end, List statements) {
		this(start, end, statements, true);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < statements.length; i++) {
			statements[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < statements.length; i++) {
			statements[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Block");
		appendInterval(buffer);
		buffer.append(" isCurly='").append(isCurly).append("'>\n");
		for (int i = 0; i < statements.length; i++) {
			statements[i].toString(buffer, TAB + tab);
			buffer.append("\n");
		}
		buffer.append(tab).append("</Block>");
	}

	public int getType() {
		return ASTNode.BLOCK;
	}

	public boolean isCurly() {
		return isCurly;
	}

	public Statement[] getStatements() {
		return statements;
	}
}
