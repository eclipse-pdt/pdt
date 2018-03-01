/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.OpenWithMenu;

public class OpenEditorActionGroup extends ActionGroup {

	private IWorkbenchSite fSite;
	private boolean fIsEditorOwner;
	private OpenAction fOpen;

	/**
	 * Creates a new <code>OpenActionGroup</code>. The group requires that the
	 * selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param part
	 *            the view part that owns this action group
	 */
	public OpenEditorActionGroup(IViewPart part) {
		fSite = part.getSite();
		fOpen = new OpenAction(fSite);
		fOpen.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_EDITOR);
		initialize(fSite.getSelectionProvider());
	}

	/**
	 * Returns the open action managed by this action group.
	 * 
	 * @return the open action. Returns <code>null</code> if the group doesn't
	 *         provide any open action
	 */
	public IAction getOpenAction() {
		return fOpen;
	}

	private void initialize(ISelectionProvider provider) {
		ISelection selection = provider.getSelection();
		fOpen.update(selection);
		if (!fIsEditorOwner) {
			provider.addSelectionChangedListener(fOpen);
		}
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	@Override
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		setGlobalActionHandlers(actionBar);
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		fOpen.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_EDITOR);
		appendToGroup(menu, fOpen);
		if (!fIsEditorOwner) {
			addOpenWithMenu(menu);
		}
	}

	/*
	 * @see ActionGroup#dispose()
	 */
	@Override
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		provider.removeSelectionChangedListener(fOpen);
		super.dispose();
	}

	private void setGlobalActionHandlers(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(PHPActionConstants.OPEN, fOpen);
	}

	private void appendToGroup(IMenuManager menu, IAction action) {
		if (action.isEnabled()) {
			menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);
		}
	}

	private void addOpenWithMenu(IMenuManager menu) {
		ISelection selection = getContext().getSelection();
		if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection ss = (IStructuredSelection) selection;
		if (ss.size() != 1) {
			return;
		}

		Object o = ss.getFirstElement();
		if (!(o instanceof IAdaptable)) {
			return;
		}

		IAdaptable element = (IAdaptable) o;
		Object resource = element.getAdapter(IResource.class);
		if (!(resource instanceof IFile)) {
			return;
		}

		// Create a menu.
		IMenuManager submenu = new MenuManager(PHPUIMessages.OpenWithMenu_label);
		submenu.add(new OpenWithMenu(fSite.getPage(), (IFile) resource));

		// Add the submenu.
		menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, submenu);
	}

}
