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

import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents the state when staying in a use statement in a
 * namespace name part. <br/>
 * Examples:
 * 
 * <pre>
 *  use A\B| 
 *  etc...
 * </pre>
 * 
 * @author zhaozw
 */
public class NamespaceUseNameContext extends UseStatementContext {

	private IType[] namespaces;
	private int elementStart;
	private boolean isGlobal;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		try {
			if (useTrait) {
				TextSequence statementText = getStatementText();
				if (statementText.toString().indexOf('{') >= 0
						|| statementText.toString().indexOf('}') >= 0) {
					return false;
				}
			} else {
				String previousWord = getPreviousWord();
				if (!"use".equalsIgnoreCase(previousWord)) { //$NON-NLS-1$
					return false;
				}
			}
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		if (getPhpVersion().isLessThan(PHPVersion.PHP5_3)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		int totalLength = statementText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, totalLength); // read whitespace
		elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				statementText, endPosition, true);

		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, elementStart);
		if (elementStart < 1) { // there's no trigger of length less than 1
								// characters
			return false;
		}

		String triggerText = statementText.subSequence(elementStart - 1,
				elementStart).toString();
		if (!triggerText.equals("\\")) { //$NON-NLS-1$
			return false;
		}

		isGlobal = false;
		if (elementStart == 1) {
			isGlobal = true;
			return true;
		}

		int endNamespace = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, elementStart - 1);
		int nsNameStart = PHPTextSequenceUtilities.readNamespaceStartIndex(
				statementText, endNamespace, false);
		String nsName = statementText.subSequence(nsNameStart, elementStart)
				.toString();
		if (nsName.indexOf('\\') < 0) {
			return false;
		}
		if (!nsName.startsWith("\\")) { //$NON-NLS-1$
			nsName = "\\" + nsName; //$NON-NLS-1$
		}
		try {
			namespaces = PHPModelUtils.getNamespaceOf(nsName, sourceModule,
					offset, null, null);
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
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
	 * Returns the left hand side possible namespace elements.
	 */
	public IType[] getNamespaces() {
		return namespaces;
	}

	/**
	 * Returns whether the namespace is global (only the '\' presents in the
	 * left side)
	 */
	public boolean isGlobal() {
		return isGlobal;
	}

	public String getPrefix() throws BadLocationException {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, statementLength); // read whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				statementText, prefixEnd, true);
		return statementText.subSequence(prefixStart, prefixEnd).toString();
	}

	public int getPrefixEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_NS_SEPARATOR) {
			if (phpToken.getLength() == phpToken.getTextLength()) {
				IPhpScriptRegion phpScriptRegion = getPhpScriptRegion();
				ITextRegion nextRegion = phpScriptRegion.getPhpToken(phpToken
						.getEnd());
				return getRegionCollection().getStartOffset()
						+ phpScriptRegion.getStart() + nextRegion.getTextEnd();
			}
		}
		return super.getPrefixEnd();
	}

}
