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
package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceRange;

public interface ICompletionScope {
	public static enum Type {
		FILE, NAMESPACE, TRAIT, INTERFACE, CLASS, FUNCTION, USE, USE_GROUP, TRAIT_USE, TRAIT_PRECEDENCE;
	}

	Type getType();

	ICompletionScope getParent();

	ICompletionScope findParent(Type... types);

	ISourceRange getRange();

	@Nullable
	ASTNode getAST();
}
