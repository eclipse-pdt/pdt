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
 * Represents a field access
 * <pre>e.g.<pre> $a->$b
 */
public class FieldAccess extends Dispatch {

	private Variable field;

	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor DISPATCHER_PROPERTY = 
		new ChildPropertyDescriptor(FieldAccess.class, "dispatcher", VariableBase.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor FIELD_PROPERTY = 
		new ChildPropertyDescriptor(FieldAccess.class, "field", Variable.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	@Override
	ChildPropertyDescriptor getDispatcherProperty() {
		return FieldAccess.DISPATCHER_PROPERTY;
	}
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(2);
		propertyList.add(FIELD_PROPERTY);
		propertyList.add(DISPATCHER_PROPERTY);		
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}
	
	public FieldAccess(int start, int end, AST ast, VariableBase dispatcher, Variable field) {
		super(start, end, ast, dispatcher);

		if (field == null) {
			throw new IllegalArgumentException();
		}
		setField(field);
	}

	public FieldAccess(AST ast) {
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
		getDispatcher().accept(visitor);
		field.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getDispatcher().accept(visitor);
		field.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getDispatcher().traverseBottomUp(visitor);
		field.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FieldAccess"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Dispatcher>\n"); //$NON-NLS-1$
		getDispatcher().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Property>\n"); //$NON-NLS-1$
		field.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Property>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</FieldAccess>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FIELD_ACCESS;
	}

	/**
	 * Return the field component of this field access
	 * 
	 * @return the field component of this field access
	 */
	public Variable getField() {
		return field;
	}


	/**
	 * see {@link #getField()}
	 */
	public VariableBase getMember() {
		return getField();
	}
	
	/**
	 * Sets the field component of this field access.
	 * 
	 * @param variable the new expression node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setField(Variable variable) {
		if (variable == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.field;
		preReplaceChild(oldChild, variable, FIELD_PROPERTY);
		this.field = variable;
		postReplaceChild(oldChild, variable, FIELD_PROPERTY);
	}	
	
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == FIELD_PROPERTY) {
			if (get) {
				return getField();
			} else {
				setField((Variable) child);
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
		final VariableBase dispatcher = ASTNode.copySubtree(target, getDispatcher());
		final Variable field = ASTNode.copySubtree(target, getField());
		final FieldAccess result = new FieldAccess(getStart(), getEnd(), target, dispatcher, field);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/**
	 * Resolves and returns the binding for the field accessed by this
	 * expression.
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be 
	 *    resolved
	 */			
	public IVariableBinding resolveFieldBinding() {
		return this.ast.getBindingResolver().resolveField(this);
	}
}
