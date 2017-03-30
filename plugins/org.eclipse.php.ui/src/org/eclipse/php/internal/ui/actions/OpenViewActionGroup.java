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

import org.eclipse.dltk.internal.ui.callhierarchy.ICallHierarchyViewPart;
import org.eclipse.dltk.internal.ui.typehierarchy.TypeHierarchyViewPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.part.Page;

public class OpenViewActionGroup extends ActionGroup {

	private boolean fEditorIsOwner;
	private IWorkbenchSite fSite;
	private OpenTypeHierarchyAction fOpenTypeHierarchy;
	private OpenCallHierarchyAction fOpenCallHierarchy;

	private PropertyDialogAction fOpenPropertiesDialog;
	private boolean fIsTypeHiararchyViewerOwner;
	private boolean fIsCallHiararchyViewerOwner;

	private ISelectionProvider fSelectionProvider;

	private boolean fShowOpenPropertiesAction = true;

	/**
	 * Creates a new <code>OpenActionGroup</code>. The group requires that the
	 * selection provided by the page's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param page
	 *            the page that owns this action group
	 */
	public OpenViewActionGroup(Page page) {
		createSiteActions(page.getSite());
	}

	/**
	 * Creates a new <code>OpenActionGroup</code>. The group requires that the
	 * selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param part
	 *            the view part that owns this action group
	 */
	public OpenViewActionGroup(IViewPart part) {
		createSiteActions(part.getSite());
		fIsTypeHiararchyViewerOwner = part instanceof TypeHierarchyViewPart;
		fIsCallHiararchyViewerOwner = part instanceof ICallHierarchyViewPart;
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param part
	 *            the editor part
	 */
	public OpenViewActionGroup(PHPStructuredEditor part) {
		fEditorIsOwner = true;
		fOpenTypeHierarchy = new OpenTypeHierarchyAction(part);
		fOpenTypeHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY);
		part.setAction("OpenTypeHierarchy", fOpenTypeHierarchy); //$NON-NLS-1$

		fOpenCallHierarchy = new OpenCallHierarchyAction(part);
		fOpenCallHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY);
		part.setAction("OpenCallHierarchy", fOpenCallHierarchy); //$NON-NLS-1$

		initialize(part.getEditorSite());
	}

	private void createSiteActions(IWorkbenchSite site) {

		fOpenPropertiesDialog = new PropertyDialogAction(site, site.getSelectionProvider());
		fOpenPropertiesDialog.setActionDefinitionId(IWorkbenchCommandConstants.FILE_PROPERTIES);

		fOpenTypeHierarchy = new OpenTypeHierarchyAction(site);
		fOpenTypeHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY);

		fOpenCallHierarchy = new OpenCallHierarchyAction(site);
		fOpenCallHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY);

		initialize(site);
	}

	private void initialize(IWorkbenchSite site) {
		fSite = site;
		fSelectionProvider = fSite.getSelectionProvider();
		ISelection selection = fSelectionProvider.getSelection();
		fOpenTypeHierarchy.update(selection);
		fOpenCallHierarchy.update(selection);
		if (!fEditorIsOwner) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				fOpenPropertiesDialog.selectionChanged(ss);
			} else {
				fOpenPropertiesDialog.selectionChanged(selection);
			}
			fSelectionProvider.addSelectionChangedListener(fOpenTypeHierarchy);
			fSelectionProvider.addSelectionChangedListener(fOpenCallHierarchy);
			// no need to register the open properties dialog action since it
			// registers itself
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
		if (!fIsTypeHiararchyViewerOwner)
			appendToGroup(menu, fOpenTypeHierarchy);
		if (!fIsCallHiararchyViewerOwner)
			appendToGroup(menu, fOpenCallHierarchy);
		IStructuredSelection selection = getStructuredSelection();
		if (fShowOpenPropertiesAction && fOpenPropertiesDialog != null && fOpenPropertiesDialog.isEnabled()
				&& selection != null && fOpenPropertiesDialog.isApplicableForSelection(selection)) {
			menu.appendToGroup(IContextMenuConstants.GROUP_PROPERTIES, fOpenPropertiesDialog);
		}

		MenuManager showInSubMenu = new MenuManager(getShowInMenuLabel());
		IWorkbenchWindow workbenchWindow = fSite.getWorkbenchWindow();
		showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN.create(workbenchWindow));
		menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, showInSubMenu);
	}

	/*
	 * @see ActionGroup#dispose()
	 */
	@Override
	public void dispose() {
		fSelectionProvider.removeSelectionChangedListener(fOpenTypeHierarchy);
		fSelectionProvider.removeSelectionChangedListener(fOpenCallHierarchy);
		super.dispose();
	}

	private void setGlobalActionHandlers(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(PHPActionConstants.OPEN_TYPE_HIERARCHY, fOpenTypeHierarchy);
		actionBars.setGlobalActionHandler(PHPActionConstants.OPEN_CALL_HIERARCHY, fOpenCallHierarchy);
		if (!fEditorIsOwner)
			actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(), fOpenPropertiesDialog);
	}

	private void appendToGroup(IMenuManager menu, IAction action) {
		if (action.isEnabled())
			menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);
	}

	private IStructuredSelection getStructuredSelection() {
		if (getContext() == null) {
			return null;
		}
		ISelection selection = getContext().getSelection();
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return null;
	}

	private String getShowInMenuLabel() {
		String keyBinding = null;

		IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		if (bindingService != null)
			keyBinding = bindingService.getBestActiveBindingFormattedFor("org.eclipse.ui.navigate.showInQuickMenu"); //$NON-NLS-1$

		if (keyBinding == null)
			keyBinding = ""; //$NON-NLS-1$

		return Messages.OpenViewActionGroup_ShowInLabel + '\t' + keyBinding;
	}

	public void containsOpenPropertiesAction(boolean enable) {
		fShowOpenPropertiesAction = enable;
	}

}
