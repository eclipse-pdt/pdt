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

import org.eclipse.dltk.ast.ASTVisitor;

public class TraitAliasStatement extends TraitStatement {
	private TraitAlias alias;

	public TraitAliasStatement(int start, int end, TraitAlias alias) {
		super(start, end);
		this.alias = alias;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (alias != null) {
				alias.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public TraitAlias getAlias() {
		return alias;
	}

	@Override
	public int getKind() {
		return 0;
	}

}
