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
 * Represnts a constant class access
 * <pre>e.g.<pre> MyClass::CONST
 */
public class StaticConstantAccess extends StaticDispatch {

	private final Identifier constant;

	public StaticConstantAccess(int start, int end, Identifier className, Identifier constant) {
		super(start, end, className);

		assert constant != null;
		this.constant = constant;

		constant.setParent(this);
	}

	public StaticConstantAccess(int start, int end, Identifier name) {
		this(start, end, null, name);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		getClassName().accept(visitor);
		constant.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getClassName().traverseTopDown(visitor);
		constant.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getClassName().traverseTopDown(visitor);
		constant.traverseTopDown(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<StaticConstantAccess"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		getClassName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Constant>\n"); //$NON-NLS-1$
		constant.toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</Constant>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append("</StaticConstantAccess>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.STATIC_CONSTANT_ACCESS;
	}

	public Identifier getConstant() {
		return constant;
	}

	public ASTNode getProperty() {
		return getConstant();
	}
}
