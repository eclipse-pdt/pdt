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
 * Represents while statement.
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * while (expr) statement;
 * 
 * while (expr): statement ... endwhile;
 */
public class WhileStatement extends Statement {

	private Expression condition;
	private Statement body;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor CONDITION_PROPERTY = new ChildPropertyDescriptor(WhileStatement.class,
			"condition", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = new ChildPropertyDescriptor(WhileStatement.class,
			"body", Statement.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> list = new ArrayList<StructuralPropertyDescriptor>(3);
		list.add(CONDITION_PROPERTY);
		list.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(list);
	}

	public WhileStatement(AST ast) {
		super(ast);
	}

	public WhileStatement(int start, int end, AST ast, Expression condition, Statement action) {
		super(start, end, ast);

		if (condition == null || action == null) {
			throw new IllegalArgumentException();
		}
		setCondition(condition);
		setBody(action);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		condition.accept(visitor);
		body.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		condition.traverseTopDown(visitor);
		body.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		condition.traverseBottomUp(visitor);
		body.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<WhileStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</WhileStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.WHILE_STATEMENT;
	}

	/**
	 * @return the body component of this while statement
	 */
	public Statement getBody() {
		return this.body;
	}

	/**
	 * Returns the condition expression of this while statement.
	 * 
	 * @return the expression node
	 */
	public Expression getCondition() {
		return this.condition;
	}

	/**
	 * Sets the condition of this while statement.
	 * 
	 * @param expression
	 *            the expression node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setCondition(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.condition;
		preReplaceChild(oldChild, expression, CONDITION_PROPERTY);
		this.condition = expression;
		postReplaceChild(oldChild, expression, CONDITION_PROPERTY);
	}

	/**
	 * Sets the body part of this whil e statement.
	 * <p>
	 * 
	 * @param body
	 *            the "then" statement node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setBody(Statement body) {
		if (body == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.body;
		preReplaceChild(oldChild, body, BODY_PROPERTY);
		this.body = body;
		postReplaceChild(oldChild, body, BODY_PROPERTY);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == CONDITION_PROPERTY) {
			if (get) {
				return getCondition();
			} else {
				setCondition((Expression) child);
				return null;
			}
		}
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Statement) child);
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
		final Statement body = ASTNode.copySubtree(target, getBody());
		final Expression condition = ASTNode.copySubtree(target, getCondition());
		final WhileStatement result = new WhileStatement(this.getStart(), this.getEnd(), target, condition, body);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
