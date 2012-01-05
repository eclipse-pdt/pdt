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
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents function invocation. Holds the function name and the invocation
 * parameters.
 * 
 * <pre>e.g.
 * 
 * <pre>
 * foo(),
 * $a(),
 * foo($a, 'a', 12)
 */
public class FunctionInvocation extends VariableBase {

	private FunctionName functionName;
	private final ASTNode.NodeList<Expression> parameters = new ASTNode.NodeList<Expression>(
			PARAMETERS_PROPERTY);
	private PHPArrayDereferenceList arrayDereferenceList;
	/**
	 * The "expressions" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor FUNCTION_PROPERTY = new ChildPropertyDescriptor(
			FunctionInvocation.class,
			"functionName", FunctionName.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor PARAMETERS_PROPERTY = new ChildListPropertyDescriptor(
			FunctionInvocation.class,
			"parameters", Expression.class, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor ARRAY_DEREFERENCE_LIST = new ChildPropertyDescriptor(
			FunctionInvocation.class,
			"arrayDereferenceList", PHPArrayDereferenceList.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		propertyList.add(FUNCTION_PROPERTY);
		propertyList.add(PARAMETERS_PROPERTY);
		propertyList.add(ARRAY_DEREFERENCE_LIST);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	private FunctionInvocation(int start, int end, AST ast,
			FunctionName functionName, Expression[] parameters,
			PHPArrayDereferenceList arrayDereferenceList) {
		super(start, end, ast);

		if (functionName == null || parameters == null) {
			throw new IllegalArgumentException();
		}

		setFunctionName(functionName);
		for (Expression expression : parameters) {
			this.parameters.add(expression);
		}
		this.arrayDereferenceList = arrayDereferenceList;
	}

	public FunctionInvocation(AST ast) {
		super(ast);
	}

	public FunctionInvocation(int start, int end, AST ast,
			FunctionName functionName, List parameters) {
		this(start, end, ast, functionName, parameters == null ? null
				: (Expression[]) parameters.toArray(new Expression[parameters
						.size()]), null);
	}

	public FunctionInvocation(int start, int end, AST ast,
			FunctionName functionName, List parameters,
			PHPArrayDereferenceList arrayDereferenceList) {
		this(start, end, ast, functionName, parameters == null ? null
				: (Expression[]) parameters.toArray(new Expression[parameters
						.size()]), arrayDereferenceList);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		functionName.accept(visitor);
		for (ASTNode node : parameters) {
			node.accept(visitor);
		}
		if (arrayDereferenceList != null) {
			arrayDereferenceList.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		functionName.traverseTopDown(visitor);
		for (ASTNode node : parameters) {
			node.traverseTopDown(visitor);
		}
		if (arrayDereferenceList != null) {
			arrayDereferenceList.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		functionName.traverseBottomUp(visitor);
		for (ASTNode node : parameters) {
			node.traverseBottomUp(visitor);
		}
		if (arrayDereferenceList != null) {
			arrayDereferenceList.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		functionName.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Parameters>\n"); //$NON-NLS-1$
		for (ASTNode node : parameters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Parameters>\n"); //$NON-NLS-1$
		if (arrayDereferenceList != null) {
			arrayDereferenceList.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</FunctionInvocation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FUNCTION_INVOCATION;
	}

	/**
	 * The function name component of this function invocation
	 * 
	 * @return function name component of this function invocation
	 */
	public FunctionName getFunctionName() {
		return functionName;
	}

	/**
	 * Sets the function name of this instantiation.
	 * 
	 * @param functionname
	 *            the new class name
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setFunctionName(FunctionName functionname) {
		if (functionname == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.functionName;
		preReplaceChild(oldChild, functionname, FUNCTION_PROPERTY);
		this.functionName = functionname;
		postReplaceChild(oldChild, functionname, FUNCTION_PROPERTY);
	}

	public PHPArrayDereferenceList getArrayDereferenceList() {
		return arrayDereferenceList;
	}

	public void setArrayDereferenceList(
			PHPArrayDereferenceList arrayDereferenceList) {
		this.arrayDereferenceList = arrayDereferenceList;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == FUNCTION_PROPERTY) {
			if (get) {
				return getFunctionName();
			} else {
				setFunctionName((FunctionName) child);
				return null;
			}
		} else if (property == ARRAY_DEREFERENCE_LIST) {
			if (get) {
				return getArrayDereferenceList();
			} else {
				setArrayDereferenceList((PHPArrayDereferenceList) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == PARAMETERS_PROPERTY) {
			return parameters();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	/**
	 * @deprecated use {@link #parameters()}
	 */
	public Expression[] getParameters() {
		return parameters.toArray(new Expression[parameters.size()]);
	}

	/**
	 * @return the parameters component of this function invocation expression
	 */
	public List<Expression> parameters() {
		return parameters;
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
		final FunctionName function = ASTNode.copySubtree(target,
				getFunctionName());
		final List params = ASTNode.copySubtrees(target, parameters());
		PHPArrayDereferenceList newArrayDereferenceList = null;
		if (arrayDereferenceList != null) {
			newArrayDereferenceList = ASTNode.copySubtree(target,
					arrayDereferenceList);
		}
		final FunctionInvocation result = new FunctionInvocation(getStart(),
				getEnd(), target, function, params, newArrayDereferenceList);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	/**
	 * Resolves and returns the binding for this function
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be
	 *         resolved
	 */
	public IFunctionBinding resolveFunctionBinding() {
		return this.ast.getBindingResolver().resolveFunction(this);
	}
}
