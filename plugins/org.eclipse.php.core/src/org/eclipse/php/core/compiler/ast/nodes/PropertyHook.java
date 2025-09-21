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
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PropertyHook extends Declaration implements IAttributed, IPHPDocAwareDeclaration {

	private PHPDocBlock phpDoc;
	private List<Attribute> attributes;
	private final boolean isReference;
	private final List<FormalParameter> arguments;
	private final Statement body;

	public PropertyHook(int start, int end, int modifiers, boolean isReference, String name, int nameStart, int nameEnd,
			List<FormalParameter> arguments, Statement body) {
		super(start, end);
		setModifiers(modifiers);
		this.isReference = isReference;
		setName(name);
		setNameStart(nameStart);
		setNameEnd(nameEnd);
		this.arguments = arguments;
		this.body = body;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (attributes != null) {
				for (Attribute attr : attributes) {
					attr.traverse(pVisitor);
				}
			}
			if (arguments != null) {
				for (FormalParameter arg : arguments) {
					arg.traverse(pVisitor);
				}
			}
			if (body != null) {
				body.traverse(pVisitor);
			}

			pVisitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.PROPERTY_HOOK;
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

	public List<FormalParameter> getArguments() {
		return arguments;
	}

	public Statement getBody() {
		return body;
	}

	public boolean isArrow() {
		return !(body instanceof Block);
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public void setPHPDoc(PHPDocBlock block) {
		this.phpDoc = block;
	}

	public boolean isReference() {
		return isReference;
	}
}
