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
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.text.PHPDocumentRegionEdgeMatcher;
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

	protected static PHPDocumentRegionEdgeMatcher matcher = new PHPDocumentRegionEdgeMatcher();

	protected boolean isClosingBracket(final char c) {
		return c == ROUND_CLOSE || c == SQUARE_CLOSE || c == CURLY_CLOSE;
	}

	protected boolean isQuote(final char c) {
		return c == SINGLE_QOUTE || c == DOUBLE_QOUTES || c == BACK_QOUTE;
	}

	protected static char getMatchingChar(final char c) {
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
	protected boolean shouldAddClosingBracket(
			final IStructuredDocument document, final int offset,
			final boolean isQuote) throws BadLocationException {
		// check the case of the end of the document
		// if we are after close PHP tag, don't give auto completion
		// otherwise, we could be typing our code without having a php close tag
		// and we do need completion
		// (can't check region type since it is wrong)
		if (document.getLength() == offset) {
			if (document.getChar(offset - 2) == '?'
					&& document.getChar(offset - 1) == '>')
				return false;
			else
				return true;
		}
		if (document.getLength() == offset + 1) {
			return true;
		}

		final char currChar = document.getChar(offset);
		final char nextChar = document.getChar(offset + 1);

		if (Character.isWhitespace(currChar) || isClosingBracket(currChar)
				|| isQuote && isQuote(currChar) || currChar == ';'
				|| currChar == ',')
			return true;
		if (offset + 1 >= document.getLength())
			return false;

		final String state = FormatterUtils.getPartitionType(document, offset);

		if (state == PHPRegionTypes.PHP_OPENTAG)
			return true;
		if (currChar == '/' && (nextChar == '/' || nextChar == '*'))
			return true;
		if (currChar == '?' && nextChar == '>')
			return true;
		// in case of <<<
		if (currChar == '<' && nextChar == '<'
				&& offset + 2 < document.getLength()
				&& document.getChar(offset + 2) == '<')
			return true;
		return false;
	}

	protected static boolean isSpecialOpenCurlyInQuotes(
			final IStructuredDocument document, final int offset)
			throws BadLocationException {
		final IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(offset);
		if (sdRegion == null)
			return false;
		final ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		// TODO need to support heredoc also
		if (tRegion.getType() != PHPRegionTypes.PHP_ENCAPSED_AND_WHITESPACE)
			return false;

		final char firstChar = document.getChar(sdRegion.getStartOffset()
				+ tRegion.getStart());
		if (firstChar != DOUBLE_QOUTES && firstChar != BACK_QOUTE)
			return false;

		final char bracketChar = document.getChar(offset + 1);
		return bracketChar == '$';
	}

	/**
	 * when we call command.offset++; we also need to check command.caretOffset.
	 * bug 312439: Improper cursor jumps when using associative arrays in
	 * content assist https://bugs.eclipse.org/bugs/show_bug.cgi?id=312439
	 * 
	 * @param command
	 */
	protected void adjustDocumentOffset(DocumentCommand command) {
		command.offset++;
		if (command.caretOffset != -1) {
			command.caretOffset = command.offset;
		}
	}
}
