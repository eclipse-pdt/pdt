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
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PropertyHook extends Statement {

	private PHPDocBlock phpDoc;
	private final SimpleReference name;
	private final List<FormalParameter> arguments;
	private final Statement body;

	public PropertyHook(int start, int end, SimpleReference name, List<FormalParameter> arguments, Statement body) {
		super(start, end);
		this.name = name;
		this.arguments = arguments;
		this.body = body;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			name.traverse(pVisitor);
			if (arguments != null) {
				for (FormalParameter arg : arguments) {
					arg.traverse(pVisitor);
				}
			}
			body.traverse(pVisitor);

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

	public SimpleReference getName() {
		return name;
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

}
