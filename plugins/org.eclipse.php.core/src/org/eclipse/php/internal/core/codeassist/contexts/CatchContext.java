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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a catch statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. catch (|) { }
 *  2. catch (Exception $|) { }
 *  3. catch (Ex|) { }
 * </pre>
 * 
 * @author michael
 */
public abstract class CatchContext extends StatementContext {

	protected static final Pattern CATCH_PATTERN = Pattern.compile(
			"catch\\s[^{]*", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	private int catchStart;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		TextSequence statementText = getStatementText();
		Matcher matcher = CATCH_PATTERN.matcher(statementText);
		catchStart = statementText.length();
		while (matcher.find()) {
			if (statementText.length() == matcher.end()) {
				catchStart = matcher.start() + 1; // for the white space before
													// the 'class'
				break;
			}
		}
		if (catchStart == statementText.length()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns starting offset of the catch keyword relative to the current
	 * statement.
	 * 
	 * @see #getStatementText()
	 * @return
	 */
	public int getCatchStart() {
		return catchStart;
	}
}
