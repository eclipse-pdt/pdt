/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.actions.ActionMessages;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Action group that adds the 'new' menu to a context menu.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * 
 */
public class PHPNewWizardsActionGroup extends ActionGroup {

	private IWorkbenchSite fSite;

	/**
	 * Creates a new <code>NewWizardsActionGroup</code>. The group requires that
	 * the selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the view part that owns this action group
	 */
	public PHPNewWizardsActionGroup(IWorkbenchSite site) {
		fSite = site;
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);

		ISelection selection = getContext().getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (sel.size() <= 1 && isNewTarget(sel.getFirstElement())) {
				MenuManager newMenu = new MenuManager(
						ActionMessages.NewWizardsActionGroup_new);
				menu.appendToGroup(IContextMenuConstants.GROUP_NEW, newMenu);
				newMenu.add(new NewWizardMenu(fSite.getWorkbenchWindow(), sel
						.size() == 0));
			}
		}

	}

	private boolean isNewTarget(Object element) {
		if (element == null)
			return true;
		if (element instanceof IResource) {
			return true;
		}
		if (element instanceof IModelElement) {
			int type = ((IModelElement) element).getElementType();
			return type == IModelElement.SCRIPT_PROJECT
					|| type == IModelElement.PROJECT_FRAGMENT
					|| type == IModelElement.SCRIPT_FOLDER
					|| type == IModelElement.SOURCE_MODULE
					|| type == IModelElement.TYPE;
		}
		return false;
	}

}
