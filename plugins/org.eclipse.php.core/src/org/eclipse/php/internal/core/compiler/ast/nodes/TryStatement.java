/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

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
	private final List<CatchClause> catchClauses;

	public TryStatement(int start, int end, Block tryStatement, List<CatchClause> catchClauses) {
		super(start, end);

		assert tryStatement != null && catchClauses != null;
		this.tryStatement = tryStatement;
		this.catchClauses = catchClauses;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			tryStatement.traverse(visitor);
			for (CatchClause catchClause : catchClauses) {
				catchClause.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.TRY_STATEMENT;
	}

	public Collection<CatchClause> getCatchClauses() {
		return catchClauses;
	}

	public Block getTryStatement() {
		return tryStatement;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
