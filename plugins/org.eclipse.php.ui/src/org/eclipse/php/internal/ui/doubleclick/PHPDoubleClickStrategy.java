/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.doubleclick;

import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPPairMatcher;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.w3c.dom.Node;

/**
 * This class was added in order to solve the problem of selecting variable name
 * in the editor. The default behaviour when double-clicking a variable, for
 * instance $myVar, is to make only myVar selected - without the dollar sign.
 * This class also fixes selection of PHPdoc tags. This class fixes this
 * behaviour.
 * 
 * @author guy.g
 * 
 */
public class PHPDoubleClickStrategy extends DefaultTextDoubleClickStrategy {

	protected final static char[] BRACKETS = { '{', '}', '(', ')', '[', ']' };

	protected PHPPairMatcher fPairMatcher = new PHPPairMatcher(BRACKETS);

	@Override
	public void doubleClicked(ITextViewer textViewer) {
		if (textViewer instanceof StructuredTextViewer) {
			StructuredTextViewer structuredTextViewer = (StructuredTextViewer) textViewer;
			IStructuredModel structuredModel = null;
			try {
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(structuredTextViewer.getDocument());
				if (structuredModel != null) {
					int caretPosition = textViewer.getSelectedRange().x;
					if (caretPosition > 0) {
						Node node = (Node) structuredModel.getIndexedRegion(caretPosition);
						if (node != null) {
							IStructuredDocumentRegion sdRegion = structuredModel.getStructuredDocument()
									.getRegionAtCharacterOffset(caretPosition);
							if (sdRegion != null) {
								ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(caretPosition);

								ITextRegionCollection container = sdRegion;
								if (tRegion instanceof ITextRegionContainer) {
									container = (ITextRegionContainer) tRegion;
									tRegion = container.getRegionAtCharacterOffset(caretPosition);
								}

								// We should always hit the PhpScriptRegion:
								if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
									IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
									int offset = caretPosition - container.getStartOffset()
											- phpScriptRegion.getStart();
									tRegion = phpScriptRegion.getPhpToken(offset);

									// Handle double-click on PHPDoc tags:
									if (tRegion.getType() == PHPRegionTypes.PHP_VARIABLE
											|| tRegion.getType() == PHPRegionTypes.PHP_THIS
											|| PHPPartitionTypes.isPHPDocTagState(tRegion.getType())) {
										int regionStart = container.getStartOffset() + phpScriptRegion.getStart();
										if (caretPosition == regionStart + tRegion.getTextEnd()) {
											final IDocument document = textViewer.getDocument();
											IRegion region = findWord(document, caretPosition - 1);
											if (region != null) {
												textViewer.setSelectedRange(region.getOffset(), region.getLength());
											}
										} else {
											textViewer.setSelectedRange(regionStart + tRegion.getStart(),
													tRegion.getTextLength());
										}
										return; // Stop processing
									}

									// Check if the user double clicked on a
									// variable in a PHP comment (fix
									// bug#201079).
									// https://bugs.eclipse.org/bugs/show_bug.cgi?id=493467
									// TODO: we don't have a method like
									// PHPPartitionTypes.isPHPLineCommentStartRegion(type)
									// to determine if we're at the beginning
									// of a single-line comment so for now check
									// region type at offset - 1 to be sure that
									// previous character is still in comment.
									if (PHPPartitionTypes.isPHPCommentState(tRegion.getType()) && offset > 0
											&& PHPPartitionTypes.isPHPCommentState(
													phpScriptRegion.getPhpToken(offset - 1).getType())) {
										resetVariableSelectionRangeInComments(textViewer, structuredTextViewer);
										return;
									}

								}
							}
						}
					}
				}
			} catch (BadLocationException e) {
				PHPUiPlugin.log(e);
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}

		// We reach here only if there was an error or one of conditions hasn't
		// met our requirements:
		super.doubleClicked(textViewer);
	}

	@Override
	protected IRegion findExtendedDoubleClickSelection(IDocument document, int offset) {
		IRegion match = fPairMatcher.match(document, offset);
		if (match != null && match.getLength() >= 2) {
			return new Region(match.getOffset() + 1, match.getLength() - 2);
		}
		return findWord(document, offset);
	}

	/**
	 * When the user double click on a variable in a comment, include the
	 * preceding $ sign in the selection (bug#201079)
	 * 
	 * @param textViewer
	 * @param structuredTextViewer
	 * @throws BadLocationException
	 */
	private void resetVariableSelectionRangeInComments(ITextViewer textViewer,
			StructuredTextViewer structuredTextViewer) throws BadLocationException {
		super.doubleClicked(textViewer);
		Point selectedRange = structuredTextViewer.getSelectedRange();
		int offset = selectedRange.x;

		if (offset > 0) {
			IDocument document = structuredTextViewer.getDocument();
			char previousChar = document.getChar(offset - 1);
			if (previousChar == '$') {
				structuredTextViewer.setSelectedRange(offset - 1, selectedRange.y + 1);
				// handle one letter variable name selection (the default just
				// selectes the $ sign)
			} else if (selectedRange.y == 1 && document.getChar(offset) == '$') {
				structuredTextViewer.setSelectedRange(offset, selectedRange.y + 1);
			}
		}
	}
}
