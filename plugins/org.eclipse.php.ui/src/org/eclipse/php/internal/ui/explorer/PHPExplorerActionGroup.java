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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.IOpenable;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.actions.CCPActionGroup;
import org.eclipse.dltk.internal.ui.actions.OpenProjectAction;
import org.eclipse.dltk.internal.ui.actions.refactoring.RefactorActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.LayoutActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.actions.GenerateActionGroup;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.actions.NavigateActionGroup;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkingSet;
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
	private NavigateActionGroup fNavigateActionGroup;
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
					|| groups[i] instanceof GenerateActionGroup
					|| groups[i] instanceof RefactorActionGroup || groups[i] instanceof CCPActionGroup)) {
				//use pdt's NavigateActionGroup instead of dltk's
				if (groups[i] instanceof org.eclipse.dltk.internal.ui.actions.NavigateActionGroup) {
					groups[i].dispose();
					fNavigateActionGroup = new NavigateActionGroup(getPart());
					groups[i] = fNavigateActionGroup;
				}
				filtered.add(groups[i]);
			}
		}
		phpRefactorActionGroup = new PHPRefactorActionGroup(getPart());
		filtered.add(phpRefactorActionGroup);
		filtered.add(new GenerateIncludePathActionGroup(getPart()));
		filtered
				.add(new NamespaceGroupingActionGroup(getPart().getTreeViewer()));
		filtered.add(new PHPFileOperationActionGroup(getPart()));

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
		//use our action to do the open operation
		IAction openAction= fNavigateActionGroup.getOpenAction();
		if (openAction != null && openAction.isEnabled()) {
			openAction.run();
			return;
		}

	}

	@Override
	protected void setGlobalActionHandlers(IActionBars actionBars) {
		super.setGlobalActionHandlers(actionBars);
		phpRefactorActionGroup.retargetFileMenuActions(actionBars);
	}

	//---- Key board and mouse handling ------------------------------------------------------------

	/**
	 * this method call ScriptExplorerActionGroup.handleDoubleClick(event) at most cases,
	 * except fNavigateActionGroup relative operation
	 */
	protected void handleDoubleClick(DoubleClickEvent event) {
		TreeViewer viewer= getPart().getTreeViewer();
		IStructuredSelection selection= (IStructuredSelection)event.getSelection();
		Object element= selection.getFirstElement();
		if (viewer.isExpandable(element)) {
			if (doubleClickGoesInto()) {
				super.handleDoubleClick(event);
			} else {
				IAction openAction= fNavigateActionGroup.getOpenAction();
				if (openAction != null && openAction.isEnabled() && OpenStrategy.getOpenMethod() == OpenStrategy.DOUBLE_CLICK)
					return;
				if (selection instanceof ITreeSelection) {
					TreePath[] paths= ((ITreeSelection)selection).getPathsFor(element);
					for (int i= 0; i < paths.length; i++) {
						viewer.setExpandedState(paths[i], !viewer.getExpandedState(paths[i]));
					}
				} else {
					viewer.setExpandedState(element, !viewer.getExpandedState(element));
				}
			}
		} else {
			super.handleDoubleClick(event);
		}
	}
	/**
	 * copy from ScriptExplorerActionGroup
	 * @return
	 */
	private boolean doubleClickGoesInto() {
		return PreferenceConstants.DOUBLE_CLICK_GOES_INTO.equals(DLTKUIPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.DOUBLE_CLICK));
	}
}
