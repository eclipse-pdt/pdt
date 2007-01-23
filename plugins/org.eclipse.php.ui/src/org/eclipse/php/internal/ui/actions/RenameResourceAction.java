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

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.explorer.ExplorerPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.views.navigator.ResourceNavigatorRenameAction;

public class RenameResourceAction extends SelectionDispatchAction {

	TreeViewer treeViewer;

	public RenameResourceAction(IWorkbenchSite site) {
		super(site);
		if (site instanceof ViewSite) {
			ViewSite viewSite = (ViewSite) site;
			IWorkbenchPart part = viewSite.getPart();
			if (part instanceof ExplorerPart) {
				ExplorerPart explorer = (ExplorerPart) part;
				treeViewer = explorer.getViewer();
			}
		}
	}

	public void selectionChanged(IStructuredSelection selection) {
		IResource element = getResource(selection);
		if (element == null)
			setEnabled(false);
		else
			setEnabled(ActionUtils.isRenameAvailable(element));
	}

	public void run(IStructuredSelection selection) {
		IResource resource = getResource(selection);
		if (!ActionUtils.isProcessable(getShell(), resource))
			return;
		if (!ActionUtils.isRenameAvailable(resource))
			return;
		createWorkbenchAction(selection).run();
	}

	private ResourceNavigatorRenameAction createWorkbenchAction(IStructuredSelection selection) {
		ResourceNavigatorRenameAction action;
		action = new ResourceNavigatorRenameAction(getShell(), treeViewer);
		action.selectionChanged(selection);
		return action;
	}

	private static IResource getResource(IStructuredSelection selection) {
		if (selection.size() != 1)
			return null;
		Object first = selection.getFirstElement();
		if ((first instanceof PHPCodeData) && !(first instanceof PHPFileData))
			return null;
		first = PHPModelUtil.getResource(first);
		if (!(first instanceof IResource))
			return null;
		return (IResource) first;
	}
}
