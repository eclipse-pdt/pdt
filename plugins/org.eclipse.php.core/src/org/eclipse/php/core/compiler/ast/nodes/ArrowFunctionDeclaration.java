/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents an arrow function declaration e.g.
 * 
 * <pre>
 * fn & (parameters) => body
 * </pre>
 * 
 * @see https://wiki.php.net/rfc/arrow_functions
 * @since PHP 7.4
 */
public class ArrowFunctionDeclaration extends Expression {

	private final boolean isReference;
	private final boolean isStatic;
	protected List<FormalParameter> arguments = new LinkedList<>();
	private Expression body;
	private ReturnType returnType;

	public ArrowFunctionDeclaration(int start, int end, List<FormalParameter> formalParameters, Expression body,
			final boolean isReference, boolean isStatic, TypeReference returnType) {
		super(start, end);

		if (formalParameters != null) {
			this.arguments = formalParameters;
		}
		this.body = body;

		this.isReference = isReference;
		this.isStatic = isStatic;
		setReturnType(returnType);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (FormalParameter param : arguments) {
				param.traverse(visitor);
			}

			if (this.returnType != null) {
				this.returnType.traverse(visitor);
			}

			if (this.body != null) {
				this.body.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public Collection<FormalParameter> getArguments() {
		return arguments;
	}

	public Expression getBody() {
		return body;
	}

	public void setBody(Expression body) {
		this.body = body;
	}

	public boolean isReference() {
		return isReference;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public TypeReference getReturnType() {
		if (returnType != null) {
			return returnType.getReturnType();
		}
		return null;
	}

	public void setReturnType(TypeReference returnType) {
		if (returnType != null) {
			this.returnType = new ReturnType(returnType);
		}
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
		return ASTNodeKinds.ARROW_FUNCTION;
	}
}
