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
 * Represents an HTML blocks in the resource
 * <pre>e.g.<pre> <html> </html>
 * <html> <?php ?> </html> <?php ?>
 */
public class InLineHtml extends Statement {

	public InLineHtml(int start, int end) {
		super(start, end);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		// no children
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<InLineHtml"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append("/>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.IN_LINE_HTML;
	}
}
