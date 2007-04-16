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
 * Represents a field access
 * <pre>e.g.<pre> $a->$b
 */
public class FieldAccess extends Dispatch {

	private final Variable field;

	public FieldAccess(int start, int end, VariableBase dispatcher, Variable field) {
		super(start, end, dispatcher);

		assert field != null;
		this.field = field;

		field.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
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
		buffer.append(tab).append("<FieldAccess");
		appendInterval(buffer);
		buffer.append(">\n");
		buffer.append(TAB).append(tab).append("<Dispatcher>\n");
		getDispatcher().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n");
		buffer.append(TAB).append(tab).append("<Property>\n");
		field.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Property>\n");
		buffer.append(tab).append("</FieldAccess>");
	}

	public int getType() {
		return ASTNode.FIELD_ACCESS;
	}

	public Variable getField() {
		return field;
	}

	public VariableBase getProperty() {
		return getField();
	}
}
