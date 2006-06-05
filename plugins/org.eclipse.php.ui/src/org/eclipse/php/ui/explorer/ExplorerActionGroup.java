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
package org.eclipse.php.ui.explorer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.actions.*;
import org.eclipse.php.ui.IContextMenuConstants;
import org.eclipse.php.ui.actions.CompositeActionGroup;
import org.eclipse.php.ui.actions.CustomFiltersActionGroup;
import org.eclipse.php.ui.workingset.ExplorerViewActionGroup;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.OpenInNewWindowAction;
import org.eclipse.ui.views.framelist.Frame;
import org.eclipse.ui.views.framelist.FrameList;
import org.eclipse.ui.views.framelist.GoIntoAction;
import org.eclipse.ui.views.framelist.TreeFrame;


public class ExplorerActionGroup extends CompositeActionGroup {

	private ExplorerPart fPart;

	private CollapseAllAction fCollapseAllAction;
	private GoIntoAction fZoomInAction;
	private FrameList fFrameList;

	private ToggleLinkingAction fToggleLinkingAction;

	private RefactorActionGroup fRefactorActionGroup;
	private NavigateActionGroup fNavigateActionGroup;
	private ExplorerViewActionGroup fViewActionGroup;
	private CustomFiltersActionGroup fCustomFiltersActionGroup;

	public ExplorerActionGroup(ExplorerPart part) {
		super();
		fPart = part;
		TreeViewer viewer = part.getViewer();

		IPropertyChangeListener workingSetListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				doWorkingSetChanged(event);
			}
		};

		IWorkbenchPartSite site = fPart.getSite();
		setGroups(new ActionGroup[] { new NewWizardsActionGroup(site), fNavigateActionGroup = new NavigateActionGroup(fPart), new CCPActionGroup(fPart), fRefactorActionGroup = new RefactorActionGroup(fPart), new ImportActionGroup(fPart), new BuildActionGroup(fPart), new ProjectActionGroup(fPart),
			fViewActionGroup = new ExplorerViewActionGroup(fPart.getRootMode(), workingSetListener, site), fCustomFiltersActionGroup = new CustomFiltersActionGroup(fPart, viewer), });
		fViewActionGroup.fillFilters(viewer);

		ExplorerFrameSource frameSource = new ExplorerFrameSource(fPart);
		fFrameList = new FrameList(frameSource);
		frameSource.connectTo(fFrameList);

		fZoomInAction = new GoIntoAction(fFrameList);
		fCollapseAllAction = new CollapseAllAction(fPart);
		fToggleLinkingAction = new ToggleLinkingAction(fPart);
	}

	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		setGlobalActionHandlers(actionBars);
		fillToolBar(actionBars.getToolBarManager());
		fillViewMenu(actionBars.getMenuManager());
	}

	void updateActionBars(IActionBars actionBars) {
		actionBars.getToolBarManager().removeAll();
		actionBars.getMenuManager().removeAll();
		fZoomInAction.setEnabled(true);
		fillActionBars(actionBars);
		actionBars.updateActionBars();
	}

	private void addGotoMenu(IMenuManager menu, Object element, int size) {
		boolean enabled = size == 1 && fPart.getViewer().isExpandable(element) && (isGoIntoTarget(element) || element instanceof IContainer);
		fZoomInAction.setEnabled(enabled);
		if (enabled)
			menu.appendToGroup(IContextMenuConstants.GROUP_GOTO, fZoomInAction);
	}

	private boolean isGoIntoTarget(Object element) {
		if (element == null)
			return false;
		if (element instanceof PHPProjectModel || element instanceof IFolder) {
			return true;
		}

		return false;
	}

	private void setGlobalActionHandlers(IActionBars actionBars) {

		actionBars.setGlobalActionHandler(IWorkbenchActionConstants.GO_INTO, fZoomInAction);
		fRefactorActionGroup.retargetFileMenuActions(actionBars);
	}

	void fillToolBar(IToolBarManager toolBar) {

		toolBar.add(fCollapseAllAction);
		toolBar.add(fToggleLinkingAction);

	}

	void fillViewMenu(IMenuManager menu) {
		menu.add(fToggleLinkingAction);

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end"));//$NON-NLS-1$		
	}

	//---- Context menu -------------------------------------------------------------------------

	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		int size = selection.size();
		Object element = selection.getFirstElement();

