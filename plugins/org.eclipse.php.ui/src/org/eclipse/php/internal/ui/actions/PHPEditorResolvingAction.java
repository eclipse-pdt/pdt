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

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.actions.ActionMessages;
import org.eclipse.dltk.internal.ui.actions.OpenActionUtil;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.text.ScriptWordFinder;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.wst.xml.core.internal.Logger;

public abstract class PHPEditorResolvingAction extends TextEditorAction
		implements IUpdate {

	public PHPEditorResolvingAction(ResourceBundle bundle, String prefix,
			ITextEditor editor) {
		super(bundle, prefix, editor);
		setEnabled(true);
	}

	public void run() {
		IModelElement[] modelElement = getSelectedElement();
		IModelElement selected = null;
		if (modelElement != null) {
			if (modelElement.length > 1) {
				IModelElement[] filteredElements = filterElements(modelElement);
				selected = OpenActionUtil.selectModelElement(filteredElements,
						getTextEditor().getSite().getShell(),
						ActionMessages.OpenAction_error_title,
						ActionMessages.OpenAction_select_element);
				if (selected == null)
					return;
			} else {
				selected = modelElement[0];
			}
			doRun(selected);
		}
	}

	/**
	 * run the action
	 */
	abstract protected void doRun(IModelElement modelElement);

	/**
	 * @return is action valid and can be run
	 */
	protected boolean isValid(IModelElement modelElement) {
		return modelElement != null;
	}

	public void update() {
		setEnabled(getTextEditor() != null/* && isValid(getSelectedElement()) */);
	}

	protected IModelElement[] getSelectedElement() {

		ITextEditor editor = getTextEditor();
		ITextSelection textSelection = (ITextSelection) editor
				.getSelectionProvider().getSelection();
		int offset = textSelection.getOffset();

		IModelElement input = EditorUtility.getEditorInputModelElement(editor,
				false);
		if (input == null) {
			return null;
		}

		try {
			IDocument document = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());
			IRegion wordRegion = ScriptWordFinder.findWord(document, offset);
			if (wordRegion == null)
				return null;

			if (wordRegion.getOffset() < 0 || wordRegion.getLength() < 0) {
				return null;
			}

			IModelElement[] elements = null;
			elements = ((ICodeAssist) input).codeSelect(wordRegion.getOffset(),
					wordRegion.getLength());
			if (elements != null && elements.length > 0) {
				return elements;
			}
		} catch (Exception e) {
			Logger.logException(e);
		}

		return null;
	}

	private IModelElement[] filterElements(IModelElement[] elements) {
		if (elements == null)
			return null;

		Map<IModelElement, IModelElement> uniqueElements = new HashMap<IModelElement, IModelElement>();
		for (int i = 0; i < elements.length; i++) {
			IModelElement element = elements[i];
			IModelElement module = element
					.getAncestor(IModelElement.SOURCE_MODULE);
			if (module != null) {
				if (!uniqueElements.containsKey(module)) {
					uniqueElements.put(module, element);
				}
			}
		}
		return uniqueElements.values().toArray(
				new IModelElement[uniqueElements.size()]);
	}
}