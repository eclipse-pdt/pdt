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
 * Holds a class name. 
 * note that the class name can be expression, 
 * <pre>e.g.<pre> MyClass,
 * getClassName() - the function getClassName return a class name
 * $className - the variable $a holds the class name
 */
public class ClassName extends ASTNode {

	private final Expression className;

	public ClassName(int start, int end, Expression className) {
		super(start, end);
		assert className != null;

		this.className = className;

		className.setParent(this);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		className.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		className.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		className.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</ClassName>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.CLASS_NAME;
	}

	public Expression getClassName() {
		return className;
	}
}
