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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.php.internal.ui.actions.ActionMessages;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.internal.ui.actions.PHPActionConstants;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;

/**
 * A PHPEditorActionBarContributor, which is a simple extention for
 * TextEditorActionContributor.
 */
public class PHPEditorActionBarContributor extends TextEditorActionContributor {

	private RetargetAction fRetargetShowPHPDoc;
	private List fPartListeners = new ArrayList();
	private RetargetTextEditorAction fShowPHPDoc;

	/** The global actions to be connected with PHP editor actions */
	private final static String[] PHPEDITOR_ACTIONS = { "org.eclipse.php.ui.actions.RemoveBlockComment", //$NON-NLS-1$
		"org.eclipse.php.ui.actions.ToggleCommentAction", //$NON-NLS-1$
		"org.eclipse.php.ui.actions.AddBlockComment", }; //$NON-NLS-1$

	// private ToggleCommentAction fToggleCommentAction;

	public PHPEditorActionBarContributor() {
		super();

		ResourceBundle b = ActionMessages.getBundleForConstructedKeys();

		fRetargetShowPHPDoc = new RetargetAction(PHPActionConstants.SHOW_PHP_DOC, PHPEditorMessages.ShowPHPDoc_label);
		fRetargetShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		markAsPartListener(fRetargetShowPHPDoc);

		fShowPHPDoc = new RetargetTextEditorAction(b, "ShowPHPDoc."); //$NON-NLS-1$
		fShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);

	}

	protected final void markAsPartListener(RetargetAction action) {
		fPartListeners.add(action);
	}

	/*
	 * @see IEditorActionBarContributor#init(IActionBars, IWorkbenchPage)
	 */
	public void init(IActionBars bars, IWorkbenchPage page) {
		Iterator e = fPartListeners.iterator();
		while (e.hasNext())
			page.addPartListener((RetargetAction) e.next());

		super.init(bars, page);

		bars.setGlobalActionHandler(PHPActionConstants.SHOW_PHP_DOC, fShowPHPDoc);
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		fShowPHPDoc.setAction(getAction(editor, "ShowPHPDoc"));

		IActionBars actionBars = getActionBars();
		if (actionBars == null)
			return;

		for (int i = 0; i < PHPEDITOR_ACTIONS.length; i++)
			actionBars.setGlobalActionHandler(PHPEDITOR_ACTIONS[i], getAction(editor, PHPEDITOR_ACTIONS[i]));
	}

	/*
	 * @see IEditorActionBarContributor#dispose()
	 */
	public void dispose() {

		Iterator e = fPartListeners.iterator();
		while (e.hasNext())
			getPage().removePartListener((RetargetAction) e.next());
		fPartListeners.clear();

		if (fRetargetShowPHPDoc != null) {
			fRetargetShowPHPDoc.dispose();
			fRetargetShowPHPDoc = null;
		}

		setActiveEditor(null);
		super.dispose();
	}
}
