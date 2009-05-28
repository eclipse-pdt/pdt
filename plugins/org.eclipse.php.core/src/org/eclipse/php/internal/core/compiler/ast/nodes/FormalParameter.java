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
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a function formal parameter
 * <pre>e.g.<pre> $a,
 * MyClass $a,
 * $a = 3,
 * int $a = 3
 */
public class FormalParameter extends Argument {

	private final SimpleReference parameterType; // this can be either type reference or array
	private final VariableReference parameterName;
	private final boolean isMandatory;

	private FormalParameter(int start, int end, SimpleReference type, final VariableReference parameterName, Expression defaultValue, boolean isMandatory) {
		super(parameterName, start, end, defaultValue, 0);
		
		SimpleReference ref = getRef();
		if (ref != null) {
			ref.setStart(parameterName.sourceStart());
			ref.setEnd(parameterName.sourceEnd());
		}

		assert parameterName != null;

		this.parameterType = type;
		this.parameterName = parameterName;
		this.isMandatory = isMandatory;
	}

	public FormalParameter(int start, int end, SimpleReference type, final VariableReference parameterName, Expression defaultValue) {
		this(start, end, type, parameterName, defaultValue, false);
	}

	public FormalParameter(int start, int end, SimpleReference type, final VariableReference parameterName, boolean isMandatory) {
		this(start, end, type, parameterName, null, isMandatory);
	}

	public FormalParameter(int start, int end, SimpleReference type, final VariableReference parameterName) {
		this(start, end, type, parameterName, null, true);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (parameterType != null) {
				parameterType.traverse(visitor);
			}
			parameterName.traverse(visitor);
			Expression defaultValue = (Expression) getInitialization();
			if (defaultValue != null) {
				defaultValue.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.FORMAL_PARAMETER;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public VariableReference getParameterName() {
		return parameterName;
	}

	public SimpleReference getParameterType() {
		return parameterType;
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
