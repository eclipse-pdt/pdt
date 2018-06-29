/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class AnonymousClassDeclaration extends Expression implements IRecoverable {

	private TypeReference superClass;
	private List<TypeReference> interfaceList;
	private Block body;
	private boolean isRecovered;

	public AnonymousClassDeclaration(int start, int end, TypeReference superClass, List<TypeReference> interfaceList,
			Block body) {
		super(start, end);

		assert body != null;

		this.superClass = superClass;
		this.interfaceList = interfaceList;
		this.body = body;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (superClass != null) {
				superClass.traverse(visitor);
			}
			if (interfaceList != null) {
				for (TypeReference type : interfaceList) {
					type.traverse(visitor);
				}
			}
			body.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public TypeReference getSuperClass() {
		return superClass;
	}

	public List<TypeReference> getInterfaceList() {
		return interfaceList;
	}

	public Block getBody() {
		return body;
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
	public int getKind() {
		return ASTNodeKinds.ANONYMOUS_CLASS_DECLARATION;
	}

	@Override
	public boolean isRecovered() {
		return isRecovered;
	}

	@Override
	public void setRecovered(boolean isRecovered) {
		this.isRecovered = isRecovered;
	}

}
