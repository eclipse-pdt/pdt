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
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class DefaultIndentationStrategy implements IIndentationStrategy {

	/**
	 * Check if the line contains any non blank chars.
	 */
	protected static boolean isBlanks(final IStructuredDocument document, final int startOffset, final int endOffset, final int currentOffset) throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim().length() == 0 || document.get(startOffset, currentOffset - startOffset).trim().length() == 0;
	}

	private int getIndentationBaseLine(final IStructuredDocument document, final int lineNumber, final int offset) throws BadLocationException {
		int currLineIndex = lineNumber;
		while (currLineIndex >= 0) {
			final IRegion lineInfo = document.getLineInformation(currLineIndex);
			if (lineInfo.getLength() == 0) {
				//then its not indentation base for sure
				currLineIndex--;
				continue;
			}
			final int currLineEndOffset = lineInfo.getOffset() + lineInfo.getLength();
			final boolean isIndentationBase = isIndentationBase(document, Math.min(offset, currLineEndOffset), offset);
			if (isIndentationBase)
				return currLineIndex;
			currLineIndex--;
		}
		return 0;
	}

	// go backward and look for any region except comment region or white space region 
	// in the given line
	private ITextRegion getLastTokenRegion(final IStructuredDocument document, final IRegion line, final int forOffset) throws BadLocationException {
		int offset = forOffset;
		int lineStartOffset = line.getOffset();
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		if (sdRegion == null) {
			return null;
		}

		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		if (tRegion == null && offset == document.getLength()) {
			offset -= 1;
			tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		}
		int regionStart = sdRegion.getStartOffset(tRegion);

		// in case of container we have the extract the PhpScriptRegion
		if (tRegion instanceof ITextRegionContainer) {
			ITextRegionContainer container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
			regionStart += tRegion.getStart();
		}

		if (tRegion instanceof IPhpScriptRegion) {
			IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
			tRegion = scriptRegion.getPhpToken(offset - regionStart);

			if (tRegion == null)
				return null;

			// go backward over the region to find a region (not comment nor whitespace)
			// in the same line
			do {
				String token = tRegion.getType();
				if(regionStart + tRegion.getStart() >= forOffset){
					//making sure the region found is not after the caret (https://bugs.eclipse.org/bugs/show_bug.cgi?id=222019 - caret before '{')
				} else if (!PHPPartitionTypes.isPHPCommentState(token) && token != PHPRegionTypes.WHITESPACE) {
					// not comment nor white space
					return tRegion;
				}
				tRegion = scriptRegion.getPhpToken(tRegion.getStart() - 1);
			} while (tRegion != null && tRegion.getStart() + regionStart > lineStartOffset);
		}

		return null;
	}

	private boolean isIndentationBase(final IStructuredDocument document, final int checkedOffset, final int forOffset) throws BadLocationException {
		final IRegion lineInfo = document.getLineInformationOfOffset(checkedOffset);
		int lineStart = lineInfo.getOffset();

		if (isBlanks(document, lineStart, checkedOffset, forOffset))
			return false;

		//need to get to the first tRegion - so that we wont get the state of the 
		//tRegion in the previos line
		while (Character.isWhitespace(document.getChar(lineStart)))
			lineStart++;

		//checked line beginning offset (after incrementing spaces in beginning
		final String checkedLineBeginState = FormatterUtils.getPartitionType(document, lineStart, true);

		//checked line end
		final String checkedLineEndState = FormatterUtils.getPartitionType(document, checkedOffset, true);

		//the current potential line for formatting begin offset
		final String forLineEndState = FormatterUtils.getPartitionType(document, forOffset);

		if (shouldNotConsiderAsIndentationBase(checkedLineBeginState, forLineEndState))
			return false;

		//Fix bug #201688
		if (((checkedLineBeginState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) || (checkedLineBeginState == PHPPartitionTypes.PHP_DOC)) && (checkedLineBeginState == forLineEndState)) {
			//the whole document
			final IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(lineStart);
			//the whole PHP script
			ITextRegion phpScriptRegion = sdRegion.getRegionAtCharacterOffset(lineStart);
			int phpContentStartOffset = sdRegion.getStartOffset(phpScriptRegion);

			if (phpScriptRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) phpScriptRegion;
				phpScriptRegion = container.getRegionAtCharacterOffset(lineStart);
				phpContentStartOffset += phpScriptRegion.getStart();
			}

			if (phpScriptRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) phpScriptRegion;
				//the region we are trying to check if it is the indent base for the line we need to format
				ContextRegion checkedRegion = (ContextRegion) scriptRegion.getPhpToken(lineStart - phpContentStartOffset);
				//the current region we need to format
				ContextRegion currentRegion = (ContextRegion) scriptRegion.getPhpToken(forOffset - phpContentStartOffset);
				String checkedType = checkedRegion.getType();
				String currentType = currentRegion.getType();
				//if we are in the beginning of a comment (DOC or Multi comment) and we have before another
				//Doc comment or Multi comment, the base line we'll be the beginning of the previous multi comment
				if (currentType.equals(PHPRegionTypes.PHPDOC_COMMENT_START) || currentType.equals(PHPRegionTypes.PHP_COMMENT_START)) {
					return checkedType.equals(PHPRegionTypes.PHPDOC_COMMENT_START) || checkedType.equals(PHPRegionTypes.PHP_COMMENT_START);
				}
			}
		}

		return lineShouldInedent(checkedLineBeginState, checkedLineEndState) || forLineEndState == checkedLineBeginState;
	}

	boolean lineShouldInedent(final String beginState, final String endState) {
		return beginState == PHPPartitionTypes.PHP_DEFAULT || endState == PHPPartitionTypes.PHP_DEFAULT;
	}

	public void placeMatchingBlanks(final IStructuredDocument document, final StringBuffer result, final int lineNumber, final int forOffset) throws BadLocationException {
		final int lastNonEmptyLineIndex = getIndentationBaseLine(document, lineNumber, forOffset);
		final IRegion lastNonEmptyLine = document.getLineInformation(lastNonEmptyLineIndex);
		final String blanks = FormatterUtils.getLineBlanks(document, lastNonEmptyLine);
		result.append(blanks);

		final int lastLineEndOffset = lastNonEmptyLine.getOffset() + lastNonEmptyLine.getLength();
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
			final int indentationSize = FormatPreferencesSupport.getInstance().getIndentationSize(document);
			final char indentationChar = FormatPreferencesSupport.getInstance().getIndentationChar(document);
			for (int i = 0; i < indentationSize; i++)
				result.append(indentationChar);
		}
	}

	boolean shouldIndent(final IStructuredDocument document, int offset, final int lineNumber) {
		try {
			final IRegion lineInfo = document.getLineInformation(lineNumber);

			final IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
			ITextRegion token = getLastTokenRegion(document, lineInfo, offset);
			if (token == null)
				return false;
			String tokenType = token.getType();

			if (tokenType == PHPRegionTypes.PHP_CURLY_OPEN)
				return true;

			ITextRegion scriptRegion = sdRegion.getRegionAtCharacterOffset(offset);
			if (scriptRegion == null && offset == document.getLength()) {
				offset -= 1;
				scriptRegion = sdRegion.getRegionAtCharacterOffset(offset);
			}
			int regionStart = sdRegion.getStartOffset(scriptRegion);
			// in case of container we have the extract the PhpScriptRegion
			if (scriptRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) scriptRegion;
				scriptRegion = container.getRegionAtCharacterOffset(offset);
				regionStart += scriptRegion.getStart();
			}
			if (scriptRegion instanceof IPhpScriptRegion) {
				if (tokenType == PHPRegionTypes.PHP_TOKEN && document.getChar(regionStart + token.getStart()) == ':') {
					//checking if the line starts with "case" or "default"
					int currentOffset = regionStart + token.getStart() - 1;
					while (currentOffset >= lineInfo.getOffset()) {
						token = ((IPhpScriptRegion) scriptRegion).getPhpToken(token.getStart() - 1);
						tokenType = token.getType();
						if (tokenType == PHPRegionTypes.PHP_CASE || tokenType == PHPRegionTypes.PHP_DEFAULT)
							return true;
						currentOffset = regionStart + token.getStart() - 1;
					}
				}
			}
		} catch (final BadLocationException e) {
		}
		return false;
	}

	boolean shouldNotConsiderAsIndentationBase(final String currentState, final String forState) {
		return currentState != forState && (currentState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || currentState == PHPPartitionTypes.PHP_DOC || currentState == PHPPartitionTypes.PHP_QUOTED_STRING);
	}

}
