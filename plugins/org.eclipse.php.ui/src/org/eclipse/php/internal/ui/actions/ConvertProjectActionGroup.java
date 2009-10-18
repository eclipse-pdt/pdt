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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

public class ConvertProjectActionGroup extends ActionGroup {

	private IWorkbenchSite fSite;
	private boolean fIsEditorOwner;
	private ConvertToPDTProjectAction fConvertToPDTProjectAction;

	/**
	 * Creates a new <code>ConfigureBuildPathActionGroup</code>. The group
	 * requires that the selection provided by the part's selection provider is
	 * of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param part
	 *            the view part that owns this action group
	 */
	public ConvertProjectActionGroup(IViewPart part) {
		fSite = part.getSite();
		fConvertToPDTProjectAction = new ConvertToPDTProjectAction(fSite);
		initialize(fSite.getSelectionProvider());
	}

	/**
	 * Returns the open action managed by this action group.
	 * 
	 * @return the open action. Returns <code>null</code> if the group doesn't
	 *         provide any open action
	 */
	public IAction getOpenAction() {
		return fConvertToPDTProjectAction;
	}

	private void initialize(ISelectionProvider provider) {
		ISelection selection = provider.getSelection();
		fConvertToPDTProjectAction.update(selection);
		if (!fIsEditorOwner) {
			provider.addSelectionChangedListener(fConvertToPDTProjectAction);
		}
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		appendToGroup(menu, fConvertToPDTProjectAction);

	}

	/*
	 * @see ActionGroup#dispose()
	 */
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		provider.removeSelectionChangedListener(fConvertToPDTProjectAction);
		super.dispose();
	}

	private void appendToGroup(IMenuManager menu, IAction action) {
		if (action.isEnabled()) {
			menu.appendToGroup(IContextMenuConstants.GROUP_BUILD, action);
		}
	}
}
