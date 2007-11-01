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
 * Represents a static field access. 
 * <pre>e.g.<pre> MyClass::$a
 * MyClass::$$a[3]
 */
public class StaticFieldAccess extends StaticDispatch {

	private final Variable field;

	public StaticFieldAccess(int start, int end, Identifier className, Variable field) {
		super(start, end, className);

		assert field != null;
		this.field = field;

		field.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		getClassName().accept(visitor);
		field.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getClassName().traverseTopDown(visitor);
		field.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getClassName().traverseBottomUp(visitor);
		field.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<StaticFieldAccess"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		getClassName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		field.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</StaticFieldAccess>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.STATIC_FIELD_ACCESS;
	}

	public Variable getField() {
		return field;
	}

	public ASTNode getMember() {
		return getField();
	}

}
