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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.core.internal.text.rules.SimpleStructuredRegion;

public class PhpIndentationFormatter {

	private final IIndentationStrategy defaultIndentationStrategy;
	private final IIndentationStrategy curlyCloseIndentationStrategy;
	private final IIndentationStrategy caseDefaultIndentationStrategy;
	private final IIndentationStrategy commentIndentationStrategy;
	private final IIndentationStrategy phpCloseTagIndentationStrategy;

	private final int length;
	private final int start;

	private static final byte CHAR_TAB = '\t';
	private static final byte CHAR_SPACE = ' ';

	private final StringBuilder resultBuffer = new StringBuilder();
	private final StringBuilder lastEmptyLineIndentationBuffer = new StringBuilder();
	private int lastEmptyLineNumber;

	public PhpIndentationFormatter(int start, int length, IndentationObject indentationObject) {
		this.start = start;
		this.length = length;
		this.defaultIndentationStrategy = new DefaultIndentationStrategy(indentationObject);
		this.curlyCloseIndentationStrategy = new CurlyCloseIndentationStrategy();
		this.caseDefaultIndentationStrategy = new CaseDefaultIndentationStrategy(indentationObject);
		this.commentIndentationStrategy = new CommentIndentationStrategy(indentationObject);
		this.phpCloseTagIndentationStrategy = new PHPCloseTagIndentationStrategy(indentationObject);
	}

	// XXX: also give the possibility to modify this.start, this.length and
	// indentationObject?
	protected void reset() {
		resultBuffer.setLength(0);
		lastEmptyLineIndentationBuffer.setLength(0);
		lastEmptyLineNumber = -1;
	}

	public void format(IStructuredDocumentRegion sdRegion) {
		assert sdRegion != null;

		reset();

		// resolve formatter range
		int regionStart = sdRegion.getStartOffset();
		int regionEnd = sdRegion.getEndOffset();

		int formatRequestStart = getStart();
		int formatRequestEnd = formatRequestStart + getLength();

		int startFormat = Math.max(formatRequestStart, regionStart);
		int endFormat = Math.min(formatRequestEnd, regionEnd);

		// calculate lines
		IStructuredDocument document = sdRegion.getParentDocument();
		int lineIndex = document.getLineOfOffset(startFormat);
		int endLineIndex = document.getLineOfOffset(endFormat);

		// TODO get token of each line then insert line separator after { and
		// after } if there is no line separator
		// format each line
		for (; lineIndex <= endLineIndex; lineIndex++) {
			formatLine(document, lineIndex);
		}

		reset();
	}

	private void doEmptyLineIndentation(IStructuredDocument document, StringBuilder result, int lineNumber,
			int forOffset, int forLength) throws BadLocationException {
		if (lastEmptyLineNumber >= 0 && lastEmptyLineNumber == lineNumber - 1) {
			// re-use previous line indentation if previous line was also an
			// empty line (to avoid unnecessary calls to placeMatchingBlanks())
			result.append(lastEmptyLineIndentationBuffer);
		} else {
			getDefaultIndentationStrategy().placeMatchingBlanks(document, result, lineNumber, forOffset);
			lastEmptyLineIndentationBuffer.setLength(0);
			lastEmptyLineIndentationBuffer.append(result);
		}
		lastEmptyLineNumber = lineNumber;
		if (!(forLength == 0 && result.length() == 0)) {
			document.replace(forOffset, forLength, result.toString());
		}
	}

