/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
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

public class IndentationBaseDetector {

	private class BlockCalculationCache {
		int checkedOffset;
		int lineStart;
		int currLineIndex;
		boolean lineContainIncompleteBlock;

		public BlockCalculationCache(int checkedOffset, int lineStart,
				int currLineIndex) {
			this.checkedOffset = checkedOffset;
			this.lineStart = lineStart;
			this.currLineIndex = currLineIndex;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof BlockCalculationCache) {
				BlockCalculationCache tmpResult = (BlockCalculationCache) obj;
				return checkedOffset == tmpResult.checkedOffset
						&& lineStart == tmpResult.lineStart
						&& currLineIndex == tmpResult.currLineIndex;
			}
			return false;
		}
	}

	private IStructuredDocument document;
	private List<BlockCalculationCache> cache = new ArrayList<IndentationBaseDetector.BlockCalculationCache>();

	public IndentationBaseDetector(IStructuredDocument document) {
		this.document = document;
	}

	public int getIndentationBaseLine(int currLineIndex, final int offset,
			boolean checkMultiLine) throws BadLocationException {
		if (checkMultiLine) {
			currLineIndex = adjustLine(currLineIndex, offset);
		}
		while (currLineIndex > 0) {
			if (isIndentationBase(offset, currLineIndex, checkMultiLine)) {
				return currLineIndex;
			}
			currLineIndex = getNextLineIndex(offset, currLineIndex,
					checkMultiLine);
		}
		return 0;
	}

	private int adjustLine(int currLineIndex, int offset)
			throws BadLocationException {
		// TODO ignore the comment
		final IRegion lineInfo = document.getLineInformation(currLineIndex);

		int lineEnd = lineInfo.getOffset() + lineInfo.getLength();
		lineEnd = Math.min(lineEnd, offset);
		if (lineEnd == lineInfo.getOffset()) {
			lineEnd = IndentationUtils.moveLineStartToNonBlankChar(document,
					lineEnd, currLineIndex, false);
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
		} else if (token == PHPHeuristicScanner.TokenRBRACKET) {
			int peer = scanner.findOpeningPeer(scanner.getPosition(),
					PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LBRACKET,
					PHPHeuristicScanner.RBRACKET);
			if (peer != PHPHeuristicScanner.NOT_FOUND) {
				return document.getLineOfOffset(scanner.getPosition());
			}
		}

		return currLineIndex;
	}

	private boolean isIndentationBase(final int forOffset, int currLineIndex,
			boolean checkMultiLine) throws BadLocationException {
		final IRegion lineInfo = document.getLineInformation(currLineIndex);

		if (lineInfo.getLength() == 0) {
			return false;
		}

		final int checkedOffset = Math.min(
				lineInfo.getOffset() + lineInfo.getLength(), forOffset);

		int lineStart = lineInfo.getOffset();

		if (IndentationUtils.isBlanks(document, lineStart, checkedOffset,
				forOffset))
			return false;

		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, checkedOffset, true);
		if (IndentationUtils.inBracelessBlock(scanner, document, checkedOffset)) {
			return true;
		}

		while (Character.isWhitespace(document.getChar(lineStart)))
			lineStart++;

		// need to get to the first tRegion - so that we wont get the state of
		// the
		// tRegion in the previos line

		// checked line beginning offset (after incrementing spaces in beginning
		final String checkedLineBeginState = FormatterUtils.getPartitionType(
				document, lineStart, true);

		// checked line end
		final String checkedLineEndState = FormatterUtils.getPartitionType(
				document, checkedOffset, true);

		// the current potential line for formatting begin offset
		final String forLineEndState = FormatterUtils.getPartitionType(
				document, forOffset);
		if (isMultilineAfterBraceless(checkedOffset)) {
			// braceless block end go up
			return false;
		}

		boolean lineContainIncompleteBlock = false;
		BlockCalculationCache result = new BlockCalculationCache(checkedOffset,
				lineStart, currLineIndex);
		int index = cache.indexOf(result);
		if (index != -1) {
			lineContainIncompleteBlock = cache.get(index).lineContainIncompleteBlock;
		} else {
			lineContainIncompleteBlock = lineContainIncompleteBlock(
					checkedOffset, lineStart, currLineIndex);
			result.lineContainIncompleteBlock = lineContainIncompleteBlock;
			cache.add(result);
		}
		boolean shouldNotConsiderAsIndentationBase = shouldNotConsiderAsIndentationBase(
				checkedLineBeginState, forLineEndState);

		if (!lineContainIncompleteBlock && shouldNotConsiderAsIndentationBase) {
			return false;
		}
		if (!lineContainIncompleteBlock
				&& (shouldNotConsiderAsIndentationBase || (checkMultiLine
						&& isInMultiLineStatement(checkedLineBeginState,
								checkedLineEndState, checkedOffset, lineStart,
								currLineIndex) && !isMultilineContentInsideBraceless(checkedOffset)))) {
			return false;
		}

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
		return lineShouldIndent(checkedLineBeginState, checkedLineEndState)
				|| forLineEndState == checkedLineBeginState;
	}

	private int getMultiLineStatementStartOffset(int lineStart,
			int currLineIndex, int checkedOffset) throws BadLocationException {
		lineStart = IndentationUtils.moveLineStartToNonBlankChar(document,
				lineStart, currLineIndex, true);

		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence.length() != 0
				&& IndentationUtils.isRegionTypeAllowedMultiline(FormatterUtils
						.getRegionType(document,
								textSequence.getOriginalOffset(0)))
				&& document.getLineOfOffset(textSequence.getOriginalOffset(0)) < currLineIndex) {
			return document.getLineOfOffset(textSequence.getOriginalOffset(0));
		}

		return -1;
	}

	private boolean lineContainIncompleteBlock(int checkedOffset,
			int lineStart, int currLineIndex) throws BadLocationException {
		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, lineStart, true);
		if (checkedOffset == document.getLength() && checkedOffset > 0) {
			checkedOffset--;
		}

		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence.length() != 0
				&& IndentationUtils.isRegionTypeAllowedMultiline(FormatterUtils
						.getRegionType(document,
								textSequence.getOriginalOffset(0)))) {
			int statementStart = textSequence.getOriginalOffset(0);
			// we only search for opening pear in textSequence

			int openParenPeer = scanner.findOpeningPeer(checkedOffset - 1,
					statementStart, PHPHeuristicScanner.LPAREN,
					PHPHeuristicScanner.RPAREN);

			int bound = openParenPeer != -1 ? Math.max(statementStart,
					openParenPeer) : statementStart;
			int openBracePeer = scanner.findOpeningPeer(checkedOffset - 1,
					bound, PHPHeuristicScanner.LBRACE,
					PHPHeuristicScanner.RBRACE);

			bound = openBracePeer != -1 || openParenPeer != -1 ? Math.max(
					statementStart, Math.max(openParenPeer, openBracePeer))
					: statementStart;
			int openBracketPeer = scanner.findOpeningPeer(checkedOffset - 1,
					bound, PHPHeuristicScanner.LBRACKET,
					PHPHeuristicScanner.RBRACKET);

			int biggest = Math.max(openParenPeer, openBracePeer);
			biggest = Math.max(biggest, openBracketPeer);
			if (biggest != PHPHeuristicScanner.NOT_FOUND
					&& biggest >= lineStart) {
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

				if (phpScriptRegion instanceof IPhpScriptRegion
						&& lineStart <= phpScriptRegion.getEnd()) {
					IPhpScriptRegion scriptRegion = (IPhpScriptRegion) phpScriptRegion;
					ITextRegion[] tokens = null;
					try {
						tokens = scriptRegion.getPhpTokens(
								Math.min(lineStart - 1, scriptRegion.getEnd()),
								biggest - lineStart + 1);
					} catch (BadLocationException e) {
						// ignore it, scriptRegion.getEnd() is greater than last
						// phpToken
					}

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
						} else if (biggest == openBracketPeer
								&& scanner.previousToken(biggest - 1,
										PHPHeuristicScanner.UNBOUND) < PHPHeuristicScanner.TokenIDENT) {
							return true;
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

	private int getNextLineIndex(int offset, int currLineIndex,
			boolean checkMultiLine) throws BadLocationException {
		final IRegion lineInfo = document.getLineInformation(currLineIndex);
		final int currLineEndOffset = lineInfo.getOffset()
				+ lineInfo.getLength();
		String checkedLineBeginState = FormatterUtils.getPartitionType(
				document, lineInfo.getOffset(), true);

		String forLineEndState = FormatterUtils.getPartitionType(document,
				currLineEndOffset);

		int insideBraceless = getMultilineInsideBraceless(Math.min(
				currLineEndOffset, offset));
		if (insideBraceless >= 0) {
			return document.getLineOfOffset(insideBraceless);
		}
		if (isMultilineType(checkedLineBeginState)
				&& (checkMultiLine || shouldNotConsiderAsIndentationBase(
						checkedLineBeginState, forLineEndState))) {
			int index = getMultiLineStatementStartOffset(lineInfo.getOffset(),
					currLineIndex);
			if (index > -1) {
				return index;
			}
		}

		if (checkMultiLine) {
			int result = adjustLine(currLineIndex, currLineEndOffset);
			if (result == currLineIndex && result != 0) {
				result--;
			}
			return result;
		}

		return currLineIndex - 1;
	}

	private int getMultiLineStatementStartOffset(int lineStart,
			int currLineIndex) {
		lineStart = IndentationUtils.moveLineStartToNonBlankChar(document,
				lineStart, currLineIndex, true);

		TextSequence textSequence = PHPTextSequenceUtilities
				.getStatement(lineStart,
						document.getRegionAtCharacterOffset(lineStart), true);
		if (textSequence.length() != 0) {
			int textOriginalOffset = textSequence.getOriginalOffset(0);
			int textSequenceLine = document.getLineOfOffset(textOriginalOffset);
			if (textSequenceLine < currLineIndex
					&& IndentationUtils
							.isRegionTypeAllowedMultiline(FormatterUtils
									.getRegionType(document, textOriginalOffset))) {
				return textSequenceLine;
			}
		}

		return -1;
	}

	private int getMultilineInsideBraceless(int checkedOffset) {
		try {
			PHPHeuristicScanner scanner = PHPHeuristicScanner
					.createHeuristicScanner(document, checkedOffset - 1, true);
			int start = scanner.previousToken(checkedOffset - 1,
					PHPHeuristicScanner.UNBOUND);
			if (!(start == PHPHeuristicScanner.TokenRBRACE))
				return -1;

			int openingPeer = scanner.findOpeningPeer(scanner.getPosition(),
					PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LBRACE,
					PHPHeuristicScanner.RBRACE);
			if (openingPeer == PHPHeuristicScanner.NOT_FOUND) {
				return -1;
			}
			int prev = scanner.previousToken(openingPeer - 1,
					PHPHeuristicScanner.UNBOUND);
			if (prev == PHPHeuristicScanner.TokenRPAREN) {
				int openParent = scanner.findOpeningPeer(scanner.getPosition(),
						PHPHeuristicScanner.UNBOUND,
						PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
				if (openParent == PHPHeuristicScanner.NOT_FOUND) {
					return -1;
				}
				prev = scanner.previousToken(openParent - 1,
						PHPHeuristicScanner.UNBOUND);
			}
			if (!IndentationUtils.inBracelessBlock(scanner, document,
					scanner.getPosition() - 1)) {
				return -1;
			}

			// move to first from braceless
			prev = scanner.previousToken(scanner.getPosition() - 1,
					PHPHeuristicScanner.UNBOUND);

			// we inside braceless block. Now we have to move before it
			if (prev == PHPHeuristicScanner.TokenRPAREN) {
				// if, for, while or similar
				int openParent = scanner.findOpeningPeer(scanner.getPosition(),
						PHPHeuristicScanner.UNBOUND,
						PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
				if (openParent == PHPHeuristicScanner.NOT_FOUND) {
					return -1;
				}
			}
			prev = scanner.previousToken(scanner.getPosition() - 1,
					PHPHeuristicScanner.UNBOUND);
			int result = scanner.getPosition();
			if (prev == PHPHeuristicScanner.TokenIF) {
				prev = scanner
						.previousToken(start, PHPHeuristicScanner.UNBOUND);
				if (prev == PHPHeuristicScanner.TokenELSE) {
					result = scanner.getPosition();
				}
			}

			return result;

		} catch (BadLocationException e) {
		}

		return -1;
	}

	private boolean isMultilineContentInsideBraceless(int checkedOffset) {
		try {
			PHPHeuristicScanner scanner = PHPHeuristicScanner
					.createHeuristicScanner(document, checkedOffset - 1, true);
			int start = scanner.previousToken(checkedOffset - 1,
					PHPHeuristicScanner.UNBOUND);
			if (start == PHPHeuristicScanner.TokenLBRACE) {
				if (scanner.isBracelessBlockStart(scanner.getPosition() - 1,
						PHPHeuristicScanner.UNBOUND)) {
					return true;
				}
			}
		} catch (BadLocationException e) {
		}
		return false;
	}

	private boolean lineShouldIndent(final String beginState,
			final String endState) {
		return beginState == PHPPartitionTypes.PHP_DEFAULT
				|| endState == PHPPartitionTypes.PHP_DEFAULT;
	}

	private boolean isInMultiLineStatement(String checkedLineBeginState,
			String checkedLineEndState, int checkedOffset, int lineStart,
			int currLineIndex) throws BadLocationException {
		return getMultiLineStatementStartOffset(lineStart, currLineIndex,
				checkedOffset) > -1 ? true : false;
	}

	/**
	 * @since 2.2
	 */
	private boolean shouldNotConsiderAsIndentationBase(
			final String currentState, final String forState) {
		return currentState != forState && isMultilineType(currentState);
	}

	private boolean isMultilineAfterBraceless(int checkedOffset) {
		return getMultilineInsideBraceless(checkedOffset) >= 0;
	}

	private boolean isMultilineType(final String state) {
		return state.equals(PHPPartitionTypes.PHP_QUOTED_STRING)
				|| state.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)
				|| state.equals(PHPPartitionTypes.PHP_DOC);
	}

}
