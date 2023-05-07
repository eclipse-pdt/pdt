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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a lambda function declaration e.g.
 * 
 * <pre>
 * function & (parameters) use (lexical vars) { body }
 * </pre>
 * 
 * @see http://wiki.php.net/rfc/closures
 */
public class LambdaFunctionDeclaration extends Expression implements IAttributed {

	private final boolean isReference;
	private final boolean isStatic;
	private final List<? extends Expression> lexicalVars;
	protected List<FormalParameter> arguments = new LinkedList<>();
	private Block body;
	private ReturnType returnType;
	private List<Attribute> attributes;

	public LambdaFunctionDeclaration(int start, int end, List<FormalParameter> formalParameters,
			List<? extends Expression> lexicalVars, Block body, final boolean isReference) {
		this(start, end, formalParameters, lexicalVars, body, isReference, false);
	}

	public LambdaFunctionDeclaration(int start, int end, List<FormalParameter> formalParameters,
			List<? extends Expression> lexicalVars, Block body, final boolean isReference, boolean isStatic) {
		this(start, end, formalParameters, lexicalVars, body, isReference, isStatic, null);
	}

	public LambdaFunctionDeclaration(int start, int end, List<FormalParameter> formalParameters,
			List<? extends Expression> lexicalVars, Block body, final boolean isReference, boolean isStatic,
			TypeReference returnType) {
		super(start, end);

		if (formalParameters != null) {
			this.arguments = formalParameters;
		}
		this.body = body;

		this.lexicalVars = lexicalVars;
		this.isReference = isReference;
		this.isStatic = isStatic;
		setReturnType(returnType);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (attributes != null) {
				for (Attribute attr : attributes) {
					attr.traverse(visitor);
				}
			}
			for (FormalParameter param : arguments) {
				param.traverse(visitor);
			}

			if (lexicalVars != null) {
				for (Expression var : lexicalVars) {
					var.traverse(visitor);
				}
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

	public Collection<? extends Expression> getLexicalVars() {
		return lexicalVars;
	}

	public Collection<FormalParameter> getArguments() {
		return arguments;
	}

	public Block getBody() {
		return body;
	}

	public void setBody(Block body) {
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
		return ASTNodeKinds.LAMBDA_FUNCTION;
	}

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
