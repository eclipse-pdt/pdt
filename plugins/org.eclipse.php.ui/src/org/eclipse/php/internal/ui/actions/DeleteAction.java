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
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.DeleteResourceAction;

public class DeleteAction extends SelectionDispatchAction {

	public DeleteAction(IWorkbenchSite site) {
		super(site);
		setText(ActionMessages.DeleteAction_text);
		setDescription(ActionMessages.DeleteAction_desc);
		ISharedImages workbenchImages = PHPUiPlugin.getDefault().getWorkbench().getSharedImages();
		setDisabledImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setHoverImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

		update(getSelection());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.DELETE_ACTION);
	}

	/*
	 * @see SelectionDispatchAction#selectionChanged(IStructuredSelection)
	 */
	public void selectionChanged(IStructuredSelection selection) {
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			setEnabled(createWorkbenchAction(selection).isEnabled());
			return;
		}
		Object []elements=selection.toArray();
		boolean enabled=ActionUtils.isDeleteAvailable(elements);
//TODO: disable until model supports delete 
		if (enabled)
			enabled=!ActionUtils.arePHPElements(elements);
		setEnabled(enabled);
	}

	private IAction createWorkbenchAction(IStructuredSelection selection) {
		DeleteResourceAction action = new DeleteResourceAction(getShell());
		action.selectionChanged(selection);
		return action;
	}

	public void run(IStructuredSelection selection) {
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			createWorkbenchAction(selection).run();
			return;
		}

		ArrayList list = new ArrayList();
		for (Iterator iter = selection.toList().iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof PHPFileData) {
				PHPFileData phpFile = (PHPFileData) element;
				list.add(PHPModelUtil.getResource(phpFile));
			} else if (element instanceof IFile) {
				list.add(element);
			}
		}
		if (list.size() == selection.size()) { // add items are files
			StructuredSelection sel = new StructuredSelection(list);
			createWorkbenchAction(sel).run();
			return;
		}
//		TODO:  until model supports delete 

		throw new RuntimeException("implement me, delete of model item within file");
	}
}