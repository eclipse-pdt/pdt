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
 * Represents an indirect reference to a variable.
 * <pre>e.g.<pre> $$a
 * $$foo()
 */
public class ReflectionVariable extends Variable {

	public ReflectionVariable(int start, int end, Expression variable) {
		super(start, end, variable);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ReflectionVariable");
		appendInterval(buffer);
		buffer.append(">\n");
		getVariableName().toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</ReflectionVariable>");
	}

	public int getType() {
		return ASTNode.REFLECTION_VARIABLE;
	}
}
