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
import org.eclipse.php.core.compiler.IPHPModifiers;

/**
 * Represents a enum case declaration
 * 
 * <pre>
 * e.g.
 * case MY_CONS;
 * case MY_CONST = 5;
 * </pre>
 */
public class EnumCaseDeclaration extends BodyDeclaration {

	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(EnumCaseDeclaration.class,
			"name", Identifier.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor INITIALIZER_PROPERTY = new ChildPropertyDescriptor(
			EnumCaseDeclaration.class, "initializer", Identifier.class, OPTIONAL, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor MODIFIER_PROPERTY = new SimplePropertyDescriptor(
			EnumCaseDeclaration.class, "modifier", Integer.class, OPTIONAL);

	public static final ChildListPropertyDescriptor ATTRIBUTES_PROPERTY = new ChildListPropertyDescriptor(
			EnumCaseDeclaration.class, "attributes", AttributeGroup.class, //$NON-NLS-1$
			CYCLE_RISK);

	private Identifier name;

	private Expression initializer;

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(4);
		properyList.add(NAME_PROPERTY);
		properyList.add(INITIALIZER_PROPERTY);
		properyList.add(MODIFIER_PROPERTY);
		properyList.add(ATTRIBUTES_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);

	}

	public EnumCaseDeclaration(int start, int end, AST ast, Identifier name, Expression initializer) {
		this(start, end, ast, name, initializer, null);
	}

	public EnumCaseDeclaration(int start, int end, AST ast, Identifier name, Expression initializer,
			List<AttributeGroup> attributes) {
		super(start, end, ast, IPHPModifiers.AccFinal | IPHPModifiers.AccConstant
				| IPHPModifiers.AccEnumCase & IPHPModifiers.AccPublic, false);

		if (name == null) {
			throw new IllegalArgumentException();
		}
		if (attributes != null) {
			attributes().addAll(attributes);
		}

		setName(name);
		setInitializer(initializer);
	}

	public EnumCaseDeclaration(AST ast) {
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
		name.accept(visitor);
		if (initializer != null) {
			initializer.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (AttributeGroup attr : attributes()) {
			attr.traverseTopDown(visitor);
		}
		name.traverseTopDown(visitor);
		if (initializer != null) {
			initializer.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {

		for (AttributeGroup attr : attributes()) {
			attr.traverseBottomUp(visitor);
		}
		name.traverseBottomUp(visitor);
		if (initializer != null) {
			initializer.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<EnumCaseDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append('\''); //$NON-NLS-1$
		buffer.append(">\n"); //$NON-NLS-1$
		toStringAttributes(buffer, tab + TAB);

		buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$

		if (initializer != null) {
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			initializer.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}

		buffer.append(tab).append("</EnumCaseDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.ENUM_CASE_DECLARATION;
	}

	public Identifier getName() {
		return this.name;
	}

	public void setName(Identifier id) {
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, id, NAME_PROPERTY);
		this.name = id;
		postReplaceChild(oldChild, id, NAME_PROPERTY);
	}

	public Expression getInitializer() {
		return this.initializer;
	}

	public void setInitializer(Expression id) {
		ASTNode oldChild = this.initializer;
		preReplaceChild(oldChild, id, INITIALIZER_PROPERTY);
		this.initializer = id;
		postReplaceChild(oldChild, id, INITIALIZER_PROPERTY);
	}

	ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((Identifier) child);
				return null;
			}
		}
		if (property == INITIALIZER_PROPERTY) {
			if (get) {
				return getInitializer();
			} else {
				setInitializer((Expression) child);
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
		final Identifier name = ASTNode.copySubtree(target, getName());
		final Expression initializer = ASTNode.copySubtree(target, getInitializer());
		final List<AttributeGroup> attributes = ASTNode.copySubtrees(target, attributes());
		return new EnumCaseDeclaration(this.getStart(), this.getEnd(), target, name, initializer, attributes);

	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	@Override
	public SimplePropertyDescriptor getModifierProperty() {
		return MODIFIER_PROPERTY;
	}

	@Override
	protected ChildListPropertyDescriptor getAttributesProperty() {
		return ATTRIBUTES_PROPERTY;
	}
}
