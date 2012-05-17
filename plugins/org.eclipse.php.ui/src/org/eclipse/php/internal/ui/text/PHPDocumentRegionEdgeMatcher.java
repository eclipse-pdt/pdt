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

package org.eclipse.php.internal.ui.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.ui.editor.PHPPairMatcher;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.internal.text.DocumentRegionEdgeMatcher;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;

public class PHPDocumentRegionEdgeMatcher extends DocumentRegionEdgeMatcher {
	protected final static char[] BRACKETS = { '{', '}', '(', ')', '[', ']' };

	/**
	 * @param validContexts
	 * @param nextMatcher
	 */
	public PHPDocumentRegionEdgeMatcher() {
		super(new String[] { DOMRegionContext.XML_TAG_NAME,
				DOMRegionContext.XML_COMMENT_TEXT,
				DOMRegionContext.XML_CDATA_TEXT, DOMRegionContext.XML_PI_OPEN,
				DOMRegionContext.XML_PI_CONTENT }, new PHPPairMatcher(BRACKETS));
	}

	public IRegion match(IDocument document, int offset) {
		if (offset < 0 || offset > document.getLength())
			return null;

		IRegion match = null;
		if (!fRegionTypes.isEmpty() && document instanceof IStructuredDocument) {
			IStructuredDocumentRegion docRegion = ((IStructuredDocument) document)
					.getRegionAtCharacterOffset(offset);
			if (docRegion != null) {
				// look at the previous document region first since its end ==
				// this one's start
				if (docRegion.getPrevious() != null
						&& docRegion.getPrevious().getEndOffset() == offset
						&& fRegionTypes.contains(docRegion.getPrevious()
								.getType())) {
					fAnchor = ICharacterPairMatcher.RIGHT;
					match = new Region(
							docRegion.getPrevious().getStartOffset(), 1);
				}
				// check for offset in the last text region for a match to
				// document region start offset
				else if (fRegionTypes.contains(docRegion.getType())
						&& docRegion.getStartOffset(docRegion.getLastRegion()) <= offset
						&& offset <= docRegion.getEndOffset(docRegion
								.getLastRegion())) {
					fAnchor = ICharacterPairMatcher.RIGHT;
					match = new Region(docRegion.getStartOffset(), 1);
				}
				// check for offset in the first text region for a match to
				// document region end offset
				else if (fRegionTypes.contains(docRegion.getType())) {
					if (docRegion.getStartOffset(docRegion.getFirstRegion()) <= offset
							&& offset <= docRegion.getEndOffset(docRegion
									.getFirstRegion())) {
						fAnchor = ICharacterPairMatcher.LEFT;
						match = new Region(docRegion.getEndOffset() - 1, 1);
					}
				}

				if (match == null) {
					/* Now check the text region */
					ITextRegion currentTextRegion = docRegion
							.getRegionAtCharacterOffset(offset);

					// in case of container we have the extract the
					// PhpScriptRegion
					if (currentTextRegion instanceof ITextRegionContainer) {
						ITextRegionContainer container = (ITextRegionContainer) currentTextRegion;
						currentTextRegion = container
								.getRegionAtCharacterOffset(offset);
					}
					if (currentTextRegion instanceof IPhpScriptRegion) {
						IPhpScriptRegion scriptRegion = (IPhpScriptRegion) currentTextRegion;
						try {
							currentTextRegion = scriptRegion.getPhpToken(offset
									- docRegion.getStartOffset(scriptRegion));

							if (currentTextRegion != null
									&& currentTextRegion.getTextLength() > 1) {
								int offsetAtNearEdge = offset;
								if (offset == docRegion
										.getTextEndOffset(currentTextRegion)) {
									offsetAtNearEdge = offset - 1;
								} else if (offset == docRegion
										.getStartOffset(currentTextRegion) + 1) {
									offsetAtNearEdge = offset - 1;
								}

								if (offsetAtNearEdge == docRegion
										.getStartOffset(currentTextRegion)) {
									int end = docRegion
											.getTextEndOffset(currentTextRegion);
									try {
										if (document.getChar(offsetAtNearEdge) == document
												.getChar(end - 1)) {
											fAnchor = ICharacterPairMatcher.LEFT;
											match = new Region(end - 1, 1);
										}
									} catch (BadLocationException e) {
										// nothing, not important enough
									}
								} else if (offsetAtNearEdge == docRegion
										.getTextEndOffset(currentTextRegion) - 1) {
									int start = docRegion
											.getStartOffset(currentTextRegion);
									if (document.getChar(offsetAtNearEdge) == document
											.getChar(start)) {
										fAnchor = ICharacterPairMatcher.RIGHT;
										match = new Region(start, 1);
									}
								}
							}
						} catch (BadLocationException e) {
							// nothing, not important enough
						}
					}
				}
			}
		}
		try {
			// blank char
			if (match != null && match.getLength() == 1) {
				char currChar = document.getChar(match.getOffset());
				// System.out.println((int) currChar);
				if (currChar == 32 || currChar == 9) {
					match = null;
				}
			}
		} catch (BadLocationException e) {
		}
		if (match == null && fNextMatcher != null) {
			fAnchor = -1;
			match = fNextMatcher.match(document, offset);
		}
		return match;
	}
}
