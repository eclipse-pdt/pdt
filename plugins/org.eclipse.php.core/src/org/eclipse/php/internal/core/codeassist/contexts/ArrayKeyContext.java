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
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents the state of staying inside an array key. <br/>
 * Examples:
 * 
 * <pre>
 *  1. $array[|]
 *  2. $array['|']
 *  3. $array[&quot;|&quot;]
 * </pre>
 * 
 * @author michael
 */
public class ArrayKeyContext extends AbstractCompletionContext {

	private String arrayVarName;
	private boolean hasQuotes;

	/**
	 * Returns array variable name
	 * 
	 * @return
	 */
	public String getArrayVarName() {
		return arrayVarName;
	}

	/**
	 * Returns whether there are quotes around array key
	 * 
	 * @return
	 */
	public boolean hasQuotes() {
		return hasQuotes;
	}

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		try {
			ITextRegion phpToken = getPHPToken();

			TextSequence statementText = getStatementText();
			int length = statementText.length();
			int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, length);
			int startPosition = PHPTextSequenceUtilities
					.readIdentifierStartIndex(getPhpVersion(), statementText,
							endPosition, true);

			hasQuotes = false;

			if (PHPPartitionTypes.isPHPQuotesState(phpToken.getType())) {
				hasQuotes = true;

				endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
						statementText, startPosition);
				if (endPosition == 0
						|| (statementText.charAt(endPosition - 1) != '\"' && statementText
								.charAt(endPosition - 1) != '\'')) {
					return false;
				}
				startPosition = endPosition - 1;
			}
			endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, startPosition);
			if (endPosition > 0
					&& (statementText.charAt(endPosition - 1) == '\"' || statementText
							.charAt(endPosition - 1) == '\'')) {
				hasQuotes = true;
				startPosition = endPosition - 1;
				endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
						statementText, startPosition);
			}
			if (endPosition == 0
					|| statementText.charAt(endPosition - 1) != '[') {
				return false;
			}

			endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, endPosition - 1);
			startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(
					getPhpVersion(), statementText, endPosition, true);
			arrayVarName = statementText
					.subSequence(startPosition, endPosition).toString();
			if (!arrayVarName.startsWith("$")) { //$NON-NLS-1$
				return false;
			}

		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return true;
	}

	public int getPrefixEnd() throws BadLocationException {
		int prefixEnd = super.getPrefixEnd();
		if (hasQuotes) {
			--prefixEnd;
		}
		return prefixEnd;
	}
}
