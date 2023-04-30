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
 * Match arm
 * 
 * <pre>
 * 	value,value2 => target,
 *  default => target
 * </pre>
 */
public class MatchArm extends Statement {

	private Expression value;
	private ASTNode.NodeList<Expression> conditions = new ASTNode.NodeList<>(CONDITIONS_PROPERTY);
	private boolean isDefault;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor VALUE_PROPERTY = new ChildPropertyDescriptor(MatchArm.class, "value", //$NON-NLS-1$
			Expression.class, MANDATORY, CYCLE_RISK);
	public static final ChildListPropertyDescriptor CONDITIONS_PROPERTY = new ChildListPropertyDescriptor(
			MatchArm.class, "conditions", Expression.class, CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor IS_DEFAULT_PROPERTY = new SimplePropertyDescriptor(MatchArm.class,
			"isDefault", Boolean.class, OPTIONAL); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<>(1);
		propertyList.add(VALUE_PROPERTY);
		propertyList.add(CONDITIONS_PROPERTY);
		propertyList.add(IS_DEFAULT_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public MatchArm(int start, int end, AST ast, Expression value, List<Expression> conditions, boolean isDefault) {
		super(start, end, ast);

		if (conditions == null) {
			throw new IllegalArgumentException();
		}

		if (value != null) {
			setValue(value);
		}
		this.conditions.addAll(conditions);
		setIsDefault(isDefault);
	}

	public MatchArm(AST ast) {
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
		if (value != null) {
			value.accept(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.accept(visitor);
		}
	}

	@Override
	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (value != null) {
			value.traverseTopDown(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.traverseTopDown(visitor);
		}
	}

	@Override
	public void traverseBottomUp(Visitor visitor) {
		if (value != null) {
			value.traverseBottomUp(visitor);
		}
		for (ASTNode node : this.conditions) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	@Override
	public void toString(StringBuilder buffer, String tab) {
		buffer.append(tab).append("<SwitchArm"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isDefault='").append(isDefault).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
		if (value != null) {
			value.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$
		for (ASTNode node : this.conditions) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</SwitchCase>"); //$NON-NLS-1$
	}

	@Override
	public int getType() {
		return ASTNode.MATCH_ARM;
	}

	public List<Expression> conditions() {
		return this.conditions;
	}

	/**
	 * True if this is a default case statement
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Set to true if this case statement represents a 'default' case
	 * 
	 * @param isDefault
	 * @exception IllegalArgumentException
	 *                if the argument is incorrect
	 */
	public void setIsDefault(boolean isDefault) {
		preValueChange(IS_DEFAULT_PROPERTY);
		this.isDefault = isDefault;
		postValueChange(IS_DEFAULT_PROPERTY);
	}

	/**
	 * The value (expression) of this case statement
	 * 
	 * @return value (expression) of this case statement
	 */
	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		if (isDefault || value == null) {
			throw new IllegalArgumentException();
		}
		// an Assignment may occur inside a Expression - must check cycles
		ASTNode oldChild = this.value;
		preReplaceChild(oldChild, value, VALUE_PROPERTY);
		this.value = value;
		postReplaceChild(oldChild, value, VALUE_PROPERTY);
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
	final boolean internalGetSetBooleanProperty(SimplePropertyDescriptor property, boolean get, boolean value) {
		if (property == IS_DEFAULT_PROPERTY) {
			if (get) {
				return isDefault();
			} else {
				setIsDefault(value);
				return false;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetBooleanProperty(property, get, value);
	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == VALUE_PROPERTY) {
			if (get) {
				return getValue();
			} else {
				setValue((Expression) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	@Override
	final List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == CONDITIONS_PROPERTY) {
			return conditions();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	@Override
	ASTNode clone0(AST target) {
		final boolean isDefault = isDefault();
		final List<Expression> conditions = ASTNode.copySubtrees(target, conditions());
		final Expression value = ASTNode.copySubtree(target, getValue());
		return new MatchArm(getStart(), getEnd(), target, value, conditions, isDefault);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
