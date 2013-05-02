/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.internal.ui.actions.refactoring.RefactorActionGroup;
import org.eclipse.dltk.ui.actions.IScriptEditorActionDefinitionIds;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;

public class PHPRefactorActionGroup extends RefactorActionGroup {
	IViewPart part;

	public PHPRefactorActionGroup(IViewPart part) {
		super(part);
		this.part = part;
		IPHPActionDelegator renamePHPElement = PHPActionDelegatorRegistry
				.getActionDelegator("org.eclipse.php.ui.actions.RenameElement"); //$NON-NLS-1$

		if (renamePHPElement != null) {
			fRenameAction = new SelectionDispatchActionDelegate(part.getSite(),
					renamePHPElement);
			fRenameAction.setText(Messages.PHPRefactorActionGroup_1);
			fRenameAction
					.setActionDefinitionId(IScriptEditorActionDefinitionIds.RENAME_ELEMENT);
		}

		IPHPActionDelegator movePHPElement = PHPActionDelegatorRegistry
				.getActionDelegator("org.eclipse.php.ui.actions.Move"); //$NON-NLS-1$

		if (movePHPElement != null) {
			fMoveAction = new SelectionDispatchActionDelegate(part.getSite(),
					movePHPElement);
			fMoveAction.setText(Messages.PHPRefactorActionGroup_3);
			fMoveAction
					.setActionDefinitionId("org.eclipse.php.ui.edit.text.move.element"); //$NON-NLS-1$
		}

	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			if (!((IStructuredSelection) selection).isEmpty()) {
				super.fillContextMenu(menu);
			}
		} else {
			super.fillContextMenu(menu);
		}
	}

	public ISelectionProvider getSelectionProvider() {
		return part.getSite().getSelectionProvider();
	}

	/**
	 * Returns the selection provided by the site owning this action.
	 * 
	 * @return the site's selection
	 */
	public ISelection getSelection() {
		ISelectionProvider selectionProvider = getSelectionProvider();
		if (selectionProvider != null)
			return selectionProvider.getSelection();
		else
			return null;
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			if (!((IStructuredSelection) selection).isEmpty()) {
				super.fillActionBars(actionBars);
			}
		} else {
			super.fillActionBars(actionBars);
		}
	}
}
