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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a method declaration on the
 * method name. <br/>
 * Examples:
 * 
 * <pre>
 *  1. function |
 *  2. function foo|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class MethodNameContext extends FunctionDeclarationContext {

	private IType declaringClass;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		int functionEnd = getFunctionEnd();

		for (int i = statementText.length() - 1; i >= functionEnd; i--) {
			if (statementText.charAt(i) == '(') {
				return false;
			}
		}

		declaringClass = PHPModelUtils.getCurrentType(getCompanion().getSourceModule(),
				statementText.getOriginalOffset(functionEnd));
		if (declaringClass == null) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the method class
	 * 
	 * @return
	 */
	public IType getDeclaringClass() {
		return declaringClass;
	}
}
