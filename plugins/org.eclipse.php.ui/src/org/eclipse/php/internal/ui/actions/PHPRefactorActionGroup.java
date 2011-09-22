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
import org.eclipse.ui.IViewPart;

public class PHPRefactorActionGroup extends RefactorActionGroup {

	public PHPRefactorActionGroup(IViewPart part) {
		super(part);

		IPHPActionDelegator renamePHPElement = PHPActionDelegatorRegistry
				.getActionDelegator("org.eclipse.php.ui.actions.RenameElement");

		if (renamePHPElement != null) {
			fRenameAction = new SelectionDispatchActionDelegate(part.getSite(),
					renamePHPElement);
			fRenameAction.setText("Rename...");
			fRenameAction
					.setActionDefinitionId(IScriptEditorActionDefinitionIds.RENAME_ELEMENT);
		}

		IPHPActionDelegator movePHPElement = PHPActionDelegatorRegistry
				.getActionDelegator("org.eclipse.php.ui.actions.Move");

		if (movePHPElement != null) {
			fMoveAction = new SelectionDispatchActionDelegate(part.getSite(),
					movePHPElement);
			fMoveAction.setText("Move...");
			fMoveAction
					.setActionDefinitionId("org.eclipse.php.ui.edit.text.move.element");
		}

	}

}
