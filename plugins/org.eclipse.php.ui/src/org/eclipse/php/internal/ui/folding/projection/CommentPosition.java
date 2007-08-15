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
package org.eclipse.php.internal.ui.folding.projection;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.projection.IProjectionPosition;

public class CommentPosition extends Position implements IProjectionPosition {
	/**
	 * Constructs a new CommentAnnotatedPosition.
	 *
	 * @param offset
	 * @param length
	 */
	public CommentPosition(int offset, int length) {
		super(offset, length);
	}

	/**
	 * Returns the offset of the caption (the anchor region) of this projection
	 * position. The returned offset is relative to the receivers offset into
	 * the document.
	 *
	 * @param document the document that this position is attached to
	 * @return the caption offset relative to the position's offset
	 * @throws BadLocationException if accessing the document fails
	 */
	public int computeCaptionOffset(IDocument document) throws BadLocationException {
		return findFirstContent(document, getOffset(), getLength());
	}

	/**
	 * Returns an array of regions that should be collapsed when the annotation
	 * belonging to this position is collapsed. May return null instead of
	 * an empty array.
	 *
	 * @param document the document that this position is attached to
	 * @return the foldable regions for this position
	 * @throws BadLocationException if accessing the document fails
	 */
	public IRegion[] computeProjectionRegions(IDocument document) throws BadLocationException {
		int contentStart = findFirstContent(document, getOffset(), getLength());

		int firstLine = document.getLineOfOffset(getOffset());
		int captionLine = document.getLineOfOffset(getOffset() + contentStart);
		int lastLine = document.getLineOfOffset(getOffset() + getLength());

		IRegion preRegion;
		if (firstLine < captionLine) {
			int preOffset = document.getLineOffset(firstLine);
			IRegion preEndLineInfo = document.getLineInformation(captionLine);
			int preEnd = preEndLineInfo.getOffset();
			preRegion = new Region(preOffset, preEnd - preOffset);
		} else {
			preRegion = null;
		}

		if (captionLine < lastLine) {
			int postOffset = document.getLineOffset(captionLine + 1);
			IRegion postRegion = new Region(postOffset, getOffset() + getLength() - postOffset);

			if (preRegion == null)
				return new IRegion[] { postRegion };

			return new IRegion[] { preRegion, postRegion };
		}

		if (preRegion != null) {
			return new IRegion[] { preRegion };
		}

		return null;
	}

	/**
	 * Finds the offset of the first identifier part within <code>content</code>.
	 * Returns 0 if none is found.
	 *
	 * @param content the content to search
	 * @param prefixEnd the end of the prefix
	 * @return the first index of a unicode identifier part, or zero if none can
	 *         be found
	 * @throws BadLocationException
	 */
	private int findFirstContent(final IDocument document, int offset, int length) throws BadLocationException {
		for (int index = 0; index < length; index++) {
			char currentChar = document.getChar(offset + index);
			if (Character.isUnicodeIdentifierPart(currentChar))
				return index;
		}
		return 0;
	}
}
