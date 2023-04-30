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
package org.eclipse.php.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

/**
 * Represents a dispaching expression
 * 
 * <pre>
 * e.g.
 * 
 * foo()->bar(), $myClass->foo()->bar(), A::$a->foo()
 * </pre>
 */
public class MethodInvocation extends Dispatch {

	private FunctionInvocation method;
	private boolean nullSafe;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor DISPATCHER_PROPERTY = new ChildPropertyDescriptor(
			MethodInvocation.class, "dispatcher", VariableBase.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor METHOD_PROPERTY = new ChildPropertyDescriptor(MethodInvocation.class,
			"method", FunctionInvocation.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	public static final SimplePropertyDescriptor NULLSAFE_PROPERTY = new SimplePropertyDescriptor(
			MethodInvocation.class, "isNullSafe", Boolean.class, OPTIONAL); //$NON-NLS-1$

	@Override
	ChildPropertyDescriptor getDispatcherProperty() {
		return MethodInvocation.DISPATCHER_PROPERTY;
	}

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS_PHP8;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<>(2);
		propertyList.add(METHOD_PROPERTY);
		propertyList.add(DISPATCHER_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);

		propertyList = new ArrayList<>(3);
		propertyList.add(METHOD_PROPERTY);
		propertyList.add(DISPATCHER_PROPERTY);
		propertyList.add(NULLSAFE_PROPERTY);
		PROPERTY_DESCRIPTORS_PHP8 = Collections.unmodifiableList(propertyList);
	}

	public MethodInvocation(AST ast) {
		super(ast);
	}

	public MethodInvocation(int start, int end, AST ast, VariableBase dispatcher, FunctionInvocation method) {
		this(start, end, ast, dispatcher, method, false);
	}

	public MethodInvocation(int start, int end, AST ast, VariableBase dispatcher, FunctionInvocation method,
			boolean nullSafe) {
		super(start, end, ast, dispatcher);

		if (method == null) {
			throw new IllegalArgumentException();
		}

		setMethod(method);
		setNullSafe(nullSafe);
	}

	@Override
	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	@Override
	public void childrenAccept(Visitor visitor) {
		getDispatcher().accept(visitor);
		method.accept(visitor);
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getDispatcher().accept(visitor);
		method.traverseTopDown(visitor);
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		getDispatcher().traverseBottomUp(visitor);
		method.traverseBottomUp(visitor);
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<MethodInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		if (isNullSafe()) {
			buffer.append("' nullable='").append(nullSafe); //$NON-NLS-1$
		}
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Dispatcher>\n"); //$NON-NLS-1$
		getDispatcher().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Property>\n"); //$NON-NLS-1$
		method.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Property>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</MethodInvocation>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.METHOD_INVOCATION;
	}

	/**
	 * The method component of this method invocation expression
	 * 
	 * @return method component of this method invocation expression
	 */
	public FunctionInvocation getMethod() {
		return method;
	}

	public boolean isNullSafe() {
		return nullSafe;
	}

	@Override
	public FunctionInvocation getMember() {
		return getMethod();
	}

	/**
	 * Sets the method component of this field access.
	 * 
	 * @param method
	 *            the new expression node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setMethod(FunctionInvocation method) {
		if (method == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.method;
		preReplaceChild(oldChild, method, METHOD_PROPERTY);
		this.method = method;
		postReplaceChild(oldChild, method, METHOD_PROPERTY);
	}

	public void setNullSafe(boolean value) {
		preValueChange(NULLSAFE_PROPERTY);
		this.nullSafe = value;
		postValueChange(NULLSAFE_PROPERTY);
	}

	@Override
	boolean internalGetSetBooleanProperty(SimplePropertyDescriptor property, boolean get, boolean value) {
		if (property == NULLSAFE_PROPERTY) {
			if (get) {
				return isNullSafe();
			} else {
				setNullSafe(value);
				return false;
			}
		}
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == METHOD_PROPERTY) {
			if (get) {
				return getMethod();
			} else {
				setMethod((FunctionInvocation) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	/*
	 * Method declared on ASTNode.
	 */
	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final VariableBase dispatcher = ASTNode.copySubtree(target, getDispatcher());
		final FunctionInvocation field = ASTNode.copySubtree(target, getMethod());
		final MethodInvocation result = new MethodInvocation(getStart(), getEnd(), target, dispatcher, field, nullSafe);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		if (PHPVersion.PHP8_0.isGreaterThan(apiLevel)) {
			return PROPERTY_DESCRIPTORS;
		}

		return PROPERTY_DESCRIPTORS_PHP8;
	}

	/**
	 * Resolves and returns the binding for the Constructor accessed by this
	 * expression.
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be
	 *         resolved
	 */
	public IMethodBinding resolveConstructorBinding() {
		return this.ast.getBindingResolver().resolveConstructor(this);
	}

	/**
	 * Resolves and returns the binding for the method invoked by this
	 * expression.
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be
	 *         resolved
	 */
	public IMethodBinding resolveMethodBinding() {
		return this.ast.getBindingResolver().resolveMethod(this);
	}

}
