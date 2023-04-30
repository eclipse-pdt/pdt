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
 * Represents namespace name:
 * 
 * e.g.
 * 
 * <pre>
 * 
 * </pre>
 */
public class DNFType extends Identifier {

	public final static int T_UNION = 1;
	public final static int T_INTERSECTION = 2;

	protected ASTNode.NodeList<Identifier> elements = new ASTNode.NodeList<>(ELEMENTS_PROPERTY);
	protected int type;

	/**
	 * The "namespace" structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor ELEMENTS_PROPERTY = new ChildListPropertyDescriptor(DNFType.class,
			"elements", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor TYPE_PROPERTY = new SimplePropertyDescriptor(DNFType.class, "type", //$NON-NLS-1$
			Integer.class, MANDATORY);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(5);
		properyList.add(NAME_PROPERTY);
		properyList.add(ELEMENTS_PROPERTY);
		properyList.add(NULLABLE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public DNFType(AST ast) {
		super(ast);
	}

	public DNFType(int start, int end, AST ast, boolean nullable, List<Identifier> elements, int type) {
		super(start, end, ast, buildName(elements, type));
		this.type = type;
		this.elements.addAll(elements);
		setNullable(nullable);
	}

	private static String buildName(List<Identifier> elements, int type) {
		StringBuilder sb = new StringBuilder();
		for (Identifier el : elements) {
			if (!sb.isEmpty()) {
				switch (type) {
				case T_UNION:
					sb.append('|');
					break;
				case T_INTERSECTION:
					sb.append('&');
					break;
				}
			}
			sb.append(el.getName());
		}
		return sb.toString();
	}

	@Override
	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.elements) {
			node.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.elements) {
			node.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.elements) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<DNFType"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" type='").append(type).append('\''); //$NON-NLS-1$
		if (isNullable()) {
			buffer.append(" nullable='").append(isNullable()).append('\''); //$NON-NLS-1$
		}
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.elements) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</DNFType>"); //$NON-NLS-1$
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
	public int getType() {
		return ASTNode.DNF_TYPE;
	}

	public void setType(int type) {
		preValueChange(TYPE_PROPERTY);
		this.type = type;
		postValueChange(TYPE_PROPERTY);
	}

	/**
	 * Retrieves names parts of the namespace
	 * 
	 * @return segments. If names list is empty, that means that this namespace
	 *         is global.
	 */
	public List<Identifier> elements() {
		assert elements.size() > 0;
		return elements;
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	@Override
	protected ASTNode clone0(AST target) {
		final List<Identifier> segments = ASTNode.copySubtrees(target, elements());
		return new DNFType(this.getStart(), this.getEnd(), target, isNullable(), segments, type);
	}

	@Override
	protected List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	@Override
	int internalGetSetIntProperty(SimplePropertyDescriptor property, boolean get, int value) {
		if (property == TYPE_PROPERTY) {
			if (get) {
				return getType();
			} else {
				setType(value);
				return 0;
			}
		}

		return super.internalGetSetIntProperty(property, get, value);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == ELEMENTS_PROPERTY) {
			return elements();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

}
