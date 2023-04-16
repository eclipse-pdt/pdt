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
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class Attribute extends Statement {

	private final FullyQualifiedReference name;
	private final PHPCallArgumentsList arguments;

	public Attribute(int start, int end, FullyQualifiedReference name, PHPCallArgumentsList args) {
		super(start, end);
		this.name = name;
		this.arguments = args;
	}

	public Attribute(int start, int end, FullyQualifiedReference name) {
		super(start, end);
		this.name = name;
		this.arguments = null;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			name.traverse(pVisitor);
			if (arguments != null) {
				arguments.traverse(pVisitor);
			}

			pVisitor.endvisit(this);
		}
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.ATTRIBUTE_STATEMENT;
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

	public FullyQualifiedReference getName() {
		return name;
	}

	public PHPCallArgumentsList getArguments() {
		return arguments;
	}

}
