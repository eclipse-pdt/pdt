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
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

/**
 * Represents a class or namespace constant declaration
 * 
 * <pre>
 * e.g.
 * const MY_CONST = 5;
 * const MY_CONST = 5, YOUR_CONSTANT = 8;
 * </pre>
 */
public class ConstantDeclaration extends BodyDeclaration {

	private Expression constantType;

	private final ASTNode.NodeList<Identifier> names = new ASTNode.NodeList<>(NAMES_PROPERTY);
	private final ASTNode.NodeList<Expression> initializers = new ASTNode.NodeList<>(INITIALIZERS_PROPERTY);

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor NAMES_PROPERTY = new ChildListPropertyDescriptor(
			ConstantDeclaration.class, "names", Identifier.class, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor INITIALIZERS_PROPERTY = new ChildListPropertyDescriptor(
			ConstantDeclaration.class, "initializers", Expression.class, //$NON-NLS-1$
			CYCLE_RISK);
	public static final ChildPropertyDescriptor CONSTANT_TYPE_PROPERTY = new ChildPropertyDescriptor(
			ConstantDeclaration.class, "constantType", Expression.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	public static final SimplePropertyDescriptor MODIFIER_PROPERTY = new SimplePropertyDescriptor(
			ConstantDeclaration.class, "modifier", Integer.class, OPTIONAL); //$NON-NLS-1$

	public static final ChildListPropertyDescriptor ATTRIBUTES_PROPERTY = new ChildListPropertyDescriptor(
			ConstantDeclaration.class, "attributes", AttributeGroup.class, //$NON-NLS-1$
			CYCLE_RISK);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS_PHP8;

	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS_PHP83;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<>(3);
		properyList.add(NAMES_PROPERTY);
		properyList.add(INITIALIZERS_PROPERTY);
		properyList.add(MODIFIER_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);

		properyList = new ArrayList<>(4);
		properyList.add(NAMES_PROPERTY);
		properyList.add(INITIALIZERS_PROPERTY);
		properyList.add(MODIFIER_PROPERTY);
		properyList.add(ATTRIBUTES_PROPERTY);
		PROPERTY_DESCRIPTORS_PHP8 = Collections.unmodifiableList(properyList);

		properyList = new ArrayList<>(5);
		properyList.add(NAMES_PROPERTY);
		properyList.add(CONSTANT_TYPE_PROPERTY);
		properyList.add(INITIALIZERS_PROPERTY);
		properyList.add(MODIFIER_PROPERTY);
		properyList.add(ATTRIBUTES_PROPERTY);
		PROPERTY_DESCRIPTORS_PHP83 = Collections.unmodifiableList(properyList);
	}

	public ConstantDeclaration(int start, int end, AST ast, List<Identifier> names, List<Expression> initializers) {
		this(start, end, ast, 0, names, initializers);
	}

	public ConstantDeclaration(int start, int end, AST ast, int modifier, List<Identifier> names,
			List<Expression> initializers) {
		this(start, end, ast, modifier, names, initializers, null);
	}

	public ConstantDeclaration(int start, int end, AST ast, int modifier, List<Identifier> names,
			List<Expression> initializers, List<AttributeGroup> attributes) {
		this(start, end, ast, modifier, null, names, initializers, attributes);
	}

	public ConstantDeclaration(int start, int end, AST ast, int modifier, Expression constantType,
			List<Identifier> names, List<Expression> initializers, List<AttributeGroup> attributes) {
		super(start, end, ast, modifier);

		if (names == null || initializers == null || names.size() != initializers.size()) {
			throw new IllegalArgumentException();
		}
		if (attributes != null) {
			attributes().addAll(attributes);
		}
		setConstantType(constantType);

		Iterator<Identifier> iteratorNames = names.iterator();
		Iterator<Expression> iteratorInitializers = initializers.iterator();
		while (iteratorNames.hasNext()) {
			this.names.add(iteratorNames.next());
			this.initializers.add(iteratorInitializers.next());
		}
	}

	public ConstantDeclaration(int start, int end, AST ast, List<ASTNode[]> variablesAndDefaults) {
		this(start, end, ast, 0, variablesAndDefaults);
	}

	public ConstantDeclaration(int start, int end, AST ast, int modifier, List<ASTNode[]> variablesAndDefaults) {
		this(start, end, ast, modifier, null, variablesAndDefaults);
	}

	public ConstantDeclaration(int start, int end, AST ast, int modifier, Expression type,
			List<ASTNode[]> variablesAndDefaults) {
		super(start, end, ast, modifier);
		if (variablesAndDefaults == null || variablesAndDefaults.size() == 0) {
			throw new IllegalArgumentException();
		}

		this.constantType = type;
		for (Iterator<ASTNode[]> iter = variablesAndDefaults.iterator(); iter.hasNext();) {
			ASTNode[] element = iter.next();
			assert element != null && element.length == 2 && element[0] != null && element[1] != null;

			this.names.add((Identifier) element[0]);
			this.initializers.add((Expression) element[1]);
		}
	}

	public ConstantDeclaration(AST ast) {
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
		if (constantType != null) {
			constantType.accept(visitor);
		}
		Iterator<Identifier> iterator1 = names.iterator();
		Iterator<Expression> iterator2 = initializers.iterator();
		while (iterator1.hasNext()) {
			iterator1.next().accept(visitor);
			iterator2.next().accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (AttributeGroup attr : attributes()) {
			attr.traverseTopDown(visitor);
		}
		if (constantType != null) {
			constantType.traverseTopDown(visitor);
		}
		Iterator<Identifier> iterator1 = names.iterator();
		Iterator<Expression> iterator2 = initializers.iterator();
		while (iterator1.hasNext()) {
			iterator1.next().traverseTopDown(visitor);
			iterator2.next().traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {

		Iterator<Identifier> iterator1 = names.iterator();
		Iterator<Expression> iterator2 = initializers.iterator();
		while (iterator1.hasNext()) {
			iterator1.next().traverseBottomUp(visitor);
			iterator2.next().traverseBottomUp(visitor);
		}
		if (constantType != null) {
			constantType.traverseBottomUp(visitor);
		}
		for (AttributeGroup attr : attributes()) {
			attr.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<ConstantDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append('\''); //$NON-NLS-1$
		buffer.append(">\n"); //$NON-NLS-1$
		toStringAttributes(buffer, tab + TAB);

		if (constantType != null) {
			buffer.append(tab).append(TAB).append("<Type>\n"); //$NON-NLS-1$
			constantType.toString(buffer, tab + TAB + TAB);
			buffer.append("\n").append(tab).append(TAB).append("</Type>\n");
			toStringAttributes(buffer, tab + TAB);
		}

		Iterator<Identifier> iterator1 = names.iterator();
		Iterator<Expression> iterator2 = initializers.iterator();
		while (iterator1.hasNext()) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			iterator1.next().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = iterator2.next();
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ConstantDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.CONSTANT_DECLARATION;
	}

	/**
	 * @return constant initializers expressions
	 */
	public List<Expression> initializers() {
		return this.initializers;
	}

	/**
	 * @return the constant names
	 */
	public List<Identifier> names() {
		return this.names;
	}

	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == NAMES_PROPERTY) {
			return names();
		}
		if (property == INITIALIZERS_PROPERTY) {
			return initializers();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
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
		final List<Identifier> names = ASTNode.copySubtrees(target, this.names());
		final List<Expression> initializers = ASTNode.copySubtrees(target, this.initializers());
		final List<AttributeGroup> attributes = ASTNode.copySubtrees(target, attributes());
		final Expression type = ASTNode.copySubtree(target, this.getConstantType());
		return new ConstantDeclaration(this.getStart(), this.getEnd(), target, this.getModifier(), type, names,
				initializers, attributes);

	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {

		if (property == CONSTANT_TYPE_PROPERTY) {
			if (get) {
				return getConstantType();
			} else {
				setConstantType((Expression) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {

		if (PHPVersion.PHP8_0.isGreaterThan(apiLevel)) {
			return PROPERTY_DESCRIPTORS;
		}
		if (PHPVersion.PHP8_2.isLessThan(apiLevel)) {
			return PROPERTY_DESCRIPTORS_PHP8;
		}
		return PROPERTY_DESCRIPTORS_PHP83;
	}

	@Override
	public SimplePropertyDescriptor getModifierProperty() {
		return MODIFIER_PROPERTY;
	}

	@Override
	protected ChildListPropertyDescriptor getAttributesProperty() {
		return ATTRIBUTES_PROPERTY;
	}

	public Expression getConstantType() {
		return constantType;
	}

	public void setConstantType(Expression constantType) {
		if (constantType != null && !(constantType instanceof Identifier) && !(constantType instanceof NamespaceName)) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.constantType;
		preReplaceChild(oldChild, constantType, CONSTANT_TYPE_PROPERTY);
		this.constantType = constantType;
		postReplaceChild(oldChild, constantType, CONSTANT_TYPE_PROPERTY);
	}

}
