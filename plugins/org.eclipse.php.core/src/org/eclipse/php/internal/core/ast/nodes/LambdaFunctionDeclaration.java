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
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a lambda function declaration e.g.
 * 
 * <pre>
 * function & (parameters) use (lexical vars) { body }
 * </pre>
 * 
 * @see http://wiki.php.net/rfc/closures
 */
public class LambdaFunctionDeclaration extends Expression {

	private boolean isReference;
	private boolean isStatic;
	private int staticStart;
	private final ASTNode.NodeList<FormalParameter> formalParameters = new ASTNode.NodeList<FormalParameter>(
			FORMAL_PARAMETERS_PROPERTY);
	private final ASTNode.NodeList<Expression> lexicalVariables = new ASTNode.NodeList<Expression>(
			LEXICAL_VARIABLES_PROPERTY);
	private Block body;

	/**
	 * The structural property of this node type.
	 */
	public static final SimplePropertyDescriptor IS_REFERENCE_PROPERTY = new SimplePropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"isReference", Boolean.class, OPTIONAL); //$NON-NLS-1$
	public static final SimplePropertyDescriptor IS_STATIC = new SimplePropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"isStatic", Boolean.class, OPTIONAL); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor FORMAL_PARAMETERS_PROPERTY = new ChildListPropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"formalParameters", FormalParameter.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor LEXICAL_VARIABLES_PROPERTY = new ChildListPropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"lexicalVariables", Expression.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = new ChildPropertyDescriptor(
			LambdaFunctionDeclaration.class,
			"body", Block.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				4);
		propertyList.add(IS_REFERENCE_PROPERTY);
		propertyList.add(IS_STATIC);
		propertyList.add(FORMAL_PARAMETERS_PROPERTY);
		propertyList.add(LEXICAL_VARIABLES_PROPERTY);
		propertyList.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public LambdaFunctionDeclaration(int start, int end, AST ast,
			List formalParameters, List lexicalVars, Block body,
			final boolean isReference) {
		this(start, end, ast, formalParameters, lexicalVars, body, isReference,
				false, -1);
	}

	public LambdaFunctionDeclaration(int start, int end, AST ast,
			List formalParameters, List lexicalVars, Block body,
			final boolean isReference, final boolean isStatic, int staticStart) {
		super(start, end, ast);

		if (formalParameters == null) {
			throw new IllegalArgumentException();
		}

		setIsReference(isReference);

		Iterator<FormalParameter> paramIt = formalParameters.iterator();
		while (paramIt.hasNext()) {
			this.formalParameters.add(paramIt.next());
		}
		if (lexicalVars != null) {
			Iterator<Expression> varsIt = lexicalVars.iterator();
			while (varsIt.hasNext()) {
				this.lexicalVariables.add(varsIt.next());
			}
		}
		if (body != null) {
			setBody(body);
		}
		setStatic(isStatic);
		this.staticStart = staticStart;
	}

	public LambdaFunctionDeclaration(AST ast) {
		super(ast);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.formalParameters) {
			node.accept(visitor);
		}
		for (ASTNode node : this.lexicalVariables) {
			node.accept(visitor);
		}
		if (body != null) {
			body.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.formalParameters) {
			node.traverseTopDown(visitor);
		}
		for (ASTNode node : this.lexicalVariables) {
			node.accept(visitor);
		}
		if (body != null) {
			body.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.formalParameters) {
			node.traverseBottomUp(visitor);
		}
		for (ASTNode node : this.lexicalVariables) {
			node.accept(visitor);
		}
		if (body != null) {
			body.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<LambdaFunctionDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isReference='").append(isReference); //$NON-NLS-1$ //$NON-NLS-2$
		if (isStatic) {
			buffer.append(" isStatic='").append(isStatic); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append("'>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<FormalParameters>\n"); //$NON-NLS-1$
		for (ASTNode node : this.formalParameters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</FormalParameters>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<LexicalVariables>\n"); //$NON-NLS-1$
		for (ASTNode node : this.lexicalVariables) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</LexicalVariables>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<FunctionBody>\n"); //$NON-NLS-1$
		if (body != null) {
			body.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</FunctionBody>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</LambdaFunctionDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.LAMBDA_FUNCTION_DECLARATION;
	}

	/**
	 * Body of this function declaration
	 * 
	 * @return Body of this function declaration
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * Sets the body part of this function declaration
	 * <p>
	 * 
	 * @param body
	 *            of this function declaration
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li> <li>the node
	 *                already has a parent</li> <li>a cycle in would be created
	 *                </li>
	 *                </ul>
	 */
	public void setBody(Block body) {
		if (body == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, body, BODY_PROPERTY);
		this.body = body;
		postReplaceChild(oldChild, body, BODY_PROPERTY);
	}

	/**
	 * List of the formal parameters of this function declaration
	 * 
	 * @return the parameters of this declaration
	 */
	public List<FormalParameter> formalParameters() {
		return this.formalParameters;
	}

	/**
	 * List of the lexical variables of this lambda function declaration
	 * 
	 * @return the lexical variables of this declaration
	 */
	public List<Expression> lexicalVariables() {
		return this.lexicalVariables;
	}

	/**
	 * True if this function's return variable will be referenced
	 * 
	 * @return True if this function's return variable will be referenced
	 */
	public boolean isReference() {
		return isReference;
	}

	/**
	 * Sets to true if this function's return variable will be referenced
	 * 
	 * @param value
	 *            for referenced function return value
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public final void setIsReference(boolean value) {
		preValueChange(IS_REFERENCE_PROPERTY);
		this.isReference = value;
		postValueChange(IS_REFERENCE_PROPERTY);
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		preValueChange(IS_STATIC);
		this.isStatic = isStatic;
		postValueChange(IS_STATIC);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	final boolean internalGetSetBooleanProperty(
			SimplePropertyDescriptor property, boolean get, boolean value) {
		if (property == IS_REFERENCE_PROPERTY) {
			if (get) {
				return isReference();
			} else {
				setIsReference(value);
				return false;
			}
		} else if (property == IS_STATIC) {
			if (get) {
				return isStatic();
			} else {
				setStatic(value);
				return false;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Block) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == FORMAL_PARAMETERS_PROPERTY) {
			return formalParameters();
		}
		if (property == LEXICAL_VARIABLES_PROPERTY) {
			return lexicalVariables();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	/*
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final Block body = ASTNode.copySubtree(target, getBody());
		final List formalParams = ASTNode.copySubtrees(target,
				formalParameters());
		final List lexicalVars = ASTNode.copySubtrees(target,
				lexicalVariables());
		final boolean isRef = isReference();

		final LambdaFunctionDeclaration result = new LambdaFunctionDeclaration(
				getStart(), getEnd(), target, formalParams, lexicalVars, body,
				isRef, isStatic(), this.staticStart);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
