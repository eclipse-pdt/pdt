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
 * Represents a fields declaration
 * <pre>e.g.<pre> var $a, $b;
 * public $a = 3;
 * final private static $var;
 */
public class SingleFieldDeclaration extends ASTNode {

	private Variable name;
	private Expression value;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = 
		new ChildPropertyDescriptor(SingleFieldDeclaration.class, "name", Variable.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor VALUE_PROPERTY = 
		new ChildPropertyDescriptor(SingleFieldDeclaration.class, "value", Expression.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(2);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(VALUE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}	
	
	
	public SingleFieldDeclaration(AST ast) {
		super(ast);
	}

	public SingleFieldDeclaration(int start, int end, AST ast, Variable name, Expression value) {
		super(start, end, ast);

		if (name == null) {
			throw new IllegalArgumentException();
		}

		setName(name);
		if (value != null) {
			setValue(value);	
		}
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
		if (value != null) {
			value.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		name.accept(visitor);
		if (value != null) {
			value.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		name.accept(visitor);
		if (value != null) {
			value.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<SingleFieldDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append("'>\n").append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		name.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
		if (value != null) {
			value.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</SingleFieldDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.SINGLE_FIELD_DECLARATION;
	}

	/**
	 * @return the name of the field
	 */
	public Variable getName() {
		return this.name;
	}

	/**
	 * Sets the variable of this field
	 * 
	 * @param name the name of this field
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setName(Variable name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, name, NAME_PROPERTY);
		this.name = name;
		postReplaceChild(oldChild, name, NAME_PROPERTY);
	}		
	
	/**
	 * @return the initial value of this field, null if none
	 */
	public Expression getValue() {
		return this.value;
	}

	/**
	 * Sets the name of this field
	 * 
	 * @param value the name of this field
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setValue(Expression value) {
		ASTNode oldChild = this.value;
		preReplaceChild(oldChild, value, VALUE_PROPERTY);
		this.value = value;
		postReplaceChild(oldChild, value, VALUE_PROPERTY);
	}	
	

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((Variable) child);
				return null;
			}
		}
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
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final Variable name = ASTNode.copySubtree(target, getName());
		final Expression value = ASTNode.copySubtree(target, getValue());
		
		final SingleFieldDeclaration result = new SingleFieldDeclaration(getStart(), getEnd(), target, name, value);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
