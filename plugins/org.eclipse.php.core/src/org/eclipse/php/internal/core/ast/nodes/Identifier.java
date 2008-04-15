/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Holds an identifier.<br>
 * uses for variable name, function name and class name.
 * <pre>e.g.<pre>  $variableName - variableName is the identifier,
 * foo() - foo is the identifier,
 * $myClass->fun() - myClass and fun are identifiers      
 */
public class Identifier extends Expression {

	private final String name;

	public Identifier(int start, int end, String value) {
		super(start, end);

		assert value != null;
		this.name = value;

		// intern the string for fast equality check
		this.name.intern();
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
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

	/**
	 * Hah code of the identifier is performed only on the name field
	 */
	public int hashCode() {
		return 31 + ((name == null) ? 0 : name.hashCode());
	}

	public int getType() {
		return ASTNode.IDENTIFIER;
	}

	public String getName() {
		return name;
	}

}
