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

import org.eclipse.dltk.core.ISourceRange;

public interface ICompletionScope extends ISourceRange {
	public static enum Type {
		FILE, NAMESPACE, TRAIT, INTERFACE, CLASS, FUNCTION, CONST, FIELD, USE, USE_CONST, USE_FUNCTION, USE_GROUP, TRAIT_USE, TRAIT_PRECEDENCE, COMMENT, PHPDOC, PHPDOC_TAG, HEAD, BLOCK, IF, WHILE, DOWHILE, FOR, FOREACH, SWITCH, CASE, TRY, CATCH, FINALLY

		;
	}

	Type getType();

	String getName();

	ICompletionScope getParent();

	ICompletionScope findParent(Type... types);

}
