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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.projection.IProjectionPosition;
import org.eclipse.php.internal.ui.text.DocumentCharacterIterator;

/**
 * Projection position that will return two foldable regions: one folding away
 * the region from after the '/**' to the beginning of the content, the other
 * from after the first content line until after the comment.
 * @see org.eclipse.jdt.ui.text.folding.DefaultJavaFoldingStructureProvider
 * @imported Roy, 2007 
 */
public final class CommentPosition extends Position implements IProjectionPosition {
	CommentPosition(int offset, int length) {
		super(offset, length);
	}

	/*
	 * @see org.eclipse.jface.text.source.projection.IProjectionPosition#computeFoldingRegions(org.eclipse.jface.text.IDocument)
	 */
	public IRegion[] computeProjectionRegions(IDocument document) throws BadLocationException {
		DocumentCharacterIterator sequence = new DocumentCharacterIterator(document, offset, offset + length);
		int prefixEnd = 0;
		int contentStart = findFirstContent(sequence, prefixEnd);

		int firstLine = document.getLineOfOffset(offset + prefixEnd);
		int captionLine = document.getLineOfOffset(offset + contentStart);
		int lastLine = document.getLineOfOffset(offset + length);

		Assert.isTrue(firstLine <= captionLine, "first folded line is greater than the caption line"); //$NON-NLS-1$
		Assert.isTrue(captionLine <= lastLine, "caption line is greater than the last folded line"); //$NON-NLS-1$

		IRegion preRegion;
		if (firstLine < captionLine) {
//			preRegion= new Region(offset + prefixEnd, contentStart - prefixEnd);
			int preOffset = document.getLineOffset(firstLine);
			IRegion preEndLineInfo = document.getLineInformation(captionLine);
			int preEnd = preEndLineInfo.getOffset();
			preRegion = new Region(preOffset, preEnd - preOffset);
		} else {
			preRegion = null;
		}

		if (captionLine < lastLine) {
			int postOffset = document.getLineOffset(captionLine + 1);
			IRegion postRegion = new Region(postOffset, offset + length - postOffset);

			if (preRegion == null)
				return new IRegion[] { postRegion };

			return new IRegion[] { preRegion, postRegion };
		}

		if (preRegion != null)
			return new IRegion[] { preRegion };

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
	 */
	private int findFirstContent(final CharSequence content, int prefixEnd) {
		int lenght = content.length();
		for (int i = prefixEnd; i < lenght; i++) {
			if (Character.isUnicodeIdentifierPart(content.charAt(i)))
				return i;
		}
		return 0;
	}

	/*
	 * @see org.eclipse.jface.text.source.projection.IProjectionPosition#computeCaptionOffset(org.eclipse.jface.text.IDocument)
	 */
	public int computeCaptionOffset(IDocument document) {
//		return 0;
		DocumentCharacterIterator sequence = new DocumentCharacterIterator(document, offset, offset + length);
		return findFirstContent(sequence, 0);
	}
}
