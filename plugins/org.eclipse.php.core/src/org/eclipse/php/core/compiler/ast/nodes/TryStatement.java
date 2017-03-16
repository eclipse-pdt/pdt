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
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents the try statement
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * try { statements... } catch (Exception $e) { statements... } catch
 * (AnotherException $ae) { statements... } finally { statements... (php 5.5) }
 */
public class TryStatement extends Statement {

	private final Block tryStatement;
	private final List<CatchClause> catchClauses;
	private final FinallyClause finallyClause;

	public TryStatement(int start, int end, Block tryStatement, List<CatchClause> catchClauses,
			FinallyClause finallyClause) {
		super(start, end);

		assert tryStatement != null && catchClauses != null;
		this.tryStatement = tryStatement;
		this.catchClauses = catchClauses;
		this.finallyClause = finallyClause;
	}

	public TryStatement(int start, int end, Block tryStatement, List<CatchClause> catchClauses) {
		this(start, end, tryStatement, catchClauses, null);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			tryStatement.traverse(visitor);
			for (CatchClause catchClause : catchClauses) {
				catchClause.traverse(visitor);
			}
			if (finallyClause != null)
				finallyClause.traverse(visitor);
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

	public FinallyClause getFinallyClause() {
		return finallyClause;
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
