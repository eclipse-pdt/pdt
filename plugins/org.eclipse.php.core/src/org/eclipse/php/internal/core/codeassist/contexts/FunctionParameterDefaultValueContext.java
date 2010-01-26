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
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a function parameter
 * initial value. <br/>
 * Examples:
 * 
 * <pre>
 * 
 *  function foo($a = self::DEFAULT_VALUE) {}, where self:DEFAULT_VALUE is a class constant
 * </pre>
 * 
 * @author vadim.p
 */
public class FunctionParameterDefaultValueContext extends
		FunctionParameterContext {

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		return getTriggerChar() == ':';
	}

	@Override
	protected char getTriggerChar() {
		TextSequence statementText = getStatementText();
		int functionEnd = getFunctionEnd();

		// are we inside parameters part in function declaration statement
		for (int i = statementText.length() - 1; i >= functionEnd; i--) {
			if (statementText.charAt(i) == '(') {
				int j = statementText.length() - 1;
				for (; j > i; j--) {
					char charAt = statementText.charAt(j);
					char charAtBefore = 0;
					if (j > i + 1) {
						charAtBefore = statementText.charAt(j);
					}
					if (charAt == ':' && charAtBefore == ':') {
						return charAt;
					}
				}
				return statementText.charAt(i);
			}
		}
		return 0;
	}
}
