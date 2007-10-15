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
 * Holds a variable and an index that point to array or hashtable
 * <pre>e.g.<pre> $a[],
 * $a[1],
 * $a[$b],
 * $a{'name'} 
 */
public class ArrayAccess extends Variable {

	public static final int VARIABLE_ARRAY = 1;
	public static final int VARIABLE_HASHTABLE = 2;

	/**
	 * In case of array / hashtable variable, the index expression is added
	 */
	private final Expression index;
	private final int arrayType;

	public ArrayAccess(int start, int end, VariableBase variableName, Expression index, int arrayType) {
		super(start, end, variableName);

		this.index = index;
		this.arrayType = arrayType;

		// set the child nodes' parent
		variableName.setParent(this);
		if (index != null) {
			index.setParent(this);
		}
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		getVariableName().accept(visitor);
		if (index != null) {
			index.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getVariableName().traverseTopDown(visitor);
		if (index != null) {
			index.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		getVariableName().traverseBottomUp(visitor);
		if (index != null) {
			index.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ArrayAccess"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" type='").append(getArrayType(arrayType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		getVariableName().toString(buffer, TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("<Index>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		if (index != null) {
			index.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Index>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ArrayAccess>"); //$NON-NLS-1$
	}

	public static String getArrayType(int type) {
		switch (type) {
			case VARIABLE_ARRAY:
				return "array"; //$NON-NLS-1$
			case VARIABLE_HASHTABLE:
				return "hashtable"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getType() {
		return ASTNode.ARRAY_ACCESS;
	}

	public Expression getIndex() {
		return index;
	}

	public int getArrayType() {
		return arrayType;
	}
}
