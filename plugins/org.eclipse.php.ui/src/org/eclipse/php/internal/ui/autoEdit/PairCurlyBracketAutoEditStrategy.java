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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.CurlyCloseIndentationStrategy;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.text.PHPDocumentRegionEdgeMatcher;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.xml.core.internal.parser.ContextRegionContainer;

/**
 * 
 * @author guy.g
 * 
 */

public class PairCurlyBracketAutoEditStrategy implements
		IAfterNewLineAutoEditStrategy {
	private static final char CURLY_OPEN = '{';
	private static final char CURLY_CLOSE = '}';

	private static PHPDocumentRegionEdgeMatcher matcher = new PHPDocumentRegionEdgeMatcher();

	/**
	 * get the php token according to the given sdRegion and offset.
	 * 
	 * @param sdRegion
	 * @param offset
	 * @return
	 */
	private ITextRegion getPhpToken(IStructuredDocumentRegion sdRegion,
			int offset) {
		try {
			// get the ITextRegionContainer region or PhpScriptRegion region
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			int regionStart = sdRegion.getStartOffset(tRegion);

			// in case of container we have the extract the PhpScriptRegion
			if (tRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
				regionStart += tRegion.getStart();
			}

			// find the specified php token in the PhpScriptRegion
			if (tRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
				tRegion = scriptRegion.getPhpToken(offset - regionStart);

				return tRegion;
			}
		} catch (BadLocationException e) {
		}
		return null;
	}

	public int autoEditAfterNewLine(IStructuredDocument document,
			DocumentCommand command, StringBuffer buffer) {
		try {

			boolean isClosingParen = isClosingParen(document, command);
			int offset = command.offset;

			int rvPosition = offset + buffer.length();
			if (!isClosingParen) {
				rvPosition += copyRestOfLine(document, command, buffer);
			}

			boolean addCurlyClose = false;
			int curlyCloseCounter = 0;
			int currOffset = offset;
			IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(currOffset);

			int regionStart = sdRegion.getStart();
			String text = sdRegion.getFullText();

			ITextRegion tRegion = null;
			int indexInText = text.length() - 1;
			while (indexInText >= 0) {
				char currChar = text.charAt(indexInText);
				if (currChar == CURLY_CLOSE) {
					tRegion = getPhpToken(sdRegion, regionStart + indexInText);
					if (tRegion == null
							|| tRegion.getType() == PHPRegionTypes.PHP_CURLY_CLOSE) {
						curlyCloseCounter++;
					}
				} else if (currChar == CURLY_OPEN) {
					tRegion = getPhpToken(sdRegion, regionStart + indexInText);
					boolean found = false;
					if (tRegion != null) {
						if (tRegion.getType().equals(
								PHPRegionTypes.PHP_CURLY_OPEN)) {
							found = true;
						} else if (tRegion.getType().equals(
								PHPRegionTypes.PHP_TOKEN)
								&& tRegion.getLength() == 2) {
							ITextRegion reg = sdRegion
									.getRegionAtCharacterOffset(regionStart
											+ indexInText);
							int start = reg.getStart() + tRegion.getStart();
							if (text.substring(start, start + 2).equals("${")) { //$NON-NLS-1$
								found = true;
							}
						}
					}
					if (!found) {
						indexInText--;
						continue;
					}
					curlyCloseCounter--;
					if (curlyCloseCounter < 0) {
						if (matcher.match(document, regionStart + indexInText
								+ 1) == null) {
							addCurlyClose = true;
							break;
						}
						curlyCloseCounter++;
					}
				}
				indexInText--;
			}

			if (addCurlyClose) {
				addCurlyCloseBracket(document, command, buffer);
			}
			if (isClosingParen) {
				rvPosition += copyRestOfLine(document, command, buffer);
			}

			return rvPosition;
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return -1;
	}

	private boolean isClosingParen(IStructuredDocument document,
			DocumentCommand command) throws BadLocationException {
		// TODO Auto-generated method stub
		int documentLength = document.getLength();
		if (command.offset + command.length == documentLength) { // if we're at
																	// the end
																	// of the
																	// document
																	// then
																	// nothing
																	// to copy
			return false;
		}
		int offset = command.offset;
		IRegion lineInfo = document.getLineInformationOfOffset(offset);
		int endOffset = lineInfo.getOffset() + lineInfo.getLength();
		int whiteSpacesAdded = 0;
		int lengthToCopyDown = endOffset - offset;

		IStructuredDocumentRegion[] structuredDocumentRegions = document
				.getStructuredDocumentRegions(offset, lengthToCopyDown);
		if (structuredDocumentRegions != null
				&& structuredDocumentRegions.length > 0) {
			IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentRegions[0];
			ITextRegion firstRegion = structuredDocumentRegion.getFirstRegion();
			ITextRegion lastRegion = structuredDocumentRegion.getLastRegion();

			int xmlRelativeOffset = 0;

			// deal with PHP block within HTML tags
			if (!(firstRegion instanceof ContextRegion)) { // meaning
															// "not-PHP-Resgion"
				ITextRegion regionAtCharacterOffset = structuredDocumentRegion
						.getRegionAtCharacterOffset(offset);
				if (regionAtCharacterOffset instanceof ContextRegionContainer) {
					ContextRegionContainer phpContext = (ContextRegionContainer) regionAtCharacterOffset;
					lastRegion = phpContext.getLastRegion();
					firstRegion = phpContext.getFirstRegion();
					xmlRelativeOffset = firstRegion.getLength();
				}

			}
			int absolutOffset = lastRegion.getStart()
					+ structuredDocumentRegion.getStartOffset()
					+ xmlRelativeOffset;
			if (absolutOffset <= endOffset && offset <= absolutOffset) {
				lengthToCopyDown = absolutOffset - offset;
				;
			}
		}
		String lineEnd = document.get(offset, lengthToCopyDown).trim();
		if (lineEnd.startsWith(")") || lineEnd.startsWith("]")) { //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}
		return false;
	}

	/**
	 * Copies the rest of the line (after the { ) into the buffer in order put
	 * the text inside the curly brackets
	 * 
	 * @return The number of characters the caret should be advanced at
	 */
	private int copyRestOfLine(IStructuredDocument document,
			DocumentCommand command, StringBuffer buffer)
			throws BadLocationException {

		int documentLength = document.getLength();
		if (command.offset + command.length == documentLength) { // if we're at
																	// the end
																	// of the
																	// document
																	// then
																	// nothing
																	// to copy
			return 0;
		}
		int offset = command.offset;
		IRegion lineInfo = document.getLineInformationOfOffset(offset);
		int endOffset = lineInfo.getOffset() + lineInfo.getLength();
		int whiteSpacesAdded = 0;
		int lengthToCopyDown = endOffset - offset;

		IStructuredDocumentRegion[] structuredDocumentRegions = document
				.getStructuredDocumentRegions(offset, lengthToCopyDown);
		if (structuredDocumentRegions != null
				&& structuredDocumentRegions.length > 0) {
			IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentRegions[0];
			ITextRegion firstRegion = structuredDocumentRegion.getFirstRegion();
			ITextRegion lastRegion = structuredDocumentRegion.getLastRegion();

			int xmlRelativeOffset = 0;

			// deal with PHP block within HTML tags
			if (!(firstRegion instanceof ContextRegion)) { // meaning
															// "not-PHP-Resgion"
				ITextRegion regionAtCharacterOffset = structuredDocumentRegion
						.getRegionAtCharacterOffset(offset);
				if (regionAtCharacterOffset instanceof ContextRegionContainer) {
					ContextRegionContainer phpContext = (ContextRegionContainer) regionAtCharacterOffset;
					lastRegion = phpContext.getLastRegion();
					firstRegion = phpContext.getFirstRegion();
					xmlRelativeOffset = firstRegion.getLength();
				}

			}
			int absolutOffset = lastRegion.getStart()
					+ structuredDocumentRegion.getStartOffset()
					+ xmlRelativeOffset;
			if (absolutOffset <= endOffset && offset <= absolutOffset) {
				lengthToCopyDown = absolutOffset - offset;
				;
			}
		}
		String lineEnd = document.get(offset, lengthToCopyDown);

		// if there are spaces between the '{' and the text in the line
		// (or the end of line)then need to override them
		for (int i = 0; i < lineEnd.length(); i++) {
			char c = lineEnd.charAt(i);
			lengthToCopyDown--; // for every iteration, need copy one less char
			if (c == '\n' || c == '\r' || !Character.isWhitespace(c)) {
				command.length += i;
				lengthToCopyDown++; // incrementing one back...
				break;
			}
		}

		String trimmedLineEnd = lineEnd.trim();
		if (trimmedLineEnd.length() > 0 && trimmedLineEnd.charAt(0) == '}') {
			// if there is a } then there is a problem with
			// curlyCloseInsertionStrategy calc
			// and we need to add manually another indentation.
			int indentationSize = FormatPreferencesSupport.getInstance()
					.getIndentationSize(document);
			char indentationChar = FormatPreferencesSupport.getInstance()
					.getIndentationChar(document);
			for (int i = 0; i < indentationSize; i++) {
				buffer.append(indentationChar);
				whiteSpacesAdded++;
			}
			buffer.append(document.getLineDelimiter());
			int baseline = DefaultIndentationStrategy.getIndentationBaseLine(
					document, document.getLineOfOffset(offset), offset, true);
			lineInfo = document.getLineInformation(baseline);
			String blanks = FormatterUtils.getLineBlanks(document, lineInfo);
			buffer.append(blanks);
		}

		// copying the rest of the line after the "{" ,
		// using length calculated according to regionEnd or EndOfLine
		offset += command.length;
		char nextChar = document.getChar(offset);
		for (int i = 0; lengthToCopyDown > 0; lengthToCopyDown--) {
			buffer.append(nextChar);
			command.length++;
			offset++;
			if (offset == documentLength) {
				break;
			}
			nextChar = document.getChar(offset);
		}

		return whiteSpacesAdded;
	}

	private final CurlyCloseIndentationStrategy curlyCloseIndentationStrategy = new CurlyCloseIndentationStrategy();

	private void addCurlyCloseBracket(IStructuredDocument document,
			DocumentCommand command, StringBuffer buffer) {

		buffer.append(document.getLineDelimiter());
		int lineIndex = document.getLineOfOffset(command.offset);
		try {
			IRegion lineInfo = document.getLineInformation(lineIndex);
			curlyCloseIndentationStrategy.placeMatchingBlanks(document, buffer,
					lineIndex, lineInfo.getOffset() + lineInfo.getLength());
		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		buffer.append('}');
	}
}
