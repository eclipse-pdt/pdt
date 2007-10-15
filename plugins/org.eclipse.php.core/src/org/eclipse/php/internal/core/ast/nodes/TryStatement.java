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
 * Represents the try statement 
 * <pre>e.g.<pre> 
 * try { 
 *   statements...
 * } catch (Exception $e) { 
 *   statements...
 * } catch (AnotherException $ae) { 
 *   statements...
 * }
 */
public class TryStatement extends Statement {

	private final Block tryStatement;
	private final CatchClause[] catchClauses;

	private TryStatement(int start, int end, Block tryStatement, CatchClause[] catchClauses) {
		super(start, end);

		assert tryStatement != null && catchClauses != null;
		this.tryStatement = tryStatement;
		this.catchClauses = catchClauses;

		tryStatement.setParent(this);
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].setParent(this);
		}
	}

	public TryStatement(int start, int end, Block tryStatement, List catchClauses) {
		this(start, end, tryStatement, catchClauses == null ? null : (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]));
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		tryStatement.accept(visitor);
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		tryStatement.traverseTopDown(visitor);
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		tryStatement.traverseBottomUp(visitor);
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TryStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		tryStatement.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</TryStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.TRY_STATEMENT;
	}

	public CatchClause[] getCatchClauses() {
		return catchClauses;
	}

	public Block getTryStatement() {
		return tryStatement;
	}
}
