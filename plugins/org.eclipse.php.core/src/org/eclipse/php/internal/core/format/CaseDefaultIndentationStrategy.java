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
package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class CaseDefaultIndentationStrategy implements IIndentationStrategy {

	public void placeMatchingBlanks(IStructuredDocument document,
			StringBuffer result, int lineNumber, int offset)
			throws BadLocationException {

		IRegion indentationBase = null;
		boolean found = false;
		boolean addIndentation = false;
		int curlyCount = 0;
		/*
		 * TODO this function has a bug in it: if there is a '{' inside inner
		 * state then it will not ignore it as it should.
		 */

		IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(offset);
		if (sdRegion == null) {
			return;
		}

		// in 'case default' indentation case we move one char back to avoid
		// the first 'case' or 'default' region
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		int regionStart = sdRegion.getStartOffset(tRegion);

		// in case of container we have the extract the PhpScriptRegion
		if (tRegion instanceof ITextRegionContainer) {
			ITextRegionContainer container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
			regionStart += tRegion.getStart();
		}

		if (tRegion instanceof IPhpScriptRegion) {
			IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
			tRegion = scriptRegion.getPhpToken(offset - regionStart - 1);

			// go backward over the region to find a 'case' or 'default' region
			// in this case is the same indentation
			// other case if look for the '{' of the 'switch' region
			while (tRegion != null) {
				String token = tRegion.getType();
				if (token == PHPRegionTypes.PHP_CURLY_OPEN) {
					curlyCount--;
					if (curlyCount < 0) {
						found = true;
						addIndentation = true;
					}
				} else if (token == PHPRegionTypes.PHP_CURLY_CLOSE) {
					curlyCount++;
				} else if ((token == PHPRegionTypes.PHP_CASE)
						|| (token == PHPRegionTypes.PHP_DEFAULT)) {
					if (curlyCount == 0)
						found = true;
				}
				if (found) {
					indentationBase = document
							.getLineInformationOfOffset(tRegion.getStart()
									+ regionStart);
					break;
				}
				if (tRegion.getStart() > 0) {
					tRegion = scriptRegion.getPhpToken(tRegion.getStart() - 1);
				} else {
					break;
				}
			}
		}

		if (indentationBase != null) {
			String blanks = FormatterUtils.getLineBlanks(document,
					indentationBase);
			result.append(blanks);
			if (addIndentation) {
				int indentationSize = FormatPreferencesSupport.getInstance()
						.getIndentationSize(document);
				char indentationChar = FormatPreferencesSupport.getInstance()
						.getIndentationChar(document);
				for (int i = 0; i < indentationSize; i++) {
					result.append(indentationChar);
				}
			}
		}
	}
}
