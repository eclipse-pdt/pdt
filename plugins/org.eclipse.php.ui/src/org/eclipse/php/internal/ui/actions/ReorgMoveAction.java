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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.MoveProjectAction;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

public class ReorgMoveAction extends SelectionDispatchAction {
	
	StructuredSelection selectedResources;
	
	public ReorgMoveAction(IWorkbenchSite site) {
		super(site);
		setText(ActionMessages.ReorgMoveAction_3);
		setDescription(ActionMessages.ReorgMoveAction_4);
		update(getSelection());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.MOVE_ACTION);
	}

	public void selectionChanged(IStructuredSelection selection) {
		selectedResources=null;
		if (!selection.isEmpty()) {
			if (ActionUtils.containsOnlyProjects(selection.toList())) {
				setEnabled(createWorkbenchAction(selection).isEnabled());
				return;
			}
			List elements = selection.toList();
			IResource[] resources = ActionUtils.getResources(elements);
			Object[] phpElements = ActionUtils.getPHPElements(elements);
			
			if (elements.size() != resources.length + phpElements.length)
				setEnabled(false);
			else
			{
				boolean enabled=true;
				if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING)
				{
					for (int i = 0; i < phpElements.length; i++) {
						if (!(phpElements[i]instanceof PHPFileData))
							enabled=false;
					}
				}
				if (enabled)
				{
					List list=new ArrayList(Arrays.asList(resources));
					for (int i = 0; i < phpElements.length; i++) {
						if (phpElements[i]instanceof PHPFileData)
						{
							list.add(PHPModelUtil.getResource(phpElements[i]));
						}
						
					}
					if (list.size()==elements.size())						// only files selected
					{
						selectedResources= new StructuredSelection(list);
						enabled=createWorkbenchAction(selectedResources).isEnabled();
					}
				}
				setEnabled(enabled);
			}
		} else
			setEnabled(false);
	}

	public void selectionChanged(ITextSelection selection) {
		setEnabled(true);
	}

	private SelectionListenerAction createWorkbenchAction(IStructuredSelection selection) {
		
		List list = selection.toList();
		SelectionListenerAction action=null;
		if (list.size()==0 || list.get(0) instanceof IProject)
		{
			 action = new MoveProjectAction(getShell());
			action.selectionChanged(selection);
		}
		else if (selectedResources!=null)
		{
			 action = new MoveResourceAction(getShell());
				action.selectionChanged(selection);
			
		}
		return action;
	}

	public void run(IStructuredSelection selection) {
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			createWorkbenchAction(selection).run();
			return;
		}
		if (selectedResources!=null)
		{
			createWorkbenchAction(selectedResources).run();
			
		}
	}
}