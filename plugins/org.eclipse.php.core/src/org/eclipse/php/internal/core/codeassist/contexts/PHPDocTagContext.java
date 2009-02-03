/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying inside of a PHPDoc tag.
 * <br/>Examples:
 * <pre>
 *   1. /**
 *       * @|
 *       
 *   2. /**
 *       * @t|
 * </pre>
 * @author michael
 */
public class PHPDocTagContext extends PHPDocContext {
	
	private String tagName;
	
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (hasWhitespaceBeforeCursor()) { 
			return false;
		}
		
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int tagEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength);
		int tagStart = PHPTextSequenceUtilities.readIdentifierStartIndex(getPhpVersion(), getStatementText(), tagEnd, true);
		
		tagName = statementText.subSequence(tagStart, tagEnd).toString();
		
		tagStart = statementText.length() - getTagName().length();
		if (tagStart <= 0 || statementText.charAt(tagStart - 1) != '@') {
			return false; // this is not a tag
		}

		tagStart--;
		
		// verify that only whitespaces and '*' before the tag
		boolean founeX = false;
		for (; tagStart > 0; tagStart--) {
			if (!Character.isWhitespace(statementText.charAt(tagStart - 1))) {
				if (founeX || statementText.charAt(tagStart - 1) != '*') {
					break;
				}
				founeX = true;
			}
		}
		if (!founeX) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the PHPDoc tag name of this context
	 * @return
	 */
	public String getTagName() {
		return tagName;
	}
}
