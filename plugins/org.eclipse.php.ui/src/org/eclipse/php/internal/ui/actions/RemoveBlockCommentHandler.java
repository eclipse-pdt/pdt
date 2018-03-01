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
package org.eclipse.php.internal.ui.actions;

import java.util.Stack;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * Handler class for removing comment tags from selected text Operates as router
 * which decides which context comment to be applied (XML or PHP)
 * 
 * @author NirC, 2008
 */

public class RemoveBlockCommentHandler extends CommentHandler implements IHandler {

	public RemoveBlockCommentHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		ITextEditor textEditor = null;
		if (editor instanceof ITextEditor) {
			textEditor = (ITextEditor) editor;
		} else if (editor != null) {
			Object o = editor.getAdapter(ITextEditor.class);
			if (o != null) {
				textEditor = (ITextEditor) o;
			}
		}
		if (textEditor != null) {
			IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());

			if (document != null) {
				// get current text selection
				ITextSelection textSelection = getCurrentSelection(textEditor);

				// If there is alternating or more then one block in the text
				// selection, action is aborted !
				if (isMoreThanOneContextBlockSelected(document, textSelection)) {
					// displayCommentActinosErrorDialog(editor);
					// return null;
					org.eclipse.wst.sse.ui.internal.handlers.RemoveBlockCommentHandler removeBlockCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.RemoveBlockCommentHandler();// org.eclipse.wst.xml.ui.internal.handlers.AddBlockCommentHandler();
					return removeBlockCommentHandlerWST.execute(event);
				}

				if (textSelection.isEmpty()) {
					return null;
				}
				if (document instanceof IStructuredDocument) {
					int selectionOffset = textSelection.getOffset();
					IStructuredDocument sDoc = (IStructuredDocument) document;
					IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(selectionOffset);
					ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(selectionOffset);

					ITextRegionCollection container = sdRegion;

					if (textRegion instanceof ITextRegionContainer) {
						container = (ITextRegionContainer) textRegion;
						textRegion = container.getRegionAtCharacterOffset(selectionOffset);
					}
					if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
						processAction(textEditor, document, textSelection);
					} else {
						org.eclipse.wst.sse.ui.internal.handlers.RemoveBlockCommentHandler removeBlockCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.RemoveBlockCommentHandler();
						return removeBlockCommentHandlerWST.execute(event);
					}
				}
			}
		}
		return null;
	}

	@Override
	void processAction(ITextEditor textEditor, IDocument document, ITextSelection textSelection) {

		int selectionOffset = textSelection.getOffset();
		int selectionLength = textSelection.getLength();

		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);
		if (model != null) {
			try {
				model.beginRecording(this, PHPUIMessages.RemoveBlockComment_tooltip);
				model.aboutToChangeModel();

				if (document instanceof IStructuredDocument) {
					IStructuredDocument sDoc = (IStructuredDocument) document;
					IStructuredDocumentRegion regionAtCharacterOffset = sDoc
							.getRegionAtCharacterOffset(selectionOffset);
					int docRegionOffset = regionAtCharacterOffset.getStartOffset();
					ITextRegion textRegion = regionAtCharacterOffset.getRegionAtCharacterOffset(selectionOffset);

					Stack<TextLocation> phpCommentLocationStack = new Stack<>(); // stack
																								// of
																								// ITextRegion
																								// including
																								// only
																								// Comments'
																								// Start
																								// and
																								// End
																								// tokens
																								// locations

					try {
						int textRegionOffset = textRegion.getStart();
						int normalizedOffset = textRegionOffset + docRegionOffset;
						ITextRegion[] phpTokens = ((PHPScriptRegion) textRegion)
								.getPHPTokens(selectionOffset - normalizedOffset, selectionLength);

						int lastOffsetParsed = -1;

						for (ITextRegion token : phpTokens) {
							if (lastOffsetParsed >= token.getEnd()) {
								// to save
																	// some
																	// redundant
																	// computation
																	// cycles...
								continue;
							}

							if (PHPPartitionTypes.isPHPMultiLineCommentState(token.getType())) {
								// if we are somewhere within a comment
								// (start/end/body), this will find the start
								// and end tokens
								ITextRegion startToken = findCommentStartToken(token, (PHPScriptRegion) textRegion);
								TextLocation commentOffsets = new TextLocation(startToken.getStart() + normalizedOffset,
										startToken.getEnd() + normalizedOffset);
								boolean result = validateAndPushLocation(phpCommentLocationStack, commentOffsets);
								assert (result);
								lastOffsetParsed = commentOffsets.endOffset - normalizedOffset;

								ITextRegion endToken = findCommentEndToken(token, (PHPScriptRegion) textRegion);
								commentOffsets = new TextLocation(endToken.getStart() + normalizedOffset,
										endToken.getEnd() + normalizedOffset);
								result = validateAndPushLocation(phpCommentLocationStack, commentOffsets);
								assert (result);
								lastOffsetParsed = commentOffsets.endOffset - normalizedOffset;

							}
						}
						for (int i = phpCommentLocationStack.size(); i > 0; i--) {
							TextLocation location = phpCommentLocationStack.pop();
							document.replace(location.startOffset, location.endOffset - location.startOffset, ""); //$NON-NLS-1$
						}

					} catch (BadLocationException e) {
						Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
					}

				}

			} finally {
				model.changedModel();
				model.endRecording(this);
				model.releaseFromEdit();
			}
		}
	}

	private boolean validateAndPushLocation(Stack<TextLocation> phpCommentLocationStack, TextLocation commentOffsets) {
		if (commentOffsets != null && !phpCommentLocationStack.contains(commentOffsets)) {
			phpCommentLocationStack.push(commentOffsets);
			return true;
		}
		return false;
	}

	private ITextRegion findCommentStartToken(ITextRegion token, PHPScriptRegion phpScriptRegion)
			throws BadLocationException {
		assert PHPPartitionTypes.isPHPMultiLineCommentState(token.getType());

		if (PHPPartitionTypes.isPHPMultiLineCommentStartRegion(token.getType())) {
			return token;
		}
		ITextRegion previousToken = phpScriptRegion.getPHPToken(token.getStart() - 1);
		return findCommentStartToken(previousToken, phpScriptRegion);

	}

	private ITextRegion findCommentEndToken(ITextRegion token, PHPScriptRegion phpScriptRegion)
			throws BadLocationException {
		assert PHPPartitionTypes.isPHPMultiLineCommentState(token.getType());

		if (PHPPartitionTypes.isPHPMultiLineCommentEndRegion(token.getType())) {
			return token;
		}
		ITextRegion nextToken = phpScriptRegion.getPHPToken(token.getEnd());
		return findCommentEndToken(nextToken, phpScriptRegion);

	}

	public class TextLocation {
		protected int startOffset;
		protected int endOffset;

		public TextLocation(int start, int end) {
			startOffset = start;
			endOffset = end;
		}

		public boolean equals(TextLocation arg0) {
			return (startOffset == arg0.startOffset && endOffset == arg0.endOffset);

		}

	}

}
