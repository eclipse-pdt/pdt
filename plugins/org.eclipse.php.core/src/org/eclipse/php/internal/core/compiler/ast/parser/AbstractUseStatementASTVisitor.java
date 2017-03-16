/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.core.compiler.ast.nodes.UseStatement;

/**
 * Base AST visitor for finding use statements in PHP source modules.
 * 
 * <p>
 * This visitor is optimized for performance to look not deeper than the
 * necessary level in the AST tree. As described in the
 * <a href="http://php.net/manual/en/language.namespaces.importing.php">PHP
 * Manual</a> use statements can be legally placed only in the global scope of a
 * file or inside namespace declarations.
 * </p>
 * 
 * <p>
 * This visitor also accepts an <code>offset</code> parameter. Searching is not
 * performed after the <code>offset</code>.
 * </p>
 * 
 * @author Kaloyan Raev
 * 
 */
public abstract class AbstractUseStatementASTVisitor extends ASTVisitor {

	/**
	 * The position in the AST tree after which the search stops.
	 */
	protected int offset;

	/**
	 * Constructor of the visitor
	 * 
	 * @param offset
	 *            the position in the AST tree after which the search stops
	 */
	protected AbstractUseStatementASTVisitor(int offset) {
		this.offset = offset;
	}

	/**
	 * The visitor must always look inside {@link ModuleDeclaration}s.
	 */
	@Override
	public boolean visit(ModuleDeclaration s) throws Exception {
		return isBeforeOffset(s);
	}

	/**
	 * The visitor must check if a {@link Statement} is an {@link UseStatement}.
	 * If yes then call {@link #visit(UseStatement)}. Otherwise the visitor must
	 * not look deeper in the AST tree.
	 */
	@Override
	public boolean visit(Statement s) throws Exception {
		if (s instanceof UseStatement && isBeforeOffset(s)) {
			UseStatement useStatement = (UseStatement) s;
			visit(useStatement);
		}
		return false;
	}

	/**
	 * The visitor must look into an {@link Expression} only if it is a
	 * {@link Block} (because it can be a block of namespace declaration).
	 */
	@Override
	public boolean visit(Expression s) throws Exception {
		return s instanceof Block && isBeforeOffset(s);
	}

	/**
	 * The visitor must look into a {@link TypeDeclaration} only if it a
	 * {@link NamespaceDeclaration}.
	 */
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		return s instanceof NamespaceDeclaration && isBeforeOffset(s);
	}

	/**
	 * In all other cases the visitor must not look deeper into the AST tree -
	 * there is no chance to find an {@link UseStatement} there.
	 */
	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		return false;
	}

	/**
	 * Visits a {@link UseStatement} node.
	 * 
	 * <p>
	 * This is a default implementation that calls {@link #visit(UsePart)} for
	 * each <code>UsePart<code> in the <code>UseStatement</code>. It can be
	 * overridden by subclasses.
	 * </p>
	 * 
	 * @param UseStatement
	 *            the <code>UseStatement</code> node that is being visited
	 */
	protected void visit(UseStatement s) throws Exception {
		for (UsePart usePart : s.getParts()) {
			if (!visit(usePart)) {
				// do not visit the rest of the use parts
				break;
			}
		}
	}

	/**
	 * Visits a {@link UsePart} node.
	 * 
	 * <p>
	 * This is a default implementation that can be overridden by subclasses.
	 * </p>
	 * 
	 * @param usePart
	 *            the <code>UsePart</code> node that is being visited
	 * @return <code>true</code> if the next <code>UsePart</code> in a
	 *         <code>UseStatament</code> should be visited, <code>false</code> -
	 *         otherwise.
	 */
	protected boolean visit(UsePart usePart) {
		return false;
	}

	/**
	 * Checks if the specified node position is before the offset.
	 * 
	 * @param node
	 *            the <code>ASTNode<code> to check
	 * @return <code>true</code> if the node's start position is before the
	 *         offset, <code>false</code> - otherwise.
	 */
	protected boolean isBeforeOffset(ASTNode node) {
		return node.sourceStart() < offset;
	}

}