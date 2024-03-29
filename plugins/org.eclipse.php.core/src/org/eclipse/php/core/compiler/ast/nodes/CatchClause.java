/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a catch clause (as part of a try statement)
 * 
 * <pre>
 * e.g.
 * 
 * catch (ClassName $e) { },
 * </pre>
 *
 */
public class CatchClause extends Statement {

	private final List<TypeReference> classNames;
	private final VariableReference variable;
	private final Block statement;

	public CatchClause(int start, int end, TypeReference className, VariableReference variable, Block statement) {
		super(start, end);

		assert className != null && statement != null;
		this.classNames = Collections.singletonList(className);
		this.variable = variable;
		this.statement = statement;
	}

	public CatchClause(int start, int end, List<TypeReference> classNames, VariableReference variable,
			Block statement) {
		super(start, end);

		assert classNames != null && !classNames.isEmpty() && statement != null;
		this.classNames = classNames;
		this.variable = variable;
		this.statement = statement;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (TypeReference typeReference : classNames) {
				typeReference.traverse(visitor);
			}
			if (variable != null) {
				variable.traverse(visitor);
			}
			statement.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.CATCH_CLAUSE;
	}

	public List<TypeReference> getClassNames() {
		return classNames;
	}

	public Block getStatement() {
		return statement;
	}

	public VariableReference getVariable() {
		return variable;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
