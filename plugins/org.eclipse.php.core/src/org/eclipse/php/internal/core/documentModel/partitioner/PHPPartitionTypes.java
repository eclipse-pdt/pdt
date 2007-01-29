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
package org.eclipse.php.internal.core.documentModel.partitioner;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public abstract class PHPPartitionTypes {

	public static final String PHP_DEFAULT = "org.eclipse.php.PHP_DEFAULT";
	public static final String PHP_SINGLE_LINE_COMMENT = "org.eclipse.php.PHP_SINGLE_LINE_COMMENT";
	public static final String PHP_MULTI_LINE_COMMENT = "org.eclipse.php.PHP_MULTI_LINE_COMMENT";
	public static final String PHP_DOC = "org.eclipse.php.PHP_DOC";
	public static final String PHP_QUOTED_STRING = "org.eclipse.php.PHP_QUOTED_STRING";

	public static boolean isPHPCommentState(final String type) {
		return type == null ? false : isPHPMultiLineCommentState(type) || isPHPLineCommentState(type) || isPHPDocState(type);
	}

	public static boolean isPHPDocState(final String type) {
		return type == null ? false : type.startsWith("PHPDOC");
	}
	
	public static boolean isPHPDocTagState(final String type) {
		return isPHPDocState(type) && !type.startsWith("PHPDOC_COMMENT"); 
	}

	public static boolean isPHPLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_LINE_COMMENT;
	}

	public static boolean isPHPMultiLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_COMMENT || type == PHPRegionTypes.PHP_COMMENT_START || type == PHPRegionTypes.PHP_COMMENT_END;
	}

	public static boolean isPHPQuotesState(final String type) {
		return type == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING || type == PHPRegionTypes.PHP_HEREDOC_TAG;
	}

	public static final boolean isPHPRegularState(final String type) {
		return !isPHPCommentState(type) && !isPHPQuotesState(type);
	}

	/**
	 * Returns offset where the current partition starts
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Starting offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionStart(PhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		while (internalRegion.getStart() > 0 && region.getPartition(internalRegion.getStart()) == partitionType) {
			internalRegion = region.getPhpToken(internalRegion.getStart() - 1);
		}
		return internalRegion.getStart();
	}
	
	/**
	 * Returns offset where the current partition ends
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Ending offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionEnd(PhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		while (internalRegion.getEnd() != region.getEnd() && region.getPartition(internalRegion.getStart()) == partitionType) {
			internalRegion = region.getPhpToken(region.getEnd());
		}
		return internalRegion.getEnd();
	}
	
	/**
	 * Returns partition which corresponds to the provided offset
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return typed region containing partition 
	 * @throws BadLocationException
	 */
	public static final ITypedRegion getPartition(PhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		int startOffset = getPartitionStart(region, offset);
		int endOffset = getPartitionEnd(region, offset);
		return new TypedRegion(startOffset, endOffset - startOffset, partitionType);
	}

	public static boolean isPHPDocCommentState(String type) {
		return type == PHPRegionTypes.PHPDOC_COMMENT;
	}	
}
