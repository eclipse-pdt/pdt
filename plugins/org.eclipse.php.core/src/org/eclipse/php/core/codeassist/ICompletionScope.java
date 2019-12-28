/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.core.ISourceRange;

public interface ICompletionScope extends ISourceRange {
	public static enum Type {
		FILE, NAMESPACE, TRAIT, INTERFACE, CLASS, TYPE_STATEMENT, FUNCTION, CONST, FIELD, USE, USE_CONST, USE_FUNCTION, USE_GROUP, TRAIT_USE, TRAIT_CONFLICT, COMMENT, PHPDOC, HEAD, BLOCK, IF, WHILE, DOWHILE, FOR, FOREACH, SWITCH, CASE, TRY, CATCH, FINALLY;
	}

	Type getType();

	String getName();

	ICompletionScope getParent();

	ICompletionScope findParent(Type... types);

}
