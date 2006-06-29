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
import org.eclipse.php.core.documentModel.parser.PhpLexer;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class DefualtIndentationStrategy implements IIndentationStrategy {

	public void placeMatchingBlanks(IStructuredDocument document, StringBuffer result, int lineNumber, int forOffset) throws BadLocationException {
		int lastNonEmptyLineIndex = getIndentationBaseLine(document, lineNumber, forOffset);
		IRegion lastNonEmptyLine = document.getLineInformation(lastNonEmptyLineIndex);
		String blanks = FormatterUtils.getLineBlanks(document, lastNonEmptyLine);
		result.append(blanks);

		int lastLineEndOffset = lastNonEmptyLine.getOffset() + lastNonEmptyLine.getLength();
		int offset;
		int line;
		if (forOffset < lastLineEndOffset) {
			offset = forOffset;
			line = lineNumber;
		} else {
			offset = lastLineEndOffset;
			line = lastNonEmptyLineIndex;
		}
		if (shouldIndent(document, offset, line)) {
			int indentationSize = FormatPreferencesSupport.getInstance().getIndentationSize(document);
			char indentationChar = FormatPreferencesSupport.getInstance().getIndentationChar(document);
			for (int i = 0; i < indentationSize; i++) {
				result.append(indentationChar);
			}
		}
	}

	private int getIndentationBaseLine(IStructuredDocument document, int lineNumber, int offset) throws BadLocationException {
		int currLineIndex = lineNumber;
		while (currLineIndex >= 0) {
			IRegion lineInfo = document.getLineInformation(currLineIndex);
			if (lineInfo.getLength() == 0) {
				//then its not indentation base for sure
				currLineIndex--;
				continue;
			}
			int currLineEndOffset = lineInfo.getOffset() + lineInfo.getLength();
			boolean isIndentationBase = isIndentationBase(document, Math.min(offset, currLineEndOffset), offset);
			if (isIndentationBase) {
				return currLineIndex;
			}
			currLineIndex--;
		}
		return 0;
	}

	private boolean isIndentationBase(IStructuredDocument document, int checkedOffset, int forOffset) throws BadLocationException {
		IRegion lineInfo = document.getLineInformationOfOffset(checkedOffset);
		int lineStart = lineInfo.getOffset();

		if (isBlanks(document, lineStart, checkedOffset, forOffset)) {
			return false;
		}

		//need to get to the first tRegion - so that we wont get the state of the 
		//tRegion in the previos line
		while (Character.isWhitespace(document.getChar(lineStart))) {
			lineStart++;
		}

		String checkedLineBeginState = FormatterUtils.getPartitionType(document, lineStart);

		String checkedLineEndState = FormatterUtils.getPartitionType(document, checkedOffset);

		String forLineEndState = FormatterUtils.getPartitionType(document, forOffset);

		if (shouldNotConsiderAsIndentationBase(checkedLineBeginState, forLineEndState)) {
			return false;
		}

		return (lineShouldInedent(checkedLineBeginState, checkedLineEndState) || (forLineEndState == checkedLineBeginState));
	}

	boolean shouldIndent(IStructuredDocument document, int offset, int lineNumber) {
		try {
			IRegion lineInfo = document.getLineInformation(lineNumber);
			ITextRegion token = getLastToken(document, lineInfo, offset);
			String tokenType = (token == null) ? (PHPRegionTypes.WHITESPACE) : token.getType();
			if (tokenType == PHPRegionTypes.PHP_CURLY_OPEN) {
				return true;
			}

			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(lineInfo.getOffset()+1);

			if (tokenType == PHPRegionTypes.PHP_TOKEN && document.getChar(sdRegion.getStartOffset() + token.getStart()) == ':') {
				//checking if the line starts with "case" or "default"
				int currentOffset = sdRegion.getStartOffset() + token.getStart() - 1;
				while (currentOffset >= lineInfo.getOffset()) {
					token = sdRegion.getRegionAtCharacterOffset(currentOffset);
					tokenType = token.getType();
					if ((tokenType == PHPRegionTypes.PHP_CASE) || (tokenType == PHPRegionTypes.PHP_DEFAULT)) {
						return true;
					}
					currentOffset = sdRegion.getStartOffset() + token.getStart() - 1;
				}

			}
		} catch (BadLocationException e) {
		}
		return false;
	}

	boolean shouldNotConsiderAsIndentationBase(String currentState, String forState) {
		return (currentState != forState) && (currentState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || currentState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || currentState == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT || currentState == PHPPartitionTypes.PHP_DOC || currentState == PHPPartitionTypes.PHP_QUOTED_STRING);
	}

	boolean lineShouldInedent(String beginState, String endState) {
		return (beginState == PHPPartitionTypes.PHP_DEFAULT || endState == PHPPartitionTypes.PHP_DEFAULT);
	}

	/**
	 * Check if the line contains any non blank chars.
	 */
	protected static boolean isBlanks(IStructuredDocument document, int startOffset, int endOffset, int currentOffset) throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim().length() == 0 || document.get(startOffset, currentOffset - startOffset).trim().length() == 0;
	}

	protected ITextRegion getLastToken(IStructuredDocument document, IRegion line, int forOffset) {

		int startOffset = line.getOffset();
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(forOffset);
		int regionStartOffset = sdRegion.getStartOffset();
		int offset = forOffset;
		while (startOffset <= offset) {
			if (document.getLength() == offset) {//if we are at the end of the document then we need to check one step before
				offset--;
				continue;
			}
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			if (tRegion == null) {
				sdRegion = sdRegion.getPrevious();
				if (sdRegion != null) {
					regionStartOffset = sdRegion.getStartOffset();
					tRegion = sdRegion.getLastRegion();
					offset = tRegion.getStart() + regionStartOffset - 1;
					continue;
				}
				return null;
			}
			if (tRegion.getStart() + regionStartOffset < startOffset) {
				//if the tRegion started before the line was then the first char in this line was a whitespace 
				return null;
			}
			if (tRegion.getTextEnd() + regionStartOffset > forOffset) {
				offset = tRegion.getStart() + regionStartOffset - 1;
				continue;
			}
			if (!PhpLexer.isPHPCommentState(tRegion.getType()) && tRegion.getType() != PHPRegionTypes.WHITESPACE) {
				return tRegion;
			}
			offset = tRegion.getStart() + regionStartOffset - 1;
		}

		return null;
	}

}
