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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.annotations.NonNull;
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

	protected static final Pattern CATCH_PATTERN = Pattern.compile("catch[ \\t\\n\\r]*[^{]*", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);

	private int catchStart;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		TextSequence statementText = getStatementText();
		Matcher matcher = CATCH_PATTERN.matcher(statementText);
		catchStart = statementText.length();
		while (matcher.find()) {
			if (statementText.length() == matcher.end()) {
				catchStart = matcher.start();
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
