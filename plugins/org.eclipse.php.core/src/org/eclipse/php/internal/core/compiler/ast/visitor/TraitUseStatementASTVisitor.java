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
package org.eclipse.php.internal.core.compiler.ast.visitor;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.TraitUseStatement;

/**
 * AST visitor for finding trait use statements in PHP source modules.
 * 
 * <p>
 * This visitor is optimized for performance to look not deeper than the
 * necessary level in the AST tree. As described in the <a
 * href="http://php.net/manual/en/language.oop5.traits.php">PHP Manual</a> trait
 * use statements can be legally placed only in the scope of class declarations.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public abstract class TraitUseStatementASTVisitor extends ASTVisitor {

	/**
	 * The visitor must always look inside {@link ModuleDeclaration}s.
	 */
	@Override
	public boolean visit(ModuleDeclaration s) throws Exception {
		return true;
	}

	/**
	 * The visitor must look into an {@link Expression} only if it is a
	 * {@link Block} (because it can be a block of a class declaration).
	 */
	@Override
	public boolean visit(Expression s) throws Exception {
		return s instanceof Block;
	}

	/**
	 * The visitor must look into a {@link TypeDeclaration} only if it a
	 * {@link ClassDeclaration}.
	 */
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		return s instanceof ClassDeclaration;
	}

	/**
	 * The visitor must check if a {@link Statement} is a
	 * {@link TraitUseStatement}. If yes then call
	 * {@link #visit(TraitUseStatement)}. Otherwise the visitor must not look
	 * deeper in the AST tree.
	 */
	@Override
	public boolean visit(Statement s) throws Exception {
		if (s instanceof TraitUseStatement) {
			return visit((TraitUseStatement) s);
		}
		return false;
	}

	/**
	 * Subclasses must implement this method to process the found
	 * {@link TraitUseStatement} nodes.
	 * 
	 * @param s
	 *            the visited AST node
	 * @return <code>true</code> if the visitor should traverse the children of
	 *         this node, <code>false</code> - otherwise.
	 * @throws Exception
	 */
	public abstract boolean visit(TraitUseStatement s) throws Exception;

	/**
	 * In all other cases the visitor must not look deeper into the AST tree -
	 * there is no chance to find an {@link TraitUseStatement} there.
	 */
	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		return false;
	}

}
