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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.explorer.ExplorerPart;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.internal.ViewSite;


public class RenameResourceAction extends SelectionDispatchAction {

	Tree tree;

	public RenameResourceAction(IWorkbenchSite site) {
		super(site);
		if (site instanceof ViewSite) {
			ViewSite viewSite = (ViewSite) site;
			IWorkbenchPart part = viewSite.getPart();
			if (part instanceof ExplorerPart) {
				ExplorerPart explorer = (ExplorerPart) part;
				tree = explorer.getViewer().getTree();
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
		if (ActionUtils.containsOnlyProjects(selection.toList()) || ActionUtils.containsOnly(selection.toList(), IFile.class)) {
			createWorkbenchAction(selection).run();
			return;
		}
		throw new RuntimeException("implement me-- rename model item within file");

	}

	private org.eclipse.ui.actions.RenameResourceAction createWorkbenchAction(IStructuredSelection selection) {
		org.eclipse.ui.actions.RenameResourceAction action;
		if (tree != null)
			action = new org.eclipse.ui.actions.RenameResourceAction(getShell(), tree);
		else
			action = new org.eclipse.ui.actions.RenameResourceAction(getShell());
		action.selectionChanged(selection);
		return action;
	}

	private static IResource getResource(IStructuredSelection selection) {
		if (selection.size() != 1)
			return null;
		Object first = selection.getFirstElement();
		if ((first instanceof PHPCodeData)&&!(first instanceof PHPFileData))
			return null;
		first = PHPModelUtil.getResource(first);
		if (!(first instanceof IResource))
			return null;
		return (IResource) first;
	}
}
