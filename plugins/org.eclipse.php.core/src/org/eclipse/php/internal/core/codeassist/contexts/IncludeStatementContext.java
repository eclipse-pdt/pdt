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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in an include statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. include '|
 *  2. include 'ab|
 *  etc...
 * </pre>
 */
public class IncludeStatementContext extends AbstractCompletionContext {

	private int variantLength = 0;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		return isIncludeStatement(statementText);
	}

	private final boolean isIncludeStatement(TextSequence statementText,
			String variant) {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=333165
		if (statementText.length() < variant.length() + 1) {
			return false;
		}
		final int length = variant.length();
		if (variant.equalsIgnoreCase(statementText.subSequence(0, length)
				.toString())
				&& Character.isWhitespace(statementText.subSequence(length,
						length + 1).charAt(0))) {
			this.variantLength = variant.length();
			return true;
		}
		return false;
	}

	private final boolean isIncludeStatement(TextSequence statementText) {
		return isIncludeStatement(statementText, "require_once") || isIncludeStatement(statementText, "require") || isIncludeStatement(statementText, "include_once") || isIncludeStatement(statementText, "include"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public boolean isExclusive() {
		return true;
	}

	public String getPrefix() throws BadLocationException {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int prefixEnd = statementText.length();
		final TextSequence cutTextSequence = statementText.cutTextSequence(0,
				this.variantLength);
		int prefixStart = PHPTextSequenceUtilities.readForwardUntilDelim(
				cutTextSequence, 0, new char[] { '\'', '"' });
		int i = this.variantLength + prefixStart + 1;
		if (i <= prefixEnd) {
			return statementText.subSequence(i, prefixEnd).toString();
		}
		return super.getPrefix();
	}
}
