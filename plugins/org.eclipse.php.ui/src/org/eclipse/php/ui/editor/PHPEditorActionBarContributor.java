/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * A PHPEditorActionBarContributor, which is a simple extention for
 * TextEditorActionContributor.
 */
public class PHPEditorActionBarContributor extends TextEditorActionContributor {

	/** The global actions to be connected with PHP editor actions */
	private final static String[] PHPEDITOR_ACTIONS = {
			"org.eclipse.php.ui.actions.RemoveBlockComment", //$NON-NLS-1$
			"org.eclipse.php.ui.actions.ToggleCommentAction", //$NON-NLS-1$
			"org.eclipse.php.ui.actions.AddBlockComment", }; //$NON-NLS-1$

	// private ToggleCommentAction fToggleCommentAction;

	public PHPEditorActionBarContributor() {
		super();
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		IActionBars actionBars = getActionBars();
		if (actionBars == null)
			return;

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		for (int i = 0; i < PHPEDITOR_ACTIONS.length; i++)
			actionBars.setGlobalActionHandler(PHPEDITOR_ACTIONS[i], getAction(
					editor, PHPEDITOR_ACTIONS[i]));
	}

	/*
	 * @see IEditorActionBarContributor#dispose()
	 */
	public void dispose() {
		setActiveEditor(null);
		super.dispose();
	}
}
