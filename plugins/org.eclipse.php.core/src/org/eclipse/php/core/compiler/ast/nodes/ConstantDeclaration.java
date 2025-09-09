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

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
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
public class ConstantDeclaration extends Declaration implements IPHPDocAwareDeclaration, IAttributed {

	private final ConstantReference constant;
	private final Expression initializer;
	private final SimpleReference constantType;
	private PHPDocBlock phpDoc;
	private List<Attribute> attributes;

	public ConstantDeclaration(ConstantReference constant, Expression initializer, int start, int end,
			PHPDocBlock phpDoc) {
		this(constant, initializer, 0, start, end, phpDoc);
	}

	public ConstantDeclaration(ConstantReference constant, Expression initializer, int modifiers, int start, int end,
			PHPDocBlock phpDoc) {
		this(null, constant, initializer, modifiers, start, end, phpDoc);

	}

	public ConstantDeclaration(SimpleReference type, ConstantReference constant, Expression initializer, int modifiers,
			int start, int end, PHPDocBlock phpDoc) {
		super(start, end);

		assert constant != null;

		this.constant = constant;
		this.initializer = initializer;
		this.phpDoc = phpDoc;
		this.constantType = type;

		setModifiers(modifiers);
		setName(constant.getName());
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (attributes != null) {
				for (Attribute attr : attributes) {
					attr.traverse(visitor);
				}
			}
			if (constantType != null) {
				constantType.traverse(visitor);
			}
			constant.traverse(visitor);
			if (initializer != null) {
				initializer.traverse(visitor);
			}
			visitor.endvisit(this);
		}
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

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void setPHPDoc(PHPDocBlock block) {
		this.phpDoc = block;
	}

	public SimpleReference getConstantType() {
		return constantType;
	}
}
