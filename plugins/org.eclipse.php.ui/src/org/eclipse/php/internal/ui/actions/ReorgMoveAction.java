/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.MoveFilesAndFoldersOperation;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

public class ReorgMoveAction extends Action implements IPHPMoveActionDelegator {

	private IStructuredSelection selectedResources; 
	private Shell fShell;
	private IContainer fTarget;
	private IResource[] fSources;
	

	public ReorgMoveAction() {
		init();
	}	
	
	public void init() {
		setText(PHPUIMessages.getString("ReorgMoveAction_3"));
		setDescription(PHPUIMessages.getString("ReorgMoveAction_4"));		
		fShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
	}

	public void setSelection(IStructuredSelection selection) {
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
			action = new PHPMoveProjectAction(fShell);
			action.selectionChanged(selection);
		} else if (selectedResources != null) {
			action = new MoveResourceAction(fShell);
			action.selectionChanged(selection);

		}
		return action;
	}

	public void runDrop(IStructuredSelection selection) {			
		MoveFilesAndFoldersOperation operation = new MoveFilesAndFoldersOperation(fShell);
    	operation.copyResources(fSources, fTarget);
	}


	public void setSources(IResource[] resources) {		
		fSources = resources;
	}

	public void setTarget(IContainer target) {
		fTarget = target;
	}
	
	

}