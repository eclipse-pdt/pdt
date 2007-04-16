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
 * Represents a single element of array.
 * Holds the key and the value both can be any expression
 * The key can be null
 * <pre>e.g.<pre> 1,
 * 'Dodo'=>'Golo',
 * $a, 
 * $b=>foo(), 
 * 1=>$myClass->getFirst() *
 */
public class ArrayElement extends ASTNode {

	private final Expression key;
	private final Expression value;

	public ArrayElement(int start, int end, Expression key, Expression value) {
		super(start, end);

		assert value != null;
		this.key = key;
		this.value = value;

		if (key != null) {
			key.setParent(this);
		}
		value.setParent(this);
	}

	public ArrayElement(int start, int end, Expression value) {
		this(start, end, null, value);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		if (key != null) {
			key.accept(visitor);
		}
		value.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (key != null) {
			key.traverseTopDown(visitor);
		}
		value.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		if (key != null) {
			key.traverseBottomUp(visitor);
		}
		value.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ArrayElement");
		appendInterval(buffer);
		buffer.append(">\n");
		buffer.append(TAB).append(tab).append("<Key>\n");
		if (key != null) {
			key.toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append(tab).append("</Key>\n");
		buffer.append(TAB).append(tab).append("<Value>\n");
		value.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Value>\n");
		buffer.append(tab).append("</ArrayElement>");
	}

	public int getType() {
		return ASTNode.ARRAY_ELEMENT;
	}

	public Expression getKey() {
		return key;
	}

	public Expression getValue() {
		return value;
	}
}
