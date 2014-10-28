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
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a 'use' statement
 * <pre>e.g.<pre>use MyNamespace;
 *use MyNamespace as MyAlias;
 *use MyProject\Sub\Level as MyAlias;
 *use \MyProject\Sub\Level as MyAlias;
 *use \MyProject\Sub\Level as MyAlias, MyNamespace as OtherAlias, MyOtherNamespace;
 */
public class UseStatement extends Statement {

	// none
	public static final int T_NONE = 0;
	// 'function' keyword
	public static final int T_FUNCTION = 1;
	// 'const' keyword
	public static final int T_CONST = 2;

	private final ASTNode.NodeList<UseStatementPart> parts = new ASTNode.NodeList<UseStatementPart>(
			PARTS_PROPERTY);
	private int statementType;
	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor PARTS_PROPERTY = new ChildListPropertyDescriptor(
			UseStatement.class, "parts", UseStatementPart.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor STATEMENT_TYPE_PROPERTY = new SimplePropertyDescriptor(
			UseStatement.class, "statementType", Integer.class, NO_CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		properyList.add(PARTS_PROPERTY);
		properyList.add(STATEMENT_TYPE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}

	public UseStatement(AST ast) {
		super(ast);
	}

	public UseStatement(int start, int end, AST ast,
			List<UseStatementPart> parts, int statementType) {
		super(start, end, ast);

		if (parts == null || parts.size() == 0) {
			throw new IllegalArgumentException();
		}

		Iterator<UseStatementPart> it = parts.iterator();
		while (it.hasNext()) {
			this.parts.add(it.next());
		}
		this.statementType = statementType;
	}

	public UseStatement(int start, int end, AST ast,
			List<UseStatementPart> parts) {
		this(start, end, ast, parts, T_NONE);
	}

	public UseStatement(int start, int end, AST ast, UseStatementPart[] parts,
			int statementType) {
		super(start, end, ast);

		if (parts == null || parts.length == 0) {
			throw new IllegalArgumentException();
		}

		for (UseStatementPart part : parts) {
			this.parts.add(part);
		}
		this.statementType = statementType;
	}

	public UseStatement(int start, int end, AST ast, UseStatementPart[] parts) {
		this(start, end, ast, parts, T_NONE);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.parts) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.parts) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.parts) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<UseStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		if (getStatementType() != T_NONE) {
			buffer.append(" statementType='").append(getStatementType()).append("'"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append(">\n"); //$NON-NLS-1$
		for (UseStatementPart part : this.parts) {
			part.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</UseStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.USE_STATEMENT;
	}

	/**
	 * The list of single parts of this 'use' statement
	 * 
	 * @return List of this statement parts
	 */
	public List<UseStatementPart> parts() {
		return this.parts;
	}

	public int getStatementType() {
		return statementType;
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == PARTS_PROPERTY) {
			return parts();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	@Override
	int internalGetSetIntProperty(SimplePropertyDescriptor property,
			boolean get, int value) {
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

	public void setStatementType(int statementType) {
		preValueChange(STATEMENT_TYPE_PROPERTY);
		this.statementType = statementType;
		postValueChange(STATEMENT_TYPE_PROPERTY);
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
		final List parts = ASTNode.copySubtrees(target, parts());
		final UseStatement result = new UseStatement(getStart(), getEnd(),
				target, parts, getStatementType());
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
