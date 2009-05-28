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

import org.eclipse.php.internal.core.util.text.TextSequence;


/**
 * This context represents the state when staying in a function parameter.
 * <br/>Examples:
 * <pre>
 *  1. function foo(A|) {}
 *  2. function foo(A $|) {}
 *  3. function foo($a, |) {}
 *  etc... 
 * </pre>
 * @author michael
 */
public abstract class FunctionParameterContext extends FunctionDeclarationContext {
	
	/**
	 * Scans the function parameters from the end to the beginning, and looks for the special
	 * character that determines what kind of code assist should we invoke:
	 * <ul>
	 * <li>'$' means: variable code assist</li>
	 * <li>'=' means: variable initializer code assist</li>
	 * <li>',' or '(' means: variable type code assist</li>
	 * </ul>
	 * @return
	 */
	protected char getTriggerChar() {
		
		TextSequence statementText = getStatementText();
		int functionEnd = getFunctionEnd();
		
		// are we inside parameters part in function declaration statement
		for (int i = statementText.length() - 1; i >= functionEnd ; i--) {
			if (statementText.charAt(i) == '(') {
				int j = statementText.length() - 1;
				for (; j > i; j--) {
					if (statementText.charAt(j) == '$' || statementText.charAt(j) == '=' || statementText.charAt(j) == ',') {
						return statementText.charAt(j);
					}
				}
				return statementText.charAt(i);
			}
		}
		return 0;
	}
}
