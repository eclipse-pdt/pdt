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
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a class member completion. <br/>
 * Examples:
 * 
 * <pre>
 *  1. A::|
 *  2. $this-&gt;
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class ClassMemberContext extends StatementContext {

	/**
	 * Trigger type of the member invocation
	 */
	public enum Trigger {
		/** Class trigger type: '::' */
		CLASS("::"), //$NON-NLS-1$
		/** Object trigger type: '->' */
		OBJECT("->"), ; //$NON-NLS-1$

		String name;

		Trigger(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private Trigger triggerType;
	private IType[] types;
	private int elementStart;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		// boolean b = isInUseTraitStatement();
		// if (b) {
		// return false;
		// }
		// TextSequence statementText1 = getStatementText(statementText
		// .getOriginalOffset(0) - 2);
		// statementText1.toString();
		int totalLength = statementText.length();
		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, totalLength);
		elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				statementText, elementStart, true);
		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, elementStart);
		if (elementStart <= 2) { // there's no trigger of length less than 2
			// characters
			return false;
		}

		String triggerText = statementText.subSequence(elementStart - 2,
				elementStart).toString();
		if (triggerText.equals("->")) { //$NON-NLS-1$
			triggerType = Trigger.OBJECT;
		} else if (triggerText.equals("::")) { //$NON-NLS-1$
			triggerType = Trigger.CLASS;
		} else {
			return false;
		}

		types = getCompanion().getLeftHandType(this, !isInUseTraitStatement());
		return true;
	}

	/**
	 * Returns the start position of class/object element relative to the text
	 * sequence.
	 * 
	 * @see #getStatementText()
	 */
	public int getElementStart() {
		return elementStart;
	}

	/**
	 * Returns the left hand side possible types. For example:
	 * 
	 * <pre>
	 * 1. $a-&gt;foo() : returns possible types of the $a
	 * 2. A::foo() : returns the 'A' type
	 * 3. $a-&gt;foo()-&gt;bar() : returns possible return types of method foo() in $a
	 * etc...
	 * </pre>
	 */
	public IType[] getLhsTypes() {
		return types;
	}

	/**
	 * Returns trigger type of the class member completion
	 * 
	 * @return
	 */
	public Trigger getTriggerType() {
		return triggerType;
	}

	public int getPrefixEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_OBJECT_OPERATOR
				|| phpToken.getType() == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM) {
			IPhpScriptRegion phpScriptRegion = getPhpScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPhpToken(phpToken
					.getEnd());

			if (phpToken.getTextLength() == phpToken.getLength()) {
				int addOffset = 0;
				if (nextRegion.getType() == PHPRegionTypes.PHP_TOKEN
						|| nextRegion.getType() == PHPRegionTypes.PHP_SEMICOLON) {
					addOffset = phpToken.getEnd();
				} else {
					addOffset = nextRegion.getTextEnd();
				}
				return getRegionCollection().getStartOffset()
						+ phpScriptRegion.getStart() + addOffset;
			}
		}
		return super.getPrefixEnd();
	}
}
