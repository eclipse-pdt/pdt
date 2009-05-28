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
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a line of a PHPDoc after the PHPDoc tag.
 * <br/>Example:
 * <pre>
 *   /**
 *    * @sometag |
 * </pre>
 * @author michael
 */
public abstract class PHPDocTagContext extends PHPDocContext {

	private String tagName;

	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();

		int tagEnd = statementText.length(), tagStart;
		boolean found = false;
		do {
			tagEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, tagEnd);
			tagStart = PHPTextSequenceUtilities.readIdentifierStartIndex(getPhpVersion(), getStatementText(), tagEnd, true);

			tagName = statementText.subSequence(tagStart, tagEnd).toString();

			if (tagStart > 0 && statementText.charAt(tagStart - 1) == '@') {
				found = true;
			}
			
			tagEnd = tagStart - 1;
		}
		while (!found && tagStart > 0);
		
		return found;
	}

	/**
	 * Returns the PHPDoc tag 
	 * @return
	 */
	public String getTagName() {
		return tagName;
	}
}
