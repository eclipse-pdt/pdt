/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
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
