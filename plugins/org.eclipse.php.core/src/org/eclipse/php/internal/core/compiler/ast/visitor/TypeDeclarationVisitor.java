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
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.php.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;

/**
 * AST visitor optimized for finding type declarations in PHP source modules.
 * 
 * <p>
 * Types are classes, interfaces and traits. Namespaces are not considered as
 * types in this implementation although {@link NamespaceDeclaration} extends
 * {@link TypeDeclaration} in the AST hierarchy.
 * </p>
 * 
 * <p>
 * This visitor is optimized for performance to look not deeper than the
 * necessary level in the AST tree. It builds on the rule the classes in PHP
 * cannot be nested. Therefore, there is no sense to look inside a
 * {@link TypeDeclaration} for finding other {@link TypeDeclaration}s.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public abstract class TypeDeclarationVisitor extends ASTVisitor {

	/**
	 * Flag that indicates that the visitor is traversing inside a
	 * {@link TypeDeclaration}.
	 */
	private boolean insideType = false;

	/**
	 * The visitor must always look into {@link Block}s. A block wraps
	 * {@link ClassDeclaration} children and it must be traversed in order to
	 * visit class' children.
	 */
	@Override
	public boolean visit(Expression s) throws Exception {
		return s instanceof Block || super.visit(s);
	}

	/**
	 * The visitor must look into a {@link TypeDeclaration}s. If class,
	 * interface or trait (i.e. different than {@link NamespaceDeclaration})
	 * then raise the {@link #insideType} flag.
	 */
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		if (!(s instanceof NamespaceDeclaration)) {
			insideType = true;
			visitType(s);
		}
		// return true to make sure endVisit() will be called
		return true;
	}

	/**
	 * Pull the {@link #insideType} flag down when ending visiting class,
	 * interface or trait.
	 */
	@Override
	public boolean endvisit(TypeDeclaration s) throws Exception {
		if (!(s instanceof NamespaceDeclaration)) {
			insideType = false;
		}
		return false;
	}

	/**
	 * Subclasses may implement this method to process the found
	 * {@link TypeDeclaration} nodes.
	 * 
	 * @param s
	 *            the visited AST node
	 * @throws Exception
	 */
	public void visitType(TypeDeclaration s) throws Exception {
		// do nothing be default
	}

	/**
	 * In all other cases the visitor must look deeper into the AST tree only
	 * and if only it is outside of a <code>TypeDecalaration</code>.
	 */
	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		return !insideType;
	}

}
