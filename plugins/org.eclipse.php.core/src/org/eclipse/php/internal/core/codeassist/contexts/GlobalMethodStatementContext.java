/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * This context represents state when staying in a method top level statement.
 * <br/>
 * Examples:
 * 
 * <pre>
 *  1. |
 *  2. pri|
 *  3. $v|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class GlobalMethodStatementContext extends AbstractGlobalStatementContext {

	private IMethod enclosingMethod;
	private IType enclosingType;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		ICompletionScope scope = getCompanion().getScope();
		if (scope.getType() == Type.ATTRIBUTE
				|| (scope.getType() == Type.HEAD && scope.getParent().getType() == Type.ATTRIBUTE)) {
			return false;
		}
		// check whether enclosing element is a method
		IModelElement enclosingElement = getEnclosingElement();
		while (enclosingElement instanceof IField) {
			enclosingElement = enclosingElement.getParent();
		}
		if (!(enclosingElement instanceof IMethod)) {
			return false;
		}

		try {
			if (isBeforeName(offset, (IMethod) enclosingElement)) {
				return false;
			}
			enclosingMethod = (IMethod) enclosingElement;

			// find the most outer enclosing (non-namespace) type, if exists
			while (enclosingElement != null) {
				if (enclosingElement instanceof ISourceModule) {
					break;
				}
				if (enclosingElement instanceof IType) {
					if (PHPFlags.isNamespace(((IType) enclosingElement).getFlags())) {
						break;
					}
					enclosingType = (IType) enclosingElement;
				}
				enclosingElement = enclosingElement.getParent();
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
			return false;
		}

		return true;
	}

	public IMethod getEnclosingMethod() {
		return enclosingMethod;
	}

	public IType getEnclosingType() {
		return enclosingType;
	}
}
