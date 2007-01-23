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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.CurlyCloseIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.text.PHPDocumentRegionEdgeMatcher;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * 
 * @author guy.g
 *
 */

public class PairCurlyBracketAutoEditStrategy implements IAfterNewLineAutoEditStrategy {
	private static final char CURLY_OPEN = '{';
	private static final char CURLY_CLOSE = '}';

	private static PHPDocumentRegionEdgeMatcher matcher = new PHPDocumentRegionEdgeMatcher();

	public int autoEditAfterNewLine(IStructuredDocument document, DocumentCommand command, StringBuffer buffer) {
		try {

			int offset = command.offset;

			int rvPosition = offset + buffer.length();
			rvPosition += copyRestOfLine(document, command, buffer);

			boolean addCurlyClose = false;
			int curlyCloseCounter = 0;
			int currOffset = offset;
			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(currOffset);
			while (currOffset >= 0 && sdRegion != null) {
				if (sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
					currOffset = sdRegion.getStartOffset() - 1;
					sdRegion = sdRegion.getPrevious();
					continue;
				}
				int regionStart = sdRegion.getStart();
				String text = sdRegion.getFullText();

				ITextRegion tRegion = null;
				int indexInText = text.length() - 1;
				while (indexInText >= 0) {
					char currChar = text.charAt(indexInText);
					if (currChar == CURLY_CLOSE) {
						tRegion = sdRegion.getRegionAtCharacterOffset(regionStart + indexInText);
						if (tRegion.getType() == PHPRegionTypes.PHP_CURLY_CLOSE) {
							curlyCloseCounter++;
						}
					} else if (currChar == CURLY_OPEN) {
						tRegion = sdRegion.getRegionAtCharacterOffset(regionStart + indexInText);
						String regionType = tRegion.getType();
						if (regionType != PHPRegionTypes.PHP_CURLY_OPEN) {
							indexInText--;
							continue;
						}
						curlyCloseCounter--;
						if (curlyCloseCounter < 0) {
							if (matcher.match(document, regionStart + indexInText + 1) == null) {
								addCurlyClose = true;
								break;
							}
							curlyCloseCounter++;
						}
					}
					indexInText--;
				}
				if (addCurlyClose) {
					break;
				}

				currOffset = sdRegion.getStartOffset() - 1;
				sdRegion = sdRegion.getPrevious();
			}

			if (addCurlyClose) {
				addCurlyCloseBracket(document, command, buffer);
			}
			return rvPosition;
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return -1;
	}

	/**
	 * Copies the rest of the line (after the { ) into the buffer in order put the text inside the curly brackets
	 * @return The number of charecters the caret should be advenced at   
	 */
	private int copyRestOfLine(IStructuredDocument document, DocumentCommand command, StringBuffer buffer) throws BadLocationException {

		if(command.offset + command.length == document.getLength()){ // if we're at the end of the document then nothing to copy
			return 0;
		}
		int offset = command.offset;
		IRegion lineInfo = document.getLineInformationOfOffset(offset);
		int endOffset = lineInfo.getOffset() + lineInfo.getLength();
		int whiteSpacesAdded = 0;
		String lineEnd = document.get(offset, endOffset - offset);

		//if there are spaces between the '{' and the text in the line 
		//(or the end of line)then need to override them

		for (int i = 0; i < lineEnd.length(); i++) {
			char c = lineEnd.charAt(i);
			if (c == '\n' || c == '\r' || !Character.isWhitespace(c)) {
				command.length += i;
				break;
			}
		}

		String trimmedLineEnd = lineEnd.trim();
		if (trimmedLineEnd.length() > 0 && trimmedLineEnd.charAt(0) == '}') {
			//if there is a } then there is a problem with curlyCloseInsertionStrategy calc
			// and we need to add manualy another indentation.
			int indentationSize = FormatPreferencesSupport.getInstance().getIndentationSize(document);
			char indentationChar = FormatPreferencesSupport.getInstance().getIndentationChar(document);
			for (int i = 0; i < indentationSize; i++) {
				buffer.append(indentationChar);
				whiteSpacesAdded++;
			}
			buffer.append(document.getLineDelimiter());
			String blanks = FormatterUtils.getLineBlanks(document, lineInfo);
			buffer.append(blanks);
		}

		//copping the rest of the line after the { 
		offset += command.length;
		char nextChar = document.getChar(offset);
		while (nextChar != '\n' && nextChar != '\r') {
			buffer.append(nextChar);
			command.length++;
			offset++;
			nextChar = document.getChar(offset);
		}

		return whiteSpacesAdded;
	}

	private CurlyCloseIndentationStrategy curlyCloseIndentationStrategy = new CurlyCloseIndentationStrategy();

	private void addCurlyCloseBracket(IStructuredDocument document, DocumentCommand command, StringBuffer buffer) {

		buffer.append(document.getLineDelimiter());
		int lineIndex = document.getLineOfOffset(command.offset);
		try {
			IRegion lineInfo = document.getLineInformation(lineIndex);
			curlyCloseIndentationStrategy.placeMatchingBlanks(document, buffer, lineIndex, lineInfo.getOffset() + lineInfo.getLength());
		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		buffer.append('}');
	}
}
