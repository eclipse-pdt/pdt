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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;


public class MoveAction extends SelectionDispatchAction {

	private PHPStructuredEditor fEditor;
	private ReorgMoveAction fReorgMoveAction;

	/**
	 * Creates a new <code>MoveAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public MoveAction(IWorkbenchSite site) {
		super(site);
		setText(ActionMessages.MoveAction_text);
		fReorgMoveAction = new ReorgMoveAction(site);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.MOVE_ACTION);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param editor the compilation unit editor
	 */
	public MoveAction(PHPStructuredEditor editor) {
		super(editor.getEditorSite());
		fEditor = editor;
		setText(ActionMessages.MoveAction_text);
		fReorgMoveAction = new ReorgMoveAction(editor.getEditorSite());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.MOVE_ACTION);
	}

	/*
	 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		fReorgMoveAction.selectionChanged(event);
		setEnabled(computeEnableState());
	}

	public void run(IStructuredSelection selection) {
		if (fReorgMoveAction.isEnabled())
			fReorgMoveAction.run();

	}

	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;

		if (tryReorgMove(selection))
			return;

		MessageDialog.openInformation(getShell(), ActionMessages.MoveAction_Move, ActionMessages.MoveAction_select);
	}

	private boolean tryReorgMove(ITextSelection selection) {
		PHPCodeData element = SelectionConverter.getElementAtOffset(fEditor);
		if (element == null)
			return false;
		StructuredSelection mockStructuredSelection = new StructuredSelection(element);
		fReorgMoveAction.selectionChanged(mockStructuredSelection);
		if (!fReorgMoveAction.isEnabled())
			return false;

		fReorgMoveAction.run(mockStructuredSelection);
		return true;
	}

	/*
	 * @see SelectionDispatchAction#update(ISelection)
	 */
	public void update(ISelection selection) {
		fReorgMoveAction.update(selection);
		setEnabled(computeEnableState());
	}

	private boolean computeEnableState() {
		return fReorgMoveAction.isEnabled();
	}
}
