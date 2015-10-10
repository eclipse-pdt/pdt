/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.internal.ui.workingsets.IWorkingSetActionGroup;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

public class WorkingSetShowActionGroup extends ActionGroup implements IWorkingSetActionGroup {

	private List fContributions = new ArrayList();
	private ConfigureWorkingSetAction fConfigureWorkingSetAction;
	private WorkingSetModel fWorkingSetModel;
	private final IWorkbenchPartSite fSite;

	public WorkingSetShowActionGroup(IWorkbenchPartSite site) {
		fSite = site;
	}

	public void setWorkingSetMode(WorkingSetModel model) {
		fWorkingSetModel = model;
		if (fConfigureWorkingSetAction != null)
			fConfigureWorkingSetAction.setWorkingSetModel(fWorkingSetModel);
	}

	/**
	 * {@inheritDoc}
	 */
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		IMenuManager menuManager = actionBars.getMenuManager();
		fillViewMenu(menuManager);
	}

	public void fillViewMenu(IMenuManager menuManager) {
		fConfigureWorkingSetAction = new ConfigureWorkingSetAction(fSite);
		if (fWorkingSetModel != null)
			fConfigureWorkingSetAction.setWorkingSetModel(fWorkingSetModel);
		addAction(menuManager, fConfigureWorkingSetAction);
	}

	public void cleanViewMenu(IMenuManager menuManager) {
		for (Iterator iter = fContributions.iterator(); iter.hasNext();) {
			menuManager.remove((IContributionItem) iter.next());
		}
		fContributions.clear();
	}

	private void addAction(IMenuManager menuManager, Action action) {
		IContributionItem item = new ActionContributionItem(action);
		menuManager.appendToGroup(ACTION_GROUP, item);
		fContributions.add(item);
	}
}
