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
 * Holds a class name. 
 * note that the class name can be expression, 
 * <pre>e.g.<pre> MyClass,
 * getClassName() - the function getClassName return a class name
 * $className - the variable $a holds the class name
 */
public class ClassName extends ASTNode {

	private Expression name;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = 
		new ChildPropertyDescriptor(ClassName.class, "name", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(1);
		propertyList.add(NAME_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}	

	public ClassName(int start, int end, AST ast, Expression className) {
		super(start, end, ast);

		if (className == null) {
			throw new IllegalArgumentException();
		}
		setClassName(className);
	}

	public ClassName(AST ast) {
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
		name.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		name.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		name.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</ClassName>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.CLASS_NAME;
	}

	/**
	 * @deprecated use {@link #getName()}
	 */
	public Expression getClassName() {
		return this.name;
	}
	
	/**
	 * Returns the expression of this class name .
	 * 
	 * @return the expression node
	 */ 
	public Expression getName() {
		return this.name;
	}
		
	/**
	 * Sets the expression of this class name.
	 * 
	 * @param expression the new expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setClassName(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, expression, NAME_PROPERTY);
		this.name = expression;
		postReplaceChild(oldChild, expression, NAME_PROPERTY);
	}
	
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setClassName((Expression) child);
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
		final Expression expr = ASTNode.copySubtree(target, getName());
		final ClassName result = new ClassName(this.getStart(), this.getEnd(), target, expr);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
