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
package org.eclipse.php.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;
import org.eclipse.php.core.compiler.PHPFlags;

/**
 * Represents property hook
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @see https://www.php.net/manual/en/language.oop5.property-hooks.php
 * @since PHP 8.4
 */
public class PropertyHook extends BodyDeclaration {

	private boolean isReference;
	private Identifier name;
	private FormalParameterList parameters;
	private Expression body;

	/**
	 * The structural property of this node type.
	 */
	public static final SimplePropertyDescriptor IS_REFERENCE_PROPERTY = new SimplePropertyDescriptor(
			PropertyHook.class, "isReference", Boolean.class, //$NON-NLS-1$
			OPTIONAL);
	public static final SimplePropertyDescriptor MODIFIER_PROPERTY = new SimplePropertyDescriptor(PropertyHook.class,
			"modifier", Integer.class, OPTIONAL); //$NON-NLS-1$

	public static final ChildPropertyDescriptor PARAMETERS_PROPERTY = new ChildPropertyDescriptor(PropertyHook.class,
			"parameters", //$NON-NLS-1$
			FormalParameterList.class, OPTIONAL, CYCLE_RISK);

	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(PropertyHook.class, "name", //$NON-NLS-1$
			Identifier.class, MANDATORY, NO_CYCLE_RISK);

	public static final ChildPropertyDescriptor BODY_PROPERTY = new ChildPropertyDescriptor(PropertyHook.class, "body", //$NON-NLS-1$
			Expression.class, OPTIONAL, CYCLE_RISK);

	public static final ChildListPropertyDescriptor ATTRIBUTES_PROPERTY = new ChildListPropertyDescriptor(
			PropertyHook.class, "attributes", AttributeGroup.class, //$NON-NLS-1$
			CYCLE_RISK);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<>(5);
		propertyList.add(ATTRIBUTES_PROPERTY);
		propertyList.add(MODIFIER_PROPERTY);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(IS_REFERENCE_PROPERTY);
		propertyList.add(PARAMETERS_PROPERTY);
		propertyList.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);

	}

	public PropertyHook(int start, int end, AST ast, int modifier, boolean isReference, Identifier name,
			FormalParameterList parameters, Expression body, List<AttributeGroup> attributes) {
		super(start, end, ast, modifier);
		if (attributes != null) {
			attributes().addAll(attributes);
		}
		setIsReference(isReference);
		setName(name);
		setParameters(parameters);
		setBody(body);
	}

	public PropertyHook(AST ast) {
		super(ast);
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
		for (AttributeGroup attr : attributes()) {
			attr.accept(visitor);
		}
		if (name != null) {
			name.accept(visitor);
		}
		if (parameters != null) {
			parameters.accept(visitor);
		}
		if (body != null) {
			body.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);

		for (AttributeGroup attr : attributes()) {
			attr.traverseTopDown(visitor);
		}
		if (name != null) {
			name.traverseTopDown(visitor);
		}
		if (parameters != null) {
			parameters.traverseTopDown(visitor);
		}
		if (body != null) {
			body.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		if (body != null) {
			body.traverseTopDown(visitor);
		}
		if (parameters != null) {
			parameters.traverseBottomUp(visitor);
		}
		if (name != null) {
			name.traverseBottomUp(visitor);
		}
		for (AttributeGroup attr : attributes()) {
			attr.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<PropertyHook"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isReference='").append(isReference()); //$NON-NLS-1$
		buffer.append(" modifier='").append(PHPFlags.toString(getModifier())).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$

		buffer.append("'>\n"); //$NON-NLS-1$
		name.toString(buffer, tab + TAB);
		toStringAttributes(buffer, tab + TAB);
		if (parameters != null) {
			parameters.toString(buffer, tab + TAB);
		}

		buffer.append(TAB).append(tab).append("<HookBody>\n"); //$NON-NLS-1$
		if (body != null) {
			body.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</HookBody>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</PropertyHook>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.PROPERTY_HOOK;
	}

	/**
	 * Body of this function declaration
	 * 
	 * @return Expression of this function declaration
	 */
	public Expression body() {
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
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setBody(Expression body) {
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
	public FormalParameterList parameters() {
		return this.parameters;
	}

	public void setParameters(FormalParameterList parameters) {
		ASTNode oldChild = this.parameters;
		preReplaceChild(oldChild, parameters, PARAMETERS_PROPERTY);
		this.parameters = parameters;
		postReplaceChild(oldChild, parameters, PARAMETERS_PROPERTY);
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

	public Identifier name() {
		return this.name;
	}

	public void setName(Identifier name) {
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, name, NAME_PROPERTY);
		this.name = name;
		postReplaceChild(oldChild, name, NAME_PROPERTY);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	@Override
	final boolean internalGetSetBooleanProperty(SimplePropertyDescriptor property, boolean get, boolean value) {
		if (property == IS_REFERENCE_PROPERTY) {
			if (get) {
				return isReference();
			} else {
				setIsReference(value);
				return false;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return body();
			} else {
				setBody((Expression) child);
				return null;
			}
		}
		if (property == PARAMETERS_PROPERTY) {
			if (get) {
				return parameters();
			} else {
				setParameters((FormalParameterList) child);
				return null;
			}
		}
		if (property == NAME_PROPERTY) {
			if (get) {
				return name();
			} else {
				setName((Identifier) child);
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
		final Expression body = ASTNode.copySubtree(target, body());
		final FormalParameterList formalParams = ASTNode.copySubtree(target, parameters());
		final boolean isRef = isReference();
		final int modifier = getModifier();
		final Identifier name = ASTNode.copySubtree(target, name());
		final List<AttributeGroup> attributes = ASTNode.copySubtrees(target, attributes());
		return new PropertyHook(getStart(), getEnd(), target, modifier, isRef, name, formalParams, body, attributes);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	@Override
	protected ChildListPropertyDescriptor getAttributesProperty() {
		return ATTRIBUTES_PROPERTY;
	}

	@Override
	public SimplePropertyDescriptor getModifierProperty() {
		return MODIFIER_PROPERTY;
	}
}
