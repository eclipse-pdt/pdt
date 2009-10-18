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
package org.eclipse.php.internal.core.codeassist.contexts;

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

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
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

		declaringClass = PHPModelUtils.getCurrentType(getSourceModule(),
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