	/**
	 * formats a PHP line according to the strategies and formatting conventions
	 * 
	 * @param document
	 * @param lineNumber
	 */
	private void formatLine(IStructuredDocument document, int lineNumber) {
		resultBuffer.setLength(0);

		try {

			// get original line information
			final IRegion originalLineInfo = document.getLineInformation(lineNumber);
			final int originalLineStart = originalLineInfo.getOffset();
			int originalLineLength = originalLineInfo.getLength();

			// get formatted line information
			final String lineText = document.get(originalLineStart, originalLineLength);
			final IRegion formattedLineInformation = getFormattedLineInformation(originalLineInfo, lineText);

			if (!shouldReformat(document, formattedLineInformation)) {
				return;
			}

			// fast resolving of empty line
			if (originalLineLength == 0) {
				doEmptyLineIndentation(document, resultBuffer, lineNumber, originalLineStart, originalLineLength);
				return;
			}

			// remove ending spaces.
			final int formattedLineStart = formattedLineInformation.getOffset();
			final int formattedTextEnd = formattedLineStart + formattedLineInformation.getLength();
			if (formattedTextEnd != originalLineStart + originalLineLength) {
				// resolve blank line
				if (formattedLineStart == formattedTextEnd) {
					doEmptyLineIndentation(document, resultBuffer, lineNumber, originalLineStart, originalLineLength);
					return;
				}
				document.replace(formattedTextEnd, originalLineStart + originalLineLength - formattedTextEnd, ""); //$NON-NLS-1$
				originalLineLength = formattedTextEnd - originalLineStart;
			}

			// get regions
			final int endingWhiteSpaces = formattedLineStart - originalLineStart;
			final IIndentationStrategy insertionStrategy;
			final IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(formattedLineStart);
			int scriptRegionPos = sdRegion.getStartOffset();
			ITextRegion firstTokenInLine = sdRegion.getRegionAtCharacterOffset(formattedLineStart);
			ITextRegion lastTokenInLine = null;
			int regionStart = firstTokenInLine != null ? sdRegion.getStartOffset(firstTokenInLine) : 0;
			if (firstTokenInLine instanceof ITextRegionContainer) {
				scriptRegionPos = regionStart;
				ITextRegionContainer container = (ITextRegionContainer) firstTokenInLine;
				firstTokenInLine = container.getRegionAtCharacterOffset(formattedLineStart);
				regionStart += firstTokenInLine.getStart();
			}
			if (firstTokenInLine instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) firstTokenInLine;
				if (regionStart + scriptRegion.getEnd() <= formattedLineStart) {
					// XXX: should never happen
					doEmptyLineIndentation(document, resultBuffer, lineNumber, originalLineStart, 0);
					return;
				}
				if (scriptRegion.isPhpQuotesState(formattedLineStart - regionStart)
						&& (formattedLineStart - regionStart == 0
								|| scriptRegion.isPhpQuotesState(formattedLineStart - regionStart - 1))) {
					// do never indent the content of php strings
					return;
				}
				scriptRegionPos = regionStart;
				firstTokenInLine = scriptRegion.getPhpToken(formattedLineStart - regionStart);
				if (regionStart + firstTokenInLine.getStart() < originalLineStart
						&& firstTokenInLine.getType() == PHPRegionTypes.WHITESPACE) {
					firstTokenInLine = scriptRegion.getPhpToken(firstTokenInLine.getEnd());
				}
				if (formattedTextEnd <= regionStart + scriptRegion.getEnd()) {
					lastTokenInLine = scriptRegion.getPhpToken(formattedTextEnd - regionStart - 1);
					if (regionStart + lastTokenInLine.getEnd() > originalLineStart + originalLineLength
							&& lastTokenInLine.getType() == PHPRegionTypes.WHITESPACE
							&& lastTokenInLine.getStart() > 0) {
						lastTokenInLine = scriptRegion.getPhpToken(lastTokenInLine.getStart() - 1);
					}
				}
			}

			// if the next char is not from this line
			if (firstTokenInLine == null) {
				doEmptyLineIndentation(document, resultBuffer, lineNumber, originalLineStart, 0);
				return;
			}

			String firstTokenType = firstTokenInLine.getType();

			if (firstTokenType == PHPRegionTypes.PHP_CASE || firstTokenType == PHPRegionTypes.PHP_DEFAULT) {
				insertionStrategy = caseDefaultIndentationStrategy;
			} else if (isInsideOfPHPCommentRegion(firstTokenType)) {
				insertionStrategy = commentIndentationStrategy;
			} else if (firstTokenType == PHPRegionTypes.PHP_CLOSETAG) {
				insertionStrategy = phpCloseTagIndentationStrategy;
			} else {
				insertionStrategy = getIndentationStrategy(lineText.charAt(endingWhiteSpaces));
			}

			// Fill the buffer with blanks as if we added a "\n" to the end of
			// the prev element.
			// insertionStrategy.placeMatchingBlanks(editor,doc,insertionStrtegyKey,resultBuffer,startOffset-1);
			insertionStrategy.placeMatchingBlanks(document, resultBuffer, lineNumber, originalLineStart);

			// replace the starting spaces
			final String newIndentation = resultBuffer.toString();
			final String oldIndentation = lineText.substring(0, endingWhiteSpaces);
			if (!StringUtils.equals(oldIndentation, newIndentation)) {
				document.replace(originalLineStart, endingWhiteSpaces, newIndentation);
			}

		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	/**
	 * @return whether we are inside a php comment
	 */
	private boolean isInsideOfPHPCommentRegion(String tokenType) {
		return PHPPartitionTypes.isPHPMultiLineCommentRegion(tokenType)
				|| PHPPartitionTypes.isPHPMultiLineCommentEndRegion(tokenType)
				|| PHPPartitionTypes.isPHPDocRegion(tokenType) || PHPPartitionTypes.isPHPDocEndRegion(tokenType);
	}

	/**
	 * @return the formatted line (without whitespaces) information
	 */
	private IRegion getFormattedLineInformation(IRegion lineInfo, String lineText) {
		// start checking from left and right to the center
		int leftNonWhitespaceChar = 0;
		int rightNonWhitespaceChar = lineText.length() - 1;
		final char[] chars = lineText.toCharArray();

		for (; leftNonWhitespaceChar <= rightNonWhitespaceChar; leftNonWhitespaceChar++) {
			if (chars[leftNonWhitespaceChar] != CHAR_SPACE && chars[leftNonWhitespaceChar] != CHAR_TAB) {
				break;
			}
		}
		for (; leftNonWhitespaceChar <= rightNonWhitespaceChar; rightNonWhitespaceChar--) {
			if (chars[rightNonWhitespaceChar] != CHAR_SPACE && chars[rightNonWhitespaceChar] != CHAR_TAB) {
				break;
			}
		}

		// if line is empty then the indexes were switched
		if (leftNonWhitespaceChar > rightNonWhitespaceChar)
			return new SimpleStructuredRegion(lineInfo.getOffset(), 0);

		// if there are no changes - return the original line information, else
		// build a fixed region
		return leftNonWhitespaceChar == 0 && rightNonWhitespaceChar == lineText.length() - 1 ? lineInfo
				: new SimpleStructuredRegion(lineInfo.getOffset() + leftNonWhitespaceChar,
						rightNonWhitespaceChar - leftNonWhitespaceChar + 1);
	}

	private boolean shouldReformat(IStructuredDocument document, IRegion lineInfo) {
		final String checkedLineBeginState = FormatterUtils.getPartitionType(document, lineInfo.getOffset());
		return ((checkedLineBeginState == PHPPartitionTypes.PHP_DEFAULT)
				|| (checkedLineBeginState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)
				|| (checkedLineBeginState == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)
				|| (checkedLineBeginState == PHPPartitionTypes.PHP_DOC)
				|| (checkedLineBeginState == PHPPartitionTypes.PHP_QUOTED_STRING));
	}

	protected final int getStart() {
		return start;
	}

	protected final int getLength() {
		return length;
	}

	protected IIndentationStrategy getIndentationStrategy(char c) {
		if (c == '}') {
			return curlyCloseIndentationStrategy;
		}
		return getDefaultIndentationStrategy();
	}

	private IIndentationStrategy getDefaultIndentationStrategy() {
		return defaultIndentationStrategy;
	}
}
