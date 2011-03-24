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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;

/**
 * Handler class for toggling comment lines Operates as router which decides
 * which context comment to be applied (XML or PHP)
 * 
 * @author NirC, 2008
 */

public class ToggleCommentHandler extends CommentHandler implements IHandler {
	static class LinePosition {
		public int line;
		public int offset;
	}

	public ToggleCommentHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		ITextEditor textEditor = null;
		if (editor instanceof ITextEditor)
			textEditor = (ITextEditor) editor;
		else {
			Object o = editor.getAdapter(ITextEditor.class);
			if (o != null)
				textEditor = (ITextEditor) o;
		}
		if (textEditor != null) {
			IDocument document = textEditor.getDocumentProvider().getDocument(
					textEditor.getEditorInput());
			if (document != null) {
				// get current text selection
				ITextSelection textSelection = getCurrentSelection(textEditor);
				if (textSelection.isEmpty()) {
					return null;
				}
				if (document instanceof IStructuredDocument) {
					int selectionOffset = textSelection.getOffset();
					IStructuredDocument sDoc = (IStructuredDocument) document;

					// If there is alternating or more then one block in the
					// text selection, action is aborted !
					if (isMoreThanOneContextBlockSelected(sDoc, textSelection)) {
						org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler addBlockCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();// org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();
						return addBlockCommentHandlerWST.execute(event);
					}

					IStructuredDocumentRegion sdRegion = sDoc
							.getRegionAtCharacterOffset(selectionOffset);
					ITextRegion textRegion = sdRegion
							.getRegionAtCharacterOffset(selectionOffset);

					ITextRegionCollection container = sdRegion;

					if (textRegion instanceof ITextRegionContainer) {
						container = (ITextRegionContainer) textRegion;
						textRegion = container
								.getRegionAtCharacterOffset(selectionOffset);
					}
					boolean isJavaScriptRegion = false;

					if (textRegion instanceof ForeignRegion) {
						isJavaScriptRegion = (textRegion.getType() == DOMRegionContext.BLOCK_TEXT);
					}
					if (textRegion == null
							|| textRegion.getType() == PHPRegionContext.PHP_CONTENT
							|| isJavaScriptRegion) {
						processAction(textEditor, document, textSelection);
					} else {
						org.eclipse.wst.sse.ui.internal.handlers.ToggleLineCommentHandler toggleCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.ToggleLineCommentHandler();// org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();
						return toggleCommentHandlerWST.execute(event);
					}
				}
			}
		}
		return null;
	}

	void processAction(ITextEditor textEditor, IDocument document,
			ITextSelection textSelection) {
		// get text selection lines info
		int selectionStartLine = textSelection.getStartLine();
		int selectionEndLine = textSelection.getEndLine();
		try {
			int selectionEndLineOffset = document
					.getLineOffset(selectionEndLine);
			int selectionEndOffset = textSelection.getOffset()
					+ textSelection.getLength();

			// adjust selection end line
			if ((selectionEndLine > selectionStartLine)
					&& (selectionEndLineOffset == selectionEndOffset)) {
				selectionEndLine--;
			}

		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}

		// save the selection position since it will be changing
		Position selectionPosition = null;
		LinePosition start = null;
		LinePosition end = null;
		boolean updateStartOffset = false;
		try {
			start = getLinePosition(document, textSelection.getOffset());
			end = getLinePosition(document, textSelection.getOffset()
					+ textSelection.getLength());
			selectionPosition = new Position(textSelection.getOffset(),
					textSelection.getLength());
			document.addPosition(selectionPosition);

			// extra check if commenting from beginning of line
			int selectionStartLineOffset = document
					.getLineOffset(selectionStartLine);
			if ((textSelection.getLength() > 0)
					&& (selectionStartLineOffset == textSelection.getOffset())
					&& !isCommentLine(document, selectionStartLine)) {
				updateStartOffset = true;
			}
		} catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}

		processAction(document, selectionStartLine, selectionEndLine);

		updateCurrentSelection(textEditor, selectionPosition, start, end,
				document, updateStartOffset);
	}

	private LinePosition getLinePosition(IDocument document, int offset)
			throws BadLocationException {
		LinePosition result = new LinePosition();
		int line = document.getLineOfOffset(offset);
		result.line = line;
		result.offset = offset - document.getLineOffset(line);
		return result;
	}

	private int getOffset(IDocument document, LinePosition position)
			throws BadLocationException {
		int result;
		int lineLength = document.getLineLength(position.line);
		if (position.offset <= lineLength) {
			result = position.offset + document.getLineOffset(position.line);
		} else {
			result = lineLength + document.getLineOffset(position.line);
		}
		return result;
	}

	private void processAction(IDocument document, int selectionStartLine,
			int selectionEndLine) {
		IStructuredModel model = StructuredModelManager.getModelManager()
				.getExistingModelForEdit(document);
		if (model != null) {
			try {
				model.beginRecording(this, PHPUIMessages.ToggleComment_tooltip);
				model.aboutToChangeModel();

				// The eclipse way is as follows:
				// If and only if all lines are commented - we should uncomment
				// else - comment all

				// Check first whether all lines are commented:
				boolean allLinesCommented = true;
				for (int i = selectionStartLine; i <= selectionEndLine; i++) {
					try {
						if (document.getLineLength(i) > 0) {
							if (!isCommentLine(document, i)) {
								allLinesCommented = false;
								break;
							}
						}
					} catch (BadLocationException e) {
						Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
					}
				}

				// If all lines are commented, uncomment all lines:
				if (allLinesCommented) {
					uncommentMultiLine(document, selectionStartLine,
							selectionEndLine);
				} else { // comment all lines
					commentMultiLine(document, selectionStartLine,
							selectionEndLine);
				}
			} finally {
				model.changedModel();
				model.endRecording(this);
				model.releaseFromEdit();
			}
		}
	}

	private void updateCurrentSelection(ITextEditor textEditor,
			Position selectionPosition, LinePosition start, LinePosition end,
			IDocument document, boolean updateStartOffset) {
		// update the selection if text selection changed
		if (start != null && end != null) {
			ITextSelection selection = null;
			try {
				int offset = getOffset(document, start);
				selection = new TextSelection(document, offset, getOffset(
						document, end) - offset);
			} catch (BadLocationException e) {
				PHPUiPlugin.log(e);
			}
			ISelectionProvider provider = textEditor.getSelectionProvider();
			if (provider != null && selection != null) {
				provider.setSelection(selection);
			}
			document.removePosition(selectionPosition);
		}
	}
}
