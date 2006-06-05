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
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.core.format.FormatterUtils;
import org.eclipse.wst.html.ui.internal.text.HTMLDocumentRegionEdgeMatcher;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * 
 * @author guy.g
 *
 */

public abstract class MatchingCharAutoEditStrategy implements IAutoEditStrategy {

	protected static final char CURLY_OPEN = '{';
	protected static final char CURLY_CLOSE = '}';
	protected static final char ROUND_OPEN = '(';
	protected static final char ROUND_CLOSE = ')';
	protected static final char SQUARE_OPEN = '[';
	protected static final char SQUARE_CLOSE = ']';
	protected static final char SINGLE_QOUTE = '\'';
	protected static final char DOUBLE_QOUTES = '\"';
	protected static final char BACK_QOUTE = '`';
	protected static final char BACK_SLASH = '\\';

	protected static final int SEARCH_NOT_VALID = -1;
	protected static final int MATCHING_BRACKET_NEEDED = 0;
	protected static final int MATCHING_BRACKET_NOT_NEEDED = 1;

	protected static HTMLDocumentRegionEdgeMatcher matcher = new HTMLDocumentRegionEdgeMatcher();

	protected boolean isClosingBracket(char c) {
		return (c == ROUND_CLOSE) || (c == SQUARE_CLOSE) || (c == CURLY_CLOSE);
	}

	protected boolean isQuote(char c) {
		return (c == SINGLE_QOUTE) || (c == DOUBLE_QOUTES) || (c == BACK_QOUTE);
	}

	protected static char getMatchingChar(char c) {
		switch (c) {
			case CURLY_OPEN:
				return CURLY_CLOSE;
			case CURLY_CLOSE:
				return CURLY_OPEN;
			case ROUND_OPEN:
				return ROUND_CLOSE;
			case ROUND_CLOSE:
				return ROUND_OPEN;
			case SQUARE_OPEN:
				return SQUARE_CLOSE;
			case SQUARE_CLOSE:
				return SQUARE_OPEN;
			case DOUBLE_QOUTES:
			case SINGLE_QOUTE:
			case BACK_QOUTE:
				return c;
		}

		return '-';
	}

	/**
	 * returns true if the offset in the document is not to the left of text
	 * excluding php closing tag (?>) and comments  
	 */
	protected boolean shouldAddClosingBracket(IStructuredDocument document, int offset, boolean isQuote) throws BadLocationException {
		char currChar = document.getChar(offset);
		if (Character.isWhitespace(currChar) || isClosingBracket(currChar) || (isQuote && isQuote(currChar)) || currChar == ';') {
			return true;
		}
		if (offset + 1 >= document.getLength()) {
			return false;
		}
		char nextChar = document.getChar(offset + 1);
		String state = FormatterUtils.getPartitionType(document, offset);

		if (state == PHPPartitionTypes.PHP_DEFAULT || state == PHPRegionTypes.PHP_OPENTAG || state == PHPRegionTypes.PHP_CLOSETAG) {
			if (currChar == '?' && nextChar == '>') {
				return true;
			}
			if (currChar == '<' && nextChar == '<') { // checking for heredoc
				int position = offset + 2;
				while (position < document.getLength()) {
					char c = document.getChar(position);
					if (Character.isWhitespace(c)) {
						if (position + 1 == document.getLength()) { // if its the end of the text then its ok
							return true;
						}
						//                        TODO add support in heredoc state
						//                        String state1 = FormatterUtils.getPartitionType(document, position + 1);
						//                        if (state1 == PhpLexer.ST_PHP_HEREDOC) {
						//                            return true;
						//                        }
						break;
					}
					position++;
				}
			}
		}

		if (currChar == '/' && (nextChar == '/' || nextChar == '*')) {
			return true;
		}
		return false;
	}

	protected static boolean isSpecialOpenCurlyInQuotes(IStructuredDocument document, int offset) throws BadLocationException {
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		if (sdRegion == null) {
			return false;

		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		// TODO need to support heredoc also
		if (tRegion.getType() != PHPRegionTypes.PHP_ENCAPSED_AND_WHITESPACE) {
			return false;
		}

		char firstChar = document.getChar(sdRegion.getStartOffset() + tRegion.getStart());
		if (firstChar != DOUBLE_QOUTES && firstChar != BACK_QOUTE) {
			return false;
		}

		char bracketChar = document.getChar(offset + 1);
		return bracketChar == '$';
	}

}
