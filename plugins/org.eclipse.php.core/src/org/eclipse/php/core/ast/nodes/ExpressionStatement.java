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
 * This class holds the expression that should be evaluated.
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * $a = 5; $a; 3+2;
 */
public class ExpressionStatement extends Statement {

	private Expression expression;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor EXPRESSION_PROPERTY = new ChildPropertyDescriptor(
			ExpressionStatement.class, "expression", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(1);
		propertyList.add(EXPRESSION_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public ExpressionStatement(int start, int end, AST ast, Expression expr) {
		super(start, end, ast);

		if (expr == null) {
			throw new IllegalArgumentException();
		}
		setExpression(expr);
	}

	public ExpressionStatement(AST ast) {
		super(ast);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		expression.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		expression.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		expression.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ExpressionStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		expression.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ExpressionStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.EXPRESSION_STATEMENT;
	}

	/**
	 * Returns the expression of this expression statement.
	 * 
	 * @return the expression node
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * Sets the expression of this expression statement.
	 * 
	 * @param expression
	 *            the new expression node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setExpression(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.expression;
		preReplaceChild(oldChild, expression, EXPRESSION_PROPERTY);
		this.expression = expression;
		postReplaceChild(oldChild, expression, EXPRESSION_PROPERTY);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == EXPRESSION_PROPERTY) {
			if (get) {
				return getExpression();
			} else {
				setExpression((Expression) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
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
		final Expression expr = ASTNode.copySubtree(target, getExpression());
		final ExpressionStatement result = new ExpressionStatement(this.getStart(), this.getEnd(), target, expr);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
