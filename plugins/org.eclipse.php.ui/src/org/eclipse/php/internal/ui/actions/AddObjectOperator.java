/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakula and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakula - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.contentassist.AutoActivationTrigger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * Dynamic object operator
 * 
 * @author Dawid Pakula
 */
public class AddObjectOperator extends AbstractHandler implements IHandler {

	public AddObjectOperator() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		ITextEditor textEditor = null;
		if (editor instanceof ITextEditor) {
			textEditor = (ITextEditor) editor;
		} else {
			Object o = editor.getAdapter(ITextEditor.class);
			if (o != null) {
				textEditor = (ITextEditor) o;
			}
		}
		if (textEditor != null) {
			IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());

			if (document != null) {
				if (textEditor.getSelectionProvider() == null
						|| !(textEditor.getSelectionProvider().getSelection() instanceof ITextSelection)) {
					return null;
				}
				ITextSelection textSelection = (ITextSelection) textEditor.getSelectionProvider().getSelection();

				if (textSelection.isEmpty()) {
					return null;
				}
				if (document instanceof IStructuredDocument) {
					int selectionOffset = textSelection.getOffset() - 1;
					IStructuredDocument sDoc = (IStructuredDocument) document;
					IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(selectionOffset);
					ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(selectionOffset);

					ITextRegionCollection container = sdRegion;

					if (textRegion instanceof ITextRegionContainer) {
						container = (ITextRegionContainer) textRegion;
						textRegion = container.getRegionAtCharacterOffset(selectionOffset);
					}
					if (textRegion == null) {
						return null;
					}
					if (sdRegion instanceof IStructuredDocumentRegion
							&& textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
						run(textEditor, document, textSelection, (IStructuredDocumentRegion) sdRegion);
					}
				}
			}
		}
		return null;
	}

	private void run(ITextEditor textEditor, IDocument document, ITextSelection textSelection,
			IStructuredDocumentRegion textRegion) {

		if (textSelection.getLength() != 0) {
			return;
		}

		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForEdit(document);
		if (model != null) {
			try {
				AutoActivationTrigger.register(document);

				TextSequence statement = PHPTextSequenceUtilities.getStatement(textSelection.getOffset(), textRegion,
						true);
				String insert = PHPTextSequenceUtilities.suggestObjectOperator(statement);
				if (insert == null) {
					return;
				}
				model.beginRecording(this, PHPUIMessages.AddObjectOperator_tooltip);
				model.aboutToChangeModel();
				try {
					document.replace(textSelection.getOffset(), 0, insert);
					textEditor.selectAndReveal(textSelection.getOffset() + insert.length(), 0);
				} catch (BadLocationException e) {
					Logger.logException(e);
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
