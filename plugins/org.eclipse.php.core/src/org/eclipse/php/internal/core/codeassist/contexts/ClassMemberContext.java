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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a class member completion.
 * <br/>Examples:
 * <pre>
 *  1. A::|
 *  2. $this->
 *  etc...
 * </pre>
 * @author michael
 */
public abstract class ClassMemberContext extends StatementContext {

	/**
	 * Trigger type of the member invocation
	 */
	enum Trigger {
		/** Class trigger type: '::' */
		CLASS,
		/** Object trigger type: '->' */
		OBJECT,
	}

	private Trigger triggerType;

	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		int totalLength = statementText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read whitespace
		int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);

		startPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, startPosition);
		if (startPosition <= 2) { // there's no trigger of length less than 2 characters
			return false;
		}

		String triggerText = statementText.subSequence(startPosition - 2, startPosition).toString();
		if (triggerText.equals("->")) {
			triggerType = Trigger.OBJECT;
		} else if (triggerText.equals("::")) {
			triggerType = Trigger.CLASS;
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Returns trigger type of the class member completion 
	 * @return
	 */
	public Trigger getTriggerType() {
		return triggerType;
	}

	public int getPrefixEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_OBJECT_OPERATOR || phpToken.getType() == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM) {
			IPhpScriptRegion phpScriptRegion = getPhpScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPhpToken(phpToken.getEnd());
			return getRegionCollection().getStartOffset() + phpScriptRegion.getStart() + nextRegion.getTextEnd();
		}
		return super.getPrefixEnd();
	}

}
