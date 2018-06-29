/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.actions.ProfilingMonitorActionGroup;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * Profiling monitor view.
 */
public class ProfilingMonitorView extends ViewPart
		implements IProfileSessionListener, IDoubleClickListener, ISelectionChangedListener, IMenuListener {

	private TreeViewer fViewer;
	private ProfilingMonitorActionGroup fActionSet;
	private Menu fContextMenu;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		fViewer = new TreeViewer(parent);
		fViewer.setContentProvider(new ProfilingMonitorContentProvider());
		fViewer.setLabelProvider(new ProfilingMonitorLabelProvider());
		fViewer.addDoubleClickListener(this);
		fViewer.addSelectionChangedListener(this);

		fActionSet = new ProfilingMonitorActionGroup(fViewer);
		fActionSet.fillActionBars(getViewSite().getActionBars());

		ProfileSessionsManager.addProfileSessionListener(this);

		getSite().setSelectionProvider(fViewer);

		hookContextMenu();

		update();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.PROFILING_MONITOR_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse
	 * .jface.viewers.DoubleClickEvent)
	 */
	@Override
	public void doubleClick(DoubleClickEvent event) {
		fActionSet.handlerDoubleClick(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		fActionSet.updateSelectionDependentActions(event.getSelection());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	public void update() {
		if (fViewer == null || fViewer.getContentProvider() == null) {
			return;
		}
		ProfilerDB[] profilerDBs = ProfileSessionsManager.getSessions();
		ProfilingMonitorElement[] elements = new ProfilingMonitorElement[profilerDBs.length];
		for (int i = 0; i < profilerDBs.length; ++i) {
			elements[i] = new ProfilingMonitorElement(profilerDBs[i]);
		}
		fViewer.setInput(elements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (fViewer != null) {
			fViewer.removeDoubleClickListener(this);
			fViewer.removeSelectionChangedListener(this);
		}
		ProfileSessionsManager.removeProfileSessionListener(this);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionAdded(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionAdded(ProfilerDB db) {
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionRemoved(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionRemoved(ProfilerDB db) {
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * currentSessionChanged(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void currentSessionChanged(ProfilerDB current) {
	}

	public void hookContextMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		fContextMenu = menuManager.createContextMenu(fViewer.getTree());
		fViewer.getTree().setMenu(fContextMenu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void menuAboutToShow(IMenuManager manager) {
		fActionSet.fillContextMenu(manager);
	}
}