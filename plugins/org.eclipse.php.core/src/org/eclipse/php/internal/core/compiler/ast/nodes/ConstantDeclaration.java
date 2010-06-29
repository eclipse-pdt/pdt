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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class/namespace constant declaration
 * 
 * <pre>e.g.
 * 
 * <pre>
 * const MY_CONST = 5;
 * const MY_CONST = 5, YOUR_CONSTANT = 8;
 */
public class ConstantDeclaration extends Declaration implements
		IPHPDocAwareDeclaration {

	private final ConstantReference constant;
	private final Expression initializer;
	private PHPDocBlock phpDoc;

	public ConstantDeclaration(ConstantReference constant,
			Expression initializer, int start, int end, PHPDocBlock phpDoc) {
		super(start, end);

		assert constant != null;
		assert initializer != null;

		this.constant = constant;
		this.initializer = initializer;
		this.phpDoc = phpDoc;

		setName(constant.getName());
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			constant.traverse(visitor);
			initializer.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_CONSTANT_DECLARATION;
	}

	public Expression getConstantValue() {
		return initializer;
	}

	public ConstantReference getConstantName() {
		return constant;
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
