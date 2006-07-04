/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class CaseDefaultIndentationStrategy implements IIndentationStrategy {

	public void placeMatchingBlanks(IStructuredDocument document, StringBuffer result, int lineNumber, int offset) throws BadLocationException {

		IRegion indentationBase = null;
		boolean found = false;
		boolean addIndentation = false;
		int curlyCount = 0;
		/*
		 *  TODO this function has a bug in it: if there is a '{' inside inner state then it will not ignore it 
		 *  as it should.
		 */
		while (offset >= 0) {
			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
			if (sdRegion == null) {
				return;
			}
			if (sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
				offset = sdRegion.getStartOffset() - 1;
				continue;
			}
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);

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
				} else if ((token == PHPRegionTypes.PHP_CASE) || (token == PHPRegionTypes.PHP_DEFAULT)) {
					found = true;
				}
				if (found) {
					indentationBase = document.getLineInformationOfOffset(tRegion.getStart() + sdRegion.getStartOffset());
					break;
				}

				tRegion = sdRegion.getRegionAtCharacterOffset(tRegion.getStart() + sdRegion.getStartOffset() - 1);
			}
			if (found) {
				break;
			}
			offset = sdRegion.getStartOffset() - 1;
		}

		if (indentationBase != null) {
			String blanks = FormatterUtils.getLineBlanks(document, indentationBase);
			result.append(blanks);
			if (addIndentation) {
				int indentationSize = FormatPreferencesSupport.getInstance().getIndentationSize(document);
				char indentationChar = FormatPreferencesSupport.getInstance().getIndentationChar(document);
				for (int i = 0; i < indentationSize; i++) {
					result.append(indentationChar);
				}
			}
		}

	}

}
