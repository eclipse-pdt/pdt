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

import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.core.compiler.ast.nodes.TraitUseStatement;

/**
 * AST visitor for finding trait use statements in PHP source modules.
 * 
 * <p>
 * This visitor is optimized for performance to look not deeper than the
 * necessary level in the AST tree. As described in the
 * <a href="http://php.net/manual/en/language.oop5.traits.php">PHP Manual</a>
 * trait use statements can be legally placed only in the scope of class
 * declarations.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public abstract class TraitUseStatementVisitor extends TypeDeclarationVisitor {

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
		return super.visit(s);
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

}
