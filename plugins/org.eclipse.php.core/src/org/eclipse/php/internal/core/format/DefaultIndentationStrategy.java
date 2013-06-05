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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class DefaultIndentationStrategy implements IIndentationStrategy {

	private static final List<String> multilinePartitionTypes = Arrays
			.asList(new String[] { PHPPartitionTypes.PHP_QUOTED_STRING,
					PHPPartitionTypes.PHP_MULTI_LINE_COMMENT,
					PHPPartitionTypes.PHP_DOC });

	private static final String BLANK = ""; //$NON-NLS-1$
	private static boolean pairArrayParen;
	private static int pairArrayOffset;

	static class LineState {
		boolean inBracelessBlock;
		boolean inMultiLine;
		// IRegion baseRegion;
		// int baseRegionOffset;
		// boolean shouldIndent;
		StringBuffer indent = new StringBuffer();
	}

	/**
	 * Check if the line contains any non blank chars.
	 */
	protected static boolean isBlanks(final IStructuredDocument document,
			final int startOffset, final int endOffset, final int currentOffset)
			throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim()
				.length() == 0
				|| document.get(startOffset, currentOffset - startOffset)
						.trim().length() == 0;
	}

	public static int getIndentationBaseLine(
			final IStructuredDocument document, int currLineIndex,
			final int offset, boolean checkMultiLine)
			throws BadLocationException {
		// PHPHeuristicScanner scanner = PHPHeuristicScanner
		// .createHeuristicScanner(document, offset - 1, true);
		// int nonWhitespacePosition = scanner.findNonWhitespaceBackward(
		// offset - 1, PHPHeuristicScanner.UNBOUND);
		// if (document.getLineOfOffset(nonWhitespacePosition) == currLineIndex
		// && nonWhitespacePosition <= offset) {
		// ITextRegion textRegion = scanner
		// .getTextRegion(nonWhitespacePosition);
		// if (textRegion != null
		// && textRegion.getType()
		// .equals(PHPRegionTypes.PHP_SEMICOLON)) {
		// return currLineIndex;
		// }
		// }
		if (checkMultiLine) {
			currLineIndex = adjustLine(document, currLineIndex, offset);
		}
		// PHPHeuristicScanner scanner = PHPHeuristicScanner
		// .createHeuristicScanner(document, offset - 1, true);
		//
		// int token = scanner.previousToken(offset - 1,
		// PHPHeuristicScanner.UNBOUND);
		// if (document.getLineOfOffset(scanner.getPosition()) == currLineIndex
		// && scanner.getPosition() <= offset) {
		// if (token == PHPHeuristicScanner.TokenSEMICOLON) {
		// // return currLineIndex;
		// }
		// }

		// if (document.getLength() == offset) {
		// if (document.getChar(offset - 1) == ';') {
		// return currLineIndex;
		// }
		// } else {
		// if (document.getChar(offset) == ';'
		// || document.getChar(offset - 1) == ';') {
		// return currLineIndex;
		// }
		// }
		while (currLineIndex > 0) {

			if (isIndentationBase(document, offset, currLineIndex,
					checkMultiLine))
				return currLineIndex;

			currLineIndex = getNextLineIndex(document, checkMultiLine,
					currLineIndex);
		}
		return 0;
	}

	private static int adjustLine(IStructuredDocument document,
			int currLineIndex, int offset) throws BadLocationException {
		// TODO ignore the comment
		final IRegion lineInfo = document.getLineInformation(currLineIndex);

		// if (lineInfo.getLength() == 0) {
		// return currLineIndex;
		// }

		int lineEnd = lineInfo.getOffset() + lineInfo.getLength();
		lineEnd = Math.min(lineEnd, offset);
		if (lineEnd == lineInfo.getOffset()) {
			lineEnd = moveLineStartToNonBlankChar(document, lineEnd,
					currLineIndex) - 1;
		}
		if (lineEnd == document.getLength() && lineEnd > 0) {
			lineEnd--;
		}
		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, lineEnd, true);
		int token = scanner.previousToken(lineEnd, PHPHeuristicScanner.UNBOUND);
		if (token == PHPHeuristicScanner.TokenSEMICOLON) {
			token = scanner.previousToken(scanner.getPosition(),
					PHPHeuristicScanner.UNBOUND);
		}
		if (token == PHPHeuristicScanner.TokenRPAREN) {
			int peer = scanner.findOpeningPeer(scanner.getPosition(),
					PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LPAREN,
					PHPHeuristicScanner.RPAREN);
			if (peer != PHPHeuristicScanner.NOT_FOUND) {
				return document.getLineOfOffset(scanner.getPosition());
			}
		} else if (token == PHPHeuristicScanner.TokenLBRACKET) {
			int peer = scanner.findOpeningPeer(scanner.getPosition(),
					PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LBRACKET,
					PHPHeuristicScanner.RBRACKET);
			if (peer != PHPHeuristicScanner.NOT_FOUND) {
				return document.getLineOfOffset(scanner.getPosition());
			}
		}
		return currLineIndex;
	}

	private static int getNextLineIndex(IStructuredDocument document,
			boolean checkMultiLine, int currLineIndex)
			throws BadLocationException {

		final IRegion lineInfo = document.getLineInformation(currLineIndex);
		final int currLineEndOffset = lineInfo.getOffset()
				+ lineInfo.getLength();
		String checkedLineBeginState = FormatterUtils.getPartitionType(
				document, lineInfo.getOffset(), true);

		String forLineEndState = FormatterUtils.getPartitionType(document,
				currLineEndOffset);

		if (isMultilineType(checkedLineBeginState)
				&& (checkMultiLine || shouldNotConsiderAsIndentationBase(
						checkedLineBeginState, forLineEndState))) {
			int index = getMultiLineStatementStartOffset(document,
					lineInfo.getOffset(), currLineIndex);
			if (index > -1) {
				return index;
			}
		}
		if (checkMultiLine) {
			int result = adjustLine(document, currLineIndex, currLineEndOffset);
			if (result == currLineIndex && result != 0) {
				result--;
			}
			return result;
		}
		return currLineIndex - 1;
	}

	private static boolean isMultilineType(final String checkedLineBeginState) {
		return multilinePartitionTypes.contains(checkedLineBeginState);
	}

	private static int moveLineStartToNonBlankChar(
			IStructuredDocument document, int lineStart, int currLineIndex) {
		try {
			char[] line = document.get(lineStart,
					document.getLineLength(currLineIndex)).toCharArray();
			for (int i = 0; i < line.length; i++) {
				char c = line[i];
				if (Character.isWhitespace(c)) {
				} else {
					// move line start to first non blank char
					lineStart += i + 1;
					break;
				}
			}
		} catch (BadLocationException e) {
		}
		return lineStart;
	}

	// go backward and look for any region except comment region or white space
	// region
	// in the given line
	private static ITextRegion getLastTokenRegion(
			final IStructuredDocument document, final IRegion line,
			final int forOffset) throws BadLocationException {
		int offset = forOffset;
		int lineStartOffset = line.getOffset();
		IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(offset);
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

			// go backward over the region to find a region (not comment nor
			// whitespace)
			// in the same line
			do {
				String token = tRegion.getType();
				if (regionStart + tRegion.getStart() >= forOffset) {
					// making sure the region found is not after the caret
					// (https://bugs.eclipse.org/bugs/show_bug.cgi?id=222019 -
					// caret before '{')
				} else if (!PHPPartitionTypes.isPHPCommentState(token)
						&& token != PHPRegionTypes.WHITESPACE) {
					// not comment nor white space
					return tRegion;
				}
				if (tRegion.getStart() >= 1) {
					tRegion = scriptRegion.getPhpToken(tRegion.getStart() - 1);
				} else {
					tRegion = null;
				}
			} while (tRegion != null
					&& tRegion.getStart() + regionStart > lineStartOffset);
		}

		return null;
	}

	private static boolean isIndentationBase(
			final IStructuredDocument document, final int forOffset,
			int currLineIndex, boolean checkMultiLine)
			throws BadLocationException {

		final IRegion lineInfo = document.getLineInformation(currLineIndex);

		if (lineInfo.getLength() == 0) {
			return false;
		}

		final int checkedOffset = Math.min(
				lineInfo.getOffset() + lineInfo.getLength(), forOffset);

		int lineStart = lineInfo.getOffset();

		if (isBlanks(document, lineStart, checkedOffset, forOffset))
			return false;

		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, lineStart, true);
		if (inBracelessBlock(scanner, document, lineStart)) {
			if (scanner.previousToken(forOffset - 1,
					PHPHeuristicScanner.UNBOUND) == PHPHeuristicScanner.TokenLPAREN)
				return true;
			return false;
		}
		// need to get to the first tRegion - so that we wont get the state of
		// the
		// tRegion in the previos line
		while (Character.isWhitespace(document.getChar(lineStart)))
			lineStart++;

		// checked line beginning offset (after incrementing spaces in beginning
		final String checkedLineBeginState = FormatterUtils.getPartitionType(
				document, lineStart, true);

		// checked line end
		final String checkedLineEndState = FormatterUtils.getPartitionType(
				document, checkedOffset, true);

		// the current potential line for formatting begin offset
		final String forLineEndState = FormatterUtils.getPartitionType(
				document, forOffset);
		if (!lineContainIncompleteBlock(document, checkedOffset, lineStart,
				currLineIndex)
				&& (shouldNotConsiderAsIndentationBase(checkedLineBeginState,
						forLineEndState) || (checkMultiLine && isInMultiLineStatement(
						document, checkedLineBeginState, checkedLineEndState,
						checkedOffset, lineStart, currLineIndex))))
			return false;

		// Fix bug #201688
		if (((checkedLineBeginState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) || (checkedLineBeginState == PHPPartitionTypes.PHP_DOC))
				&& (checkedLineBeginState == forLineEndState)) {
			// the whole document
			final IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(lineStart);
			// the whole PHP script
			ITextRegion phpScriptRegion = sdRegion
					.getRegionAtCharacterOffset(lineStart);
			int phpContentStartOffset = sdRegion
					.getStartOffset(phpScriptRegion);

			if (phpScriptRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) phpScriptRegion;
				phpScriptRegion = container
						.getRegionAtCharacterOffset(lineStart);
				phpContentStartOffset += phpScriptRegion.getStart();
			}

			if (phpScriptRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) phpScriptRegion;
				// the region we are trying to check if it is the indent base
				// for the line we need to format
				ContextRegion checkedRegion = (ContextRegion) scriptRegion
						.getPhpToken(lineStart - phpContentStartOffset);
				// the current region we need to format
				ContextRegion currentRegion = (ContextRegion) scriptRegion
						.getPhpToken(forOffset - phpContentStartOffset);
				String checkedType = checkedRegion.getType();
				String currentType = currentRegion.getType();
				// if we are in the beginning of a comment (DOC or Multi
				// comment) and we have before another
				// Doc comment or Multi comment, the base line we'll be the
				// beginning of the previous multi comment
				if (currentType.equals(PHPRegionTypes.PHPDOC_COMMENT_START)
						|| currentType.equals(PHPRegionTypes.PHP_COMMENT_START)) {
					return checkedType
							.equals(PHPRegionTypes.PHPDOC_COMMENT_START)
							|| checkedType
									.equals(PHPRegionTypes.PHP_COMMENT_START);
				}
			}
		}

		return lineShouldInedent(checkedLineBeginState, checkedLineEndState)
				|| forLineEndState == checkedLineBeginState;
	}

	private static boolean isInMultiLineStatement(IStructuredDocument document,
			String checkedLineBeginState, String checkedLineEndState,
			int checkedOffset, int lineStart, int currLineIndex)
			throws BadLocationException {
		return getMultiLineStatementStartOffset(document, lineStart,
				currLineIndex, checkedOffset) > -1 ? true : false;
	}

	private static int getMultiLineStatementStartOffset(
			IStructuredDocument document, int lineStart, int currLineIndex,
			int checkedOffset) throws BadLocationException {

		// TODO moveLineStartToNonBlankChar or moveLineEndToNonBlankChar
		lineStart = moveLineStartToNonBlankChar(document, lineStart,
				currLineIndex);
		// char lineStartChar = document.getChar(lineStart - 1);
		// if (lineStartChar == PHPHeuristicScanner.RBRACE
		// // || lineStartChar == PHPHeuristicScanner.RBRACKET
		// || lineStartChar == PHPHeuristicScanner.RPAREN) {
		//
		// PHPHeuristicScanner scanner = PHPHeuristicScanner
		// .createHeuristicScanner(document, lineStart, true);
		// if (lineStartChar == PHPHeuristicScanner.RBRACE) {
		// int peer = scanner.findOpeningPeer(checkedOffset,
		// PHPHeuristicScanner.UNBOUND,
		// PHPHeuristicScanner.LBRACE, PHPHeuristicScanner.RBRACE);
		// if (peer != PHPHeuristicScanner.NOT_FOUND) {
		// lineStart = peer;
		// }
		// } else if (lineStartChar == PHPHeuristicScanner.RBRACKET) {
		// int peer = scanner.findOpeningPeer(checkedOffset,
		// PHPHeuristicScanner.UNBOUND,
		// PHPHeuristicScanner.LBRACKET,
		// PHPHeuristicScanner.RBRACKET);
		// if (peer != PHPHeuristicScanner.NOT_FOUND) {
		// lineStart = peer;
		// }
		// } else {
		// int peer = scanner.findOpeningPeer(checkedOffset,
		// PHPHeuristicScanner.UNBOUND,
		// PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
		// if (peer != PHPHeuristicScanner.NOT_FOUND) {
		// lineStart = peer;
		// }
		// }
		// }

		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence != null
				&& isRegionTypeAllowedMultiline(FormatterUtils.getRegionType(
						document, textSequence.getOriginalOffset(0)))
				&& document.getLineOfOffset(textSequence.getOriginalOffset(0)) < currLineIndex) {
			return document.getLineOfOffset(textSequence.getOriginalOffset(0));
		}

		return -1;
	}

	private static boolean lineContainIncompleteBlock(
			IStructuredDocument document, int checkedOffset, int lineStart,
			int currLineIndex) throws BadLocationException {

		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, lineStart, true);
		if (checkedOffset == document.getLength() && checkedOffset > 0) {
			checkedOffset--;
		}
		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence != null
				&& isRegionTypeAllowedMultiline(FormatterUtils.getRegionType(
						document, textSequence.getOriginalOffset(0)))) {
			int statementStart = textSequence.getOriginalOffset(0);
			// we only search for opening pear in textSequence
			int openParenPeer = scanner.findOpeningPeer(checkedOffset,
					statementStart, PHPHeuristicScanner.LPAREN,
					PHPHeuristicScanner.RPAREN);
			int openBracePeer = scanner.findOpeningPeer(checkedOffset,
					statementStart, PHPHeuristicScanner.LBRACE,
					PHPHeuristicScanner.RBRACE);
			int openBracketPeer = scanner.findOpeningPeer(checkedOffset,
					statementStart, PHPHeuristicScanner.LBRACKET,
					PHPHeuristicScanner.RBRACKET);
			int biggest = Math.max(openParenPeer, openBracePeer);
			biggest = Math.max(biggest, openBracketPeer);
			if (biggest != PHPHeuristicScanner.NOT_FOUND && biggest > lineStart) {
				// the whole document
				final IStructuredDocumentRegion sdRegion = document
						.getRegionAtCharacterOffset(lineStart);
				// the whole PHP script
				ITextRegion phpScriptRegion = sdRegion
						.getRegionAtCharacterOffset(lineStart);
				int phpContentStartOffset = sdRegion
						.getStartOffset(phpScriptRegion);

				if (phpScriptRegion instanceof ITextRegionContainer) {
					ITextRegionContainer container = (ITextRegionContainer) phpScriptRegion;
					phpScriptRegion = container
							.getRegionAtCharacterOffset(lineStart);
					phpContentStartOffset += phpScriptRegion.getStart();
				}

				if (phpScriptRegion instanceof IPhpScriptRegion) {
					IPhpScriptRegion scriptRegion = (IPhpScriptRegion) phpScriptRegion;
					ITextRegion[] tokens = scriptRegion.getPhpTokens(lineStart,
							biggest - lineStart);
					if (tokens != null && tokens.length > 0) {
						Set<String> tokenTypeSet = new HashSet<String>();
						for (int i = 0; i < tokens.length; i++) {
							tokenTypeSet.add(tokens[i].getType());
						}
						if (biggest == openParenPeer) {
							if (tokenTypeSet.contains(PHPRegionTypes.PHP_NEW)
									|| tokenTypeSet
											.contains(PHPRegionTypes.PHP_FUNCTION)
									|| tokenTypeSet
											.contains(PHPRegionTypes.PHP_ARRAY)) {
								return true;
							}
						} else if (biggest == openBracePeer) {
							if (tokenTypeSet.contains(PHPRegionTypes.PHP_NEW)
									|| tokenTypeSet
											.contains(PHPRegionTypes.PHP_FUNCTION)) {
								return true;
							}
						} else {
							if (tokenTypeSet.contains(PHPRegionTypes.PHP_ARRAY)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static int getMultiLineStatementStartOffset(
			IStructuredDocument document, int lineStart, int currLineIndex) {

		lineStart = moveLineStartToNonBlankChar(document, lineStart,
				currLineIndex);

		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence != null
				&& isRegionTypeAllowedMultiline(FormatterUtils.getRegionType(
						document, textSequence.getOriginalOffset(0)))
				&& document.getLineOfOffset(textSequence.getOriginalOffset(0)) < currLineIndex) {
			return document.getLineOfOffset(textSequence.getOriginalOffset(0));
		}

		return -1;
	}

	/**
	 * a statement spanning multi line starts with a region of type
	 * regionType,the lines'(except 1st) indentation are same as/based on 1st
	 * line,then this this method return true,else return false.
	 * 
	 * @param regionType
	 * @return
	 */
	private static boolean isRegionTypeAllowedMultiline(String regionType) {
		// TODO maybe there are other type need to be added
		return regionType != null
				&& !PHPRegionTypes.PHPDOC_COMMENT_START.equals(regionType)
				&& !PHPRegionTypes.PHP_LINE_COMMENT.equals(regionType)
				&& !PHPRegionTypes.PHP_STRING.equals(regionType)
				&& !PHPRegionTypes.PHP_CASE.equals(regionType)
				&& !PHPRegionTypes.PHP_DEFAULT.equals(regionType);
	}

	public static boolean lineShouldInedent(final String beginState,
			final String endState) {
		return beginState == PHPPartitionTypes.PHP_DEFAULT
				|| endState == PHPPartitionTypes.PHP_DEFAULT;
	}

	public void placeMatchingBlanks(final IStructuredDocument document,
			final StringBuffer result, final int lineNumber, final int forOffset)
			throws BadLocationException {
		placeMatchingBlanksForStructuredDocument(document, result, lineNumber,
				forOffset, getCommandText());
	}

	protected String getCommandText() {
		return BLANK;
	}

	public void placeMatchingBlanksForStructuredDocument(
			final IStructuredDocument document, final StringBuffer result,
			final int lineNumber, final int forOffset)
			throws BadLocationException {
		placeMatchingBlanksForStructuredDocument(document, result, lineNumber,
				forOffset, BLANK);
	}

	protected int getIndentationSize(final IStructuredDocument document) {

		return FormatterUtils.getFormatterCommonPrferences()
				.getIndentationSize(document);
	}

	protected char getIndentationChar(final IStructuredDocument document) {

		return FormatterUtils.getFormatterCommonPrferences()
				.getIndentationChar(document);
	}

	public void placeMatchingBlanksForStructuredDocument(
			final IStructuredDocument document, final StringBuffer result,
			final int lineNumber, final int forOffset, String commandText)
			throws BadLocationException {

		IFormatterCommonPrferences formatterCommonPrferences = FormatterUtils
				.getFormatterCommonPrferences();
		int indentationWrappedLineSize = FormatterUtils
				.getFormatterCommonPrferences().getIndentationWrappedLineSize(
						document);
		int indentationArrayInitSize = FormatterUtils
				.getFormatterCommonPrferences().getIndentationArrayInitSize(
						document);
		int indentationSize = getIndentationSize(document);
		char indentationChar = getIndentationChar(document);
		IndentationObject indentationObject = new IndentationObject();
		indentationObject.indentationWrappedLineSize = indentationWrappedLineSize;
		indentationObject.indentationArrayInitSize = indentationArrayInitSize;
		indentationObject.indentationSize = indentationSize;
		indentationObject.indentationChar = indentationChar;

		boolean enterKeyPressed = document.getLineDelimiter().equals(
				result.toString());
		if (forOffset == 0) {
			return;
		}

		int lineOfOffset = document.getLineOfOffset(forOffset);
		IRegion lineInformationOfOffset = document
				.getLineInformation(lineOfOffset);
		final String lineText = document.get(
				lineInformationOfOffset.getOffset(),
				lineInformationOfOffset.getLength());

		int lastNonEmptyLineIndex;
		final int indentationBaseLineIndex;
		final int newForOffset;

		// code for not formatting comments
		if (lineText.trim().startsWith("//") && enterKeyPressed) { //$NON-NLS-1$
			lastNonEmptyLineIndex = lineOfOffset;
			indentationBaseLineIndex = lineOfOffset;
			int i = lineInformationOfOffset.getOffset();
			for (; i < lineInformationOfOffset.getOffset()
					+ lineInformationOfOffset.getLength()
					&& document.getChar(i) != '/'; i++)
				;
			newForOffset = (forOffset < i) ? i : forOffset;

		}
		// end
		else {
			newForOffset = forOffset;
			lastNonEmptyLineIndex = getIndentationBaseLine(document,
					lineNumber, newForOffset, false);
			indentationBaseLineIndex = getIndentationBaseLine(document,
					lineNumber, newForOffset, true);
		}

		final IRegion lastNonEmptyLine = document
				.getLineInformation(lastNonEmptyLineIndex);
		final IRegion indentationBaseLine = document
				.getLineInformation(indentationBaseLineIndex);
		final String blanks = FormatterUtils.getLineBlanks(document,
				indentationBaseLine);
		result.append(blanks);
		final int lastLineEndOffset = lastNonEmptyLine.getOffset()
				+ lastNonEmptyLine.getLength();
		int offset;
		int line;
		if (newForOffset < lastLineEndOffset) {
			offset = newForOffset;
			line = lineNumber;
		} else {
			offset = lastLineEndOffset;
			line = lastNonEmptyLineIndex;
		}
		if (shouldIndent(document, offset, line)) {
			indent(document, result, indentationChar, indentationSize);
		} else {
			boolean intended = indentMultiLineCase(document, lineNumber,
					newForOffset, enterKeyPressed, result, blanks, commandText,
					indentationObject);
			if (!intended) {
				lastNonEmptyLineIndex = lineNumber;
				if (!enterKeyPressed && lastNonEmptyLineIndex > 0) {
					lastNonEmptyLineIndex--;
				}
				while (lastNonEmptyLineIndex >= 0) {
					IRegion lineInfo = document
							.getLineInformation(lastNonEmptyLineIndex);
					String content = document.get(lineInfo.getOffset(),
							lineInfo.getLength());
					if (content.trim().length() > 0) {
						break;
					}
					lastNonEmptyLineIndex--;
				}
				if (!isEndOfStatement(document, offset, lastNonEmptyLineIndex)) {
					if (indentationBaseLineIndex == lastNonEmptyLineIndex) {
						// this only deal with "$a = 'aaa'.|","|" is the
						// cursor
						// position when we press enter key
						placeStringIndentation(document, lastNonEmptyLineIndex,
								result, indentationObject);
					}
					// if (enterKeyPressed) {
					// this line is one of multi line statement
					// in multi line statement,when user press enter
					// key,
					// we use the same indentation of the last non-empty
					// line.
					boolean shouldNotChangeIndent = false;
					if (newForOffset != document.getLength()) {
						final IRegion lineInfo = document
								.getLineInformation(lineNumber);
						int nonEmptyOffset = newForOffset;
						if (!enterKeyPressed) {
							if (nonEmptyOffset == lineInfo.getOffset()) {
								nonEmptyOffset = moveLineStartToNonBlankChar(
										document, nonEmptyOffset, lineNumber) - 1;
							}
						}
						char lineStartChar = document.getChar(nonEmptyOffset);
						if (lineStartChar == PHPHeuristicScanner.RBRACE
						// || lineStartChar == PHPHeuristicScanner.RBRACKET
								|| lineStartChar == PHPHeuristicScanner.RPAREN) {

							PHPHeuristicScanner scanner = PHPHeuristicScanner
									.createHeuristicScanner(document,
											nonEmptyOffset, true);
							if (lineStartChar == PHPHeuristicScanner.RBRACE) {
								int peer = scanner.findOpeningPeer(
										nonEmptyOffset - 1,
										PHPHeuristicScanner.UNBOUND,
										PHPHeuristicScanner.LBRACE,
										PHPHeuristicScanner.RBRACE);
								if (peer != PHPHeuristicScanner.NOT_FOUND) {
									shouldNotChangeIndent = true;
								}
							} else if (lineStartChar == PHPHeuristicScanner.RBRACKET) {
								int peer = scanner.findOpeningPeer(
										nonEmptyOffset - 1,
										PHPHeuristicScanner.UNBOUND,
										PHPHeuristicScanner.LBRACKET,
										PHPHeuristicScanner.RBRACKET);
								if (peer != PHPHeuristicScanner.NOT_FOUND) {
									shouldNotChangeIndent = true;
								}
							} else {
								int peer = scanner.findOpeningPeer(
										nonEmptyOffset - 1,
										PHPHeuristicScanner.UNBOUND,
										PHPHeuristicScanner.LPAREN,
										PHPHeuristicScanner.RPAREN);
								if (peer != PHPHeuristicScanner.NOT_FOUND) {
									shouldNotChangeIndent = true;
								}
							}
						}
					}

					if (!shouldNotChangeIndent) {
						result.setLength(result.length() - blanks.length());
						IRegion lineInfo = document
								.getLineInformation(lastNonEmptyLineIndex);
						result.append(FormatterUtils.getLineBlanks(document,
								lineInfo));
					}

					// }
				} else {// current is a new statement,check if we should indent
						// it based on indentationBaseLine
					if (result.length() == blanks.length()) {

						final int baseLineEndOffset = indentationBaseLine
								.getOffset() + indentationBaseLine.getLength();
						offset = baseLineEndOffset;
						line = indentationBaseLineIndex;
						if (shouldIndent(document, offset, line)) {
							indent(document, result, indentationChar,
									indentationSize);
						}
					}
				}
			}

		}
	}

	private static void indent(final IStructuredDocument document,
			final StringBuffer result, int indentationChar, int indentationSize) {
		// final int indentationSize = FormatPreferencesSupport.getInstance()
		// .getIndentationSize(document);
		// final char indentationChar = FormatPreferencesSupport.getInstance()
		// .getIndentationChar(document);
		for (int i = 0; i < indentationSize; i++)
			result.append((char) indentationChar);
	}

	private static boolean indentMultiLineCase(IStructuredDocument document,
			int lineNumber, int offset, boolean enterKeyPressed,
			StringBuffer result, String blanks, String commandText,
			IndentationObject indentationObject) {
		// LineState lineState = new LineState();
		// StringBuffer sb = new StringBuffer();
		try {
			IRegion region = document.getLineInformationOfOffset(offset);
			String content = document.get(offset,
					region.getOffset() + region.getLength() - offset);
			PHPHeuristicScanner scanner = PHPHeuristicScanner
					.createHeuristicScanner(document, offset, true);
			if (inBracelessBlock(scanner, document, offset)) {
				// lineState.inBracelessBlock = true;
				if (!"{".equals(commandText)) { //$NON-NLS-1$
					indent(document, result, indentationObject.indentationChar,
							indentationObject.indentationSize);
				}
				return true;
			} else if (content.trim().startsWith(
					BLANK + PHPHeuristicScanner.LBRACE)) {
				// lineState.inBracelessBlock = true;
				int token = scanner.previousToken(offset - 1,
						PHPHeuristicScanner.UNBOUND);
				if (token == PHPHeuristicScanner.TokenRPAREN) {

					int peer = scanner.findOpeningPeer(scanner.getPosition(),
							PHPHeuristicScanner.UNBOUND,
							PHPHeuristicScanner.LPAREN,
							PHPHeuristicScanner.RPAREN);
					if (peer != PHPHeuristicScanner.NOT_FOUND) {

						String newblanks = FormatterUtils.getLineBlanks(
								document,
								document.getLineInformationOfOffset(peer));
						StringBuffer newBuffer = new StringBuffer(newblanks);
						// IRegion region = document
						// .getLineInformationOfOffset(offset);

						result.setLength(result.length() - blanks.length());
						result.append(newBuffer.toString());
						return true;
					}
				}

			} else if (inMultiLine(scanner, document, lineNumber, offset)) {
				// lineState.inBracelessBlock = true;
				int peer = scanner.findOpeningPeer(offset - 1,
						PHPHeuristicScanner.UNBOUND,
						PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
				if (peer != PHPHeuristicScanner.NOT_FOUND) {

					// search for assignment (i.e. "=>")
					int position = peer - 1;
					int token = scanner.previousToken(position,
							PHPHeuristicScanner.UNBOUND);
					// scan tokens backwards until reaching a PHP token
					while (token > 100
							|| token == PHPHeuristicScanner.TokenOTHER) {
						position--;
						token = scanner.previousToken(position,
								PHPHeuristicScanner.UNBOUND);
					}

					position--;
					boolean isAssignment = scanner.previousToken(position,
							PHPHeuristicScanner.UNBOUND) == PHPHeuristicScanner.TokenGREATERTHAN
							&& scanner.previousToken(position - 1,
									PHPHeuristicScanner.UNBOUND) == PHPHeuristicScanner.TokenEQUAL;

					token = scanner.previousToken(peer - 1,
							PHPHeuristicScanner.UNBOUND);

					boolean isArray = token == Symbols.TokenARRAY;
					// lineState.indent.setLength(0)
					// int baseLine = document.getLineOfOffset(peer);
					String newblanks = FormatterUtils.getLineBlanks(document,
							document.getLineInformationOfOffset(peer));
					StringBuffer newBuffer = new StringBuffer(newblanks);
					pairArrayParen = false;
					// IRegion region = document
					// .getLineInformationOfOffset(offset);
					if (enterKeyPressed
							|| !document
									.get(offset,
											region.getOffset()
													+ region.getLength()
													- offset)
									.trim()
									.startsWith(
											BLANK + PHPHeuristicScanner.RPAREN)) {
						if (isArray) {
							region = document
									.getLineInformationOfOffset(offset);
							if (scanner.nextToken(offset, region.getOffset()
									+ region.getLength()) == PHPHeuristicScanner.TokenRPAREN) {
								if (isAssignment)
									indent(document, newBuffer, 0,
											indentationObject.indentationChar,
											indentationObject.indentationSize);
								else {
									indent(document,
											newBuffer,
											indentationObject.indentationArrayInitSize,
											indentationObject.indentationChar,
											indentationObject.indentationSize);
									pairArrayParen = true;
								}
							} else {
								indent(document,
										newBuffer,
										indentationObject.indentationArrayInitSize,
										indentationObject.indentationChar,
										indentationObject.indentationSize);
							}
						} else {
							indent(document,
									newBuffer,
									indentationObject.indentationWrappedLineSize,
									indentationObject.indentationChar,
									indentationObject.indentationSize);
						}
					}

					result.setLength(result.length() - blanks.length());
					result.append(newBuffer.toString());
					if (pairArrayParen) {
						pairArrayOffset = offset + result.length();
						result.append(Util.getLineSeparator(null, null));
						result.append(blanks);

					}
					return true;
				}
			} else {
				int baseLine = inMultiLineString(document, offset, lineNumber,
						enterKeyPressed);
				if (baseLine >= 0) {
					String newblanks = FormatterUtils.getLineBlanks(document,
							document.getLineInformation(baseLine));
					StringBuffer newBuffer = new StringBuffer(newblanks);
					indent(document, newBuffer,
							indentationObject.indentationWrappedLineSize,
							indentationObject.indentationChar,
							indentationObject.indentationSize);
					result.setLength(result.length() - blanks.length());
					result.append(newBuffer.toString());
					return true;
				}
			}
		} catch (final BadLocationException e) {
		}
		return false;
	}

	private static void indent(IStructuredDocument document,
			StringBuffer indent, int times, int indentationChar,
			int indentationSize) {
		for (int i = 0; i < times; i++) {
			indent(document, indent, indentationChar, indentationSize);
		}
	}

	private static boolean inMultiLine(PHPHeuristicScanner scanner,
			IStructuredDocument document, int lineNumber, int offset) {
		int lineStart = offset;
		try {
			IRegion region = document.getLineInformation(lineNumber);
			char[] line = document.get(lineStart,
					region.getOffset() + region.getLength() - lineStart)
					.toCharArray();
			for (int i = 0; i < line.length; i++) {
				char c = line[i];
				if (Character.isWhitespace(c)) {
				} else {
					// move line start to first non blank char
					lineStart += i + 1;
					break;
				}
			}
		} catch (BadLocationException e) {
		}
		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence == null) {
			return false;
		}
		String regionType = FormatterUtils.getRegionType(document,
				textSequence.getOriginalOffset(0));
		if (textSequence != null && isRegionTypeAllowedMultiline(regionType)) {
			int statementStart = textSequence.getOriginalOffset(0);
			// we only search for opening pear in textSequence
			int peer = scanner.findOpeningPeer(offset - 1,
					textSequence.getOriginalOffset(0),
					PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
			if (peer == PHPHeuristicScanner.NOT_FOUND) {
				return false;
			}
			if (statementStart < peer) {
				return true;
			}
		}
		return false;
	}

	private static int inMultiLineString(IStructuredDocument document,
			int offset, int lineNumber, boolean enterKeyPressed) {

		try {
			IRegion lineInfo = document.getLineInformation(lineNumber);
			final IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(offset);
			ITextRegion token = getLastTokenRegion(document, lineInfo, offset);
			if (token == null)
				return -1;
			String tokenType = token.getType();

			if (tokenType == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING) {
				int startLine = document.getLineOfOffset(token.getStart());
				if (enterKeyPressed && startLine <= lineNumber
						|| !enterKeyPressed && startLine < lineNumber) {
					return startLine;
				}
			}
		} catch (BadLocationException e) {
		}

		// Program program = null;
		// try {
		// final Reader reader = new StringReader(document.get());
		// program = ASTParser.newParser(reader, PHPVersion.PHP5_4, true)
		// .createAST(new NullProgressMonitor());
		// ASTNode node = NodeFinder.perform(program, offset, 0);
		// if (node != null && node.getType() == ASTNode.SCALAR
		// && ((Scalar) node).getScalarType() == Scalar.TYPE_STRING
		// && document.getLineOfOffset(node.getStart()) < lineNumber) {
		// return document.getLineOfOffset(node.getStart());
		// }
		// } catch (Exception e) {
		// }

		return -1;
	}

	private static boolean inBracelessBlock(PHPHeuristicScanner scanner,
			IStructuredDocument document, int offset) {
		boolean isBracelessBlock = scanner.isBracelessBlockStart(offset - 1,
				PHPHeuristicScanner.UNBOUND);
		if (isBracelessBlock) {
			try {
				IRegion region = document.getLineInformationOfOffset(offset);
				String trimedLine = document.get(offset,
						region.getOffset() + region.getLength() - offset)
						.trim();
				if (!(trimedLine.startsWith(BLANK + PHPHeuristicScanner.LBRACE)))
				// && trimedLine.contains(Character
				// .toString(PHPHeuristicScanner.LBRACE)))
				{
					return true;
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	private static boolean isEndOfStatement(IStructuredDocument document,
			int offset, int lineNumber) {
		try {
			IRegion lineInfo = document.getLineInformation(lineNumber);
			final IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(offset);
			ITextRegion token = getLastTokenRegion(document, lineInfo,
					lineInfo.getOffset() + lineInfo.getLength());
			if (token == null)// comment
				return true;
			if (token.getType() == PHPRegionTypes.PHP_SEMICOLON
					|| token.getType() == PHPRegionTypes.PHP_CURLY_CLOSE) {
				return true;
			} else if (token.getType() == PHPRegionTypes.PHP_HEREDOC_TAG
					&& document.get(lineInfo.getOffset(), lineInfo.getLength())
							.trim().endsWith(";")) { //$NON-NLS-1$
				return true;
			}
		} catch (final BadLocationException e) {
		}
		return false;
	}

	private static void placeStringIndentation(
			final IStructuredDocument document, int lineNumber,
			StringBuffer result, IndentationObject indentationObject) {
		try {

			IRegion lineInfo = document.getLineInformation(lineNumber);
			int offset = lineInfo.getOffset() + lineInfo.getLength();
			final IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(offset);
			ITextRegion token = getLastTokenRegion(document, lineInfo, offset);
			if (token == null)
				return;
			String tokenType = token.getType();

			if (tokenType == PHPRegionTypes.PHP_CURLY_OPEN)
				return;

			ITextRegion scriptRegion = sdRegion
					.getRegionAtCharacterOffset(offset);
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
				if (tokenType == PHPRegionTypes.PHP_TOKEN
						&& document.getChar(regionStart + token.getStart()) == '.') {
					token = ((IPhpScriptRegion) scriptRegion).getPhpToken(token
							.getStart() - 1);
					if (token != null
							&& token.getType() == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING) {
						boolean isToken = true;
						int currentOffset = regionStart + token.getStart() - 1;
						while (currentOffset >= lineInfo.getOffset()) {
							token = ((IPhpScriptRegion) scriptRegion)
									.getPhpToken(token.getStart() - 1);
							tokenType = token.getType();
							if (isToken
									&& (tokenType == PHPRegionTypes.PHP_TOKEN && document
											.getChar(regionStart
													+ token.getStart()) == '.')
									|| !isToken
									&& tokenType == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING) {
								currentOffset = regionStart + token.getStart()
										- 1;
							} else {
								break;
							}
						}
						indent(document, result,
								indentationObject.indentationWrappedLineSize,
								indentationObject.indentationChar,
								indentationObject.indentationSize);
						// for (int i = 0; i < regionStart + token.getEnd()
						// - lineInfo.getOffset(); i++){
						// result.append(' ');
						// }
					}
				}
			}
		} catch (final BadLocationException e) {
		}
	}

	static boolean shouldIndent(final IStructuredDocument document, int offset,
			final int lineNumber) {
		try {
			final IRegion lineInfo = document.getLineInformation(lineNumber);

			final IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(offset);
			ITextRegion token = getLastTokenRegion(document, lineInfo, offset);
			if (token == null)
				return false;
			String tokenType = token.getType();

			if (tokenType == PHPRegionTypes.PHP_CURLY_OPEN)
				return true;

			ITextRegion scriptRegion = sdRegion
					.getRegionAtCharacterOffset(offset);
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
				if (tokenType == PHPRegionTypes.PHP_TOKEN
						&& document.getChar(regionStart + token.getStart()) == ':') {
					// checking if the line starts with "case" or "default"
					int currentOffset = regionStart + token.getStart() - 1;
					while (currentOffset >= lineInfo.getOffset()) {
						token = ((IPhpScriptRegion) scriptRegion)
								.getPhpToken(token.getStart() - 1);
						tokenType = token.getType();
						if (tokenType == PHPRegionTypes.PHP_CASE
								|| tokenType == PHPRegionTypes.PHP_DEFAULT)
							return true;
						currentOffset = regionStart + token.getStart() - 1;
					}
				}
			}
		} catch (final BadLocationException e) {
		}
		return false;
	}

	/**
	 * @since 2.2
	 */
	public static boolean shouldNotConsiderAsIndentationBase(
			final String currentState, final String forState) {
		return currentState != forState
				&& (currentState == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT
						|| currentState == PHPPartitionTypes.PHP_DOC || currentState == PHPPartitionTypes.PHP_QUOTED_STRING);
	}

	private static class IndentationObject {
		public int indentationWrappedLineSize;
		public int indentationArrayInitSize;
		public int indentationSize;
		public char indentationChar;

	}

	public static int getPairArrayOffset() {

		// TODO Auto-generated method stub
		if (pairArrayParen) {
			return pairArrayOffset;
		}
		return -1;
	}

	public static boolean getPairArrayParen() {
		// TODO Auto-generated method stub
		return pairArrayParen;
	}

	public static void unsetPairArrayParen() {
		// TODO Auto-generated method stub
		pairArrayParen = false;
	}

}
