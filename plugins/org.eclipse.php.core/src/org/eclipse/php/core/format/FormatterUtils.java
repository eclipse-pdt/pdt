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
import org.eclipse.php.core.documentModel.parser.regions.PHPContentRegion;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class FormatterUtils {

	public static String getPartitionType(IStructuredDocument document, int offset) {
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		if (tRegion == null) {
			return null;
		}
		String result = null;

		if (tRegion instanceof PHPContentRegion) {
			if (PhpLexer.isPHPMultiLineCommentState(tRegion.getType())) {
				result = PHPPartitionTypes.PHP_MULTI_LINE_COMMENT; 
			} else if (PhpLexer.isPHPLineCommentState(tRegion.getType())) {
				result = PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT ; 
			} else if (PhpLexer.isPHPDocState(tRegion.getType())) {
				result = PHPPartitionTypes.PHP_DOC; 
			} else if (PhpLexer.isPHPQuotesState(tRegion.getType())) {
				result = checkBounds(sdRegion, tRegion, offset, PHPPartitionTypes.PHP_QUOTED_STRING);
			} else if (PHPRegionTypes.TASK.equals(tRegion.getType())) {
				// return the previous region type
				if (sdRegion.getPrevious() == null) {
					return null;
				}
				result = getPartitionType(document, sdRegion.getStartOffset() + tRegion.getStart() - 1);
			} else {
				result = PHPPartitionTypes.PHP_DEFAULT;
			}
		} else if (tRegion.getType() == PHPRegionTypes.PHP_OPENTAG) {
			if(offset >= sdRegion.getStartOffset() + tRegion.getTextEnd()){
				result = PHPPartitionTypes.PHP_DEFAULT;
			} else {
				result = PHPRegionTypes.PHP_OPENTAG;
			}
		} else if (tRegion.getType() == PHPRegionTypes.PHP_CLOSETAG) {
			if(offset == sdRegion.getStartOffset() + tRegion.getStart()){
				result = PHPPartitionTypes.PHP_DEFAULT;
			} else {
				result = PHPRegionTypes.PHP_CLOSETAG;
			}
		}
		return result;
	}

	/**
	 * This functions checks if an offset is right before/next a partition, if so then the offset bellongs to the before/next partition
	 */
	private static String checkBounds(IStructuredDocumentRegion sdRegion, ITextRegion tRegion, int offset, String defaultPartitionType) {
		if (sdRegion.getStartOffset() + tRegion.getStart() == offset) {
			tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
			if (tRegion == null) {
				return defaultPartitionType;
			}
			if (tRegion.getType() == PHPRegionTypes.PHP_OPENTAG) {
				return PHPRegionTypes.PHP_OPENTAG;
			}
			return tRegion.getType(); // PHPPartitionTypes.PHP_DEFAULT;
		}
		if (sdRegion.getStartOffset() + tRegion.getTextEnd() == offset) {
			tRegion = sdRegion.getRegionAtCharacterOffset(offset + 1);
			if (tRegion == null) {
				return defaultPartitionType;
			}
			if (tRegion.getType() == PHPRegionTypes.PHP_CLOSETAG) {
				return PHPRegionTypes.PHP_CLOSETAG;
			}
			return tRegion.getType();
		}
		return defaultPartitionType;
	}

	private static StringBuffer helpBuffer = new StringBuffer(50);

	/**
	 * Return the blanks at the start of the line.
	 */
	public static String getLineBlanks(IStructuredDocument document, IRegion lineInfo) throws BadLocationException {
		helpBuffer.setLength(0);
		int startOffset = lineInfo.getOffset();
		int length = lineInfo.getLength();
		char[] line = document.get(startOffset, length).toCharArray();
		for (int i = 0; i < line.length; i++) {
			char c = line[i];
			if (Character.isWhitespace(c)) {
				helpBuffer.append(c);
			} else {
				break;
			}
		}
		return helpBuffer.toString();
	}

}
