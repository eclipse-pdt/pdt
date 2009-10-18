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
/**
 * 
 */
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;

import org.eclipse.dltk.internal.ui.actions.refactoring.RefactorActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.LayoutActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.actions.GenerateActionGroup;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.wst.jsdt.core.IJavaScriptElement;
import org.eclipse.wst.jsdt.ui.actions.OpenAction;

/**
 * @author nir.c PHPExplorerActionGroup class extends DLTK's
 *         ScriptExplorerActionGroup, His purpose is to add "include path"
 *         actions to the popUp menu, similar to "build path" actions.
 * 
 */
public class PHPExplorerActionGroup extends ScriptExplorerActionGroup {
	private PHPRefactorActionGroup phpRefactorActionGroup;

	public PHPExplorerActionGroup(ScriptExplorerPart part) {
		super(part);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.actions.CompositeActionGroup#setGroups(org
	 * .eclipse.ui.actions.ActionGroup[])
	 */
	@Override
	protected void setGroups(ActionGroup[] groups) {
		// aggregate the PHP Explorer actions
		final ArrayList<ActionGroup> filtered = new ArrayList<ActionGroup>(
				groups.length - 1);
		for (int i = 0; i < groups.length; i++) {
			if (!(groups[i] instanceof LayoutActionGroup
					|| groups[i] instanceof GenerateActionGroup || groups[i] instanceof RefactorActionGroup)) {
				filtered.add(groups[i]);
			}
		}

		phpRefactorActionGroup = new PHPRefactorActionGroup(getPart());
		filtered.add(phpRefactorActionGroup);
		filtered.add(new GenerateIncludePathActionGroup(getPart()));
		filtered
				.add(new NamespaceGroupingActionGroup(getPart().getTreeViewer()));

		super.setGroups(filtered.toArray(new ActionGroup[filtered.size()]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup#handleOpen
	 * (org.eclipse.jface.viewers.OpenEvent)
	 */
	@Override
	protected void handleOpen(OpenEvent event) {
		// this code dispatches the selection from javascript library to JS open
		// action
		ISelection selection = event.getSelection();
		if (selection instanceof ITreeSelection) {
			ITreeSelection treeSelection = (ITreeSelection) selection;
			Object firstElement = treeSelection.getFirstElement();
			if (firstElement instanceof IJavaScriptElement) {
				// it's JS element, follow opening JS editor
				ScriptExplorerPart part = getPart();
				IViewSite viewSite = part.getViewSite();
				OpenAction openAction = new OpenAction(viewSite);
				if (openAction != null && openAction.isEnabled()) {
					openAction.run();
					return;
				}
			}
		}
		super.handleOpen(event);

	}

	@Override
	protected void setGlobalActionHandlers(IActionBars actionBars) {
		super.setGlobalActionHandlers(actionBars);
		phpRefactorActionGroup.retargetFileMenuActions(actionBars);
	}
}
