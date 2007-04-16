/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a function declaration
 * <pre>e.g.<pre>
 * function foo() {}
 * 
 * function &foo() {}
 * 
 * function foo($a, int $b, $c = 5, int $d = 6) {}
 * 
 * function foo(); -abstract function in class declaration
 */
public class FunctionDeclaration extends Statement {

	private final boolean isReference;
	private final Identifier functionName;
	private final FormalParameter[] formalParameters;
	private final Block body;

	private FunctionDeclaration(int start, int end, Identifier functionName, FormalParameter[] formalParameters, Block body, final boolean isReference) {
		super(start, end);

		assert functionName != null && formalParameters != null;
		this.isReference = isReference;
		this.functionName = functionName;
		this.formalParameters = formalParameters;
		this.body = body;

		functionName.setParent(this);
		for (int i = 0; i < formalParameters.length; i++) {
			formalParameters[i].setParent(this);
		}
		if (body != null) {
			body.setParent(this);
		}
	}

	public FunctionDeclaration(int start, int end, Identifier functionName, List formalParameters, Block body, final boolean isReference) {
		this(start, end, functionName, (FormalParameter[]) formalParameters.toArray(new FormalParameter[formalParameters.size()]), body, isReference);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		functionName.accept(visitor);
		for (int i = 0; i < formalParameters.length; i++) {
			formalParameters[i].accept(visitor);
		}
		if (body != null) {
			body.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		functionName.traverseTopDown(visitor);
		for (int i = 0; i < formalParameters.length; i++) {
			formalParameters[i].traverseTopDown(visitor);
		}
		if (body != null) {
			body.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		functionName.traverseBottomUp(visitor);
		for (int i = 0; i < formalParameters.length; i++) {
			formalParameters[i].traverseBottomUp(visitor);
		}
		if (body != null) {
			body.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionDeclaration");
		appendInterval(buffer);
		buffer.append(" isReference='").append(isReference).append("'>\n");
		buffer.append(TAB).append(tab).append("<FunctionName>\n");
		functionName.toString(buffer, TAB + TAB + tab);
		buffer.append("\n");
		buffer.append(TAB).append(tab).append("</FunctionName>\n");

		buffer.append(TAB).append(tab).append("<FormalParameters>\n");
		for (int i = 0; i < formalParameters.length; i++) {
			formalParameters[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append(tab).append("</FormalParameters>\n");
		buffer.append(TAB).append(tab).append("<FunctionBody>\n");
		if (body != null) {
			body.toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append(tab).append("</FunctionBody>\n");
		buffer.append(tab).append("</FunctionDeclaration>");
	}

	public int getType() {
		return ASTNode.FUNCTION_DECLARATION;
	}

	public Block getBody() {
		return body;
	}

	public FormalParameter[] getFormalParameters() {
		return formalParameters;
	}

	public Identifier getFunctionName() {
		return functionName;
	}

	public boolean isReference() {
		return isReference;
	}
}
