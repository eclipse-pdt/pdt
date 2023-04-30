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
 * Represents base class for class declaration and interface declaration
 */
public class Attribute extends Statement {

	private Identifier name;
	protected ASTNode.NodeList<Expression> parameters = new ASTNode.NodeList<>(PARAMETERS_PROPERTY);;

	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(Attribute.class, "name", //$NON-NLS-1$
			Identifier.class, MANDATORY, NO_CYCLE_RISK);

	public static final ChildListPropertyDescriptor PARAMETERS_PROPERTY = new ChildListPropertyDescriptor(
			Attribute.class, "parameters", Expression.class, CYCLE_RISK); //$NON-NLS-1$

	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<>(2);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(PARAMETERS_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	public Attribute(int start, int end, AST ast, final Identifier name, final List<Expression> parameters) {
		super(start, end, ast);

		if (name == null) {
			throw new IllegalArgumentException();
		}
		setAttributeName(name);

		if (parameters != null) {
			this.parameters.addAll(parameters);
		}
	}

	public Attribute(AST ast) {
		super(ast);
	}

	/**
	 * List of attribute parameters
	 */
	public List<Expression> parameters() {
		return this.parameters;
	}

	/**
	 * The name of the attribute
	 */
	public Identifier getAttributeName() {
		return this.name;
	}

	/**
	 * Sets the name of this parameter
	 * 
	 * @param name
	 *            of this type declaration.
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setAttributeName(Identifier id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, id, NAME_PROPERTY);
		this.name = id;
		postReplaceChild(oldChild, id, NAME_PROPERTY);
	}

	@Override
	ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getAttributeName();
			} else {
				setAttributeName((Identifier) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == PARAMETERS_PROPERTY) {
			return parameters();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	/**
	 * Resolves and returns the binding for this type
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be
	 *         resolved
	 */
	public final IMethodBinding resolveAttributeBidining() {
		return this.ast.getBindingResolver().resolveAttribute(this);
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
		name.accept(visitor);
		for (ASTNode node : parameters) {
			node.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		name.traverseTopDown(visitor);
		for (ASTNode node : parameters) {
			node.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		name.traverseBottomUp(visitor);
		for (ASTNode node : parameters) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<Attribute"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Parameters>\n"); //$NON-NLS-1$
		for (ASTNode node : parameters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Parameters>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</Attribute>"); //$NON-NLS-1$
	}

	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public int getType() {
		return ASTNode.ATTRIBUTE;
	}

	@Override
	ASTNode clone0(AST target) {
		final Identifier name = ASTNode.copySubtree(target, getAttributeName());
		final List<Expression> arguments = ASTNode.copySubtrees(target, parameters());

		return new Attribute(getStart(), getEnd(), target, name, arguments);
	}

}
