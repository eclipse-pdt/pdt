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
package org.eclipse.php.internal.core.documentModel.partitioner;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public abstract class PHPPartitionTypes {

	public static final String PHP_DEFAULT = "org.eclipse.php.PHP_DEFAULT"; //$NON-NLS-1$
	public static final String PHP_SINGLE_LINE_COMMENT = "org.eclipse.php.PHP_SINGLE_LINE_COMMENT"; //$NON-NLS-1$
	public static final String PHP_MULTI_LINE_COMMENT = "org.eclipse.php.PHP_MULTI_LINE_COMMENT"; //$NON-NLS-1$
	public static final String PHP_DOC = "org.eclipse.php.PHP_DOC"; //$NON-NLS-1$
	public static final String PHP_QUOTED_STRING = "org.eclipse.php.PHP_QUOTED_STRING"; //$NON-NLS-1$
	public static final String PHP_COMMENT = "org.eclipse.php.PHP_COMMENT"; //$NON-NLS-1$

	public static boolean isPHPCommentState(final String type) {
		return type == null ? false : isPHPMultiLineCommentState(type)
				|| isPHPLineCommentState(type) || isPHPDocState(type);
	}

	public static boolean isPHPDocState(final String type) {
		return type == null ? false : type.startsWith("PHPDOC"); //$NON-NLS-1$
	}

	public static boolean isPHPDocTagState(final String type) {
		return isPHPDocState(type) && !type.startsWith("PHPDOC_COMMENT"); //$NON-NLS-1$
	}

	public static boolean isPHPLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_LINE_COMMENT;
	}

	public static boolean isPHPMultiLineCommentState(final String type) {
		return type == PHPRegionTypes.PHP_COMMENT
				|| type == PHPRegionTypes.PHP_COMMENT_START
				|| type == PHPRegionTypes.PHP_COMMENT_END;
	}

	public static boolean isPHPQuotesState(final String type) {
		return type == PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING
				|| type == PHPRegionTypes.PHP_HEREDOC_TAG
				|| type == PHPRegionTypes.PHP_ENCAPSED_AND_WHITESPACE;
	}

	public static final boolean isPHPRegularState(final String type) {
		return type != null && !isPHPCommentState(type)
				&& !isPHPQuotesState(type);
	}

	/**
	 * Returns starting region of the current partition
	 * 
	 * @param region
	 *            Region containing current offset
	 * @param offset
	 *            Current position relative to the containing region
	 * @return Starting region of the current partition
	 * @throws BadLocationException
	 */
	public static final ITextRegion getPartitionStartRegion(
			IPhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		ITextRegion startRegion = internalRegion;
		while (internalRegion.getStart() != 0) {
			internalRegion = region.getPhpToken(internalRegion.getStart() - 1);
			if (region.getPartition(internalRegion.getStart()) != partitionType) {
				break;
			}
			startRegion = internalRegion;
		}
		return startRegion;
	}

	/**
	 * Returns offset where the current partition starts
	 * 
	 * @param region
	 *            Region containing current offset
	 * @param offset
	 *            Current position relative to the containing region
	 * @return Starting offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionStart(IPhpScriptRegion region,
			int offset) throws BadLocationException {
		ITextRegion startRegion = getPartitionStartRegion(region, offset);
		return startRegion.getStart();
	}

	/**
	 * Returns region current partition ends on
	 * 
	 * @param region
	 *            Region containing current offset
	 * @param offset
	 *            Current position relative to the containing region
	 * @return Ending region of the current partition
	 * @throws BadLocationException
	 */
	public static final ITextRegion getPartitionEndRegion(
			IPhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		ITextRegion endRegion = internalRegion;
		while (internalRegion.getEnd() != region.getLength()) {
			internalRegion = region.getPhpToken(internalRegion.getEnd());
			if (region.getPartition(internalRegion.getStart()) != partitionType) {
				break;
			}
			endRegion = internalRegion;
		}
		return endRegion;
	}

	/**
	 * Returns offset where the current partition ends
	 * 
	 * @param region
	 *            Region containing current offset
	 * @param offset
	 *            Current position relative to the containing region
	 * @return Ending offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionEnd(IPhpScriptRegion region, int offset)
			throws BadLocationException {
		ITextRegion endRegion = getPartitionEndRegion(region, offset);
		return endRegion.getEnd();
	}

	/**
	 * Returns partition which corresponds to the provided offset
	 * 
	 * @param region
	 *            Region containing current offset
	 * @param offset
	 *            Current position relative to the containing region
	 * @return typed region containing partition
	 * @throws BadLocationException
	 */
	public static final ITypedRegion getPartition(IPhpScriptRegion region,
			int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		int startOffset = getPartitionStart(region, offset);
		int endOffset = getPartitionEnd(region, offset);
		return new TypedRegion(startOffset, endOffset - startOffset,
				partitionType);
	}

	public static boolean isPHPDocCommentState(String type) {
		return type == PHPRegionTypes.PHPDOC_COMMENT;
	}

	public static boolean isPHPCondition(String type) {
		return (type == PHPRegionTypes.PHP_IF || type == PHPRegionTypes.PHP_FOR || type == PHPRegionTypes.PHP_FOREACH);

	}

	public static boolean isPHPLoop(String type) {
		return (type == PHPRegionTypes.PHP_WHILE || type == PHPRegionTypes.PHP_DO);

	}
}
