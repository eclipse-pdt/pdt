/*******************************************************************************
 * Copyright (c) 2009, 2016, 2017 IBM Corporation and others.
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
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class CommentIndentationStrategy extends DefaultIndentationStrategy {

	public CommentIndentationStrategy() {
	}

	/**
	 * 
	 * @param indentationObject
	 *            basic indentation preferences, can be null
	 */
	public CommentIndentationStrategy(IndentationObject indentationObject) {
		setIndentationObject(indentationObject);
	}

	/**
	 * If we are inside a comment, check the previous line: In case it is the
	 * comment start (meaning the first line), this line will be indented.
	 */
	@Override
	public void placeMatchingBlanks(IStructuredDocument document, StringBuilder result, int lineNumber, int forOffset)
			throws BadLocationException {

		if (lineNumber == 0) {
			return;
		}
		IRegion previousLine = document.getLineInformation(lineNumber - 1);

		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(previousLine.getOffset());
		if (sdRegion == null)
			return;

		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(previousLine.getOffset());
		if (tRegion == null)
			return;

		int regionStart = sdRegion.getStartOffset(tRegion);
		// in case of container we have to extract the PhpScriptRegion
		if (tRegion instanceof ITextRegionContainer) {
			ITextRegionContainer container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(previousLine.getOffset());
			regionStart += tRegion.getStart();
		}

		if (tRegion instanceof IPhpScriptRegion) {
			IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
			tRegion = scriptRegion.getPhpToken(previousLine.getOffset() - regionStart);
			if (regionStart + tRegion.getTextEnd() <= previousLine.getOffset()) {
				// blanks at previousLine.getOffset() belong to previous token
				tRegion = scriptRegion.getPhpToken(tRegion.getEnd());
			}
		}

		// Check if the previous line is the start of the comment.
		if (PHPPartitionTypes.isPHPMultiLineCommentStartRegion(tRegion.getType())
				|| PHPPartitionTypes.isPHPDocStartRegion(tRegion.getType())) {
			final String blanks = FormatterUtils.getLineBlanks(document, previousLine);
			// add the indentation of the previous line and a single space in
			// addition
			result.append(blanks);
			result.append(" "); //$NON-NLS-1$
		} else if (PHPPartitionTypes.isPHPMultiLineCommentRegion(tRegion.getType())
				|| PHPPartitionTypes.isPHPDocRegion(tRegion.getType())) {
			// add the indentation of the previous (non-empty) line belonging to
			// the comment
			String blanks = FormatterUtils.getLineBlanks(document, previousLine);
			// update line location
			lineNumber--;
			// check if previous line was not totally blank, otherwise choose
			// another line
			while (lineNumber > 0 && blanks.length() == previousLine.getLength()) {
				previousLine = document.getLineInformation(--lineNumber);
				blanks = FormatterUtils.getLineBlanks(document, previousLine);
			}
			result.append(blanks);
		} else {
			super.placeMatchingBlanks(document, result, lineNumber, forOffset);
		}
	}

}
