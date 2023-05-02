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
 * Represents an enum declaration
 * 
 * <pre>
 * 
 * e.g.
 * 
 * enum MyEnum: type implmenets something { case MyCase }
 * </pre>
 */
public class EnumDeclaration extends TypeDeclaration {

	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(EnumDeclaration.class,
			"name", Identifier.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor TYPE_PROPERTY = new ChildPropertyDescriptor(EnumDeclaration.class,
			"type", Identifier.class, OPTIONAL, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor INTERFACES_PROPERTY = new ChildListPropertyDescriptor(
			EnumDeclaration.class, "interfaces", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = new ChildPropertyDescriptor(EnumDeclaration.class,
			"body", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor ATTRIBUTES_PROPERTY = new ChildListPropertyDescriptor(
			EnumDeclaration.class, "attributes", AttributeGroup.class, CYCLE_RISK); //$NON-NLS-1$

	private Identifier type;

	@Override
	protected ChildPropertyDescriptor getBodyProperty() {
		return BODY_PROPERTY;
	}

	@Override
	protected ChildListPropertyDescriptor getInterfacesProperty() {
		return INTERFACES_PROPERTY;
	}

	@Override
	protected ChildPropertyDescriptor getNameProperty() {
		return NAME_PROPERTY;
	}

	@Override
	protected ChildListPropertyDescriptor getAttributesProperty() {
		return ATTRIBUTES_PROPERTY;
	}

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<>(4);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(INTERFACES_PROPERTY);
		propertyList.add(BODY_PROPERTY);
		propertyList.add(ATTRIBUTES_PROPERTY);
		propertyList.add(TYPE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);

	}

	public EnumDeclaration(int start, int end, AST ast, Identifier interfaceName, Identifier type,
			List<Identifier> interfaces, Block body, List<AttributeGroup> attributes) {
		super(start, end, ast, interfaceName, interfaces.toArray(new Identifier[interfaces.size()]), body);
		setEnumType(type);
		if (attributes != null) {
			attributes().addAll(attributes);
		}
	}

	public EnumDeclaration(AST ast) {
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
		for (AttributeGroup object : attributes()) {
			object.accept(visitor);
		}
		getName().accept(visitor);
		if (getEnumType() != null) {
			getEnumType().accept(visitor);
		}
		final List<Identifier> interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.accept(visitor);
		}
		getBody().accept(visitor);
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (AttributeGroup object : attributes()) {
			object.traverseTopDown(visitor);
		}
		getName().traverseTopDown(visitor);
		if (getEnumType() != null) {
			getEnumType().traverseTopDown(visitor);
		}
		final List<Identifier> interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.traverseTopDown(visitor);
		}
		getBody().traverseTopDown(visitor);
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		for (AttributeGroup object : attributes()) {
			object.traverseBottomUp(visitor);
		}
		getName().traverseBottomUp(visitor);
		if (getEnumType() != null) {
			getEnumType().traverseBottomUp(visitor);
		}
		final List<Identifier> interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.traverseBottomUp(visitor);
		}
		getBody().traverseBottomUp(visitor);
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<EnumDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		toStringAttributes(buffer, TAB + tab);
		buffer.append(tab).append(TAB).append("<EnumName>\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</EnumName>\n"); //$NON-NLS-1$
		if (getEnumType() != null) {
			buffer.append(tab).append(TAB).append("<EnumType>\n"); //$NON-NLS-1$
			getEnumType().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</EnumType>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("<Interfaces>\n"); //$NON-NLS-1$
		final List<Identifier> interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Interfaces>\n"); //$NON-NLS-1$
		getBody().toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</EnumDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.ENUM_DECLARATION;
	}

	/*
	 * Method declared on ASTNode.
	 */
	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	public Identifier getEnumType() {
		return this.type;
	}

	public void setEnumType(Identifier id) {
		ASTNode oldChild = this.type;
		preReplaceChild(oldChild, id, TYPE_PROPERTY);
		this.type = id;
		postReplaceChild(oldChild, id, TYPE_PROPERTY);
	}

	ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == TYPE_PROPERTY) {
			if (get) {
				return getEnumType();
			} else {
				setEnumType((Identifier) child);
				return null;
			}
		}
		return super.internalGetSetChildProperty(property, get, child);
	}

	@Override
	ASTNode clone0(AST target) {
		final Identifier name = ASTNode.copySubtree(target, getName());
		final Identifier type = ASTNode.copySubtree(target, getEnumType());
		final Block body = ASTNode.copySubtree(target, getBody());
		final List<Identifier> interfaces = ASTNode.copySubtrees(target, interfaces());
		final List<AttributeGroup> attributes = ASTNode.copySubtrees(target, attributes());
		return new EnumDeclaration(getStart(), getEnd(), target, name, type, interfaces, body, attributes);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
