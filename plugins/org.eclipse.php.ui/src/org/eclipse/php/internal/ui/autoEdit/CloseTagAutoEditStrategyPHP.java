/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension3;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * Auto Strategy for <?php ?> insertion code
 * when the user enters "<?" in non-PHP region he gets back "php ?>"   
 * @author Roy, 2007
 */
public class CloseTagAutoEditStrategyPHP implements IAutoEditStrategy {

	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		// if the user wants to automatic close php tags
		if (TypingPreferences.addPhpCloseTag) {

			Object textEditor = getActiveTextEditor();
			if (!(textEditor instanceof ITextEditorExtension3 && ((ITextEditorExtension3) textEditor).getInsertMode() == ITextEditorExtension3.SMART_INSERT))
				return;

			IStructuredModel model = null;
			try {
				model = StructuredModelManager.getModelManager().getExistingModelForRead(document);

				if (model != null) {
					if (command.text != null) {
						if (command.text.equals("?")) { //$NON-NLS-1$
							// scriptlet - add end ?>
							IDOMNode node = (IDOMNode) model.getIndexedRegion(command.offset - 1);
							if (node != null && prefixedWith(document, command.offset, "<") && !closeTagAppears(node.getSource(), command.offset)) { //$NON-NLS-1$ //$NON-NLS-2$
								command.text += "php ?>"; //$NON-NLS-1$
								command.shiftsCaret = false;
								command.caretOffset = command.offset + 5;
								command.doit = false;
							}
						}
					}
				}
			} finally {
				if (model != null)
					model.releaseFromRead();
			}
		}
	}

	private final boolean closeTagAppears(String source, int startFrom) {
		return source.indexOf("?>", startFrom) != -1; //$NON-NLS-1$
	}

	/**
	 * Return the active text editor if possible, otherwise the active editor
	 * part.
	 * 
	 * @return
	 */
	private Object getActiveTextEditor() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IEditorPart editor = page.getActiveEditor();
				if (editor != null) {
					if (editor instanceof ITextEditor)
						return editor;
					ITextEditor textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
					if (textEditor != null)
						return textEditor;
					return editor;
				}
			}
		}
		return null;
	}

	private boolean prefixedWith(IDocument document, int offset, String string) {

		try {
			return document.getLength() >= string.length() && document.get(offset - string.length(), string.length()).equals(string);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;
		}
	}

}
