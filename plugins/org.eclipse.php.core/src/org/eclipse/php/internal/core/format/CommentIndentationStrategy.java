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

public class CommentIndentationStrategy extends DefaultIndentationStrategy {

	/**
	 * If we are inside a comment, check the previous line: In case it is the
	 * comment start (meaning the first line), this line will be indented.
	 */
	@Override
	public void placeMatchingBlanks(IStructuredDocument document,
			StringBuffer result, int lineNumber, int forOffset)
			throws BadLocationException {

		if (lineNumber == 0) {
			return;
		}
		IRegion previousLine = document.getLineInformation(lineNumber - 1);

		IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(previousLine.getOffset());
		if (sdRegion == null)
			return;

		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(previousLine
				.getOffset());
		if (tRegion == null)
			return;

		int regionStart = sdRegion.getStartOffset(tRegion);
		// in case of container we have the extract the PhpScriptRegion
		if (tRegion instanceof ITextRegionContainer) {
			ITextRegionContainer container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(previousLine
					.getOffset());
			regionStart += tRegion.getStart();
		}

		if (tRegion instanceof IPhpScriptRegion) {
			IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
			tRegion = scriptRegion.getPhpToken(previousLine.getOffset()
					- regionStart);
			if (tRegion.getStart() + regionStart < previousLine.getOffset()) {
				tRegion = scriptRegion.getPhpToken(tRegion.getEnd());
			}
		}

		// Check if the previous line is the start of the comment.
		if (tRegion.getType() == PHPRegionTypes.PHP_COMMENT_START
				|| tRegion.getType() == PHPRegionTypes.PHPDOC_COMMENT_START) {
			final String blanks = FormatterUtils.getLineBlanks(document,
					previousLine);
			// add the indentation of jthe previous line and a single space in
			// addition
			result.append(blanks);
			result.append(" "); //$NON-NLS-1$
		} else {
			super.placeMatchingBlanks(document, result, lineNumber, forOffset);
		}
	}

}
