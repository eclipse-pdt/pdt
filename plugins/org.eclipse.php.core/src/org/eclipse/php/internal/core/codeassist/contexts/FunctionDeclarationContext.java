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
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a function declaration.
 * <br/>
 * Examples:
 * 
 * <pre>
 *  1. function |
 *  2. function foo(|) {}
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class FunctionDeclarationContext extends DeclarationContext {

	private int functionEnd = -1;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		ICompletionScope currentScope = getCompanion().getScope();
		if (currentScope.getType() != Type.FUNCTION
				&& !(currentScope.getType() == Type.HEAD && currentScope.getParent().getType() == Type.FUNCTION)) {
			return false;
		}

		TextSequence statementText = this.getStatementText();
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		while (statementText.length() > pos) {
			char ch = statementText.charAt(pos);
			if (Character.isJavaIdentifierPart(ch)) {
				sb.append(ch);
			} else if (ch == '(' || Character.isWhitespace(ch)) {
				if (sb.toString().equalsIgnoreCase("function")) { //$NON-NLS-1$
					functionEnd = pos;
					return true;
				} else if (PHPVersion.PHP7_3.isLessThan(getCompanion().getPHPVersion()) && sb.toString().equals("fn")) { //$NON-NLS-1$
					functionEnd = pos;
					return true;
				}
				if (ch == '(') {
					return false;
				}
				sb.setLength(0);
			}

			pos++;
		}

		return false;

	}

	/**
	 * Returns the end offset of word 'function' in function declaration
	 * relative to the statement text.
	 * 
	 * @see #getStatementText()
	 * @return
	 */
	public int getFunctionEnd() {
		return functionEnd;
	}
}