//		dont support "go into"  addGotoMenu(menu, element, size);
		addOpenNewWindowAction(menu, element);

		super.fillContextMenu(menu);
	}

	private void addOpenNewWindowAction(IMenuManager menu, Object element) {
		if (element instanceof PHPCodeData || element instanceof PHPProjectModel) {
			element = PHPModelUtil.getResource(element);

		}
		if (element instanceof IProject && !((IProject) element).isOpen())
			return;

		if (!(element instanceof IContainer))
			return;
		menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, new OpenInNewWindowAction(fPart.getSite().getWorkbenchWindow(), (IContainer) element));
	}

	//---- Key board and mouse handling ------------------------------------------------------------

	void handleDoubleClick(DoubleClickEvent event) {
		TreeViewer viewer = fPart.getViewer();
		Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
		if (viewer.isExpandable(element)) {
			if (doubleClickGoesInto()) {
				// don't zoom into compilation units and class files
				if (element instanceof PHPFileData)
					return;
				if (element instanceof IContainer) {
					fZoomInAction.run();
				}
			} else {
				IAction openAction = fNavigateActionGroup.getOpenAction();
				if (openAction != null && openAction.isEnabled() && OpenStrategy.getOpenMethod() == OpenStrategy.DOUBLE_CLICK)
					return;
				viewer.setExpandedState(element, !viewer.getExpandedState(element));
			}
		}
	}

	void handleOpen(OpenEvent event) {
		IAction openAction = fNavigateActionGroup.getOpenAction();
		if (openAction != null && openAction.isEnabled()) {
			openAction.run();
			return;
		}
	}

	void handleKeyEvent(KeyEvent event) {
		if (event.stateMask != 0)
			return;

	}

	private boolean doubleClickGoesInto() {
		return PreferenceConstants.DOUBLE_CLICK_GOES_INTO.equals(PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.DOUBLE_CLICK));
	}

	public ExplorerViewActionGroup getWorkingSetActionGroup() {
		return fViewActionGroup;
	}

	private void doWorkingSetChanged(PropertyChangeEvent event) {
		if (ExplorerViewActionGroup.MODE_CHANGED.equals(event.getProperty())) {
			fPart.rootModeChanged(((Integer) event.getNewValue()).intValue());
			Object oldInput = null;
			Object newInput = null;
			if (fPart.showProjects()) {
				oldInput = fPart.getWorkingSetModel();
				newInput = PHPWorkspaceModelManager.getInstance();
			} else if (fPart.showWorkingSets()) {
				oldInput = PHPWorkspaceModelManager.getInstance();
				newInput = fPart.getWorkingSetModel();
			}
			if (oldInput != null && newInput != null) {
				Frame frame;
				for (int i = 0; (frame = fFrameList.getFrame(i)) != null; i++) {
					if (frame instanceof TreeFrame) {
						TreeFrame treeFrame = (TreeFrame) frame;
						if (oldInput.equals(treeFrame.getInput()))
							treeFrame.setInput(newInput);
					}
				}
			}
		} else {
			IWorkingSet workingSet = (IWorkingSet) event.getNewValue();

			String workingSetName = null;
			if (workingSet != null)
				workingSetName = workingSet.getName();
			fPart.setWorkingSetName(workingSetName);
			fPart.updateTitle();

			String property = event.getProperty();
			if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property)) {
				TreeViewer viewer = fPart.getViewer();
				viewer.getControl().setRedraw(false);
				viewer.refresh();
				viewer.getControl().setRedraw(true);
			}
		}
	}

	void restoreFilterAndSorterState(IMemento memento) {
		fViewActionGroup.restoreState(memento);
		fCustomFiltersActionGroup.restoreState(memento);
	}

	void saveFilterAndSorterState(IMemento memento) {
		fViewActionGroup.saveState(memento);
		fCustomFiltersActionGroup.saveState(memento);
	}

}
