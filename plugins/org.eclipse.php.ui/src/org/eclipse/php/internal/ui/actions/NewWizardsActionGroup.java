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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

public class NewWizardsActionGroup extends ActionGroup {

	private IWorkbenchSite fSite;

	/**
	 * Creates a new <code>NewWizardsActionGroup</code>. The group requires
	 * that the selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the view part that owns this action group
	 */
	public NewWizardsActionGroup(IWorkbenchSite site) {
		fSite = site;
	}

	/* (non-Javadoc)
	 * Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);

		ISelection selection = getContext().getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (sel.size() <= 1 && isNewTarget(sel.getFirstElement())) {
				IMenuManager newMenu = new MenuManager(PHPUIMessages.getString("NewWizardsActionGroup_new"));
				menu.appendToGroup(IContextMenuConstants.GROUP_NEW, newMenu);
				new PHPNewWizardMenu(newMenu, fSite.getWorkbenchWindow(), false);
			}
		}

	}

	private boolean isNewTarget(Object element) {
		if (element == null)
			return true;
		if (element instanceof IResource) {
			return true;
		}
		if (element instanceof IFolder || element instanceof PHPProjectModel || element instanceof PHPClassData || element instanceof PHPFileData) {
			return true;
		}

		if (element instanceof PHPFunctionData && ((PHPFunctionData) element).getContainer() instanceof PHPFileData)
			return true;

		return false;
	}

}
