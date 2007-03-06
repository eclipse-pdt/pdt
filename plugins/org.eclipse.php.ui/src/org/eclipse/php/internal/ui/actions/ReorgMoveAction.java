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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.MoveProjectAction;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

public class ReorgMoveAction extends Action implements IPHPActionDelegator {

	private StructuredSelection selectedResources; 
	private Shell fShell;

	public ReorgMoveAction() {
		init();
	}	
	
	public void init() {
		setText(PHPUIMessages.ReorgMoveAction_3);
		setDescription(PHPUIMessages.ReorgMoveAction_4);		
		fShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
	}

	public void setSelection(StructuredSelection selection) {
		selectedResources = selection;
		
	}
	
	public void run(IStructuredSelection selection) {
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			createWorkbenchAction(selection).run();
			return;
		}
		if (selectedResources != null) {
			createWorkbenchAction(selectedResources).run();

		}
	}
	
	private SelectionListenerAction createWorkbenchAction(IStructuredSelection selection) {

		List list = selection.toList();
		SelectionListenerAction action = null;
		if (list.size() == 0 || list.get(0) instanceof IProject) {
			action = new MoveProjectAction(fShell);
			action.selectionChanged(selection);
		} else if (selectedResources != null) {
			action = new MoveResourceAction(fShell);
			action.selectionChanged(selection);

		}
		return action;
	}

}