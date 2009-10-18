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
 * This context represents state when staying in an exception type in a catch
 * statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. catch (|) { }
 *  2. catch (Ex|) { }
 * </pre>
 * 
 * @author michael
 */
public class CatchTypeContext extends CatchContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		int classEnd = getCatchStart() + 5; // "catch"
		TextSequence statementText = getStatementText();
		statementText = statementText.subTextSequence(classEnd, statementText
				.length());

		int startPosition = 0;
		for (; startPosition < statementText.length(); startPosition++) {
			if (statementText.charAt(startPosition) == '(') {
				break;
			}
		}
		if (startPosition == statementText.length()) {
			// the current position is before the '('
			return false;
		}

		startPosition = PHPTextSequenceUtilities.readForwardSpaces(
				statementText, startPosition + 1); // + 1 for the '('
		int endPosition = PHPTextSequenceUtilities.readIdentifierEndIndex(
				getPhpVersion(), statementText, startPosition, false);
		// String className = statementText.subSequence(startPosition,
		// endPosition).toString();

		if (endPosition != statementText.length()) {
			return false;
		}
		return true;
	}
}
