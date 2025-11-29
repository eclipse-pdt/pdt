/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

/**
 * Represents a list of parameter list (in PropertyHook) if exists
 * 
 * e.g.
 * 
 * <pre>
 * ($param1, $param2, )
 * </pre>
 */
public class FormalParameterList extends Statement {

	private final ASTNode.NodeList<FormalParameter> parameters = new ASTNode.NodeList<>(PARAMETERS_PROPERTY);
	private EmptyExpression emptyPart;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor PARAMETERS_PROPERTY = new ChildListPropertyDescriptor(
			FormalParameterList.class, "parameters", FormalParameter.class, NO_CYCLE_RISK); //$NON-NLS-1$

	public static final ChildPropertyDescriptor EMPTY_PART_PROPERTY = new ChildPropertyDescriptor(
			FormalParameterList.class, "emptyPart", EmptyExpression.class, OPTIONAL, //$NON-NLS-1$
			NO_CYCLE_RISK);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(2);
		properyList.add(PARAMETERS_PROPERTY);
		properyList.add(EMPTY_PART_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public FormalParameterList(AST ast) {
		super(ast);
	}

	public FormalParameterList(int start, int end, AST ast, List<FormalParameter> parameters, EmptyExpression empty) {
		super(start, end, ast);

		for (FormalParameter a : parameters) {
			this.parameters.add(a);
		}
		setEmptyPart(empty);

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
		for (ASTNode node : this.parameters) {
			node.accept(visitor);
		}
		if (emptyPart() != null) {
			emptyPart().accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.parameters) {
			node.traverseTopDown(visitor);
		}
		if (emptyPart() != null) {
			emptyPart().traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.parameters) {
			node.traverseBottomUp(visitor);
		}
		if (emptyPart() != null) {
			emptyPart().traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<FormalParameterList"); //$NON-NLS-1$
		appendInterval(buffer);

		buffer.append(">\n"); //$NON-NLS-1$

		for (FormalParameter part : this.parameters) {
			part.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		if (emptyPart() != null) {
			emptyPart().toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</FormalParameterList>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.FORMAL_PARAMETER_LIST;
	}

	/**
	 * The list of single parts of this 'use' statement
	 * 
	 * @return List of this statement parts
	 */
	public List<FormalParameter> parameters() {
		return this.parameters;
	}

	/**
	 * @return empty node after trailing comma, null otherwise
	 */
	public EmptyExpression emptyPart() {
		return emptyPart;
	}

	public void setEmptyPart(EmptyExpression emptyPart) {
		ASTNode oldChild = this.emptyPart;
		preReplaceChild(oldChild, emptyPart, EMPTY_PART_PROPERTY);
		this.emptyPart = emptyPart;
		postReplaceChild(oldChild, emptyPart, EMPTY_PART_PROPERTY);
	}

	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == PARAMETERS_PROPERTY) {
			return parameters();
		}
		return super.internalGetChildListProperty(property);
	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == EMPTY_PART_PROPERTY) {
			if (get) {
				return emptyPart();
			} else {
				setEmptyPart((EmptyExpression) child);
				return null;
			}
		}
		return super.internalGetSetChildProperty(property, get, child);
	}

	/*
	 * Method declared on ASTNode.
	 */
	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final List<FormalParameter> parts = ASTNode.copySubtrees(target, parameters());
		final EmptyExpression empty = ASTNode.copySubtree(target, emptyPart());
		return new FormalParameterList(getStart(), getEnd(), target, parts, empty);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
