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

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a namespace member completion.
 * <br/>
 * Examples:
 * 
 * <pre>
 *  1. A\B\|
 *  2. A\fo|
 *  3. \fo|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class NamespaceMemberContext extends StatementContext {

	private IType[] namespaces;
	private int elementStart;
	private boolean isGlobal;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getPHPVersion().isLessThan(PHPVersion.PHP5_3)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		// disable this context for use statement
		if (new UseStatementContext() {
		}.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		int totalLength = statementText.length();
		int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength); // read
																									// whitespace
		elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, endPosition, true);

		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(statementText, elementStart);
		if (elementStart < 1) { // there's no trigger of length less than 1
								// characters
			return false;
		}

		String triggerText = statementText.subSequence(elementStart - 1, elementStart).toString();
		if (!triggerText.equals(NamespaceReference.NAMESPACE_DELIMITER)) {
			return false;
		}

		isGlobal = false;
		if (elementStart == 1) {
			isGlobal = true;
			return true;
		}

		int endNamespace = PHPTextSequenceUtilities.readBackwardSpaces(statementText, elementStart);
		int nsNameStart = PHPTextSequenceUtilities.readNamespaceStartIndex(statementText, endNamespace, false);
		String nsName = nsNameStart < 0 ? "" //$NON-NLS-1$
				: statementText.subSequence(nsNameStart, elementStart).toString();
		if (nsName.equals(NamespaceReference.NAMESPACE_DELIMITER)) {
			isGlobal = true;
			return true;
		}

		try {
			namespaces = PHPModelUtils.getNamespaceOf(nsName, sourceModule, offset, null, null);
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

	@Override
	public String getPrefix() throws BadLocationException {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																										// whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, prefixEnd, true);
		return prefixStart < 0 ? "" //$NON-NLS-1$
				: statementText.subSequence(prefixStart, prefixEnd).toString();
	}

	@Override
	public int getReplacementEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_NS_SEPARATOR
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=458794
				// Check that there's no other (whitespace) characters
				// after the namespace separator, otherwise there's no reason
				// to retrieve the next region.
				&& phpToken.getLength() == NamespaceReference.NAMESPACE_DELIMITER.length()) {
			IPHPScriptRegion phpScriptRegion = getPHPScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPHPToken(phpToken.getEnd());
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=459368
			// Also check that we only retrieve PHP labels.
			if (nextRegion.getType() == PHPRegionTypes.PHP_LABEL) {
				return getRegionCollection().getStartOffset() + phpScriptRegion.getStart() + nextRegion.getTextEnd();
			}
		}
		return super.getReplacementEnd();
	}

}
