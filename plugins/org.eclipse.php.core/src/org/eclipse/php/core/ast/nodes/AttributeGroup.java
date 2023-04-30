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
 * Represents a Attribute group #[Attr, Attr2]
 * 
 * e.g.
 * 
 * <pre>
 * #[Attr, Attr2, ]
 * </pre>
 */
public class AttributeGroup extends Statement {

	private final ASTNode.NodeList<Attribute> attributes = new ASTNode.NodeList<>(ATTRIBUTES_PROPERTY);
	private EmptyExpression emptyPart;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ATTRIBUTES_PROPERTY = new ChildListPropertyDescriptor(
			AttributeGroup.class, "attributes", Attribute.class, NO_CYCLE_RISK); //$NON-NLS-1$

	public static final ChildPropertyDescriptor EMPTY_PART_PROPERTY = new ChildPropertyDescriptor(UseStatement.class,
			"emptyPart", EmptyExpression.class, OPTIONAL, //$NON-NLS-1$
			NO_CYCLE_RISK);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(2);
		properyList.add(ATTRIBUTES_PROPERTY);
		properyList.add(EMPTY_PART_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public AttributeGroup(AST ast) {
		super(ast);
	}

	public AttributeGroup(int start, int end, AST ast, List<Attribute> attributes, EmptyExpression empty) {
		super(start, end, ast);

		if (attributes == null || attributes.size() == 0) {
			throw new IllegalArgumentException();
		}
		for (Attribute a : attributes) {
			this.attributes.add(a);
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
		for (ASTNode node : this.attributes) {
			node.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.attributes) {
			node.traverseTopDown(visitor);
		}
		if (emptyPart() != null) {
			emptyPart().traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.attributes) {
			node.traverseBottomUp(visitor);
		}
		if (emptyPart() != null) {
			emptyPart().traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<AttributeGroup"); //$NON-NLS-1$
		appendInterval(buffer);

		buffer.append(">\n"); //$NON-NLS-1$

		for (Attribute part : this.attributes) {
			part.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		if (emptyPart() != null) {
			emptyPart().toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</AttributeGroup>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.ATTRIBUTE_GROUP;
	}

	/**
	 * The list of single parts of this 'use' statement
	 * 
	 * @return List of this statement parts
	 */
	public List<Attribute> attributes() {
		return this.attributes;
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
		if (property == ATTRIBUTES_PROPERTY) {
			return attributes();
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
		final List<Attribute> parts = ASTNode.copySubtrees(target, attributes());
		final EmptyExpression empty = ASTNode.copySubtree(target, emptyPart());
		return new AttributeGroup(getStart(), getEnd(), target, parts, empty);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
