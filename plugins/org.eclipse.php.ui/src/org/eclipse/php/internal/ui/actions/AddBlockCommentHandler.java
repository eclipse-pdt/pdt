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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * Handler class for adding comment tags to selected text Operates as router
 * which decides which context comment to be applied (XML or PHP)
 * 
 * @author NirC, 2008
 */

public class AddBlockCommentHandler extends CommentHandler implements IHandler {

	public AddBlockCommentHandler() {
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

				// If there is alternating or more then one block in the text
				// selection, action is aborted !
				if (isMoreThanOneContextBlockSelected(document, textSelection)) {
					// displayCommentActinosErrorDialog(editor);
					// return null;
					org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler addBlockCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();// org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();
					return addBlockCommentHandlerWST.execute(event);

				}

				if (textSelection.isEmpty()) {
					return null;
				}
				if (document instanceof IStructuredDocument) {
					int selectionOffset = textSelection.getOffset();
					IStructuredDocument sDoc = (IStructuredDocument) document;
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
					if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
						processAction(textEditor, document, textSelection);
					} else {
						org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler addBlockCommentHandlerWST = new org.eclipse.wst.sse.ui.internal.handlers.AddBlockCommentHandler();
						return addBlockCommentHandlerWST.execute(event);
					}
				}
			}
		}
		return null;
	}

	void processAction(ITextEditor textEditor, IDocument document,
			ITextSelection textSelection) {
		int openCommentOffset = textSelection.getOffset();
		int closeCommentOffset = openCommentOffset + textSelection.getLength();

		if (textSelection.getLength() == 0) {
			return;
		}

		IStructuredModel model = StructuredModelManager.getModelManager()
				.getExistingModelForEdit(document);
		if (model != null) {
			try {
				model.beginRecording(this,
						PHPUIMessages.AddBlockComment_tooltip);
				model.aboutToChangeModel();

				try {
					document.replace(closeCommentOffset, 0, CLOSE_COMMENT);
					document.replace(openCommentOffset, 0, OPEN_COMMENT);
				} catch (BadLocationException e) {
					Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
				} finally {
					model.changedModel();
					model.endRecording(this);
				}
			} finally {
				model.releaseFromEdit();

			}
		}
	}

}
