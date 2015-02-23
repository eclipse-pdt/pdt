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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class IndentationUtils {

	/**
	 * a statement spanning multi line starts with a region of type
	 * regionType,the lines'(except 1st) indentation are same as/based on 1st
	 * line,then this this method return true,else return false.
	 * 
	 * @param regionType
	 * @return
	 */
	public static boolean isRegionTypeAllowedMultiline(String regionType) {
		// TODO maybe there are other type need to be added
		return regionType != null
				&& !PHPRegionTypes.PHPDOC_COMMENT_START.equals(regionType)
				&& !PHPRegionTypes.PHP_COMMENT_START.equals(regionType)
				&& !PHPRegionTypes.PHP_LINE_COMMENT.equals(regionType)
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=458777
				// && !PHPRegionTypes.PHP_LABEL.equals(regionType)
				&& !PHPRegionTypes.PHP_CASE.equals(regionType)
				&& !PHPRegionTypes.PHP_DEFAULT.equals(regionType);
	}

	/**
	 * Check if the line contains any non blank chars.
	 */
	public static boolean isBlanks(final IStructuredDocument document,
			final int startOffset, final int endOffset, final int currentOffset)
			throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim()
				.length() == 0
				|| document.get(startOffset, currentOffset - startOffset)
						.trim().length() == 0;
	}

	public static int moveLineStartToNonBlankChar(IStructuredDocument document,
			int lineStart, int currLineIndex, boolean moveAfterNonBlankChar) {
		try {
			char[] line = document.get(lineStart,
					document.getLineLength(currLineIndex)).toCharArray();
			for (int i = 0; i < line.length; i++) {
				char c = line[i];
				if (Character.isWhitespace(c)) {
				} else {
					// move line start to first non blank char
					lineStart += i + (moveAfterNonBlankChar ? 1 : 0);
					break;
				}
			}
		} catch (BadLocationException e) {
		}
		return lineStart;
	}

	public static boolean inBracelessBlock(PHPHeuristicScanner scanner,
			IStructuredDocument document, int offset) {
		try {
			if (scanner.isBracelessBlockStart(offset - 1,
					PHPHeuristicScanner.UNBOUND)
					&& scanner.nextToken(offset, PHPHeuristicScanner.UNBOUND) != PHPHeuristicScanner.TokenLBRACE) {
				return true;
			}

		} catch (Throwable e) {
		}
		return false;
	}

}
