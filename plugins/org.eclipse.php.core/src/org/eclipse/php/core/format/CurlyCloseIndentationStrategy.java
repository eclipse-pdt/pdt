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

public class CurlyCloseIndentationStrategy implements IIndentationStrategy {

	public void placeMatchingBlanks(IStructuredDocument document, StringBuffer result, int lineNumber, int forOffset) throws BadLocationException {
		final IRegion indentationBaseLine = getCurlyOpenLineInformation(document, forOffset);
		if (indentationBaseLine != null) {
			final String blanks = FormatterUtils.getLineBlanks(document, indentationBaseLine);
			result.append(blanks);
		}
	}

	/**
	 * This function returns the line in which the corresponding '{' of the '}' found
	 * 
	 *  TODO this function has a bug in it: if there is a '{' inside inner state then it will not ignore it 
	 *  as it should.
	 */
	protected IRegion getCurlyOpenLineInformation(IStructuredDocument document, int forOffset) throws BadLocationException {
		int offset = forOffset;
		int curlyCounter = 0;
		while (offset >= 0) {
			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
			if (sdRegion == null) {
				return null;
			}
			if (sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
				offset = sdRegion.getStartOffset() - 1;
				continue;
			}
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);

			while (tRegion != null) {
				String token = tRegion.getType();
				if (token == PHPRegionTypes.PHP_CURLY_OPEN) {
					curlyCounter--;
					if (curlyCounter < 0) {
						return document.getLineInformationOfOffset(tRegion.getStart() + sdRegion.getStartOffset());
					}
				} else if (token == PHPRegionTypes.PHP_CURLY_CLOSE) {
					curlyCounter++;
				}

				tRegion = sdRegion.getRegionAtCharacterOffset(tRegion.getStart() + sdRegion.getStartOffset() - 1);
			}
			offset = sdRegion.getStartOffset() - 1;
		}
		return null;

	}

}
