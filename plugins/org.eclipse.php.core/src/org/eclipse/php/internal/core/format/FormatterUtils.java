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
package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class FormatterUtils {
	private static PHPStructuredTextPartitioner partitioner = new PHPStructuredTextPartitioner();

	public static String getPartitionType(IStructuredDocument document, int offset, boolean perferOpenPartitions) {
		partitioner.connect(document);
		return partitioner.getContentType(offset, perferOpenPartitions);
	}

	public static String getPartitionType(IStructuredDocument document, int offset) {
		return getPartitionType(document, offset, false);
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

	/**
	 * Returns the previous php structured document.
	 * Special cases : 
	 * 1) previous is null - returns null
	 * 2) previous is not PHP region - returns the last region of the last php block  
	 * @param currentStructuredDocumentRegion
	 */
	public static IStructuredDocumentRegion getLastPhpStructuredDocumentRegion(IStructuredDocumentRegion currentStructuredDocumentRegion) {
		assert currentStructuredDocumentRegion != null;

		// get last region
		currentStructuredDocumentRegion = currentStructuredDocumentRegion.getPrevious();

		// search for last php block (then returns the last region)
		while (currentStructuredDocumentRegion != null && currentStructuredDocumentRegion.getType() != PHPRegionContext.PHP_CONTENT) {
			currentStructuredDocumentRegion = currentStructuredDocumentRegion.getPrevious();
		}

		return currentStructuredDocumentRegion;
	}
}
