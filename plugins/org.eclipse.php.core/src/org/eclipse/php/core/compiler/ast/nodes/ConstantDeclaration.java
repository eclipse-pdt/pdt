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

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class/namespace constant declaration
 * 
 * <pre>
 * e.g.
 * 
 * const MY_CONST = 5; const MY_CONST = 5, YOUR_CONSTANT = 8;
 * </pre>
 */
public class ConstantDeclaration extends Declaration implements IPHPDocAwareDeclaration {

	private final ConstantReference constant;
	private final Expression initializer;
	private PHPDocBlock phpDoc;

	public ConstantDeclaration(ConstantReference constant, Expression initializer, int start, int end,
			PHPDocBlock phpDoc) {
		this(constant, initializer, 0, start, end, phpDoc);
	}

	public ConstantDeclaration(ConstantReference constant, Expression initializer, int modifiers, int start, int end,
			PHPDocBlock phpDoc) {
		super(start, end);

		assert constant != null;
		assert initializer != null;

		this.constant = constant;
		this.initializer = initializer;
		this.phpDoc = phpDoc;

		setModifiers(modifiers);
		setName(constant.getName());
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			constant.traverse(visitor);
			initializer.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	@Override
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
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
