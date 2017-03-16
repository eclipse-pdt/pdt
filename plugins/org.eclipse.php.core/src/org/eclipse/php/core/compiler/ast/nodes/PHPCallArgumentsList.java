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
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.Iterator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPCallArgumentsList extends CallArgumentsList {

	public PHPCallArgumentsList() {
	}

	public PHPCallArgumentsList(int start, int end) {
		super(start, end);
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (getChilds() != null) {
				for (Iterator iter = getChilds().iterator(); iter.hasNext();) {
					ASTNode s = (ASTNode) iter.next();
					s.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
