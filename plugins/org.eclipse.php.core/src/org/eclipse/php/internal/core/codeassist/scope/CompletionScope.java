/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.scope;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.php.core.codeassist.ICompletionScope;

/**
 * Provisoinal API
 */
public class CompletionScope implements ICompletionScope {

	private final Type type;

	private final ICompletionScope parent;
	private final ISourceRange sourceRange;
	private final ASTNode ast;

	public CompletionScope(Type type, ISourceRange range, ASTNode ast, ICompletionScope parent) {
		this.type = type;
		this.parent = parent;
		this.sourceRange = range;
		this.ast = ast;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public ICompletionScope getParent() {
		return parent;
	}

	@Override
	public ISourceRange getRange() {
		return sourceRange;
	}

	@Override
	public ICompletionScope findParent(Type... types) {
		ICompletionScope test = this;
		SEARCH: do {
			for (Type type : types) {
				if (test.getType() == type) {
					break SEARCH;
				}
			}
			test = test.getParent();
		} while (test != null);

		return test;
	}

	@Override
	public ASTNode getAST() {
		return ast;
	}

}
