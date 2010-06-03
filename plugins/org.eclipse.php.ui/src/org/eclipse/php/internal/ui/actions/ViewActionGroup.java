/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.internal.ui.workingsets.IWorkingSetActionGroup;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetFilterActionGroup;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetMessages;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

/**
 * An action group to provide access to the working sets.
 */
public class ViewActionGroup extends
		org.eclipse.dltk.internal.ui.workingsets.ViewActionGroup {

	private static final Integer INT_SHOW_PROJECTS = new Integer(SHOW_PROJECTS);
	private static final Integer INT_SHOW_WORKING_SETS = new Integer(
			SHOW_WORKING_SETS);

	private IPropertyChangeListener fChangeListener;

	private int fMode;
	private IMenuManager fMenuManager;
	private IWorkingSetActionGroup fActiveActionGroup;
	private WorkingSetShowActionGroup fShowActionGroup;
	private WorkingSetFilterActionGroup fFilterActionGroup;

	public ViewActionGroup(int mode, IPropertyChangeListener changeListener,
			IWorkbenchPartSite site) {
		super(mode, changeListener, site);
		fChangeListener = changeListener;
		if (fChangeListener == null) {
			fChangeListener = new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) {
				}
			};
		}
		fFilterActionGroup = new WorkingSetFilterActionGroup(site,
				fChangeListener);
		fShowActionGroup = new WorkingSetShowActionGroup(site);
		fMode = mode;
		if (showWorkingSets())
			fActiveActionGroup = fShowActionGroup;
		else
			fActiveActionGroup = fFilterActionGroup;
	}

	public void dispose() {
		fFilterActionGroup.dispose();
		fShowActionGroup.dispose();
		fChangeListener = null;
		super.dispose();
	}

	public void setWorkingSetModel(WorkingSetModel model) {
		fShowActionGroup.setWorkingSetMode(model);
	}

	/**
	 * {@inheritDoc}
	 */
	public void fillActionBars(IActionBars actionBars) {
		// super.fillActionBars(actionBars);
		fMenuManager = actionBars.getMenuManager();
		fillViewMenu(fMenuManager);

		if (fActiveActionGroup == null)
			fActiveActionGroup = fFilterActionGroup;
		((ActionGroup) fActiveActionGroup).fillActionBars(actionBars);
	}

	private void fillViewMenu(IMenuManager menu) {
		IMenuManager showMenu = new MenuManager(
				WorkingSetMessages.ViewActionGroup_show_label);
		fillShowMenu(showMenu);
		menu.add(showMenu);
		menu.add(new Separator(IWorkingSetActionGroup.ACTION_GROUP));
	}

	private void fillShowMenu(IMenuManager menu) {
		ViewAction projects = new ViewAction(this, SHOW_PROJECTS);
		projects.setText(WorkingSetMessages.ViewActionGroup_projects_label);
		menu.add(projects);
		ViewAction workingSets = new ViewAction(this, SHOW_WORKING_SETS);
		workingSets
				.setText(WorkingSetMessages.ViewActionGroup_workingSets_label);
		menu.add(workingSets);
		if (fMode == SHOW_PROJECTS) {
			projects.setChecked(true);
		} else {
			workingSets.setChecked(true);
		}
	}

	public void fillFilters(StructuredViewer viewer) {
		ViewerFilter workingSetFilter = fFilterActionGroup
				.getWorkingSetFilter();
		if (showProjects()) {
			viewer.addFilter(workingSetFilter);
		} else if (showWorkingSets()) {
			viewer.removeFilter(workingSetFilter);
		}
	}

	public void setMode(int mode) {
		fMode = mode;
		fActiveActionGroup.cleanViewMenu(fMenuManager);
		PropertyChangeEvent event;
		if (mode == SHOW_PROJECTS) {
			fActiveActionGroup = fFilterActionGroup;
			event = new PropertyChangeEvent(this, MODE_CHANGED,
					INT_SHOW_WORKING_SETS, INT_SHOW_PROJECTS);
		} else {
			fActiveActionGroup = fShowActionGroup;
			event = new PropertyChangeEvent(this, MODE_CHANGED,
					INT_SHOW_PROJECTS, INT_SHOW_WORKING_SETS);
		}
		fActiveActionGroup.fillViewMenu(fMenuManager);
		fMenuManager.updateAll(true);
		if (fChangeListener != null)
			fChangeListener.propertyChange(event);
	}

	public WorkingSetFilterActionGroup getFilterGroup() {
		return fFilterActionGroup;
	}

	public void restoreState(IMemento memento) {
		fFilterActionGroup.restoreState(memento);
	}

	public void saveState(IMemento memento) {
		fFilterActionGroup.saveState(memento);
	}

	private boolean showProjects() {
		return fMode == SHOW_PROJECTS;
	}

	private boolean showWorkingSets() {
		return fMode == SHOW_WORKING_SETS;
	}
}
