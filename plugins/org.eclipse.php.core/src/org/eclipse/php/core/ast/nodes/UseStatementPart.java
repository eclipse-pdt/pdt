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
package org.eclipse.php.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.visitor.Visitor;

/**
 * Represents an element of the 'use' declaration.
 * 
 * e.g.
 * 
 * <pre>
 * MyNamespace;
 * MyNamespace as MyAlias; 
 * MyProject\Sub\Level as MyAlias;
 * \MyProject\Sub\Level as MyAlias;
 * function \MyProject\Sub\Level as MyAlias;
 * </pre>
 */
public class UseStatementPart extends ASTNode {

	private NamespaceName name;
	private Identifier alias;
	private int statementType;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(UseStatementPart.class,
			"name", NamespaceName.class, MANDATORY, //$NON-NLS-1$
			CYCLE_RISK);
	public static final ChildPropertyDescriptor ALIAS_PROPERTY = new ChildPropertyDescriptor(UseStatementPart.class,
			"alias", Expression.class, OPTIONAL, //$NON-NLS-1$
			CYCLE_RISK);
	public static final SimplePropertyDescriptor STATEMENT_TYPE_PROPERTY = new SimplePropertyDescriptor(
			UseStatementPart.class, "statementType", Integer.class, //$NON-NLS-1$
			NO_CYCLE_RISK);

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(3);
		properyList.add(NAME_PROPERTY);
		properyList.add(ALIAS_PROPERTY);
		properyList.add(STATEMENT_TYPE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public UseStatementPart(AST ast) {
		super(ast);
	}

	public UseStatementPart(int start, int end, AST ast, NamespaceName name, Identifier alias) {
		this(start, end, ast, name, alias, UseStatement.T_NONE);
	}

	public UseStatementPart(int start, int end, AST ast, NamespaceName name, Identifier alias, int statementType) {
		super(start, end, ast);
		if (name == null) {
			throw new IllegalArgumentException();
		}

		setName(name);
		if (alias != null) {
			setAlias(alias);
		}
		setStatementType(statementType);
	}

	public void childrenAccept(Visitor visitor) {
		name.accept(visitor);
		if (alias != null) {
			alias.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		name.traverseTopDown(visitor);
		if (alias != null) {
			alias.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		name.traverseBottomUp(visitor);
		if (alias != null) {
			alias.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<UseStatementPart"); //$NON-NLS-1$
		appendInterval(buffer);
		if (getStatementType() != UseStatement.T_NONE) {
			buffer.append(" statementType='").append(getStatementType()) //$NON-NLS-1$
					.append("'"); //$NON-NLS-1$
		}
		buffer.append(">\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<Name>\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Name>\n"); //$NON-NLS-1$

		if (alias != null) {
			buffer.append(TAB).append(tab).append("<Alias>\n"); //$NON-NLS-1$
			alias.toString(buffer, TAB + TAB + tab);
			buffer.append("\n").append(TAB).append(tab).append("</Alias>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		buffer.append(tab).append("</UseStatementPart>"); //$NON-NLS-1$
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public int getType() {
		return ASTNode.USE_STATEMENT_PART;
	}

	/**
	 * Returns the name of this array element(null if missing).
	 * 
	 * @return the name of the array element
	 */
	public NamespaceName getName() {
		return name;
	}

	/**
	 * Sets the name of this array element.
	 * 
	 * @param expression
	 *            the left operand node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setName(NamespaceName name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, name, NAME_PROPERTY);
		this.name = name;
		postReplaceChild(oldChild, name, NAME_PROPERTY);
	}

	/**
	 * Returns the alias expression of this array element.
	 * 
	 * @return the alias expression of this array element
	 */
	public Identifier getAlias() {
		return this.alias;
	}

	/**
	 * Sets the name of this array expression.
	 * 
	 * @param expression
	 *            the right operand node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setAlias(Identifier alias) {
		ASTNode oldChild = this.alias;
		preReplaceChild(oldChild, alias, ALIAS_PROPERTY);
		this.alias = alias;
		postReplaceChild(oldChild, alias, ALIAS_PROPERTY);
	}

	public void setStatementType(int statementType) {
		preValueChange(STATEMENT_TYPE_PROPERTY);
		this.statementType = statementType;
		postValueChange(STATEMENT_TYPE_PROPERTY);
	}

	public int getStatementType() {
		return statementType;
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
		final NamespaceName name = ASTNode.copySubtree(target, getName());
		final Identifier alias = ASTNode.copySubtree(target, getAlias());
		final UseStatementPart result = new UseStatementPart(this.getStart(), this.getEnd(), target, name, alias,
				this.getStatementType());
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((NamespaceName) child);
				return null;
			}
		}
		if (property == ALIAS_PROPERTY) {
			if (get) {
				return getAlias();
			} else {
				setAlias((Identifier) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	@Override
	int internalGetSetIntProperty(SimplePropertyDescriptor property, boolean get, int value) {
		if (property == STATEMENT_TYPE_PROPERTY) {
			if (get) {
				return getStatementType();
			} else {
				setStatementType(value);
				return 0;
			}
		}
		return super.internalGetSetIntProperty(property, get, value);
	}

}
