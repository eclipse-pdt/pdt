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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Selection based action handler
 * 
 * @author Roy
 */
public abstract class SelectionHandler extends AbstractHandler {

	public SelectionHandler() {
		super();
	}

	/**
	 * Returns the current model element from the PHP editor
	 * 
	 * @param event
	 * @return
	 * @throws ModelException
	 */
	protected IModelElement getCurrentModelElement(ExecutionEvent event) throws ExecutionException {
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		PHPStructuredEditor textEditor = null;
		IModelElement modelElement = null;
		if (editorPart instanceof PHPStructuredEditor) {
			textEditor = (PHPStructuredEditor) editorPart;
			modelElement = textEditor.getModelElement();
		} else {
			Object o = editorPart.getAdapter(ITextEditor.class);
			if (o != null) {
				textEditor = (PHPStructuredEditor) o;
				modelElement = textEditor.getModelElement();
			}
		}

		if (textEditor != null && modelElement instanceof ISourceModule) {
			final ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
			final ISelection selection = selectionProvider.getSelection();
			if (selection instanceof TextSelection) {
				final TextSelection textSelection = (TextSelection) selection;
				try {
					return ((ISourceModule) modelElement).getElementAt(textSelection.getOffset());
				} catch (ModelException e) {
					throw new ExecutionException(Messages.SelectionHandler_0, e); // $NON-NLS-1
				}
			}
		}
		return null;
	}

}