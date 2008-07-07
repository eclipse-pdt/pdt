/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.xml.ui.internal.Logger;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;

/**
 * Handler class for the add description action, 
 * It acts as a delegate to the AddDescription action 
 * @author Roy, 2008
 */

public class ToggleCommentHandler extends CommentHandler implements IHandler {
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
			IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
			if (document != null) {
				// get current text selection
				ITextSelection textSelection = getCurrentSelection(textEditor);
				if (textSelection.isEmpty()) {
					return null;
				}
				if (document instanceof IStructuredDocument) {
					int selectionOffset = textSelection.getOffset();
					IStructuredDocument sDoc = (IStructuredDocument) document;
					IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);
					
					if (model != null) {
						// If there is alternating or more then one block in the text selection, action is aborted !
						if(isMoreThenOneContextBlockSelected(model, textSelection)){
							displayCommentActinosErrorDialog(editor);
							return null;
						}
					}
					
					IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(selectionOffset);
					ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(selectionOffset);

					ITextRegionCollection container = sdRegion;

					if (textRegion instanceof ITextRegionContainer) {
						container = (ITextRegionContainer) textRegion;
						textRegion = container.getRegionAtCharacterOffset(selectionOffset);
					}
					
					
					if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
						processAction(textEditor, document, textSelection);
					}else{
						org.eclipse.wst.xml.ui.internal.handlers.ToggleCommentHandler toggleCommentHandlerWST = new org.eclipse.wst.xml.ui.internal.handlers.ToggleCommentHandler() ;//org.eclipse.wst.xml.ui.internal.handlers.AddBlockCommentHandler();
						return toggleCommentHandlerWST.execute(event);
					}
				}
			}
		}
		return null;
	}

	void processAction(ITextEditor textEditor, IDocument document, ITextSelection textSelection) {
		// get text selection lines info
		int selectionStartLine = textSelection.getStartLine();
		int selectionEndLine = textSelection.getEndLine();
		try {
			int selectionEndLineOffset = document.getLineOffset(selectionEndLine);
			int selectionEndOffset = textSelection.getOffset() + textSelection.getLength();

			// adjust selection end line
			if ((selectionEndLine > selectionStartLine) && (selectionEndLineOffset == selectionEndOffset)) {
				selectionEndLine--;
			}

		}
		catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}

		// save the selection position since it will be changing
		Position selectionPosition = null;
		boolean updateStartOffset = false;
		try {
			selectionPosition = new Position(textSelection.getOffset(), textSelection.getLength());
			document.addPosition(selectionPosition);

			// extra check if commenting from beginning of line
			int selectionStartLineOffset = document.getLineOffset(selectionStartLine);
			if ((textSelection.getLength() > 0) && (selectionStartLineOffset == textSelection.getOffset()) && !isCommentLine(document, selectionStartLine)) {
				updateStartOffset = true;
			}
		}
		catch (BadLocationException e) {
			Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
		}

		processAction(document, selectionStartLine, selectionEndLine);

		updateCurrentSelection(textEditor, selectionPosition, document, updateStartOffset);
	}

	private void processAction(IDocument document, int selectionStartLine, int selectionEndLine) {
		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);
		if (model != null) {
			try {
				model.beginRecording(this, XMLUIMessages.ToggleComment_tooltip);
				model.aboutToChangeModel();

				for (int i = selectionStartLine; i <= selectionEndLine; i++) {
					try {
						if (document.getLineLength(i) > 0) {
							if (isCommentLine(document, i)) {
								int lineOffset = document.getLineOffset(i);
								IRegion region = document.getLineInformation(i);
								String string = document.get(region.getOffset(), region.getLength());
								int openCommentOffset = lineOffset + string.indexOf(SINGLE_LINE_COMMENT);
								uncommentSingleLine(document, openCommentOffset);
							}
							else {
								int openCommentOffset = document.getLineOffset(i);
								commentSingleLine(document, openCommentOffset);
							}
						}
					}
					catch (BadLocationException e) {
						Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
					}
				}
			}
			finally {
				model.changedModel();
				model.endRecording(this);
				model.releaseFromEdit();
			}
		}
	}


	private void updateCurrentSelection(ITextEditor textEditor, Position selectionPosition, IDocument document, boolean updateStartOffset) {
		// update the selection if text selection changed
		if (selectionPosition != null) {
			ITextSelection selection = null;
			if (updateStartOffset) {
				selection = new TextSelection(document, selectionPosition.getOffset() - SINGLE_LINE_COMMENT.length(), selectionPosition.getLength() + OPEN_COMMENT.length());
			}
			else {
				selection = new TextSelection(document, selectionPosition.getOffset(), selectionPosition.getLength());
			}
			ISelectionProvider provider = textEditor.getSelectionProvider();
			if (provider != null) {
				provider.setSelection(selection);
			}
			document.removePosition(selectionPosition);
		}
	}
}
