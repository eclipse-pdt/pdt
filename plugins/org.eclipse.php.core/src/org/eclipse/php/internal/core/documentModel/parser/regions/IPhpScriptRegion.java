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
package org.eclipse.php.internal.core.documentModel.parser.regions;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This is a base class for a PHP script region The default implementation is
 * {@link PhpScriptRegion}
 * 
 * @author Roy, 2007
 */
public interface IPhpScriptRegion extends ITextRegion {

	/**
	 * Returns an array of php tokens that lay inside the interval [from, from +
	 * length]
	 * 
	 * @param from
	 * @param min
	 * @throws BadLocationException
	 */
	public abstract ITextRegion[] getPhpTokens(int from, int length)
			throws BadLocationException;

	public abstract ITextRegion[] getUpdatedPhpTokens()
			throws BadLocationException;

	public int getUpdatedTokensStart();

	public int getUpdatedTokensLength();

	/**
	 * Returns a single php token that lay in the given offset
	 * 
	 * @param offset
	 * @return
	 * @throws BadLocationException
	 */
	public abstract ITextRegion getPhpToken(int offset)
			throws BadLocationException;

	/**
	 * returns the php partition type of the token that lay in the given offset
	 * PHP valid types: PHP_DEFAULT - {@link PHPPartitionTypes#PHP_DEFAULT}
	 * PHP_SINGLE_LINE_COMMENT -
	 * {@link PHPPartitionTypes#PHP_SINGLE_LINE_COMMENT} PHP_MULTI_LINE_COMMENT
	 * - {@link PHPPartitionTypes#PHP_MULTI_LINE_COMMENT} PHP_DOC -
	 * {@link PHPPartitionTypes#PHP_DOC} PHP_QUOTED_STRING -
	 * {@link PHPPartitionTypes#PHP_QUOTED_STRING}
	 * 
	 * @param offset
	 * @throws BadLocationException
	 */
	public abstract String getPartition(int offset) throws BadLocationException;

	/**
	 * returns the php token type of the token that lays in the given offset
	 * Please refer {@link PHPRegionTypes} for list of php token types
	 * 
	 * @param offset
	 * @throws BadLocationException
	 */
	public abstract String getPhpTokenType(int offset)
			throws BadLocationException;

	/**
	 * Returns true if the given offset is in line comment
	 * 
	 * @param relativeOffset
	 * @return
	 * @throws BadLocationException
	 */
	public abstract boolean isLineComment(int relativeOffset)
			throws BadLocationException;

	/**
	 * Performs a complete reparse in the document on the given interval
	 * 
	 * @param doc
	 * @param start
	 * @param length
	 */
	public abstract void completeReparse(IDocument doc, int start, int length);

	/**
	 * Returns true if the last operation was a full reparse action
	 * 
	 * @return
	 */
	public boolean isFullReparsed();

	/**
	 * Sets if the last operation was a full reparse action
	 * 
	 * @param b
	 */
	public abstract void setFullReparsed(boolean isFullReparse);
}
