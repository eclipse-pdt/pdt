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
 * Holds an identifier.<br>
 * uses for variable name, function name and class name.
 * <pre>e.g.<pre>  $variableName - variableName is the identifier,
 * foo() - foo is the identifier,
 * $myClass->fun() - myClass and fun are identifiers      
 */
public class Identifier extends Expression {

	private String name;

	/**
	 * The "identifier" structural property of this node type.
	 */
	public static final SimplePropertyDescriptor NAME_PROPERTY = new SimplePropertyDescriptor(Identifier.class, "name", String.class, MANDATORY); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> list = new ArrayList<StructuralPropertyDescriptor>(1);
		list.add(NAME_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(list);
	}

	public Identifier(int start, int end, AST ast, String value) {
		super(start, end, ast);

		if (value == null) {
			throw new IllegalArgumentException();
		}
		// intern the string for fast equality check
		value.intern();
		setName(value);
	}

	public Identifier(AST ast) {
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
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Identifier"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" name='").append(name).append("'/>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Equality check for Identifier.<br>
	 * Two identifiers are equal iff they have equal names 
	 * (disregarding its offset and length) <br>
	 * Note: The equality is checked by == since we {@link String#intern()} the name field. 
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Identifier other = (Identifier) obj;
		return this.name == other.name;
	}

	public int getType() {
		return ASTNode.IDENTIFIER;
	}

	public String getName() {
		return name;
	}

	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	protected ASTNode clone0(AST target) {
		Identifier result = new Identifier(this.getStart(), this.getEnd(), target, this.getName());
		return result;
	}

	@Override
	protected List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final Object internalGetSetObjectProperty(SimplePropertyDescriptor property, boolean get, Object value) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((String) value);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetObjectProperty(property, get, value);
	}

	public final void setName(String value) {
		if (value == null/* || value.length() == 0*/) {
			throw new IllegalArgumentException();
		}

		preValueChange(NAME_PROPERTY);
		this.name = value;
		postValueChange(NAME_PROPERTY);
	}

	/**
	 * Resolves and returns the binding for the entity referred to by this name.	
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be 
	 *    resolved
	 */
	public final IBinding resolveBinding() {
		return this.ast.getBindingResolver().resolveName(this);
	}
}
