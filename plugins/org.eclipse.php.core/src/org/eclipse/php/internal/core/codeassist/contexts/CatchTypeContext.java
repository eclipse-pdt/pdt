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

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		int classEnd = getCatchStart() + 5; // "catch"
		TextSequence statementText = getStatementText();
		statementText = statementText.subTextSequence(classEnd, statementText.length());

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

		startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, statementText.length() - 1,
				true);
		if (statementText.charAt(startPosition) == '$') {
			return false;
		}
		return true;
	}
}
