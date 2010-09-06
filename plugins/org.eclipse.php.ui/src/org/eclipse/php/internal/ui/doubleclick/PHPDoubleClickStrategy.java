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
						.getExistingModelForRead(
								structuredTextViewer.getDocument());
				if (structuredModel != null) {
					int caretPosition = textViewer.getSelectedRange().x;
					if (caretPosition > 0) {
						Node node = (Node) structuredModel
								.getIndexedRegion(caretPosition);
						if (node != null) {
							IStructuredDocumentRegion sdRegion = structuredModel
									.getStructuredDocument()
									.getRegionAtCharacterOffset(caretPosition);
							if (sdRegion != null) {
								ITextRegion tRegion = sdRegion
										.getRegionAtCharacterOffset(caretPosition);

								ITextRegionCollection container = sdRegion;
								if (tRegion instanceof ITextRegionContainer) {
									container = (ITextRegionContainer) tRegion;
									tRegion = container
											.getRegionAtCharacterOffset(caretPosition);
								}

								// We should always hit the PhpScriptRegion:
								if (tRegion != null
										&& tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
									IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
									tRegion = phpScriptRegion
											.getPhpToken(caretPosition
													- container
															.getStartOffset()
													- phpScriptRegion
															.getStart());

									// Handle double-click on PHPDoc tags:
									if (tRegion.getType() == PHPRegionTypes.PHP_VARIABLE
											|| tRegion.getType() == PHPRegionTypes.PHP_THIS
											|| PHPPartitionTypes
													.isPHPDocTagState(tRegion
															.getType())) {
										structuredTextViewer.setSelectedRange(
												container.getStartOffset()
														+ phpScriptRegion
																.getStart()
														+ tRegion.getStart(),
												tRegion.getTextLength());
										return; // Stop processing
									}

									// check if the user double clicked on a
									// variable in the PHPDoc block
									// fix bug#201079
									if (tRegion.getType() == PHPRegionTypes.PHPDOC_COMMENT
											|| tRegion.getType() == PHPRegionTypes.PHP_LINE_COMMENT
											|| tRegion.getType() == PHPRegionTypes.PHP_COMMENT) {
										resetVariableSelectionRangeInComments(
												textViewer,
												structuredTextViewer);
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

	protected IRegion findExtendedDoubleClickSelection(IDocument document,
			int offset) {
		IRegion match = fPairMatcher.match(document, offset);
		if (match != null && match.getLength() >= 2)
			return new Region(match.getOffset() + 1, match.getLength() - 2);
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
			StructuredTextViewer structuredTextViewer)
			throws BadLocationException {
		super.doubleClicked(textViewer);
		Point selectedRange = structuredTextViewer.getSelectedRange();
		int offset = selectedRange.x;

		if (offset > 0) {
			IDocument document = structuredTextViewer.getDocument();
			char previousChar = document.getChar(offset - 1);
			if (previousChar == '$') {
				structuredTextViewer.setSelectedRange(offset - 1,
						selectedRange.y + 1);
				// handle one letter variable name selection (the default just
				// selectes the $ sign)
			} else if (selectedRange.y == 1 && document.getChar(offset) == '$') {
				structuredTextViewer.setSelectedRange(offset,
						selectedRange.y + 1);
			}
		}
	}
}
