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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * Handler class for the add description action, 
 * It acts as a delegate to the AddDescription action 
 * @author Roy, 2008
 */

public class RemoveBlockCommentHandler extends CommentHandler implements IHandler {

	public RemoveBlockCommentHandler() {
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

				IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);

				if (model != null) {
					// If there is alternating or more then one block in the text selection, action is aborted !
					if (isMoreThenOneContextBlockSelected(model, textSelection)) {
						//						displayCommentActinosErrorDialog(editor);
						//						return null;
						org.eclipse.wst.xml.ui.internal.handlers.RemoveBlockCommentHandler removeBlockCommentHandlerWST = new org.eclipse.wst.xml.ui.internal.handlers.RemoveBlockCommentHandler();//org.eclipse.wst.xml.ui.internal.handlers.AddBlockCommentHandler();
						return removeBlockCommentHandlerWST.execute(event);
					}
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
						org.eclipse.wst.xml.ui.internal.handlers.RemoveBlockCommentHandler removeBlockCommentHandlerWST = new org.eclipse.wst.xml.ui.internal.handlers.RemoveBlockCommentHandler();
						return removeBlockCommentHandlerWST.execute(event);
					}
				}
			}
		}
		return null;
	}

	void processAction(ITextEditor textEditor, IDocument document, ITextSelection textSelection) {

		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);

		int selectionOffset = textSelection.getOffset();
		int selectionLength = textSelection.getLength();

		if (textSelection.getLength() == 0) {
			return;
		}

		model.beginRecording(this, PHPUIMessages.getString("RemoveBlockComment_tooltip"));
		model.aboutToChangeModel();

		try {
			removeOpenCloseComments(document, selectionOffset, selectionLength);
		} finally {
			model.changedModel();
			model.endRecording(this);
		}
	}

}
