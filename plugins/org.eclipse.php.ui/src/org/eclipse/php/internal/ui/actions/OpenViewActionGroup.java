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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

public class OpenViewActionGroup extends ActionGroup {

	private boolean fEditorIsOwner;
	private IWorkbenchSite fSite;

	private PropertyDialogAction fOpenPropertiesDialog;

	/**
	 * Creates a new <code>OpenActionGroup</code>. The group requires
	 * that the selection provided by the page's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param page the page that owns this action group
	 */
	public OpenViewActionGroup(Page page) {
		createSiteActions(page.getSite());
	}

	/**
	 * Creates a new <code>OpenActionGroup</code>. The group requires
	 * that the selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param part the view part that owns this action group
	 */
	public OpenViewActionGroup(IViewPart part) {
		createSiteActions(part.getSite());
		//	fIsTypeHiararchyViewerOwner= part instanceof TypeHierarchyViewPart;
		//   fIsCallHiararchyViewerOwner= part instanceof ICallHierarchyViewPart;
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param part the editor part
	 */
	public OpenViewActionGroup(PHPStructuredEditor part) {
		fEditorIsOwner = true;

		initialize(part.getEditorSite());
	}

	private void createSiteActions(IWorkbenchSite site) {

		fOpenPropertiesDialog = new PropertyDialogAction(site, site.getSelectionProvider());
		fOpenPropertiesDialog.setActionDefinitionId(IWorkbenchActionDefinitionIds.PROPERTIES);

		initialize(site);
	}

	private void initialize(IWorkbenchSite site) {
		fSite = site;
		ISelectionProvider provider = fSite.getSelectionProvider();
		ISelection selection = provider.getSelection();
		if (!fEditorIsOwner) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				fOpenPropertiesDialog.selectionChanged(ss);
			} else {
				fOpenPropertiesDialog.selectionChanged(selection);
			}
			// no need to register the open properties dialog action since it registers itself
		}
	}

	/* (non-Javadoc)
	 * Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		setGlobalActionHandlers(actionBar);
	}

	/* (non-Javadoc)
	 * Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		IStructuredSelection selection = getStructuredSelection();
		if (fOpenPropertiesDialog != null && fOpenPropertiesDialog.isEnabled() && selection != null && fOpenPropertiesDialog.isApplicableForSelection(selection))
			menu.appendToGroup(IContextMenuConstants.GROUP_PROPERTIES, fOpenPropertiesDialog);
	}

	/*
	 * @see ActionGroup#dispose()
	 */
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		super.dispose();
	}

	private void setGlobalActionHandlers(IActionBars actionBars) {

		if (!fEditorIsOwner)
			actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(), fOpenPropertiesDialog);
	}

	private void appendToGroup(IMenuManager menu, IAction action) {
		if (action.isEnabled())
			menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);
	}

	private IStructuredSelection getStructuredSelection() {
		ISelection selection = getContext().getSelection();
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return null;
	}

}
