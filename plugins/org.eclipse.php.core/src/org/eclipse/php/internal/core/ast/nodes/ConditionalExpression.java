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
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents conditional expression
 * Holds the condition, if true expression and if false expression
 * each on e can be any expression
 * <pre>e.g. (bool) $a ? 3 : 4
 * $a > 0 ? $a : -$a
 * </pre>
 * The node supports also the new notation introduced in PHP 5.3:
 * <pre>
 * $a ? : $b
 */
public class ConditionalExpression extends Expression {

	private Expression condition;
	private Expression ifTrue;
	private Expression ifFalse;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor CONDITION_PROPERTY = 
		new ChildPropertyDescriptor(ConditionalExpression.class, "condition", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor IF_TRUE_PROPERTY = 
		new ChildPropertyDescriptor(ConditionalExpression.class, "ifTrue", Expression.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor IF_FALSE_PROPERTY = 
		new ChildPropertyDescriptor(ConditionalExpression.class, "ifFalse", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> list = new ArrayList<StructuralPropertyDescriptor>(3);
		list.add(CONDITION_PROPERTY);
		list.add(IF_TRUE_PROPERTY);
		list.add(IF_FALSE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(list);
	}

	public ConditionalExpression(AST ast) {
		super(ast);
	}
	
	public ConditionalExpression(int start, int end, AST ast, Expression condition, Expression ifTrue, Expression ifFalse) {
		super(start, end, ast);

		if (condition == null || (ast.apiLevel().isLessThan(PHPVersion.PHP5_3) && ifTrue == null) || ifFalse == null) {
			throw new IllegalArgumentException();
		}
		setCondition(condition);
		setIfTrue(ifTrue);
		setIfFalse(ifFalse);
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
		if (ifTrue != null) {
			ifTrue.accept(visitor);
		}
		ifFalse.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		condition.traverseTopDown(visitor);
		if (ifTrue != null) {
			ifTrue.traverseTopDown(visitor);
		}
		ifFalse.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		condition.traverseBottomUp(visitor);
		ifTrue.traverseBottomUp(visitor);
		ifFalse.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ConditionalExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		
		if (ifTrue != null) {
			buffer.append(TAB).append(tab).append("<IfTrue>\n"); //$NON-NLS-1$
			ifTrue.toString(buffer, TAB + TAB + tab);
			buffer.append("\n").append(TAB).append(tab).append("</IfTrue>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append(TAB).append(tab).append("<IfFalse>\n"); //$NON-NLS-1$
		ifFalse.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</IfFalse>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</ConditionalExpression>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CONDITIONAL_EXPRESSION;
	}

	/**
	 * Returns the condition of this conditional expression.
	 * 
	 * @return the condition node
	 */ 
	public Expression getCondition() {
		return this.condition;
	}
	
	/**
	 * Sets the condition of this conditional expression.
	 * 
	 * @param expression the condition node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
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
	 * Returns the "then" part of this conditional expression.
	 * 
	 * @return the "then" expression node. This method may return <code>null</code> for PHP 5.3 and greater.
	 */ 
	public Expression getIfTrue() {
		return ifTrue;
	}
	
	/**
	 * Sets the "then" part of this conditional expression.
	 * 
	 * @param expression the "then" expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setIfTrue(Expression expression) {
		if (ast.apiLevel().isLessThan(PHPVersion.PHP5_3) && expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.ifTrue;
		preReplaceChild(oldChild, expression, IF_TRUE_PROPERTY);
		this.ifTrue = expression;
		postReplaceChild(oldChild, expression, IF_TRUE_PROPERTY);
	}

	/**
	 * Returns the "else" part of this conditional expression.
	 * 
	 * @return the "else" expression node
	 */ 
	public Expression getIfFalse() {
		return this.ifFalse;
	}
	
	/**
	 * Sets the "else" part of this conditional expression.
	 * 
	 * @param expression the "else" expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setIfFalse(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.ifFalse;
		preReplaceChild(oldChild, expression, IF_FALSE_PROPERTY);
		this.ifFalse = expression;
		postReplaceChild(oldChild, expression, IF_FALSE_PROPERTY);
	}
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
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
		if (property == IF_TRUE_PROPERTY) {
			if (get) {
				return getIfTrue();
			} else {
				setIfTrue((Expression) child);
				return null;
			}
		}
		if (property == IF_FALSE_PROPERTY) {
			if (get) {
				return getIfFalse();
			} else {
				setIfFalse((Expression) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}
	
	@Override
	ASTNode clone0(AST target) {
		final Expression condition = ASTNode.copySubtree(target, this.getCondition());
		final Expression ifTrue = ASTNode.copySubtree(target, this.getIfTrue());
		final Expression ifFalse = ASTNode.copySubtree(target, this.getIfTrue());
		ConditionalExpression result = new ConditionalExpression(this.getStart(), this.getEnd(), target, condition, ifTrue, ifFalse);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
