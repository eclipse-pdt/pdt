/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.ui.editor.OpenRemoteFileContentRequestor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.php.profile.ui.views.ExecutionFlowSorter;
import org.eclipse.php.profile.ui.views.ExecutionFlowTreeElement;
import org.eclipse.php.profile.ui.views.ExecutionFlowView;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Execution flow action group.
 */
public class ExecutionFlowActionGroup extends ActionGroup {

	private ExecutionFlowView fView;
	private CollapseAllAction fCollapseAllAction;
	private ExpandAllAction fExpandAllAction;
	private SortAction fSortAction;
	private ViewFunctionCallAction fViewFunctionCallAction;
	private ViewFunctionDeclarationAction fViewFunctionDeclarationAction;
	private OpenFunctionInvocationStatisticsAction fOpenFunctionInvocationStatisticsAction;

	public ExecutionFlowActionGroup(ExecutionFlowView view) {
		fView = view;
		fExpandAllAction = new ExpandAllAction();
		fCollapseAllAction = new CollapseAllAction();
		fSortAction = new SortAction();
		fViewFunctionCallAction = new ViewFunctionCallAction();
		fViewFunctionDeclarationAction = new ViewFunctionDeclarationAction();
		fOpenFunctionInvocationStatisticsAction = new OpenFunctionInvocationStatisticsAction(fView);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);

		IToolBarManager toolbarManager = actionBars.getToolBarManager();
		toolbarManager.add(fExpandAllAction);
		toolbarManager.add(fCollapseAllAction);
		toolbarManager.add(new Separator());
		toolbarManager.add(fSortAction);
		toolbarManager.add(new Separator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		menu.add(fViewFunctionCallAction);
		menu.add(fViewFunctionDeclarationAction);
		menu.add(fOpenFunctionInvocationStatisticsAction);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	public void viewFunctionCallInEditor(ExecutionFlowTreeElement element) {
		ExecutionFlowTreeElement caller = (ExecutionFlowTreeElement) element.getParent();
		if (caller != null) {
			ProfilerCallTraceLayer layer = element.getLayer();
			ProfilerFunctionData data = (ProfilerFunctionData) (caller.isRootElement() ? element.getData()
					: caller.getData());
			try {
				if (data.getLocalFileName() == null
						|| EditorUtility.openLocalFile(data.getLocalFileName(), layer.getLineNumber()) == null) {
					String url = fView.getInput().getGlobalData().getOriginalURL();
					if (!ProfilerGlobalData.URL_NOT_AVAILABLE_MSG.equals(url)) {
						// try to retrieve the file from server
						RemoteDebugger.requestRemoteFile(new OpenRemoteFileContentRequestor(),
								data.getAbsoluteFileName(), layer.getLineNumber(), url);
					} else {

					}
				}
			} catch (CoreException e) {
				MessageDialog.openError(fView.getViewer().getControl().getShell(),
						PHPProfileUIMessages.getString("ExecutionFlowActionGroup.0"), NLS.bind( //$NON-NLS-1$
								PHPProfileUIMessages.getString("ExecutionFlowActionGroup.1"), data.getLocalFileName())); //$NON-NLS-1$
			}
		}
	}

	public void viewFunctionCallInEditor(ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Object element = sSelection.getFirstElement();
			if (element instanceof ExecutionFlowTreeElement) {
				ExecutionFlowTreeElement executionFlowElement = (ExecutionFlowTreeElement) element;
				viewFunctionCallInEditor(executionFlowElement);
			}
		}
	}

	public void viewFunctionDeclarationInEditor(ExecutionFlowTreeElement element) {
		ProfilerFunctionData data = (ProfilerFunctionData) element.getData();
		try {
			if (EditorUtility.openLocalFile(data.getLocalFileName(), data.getLineNumber()) == null) {
				String url = fView.getInput().getGlobalData().getOriginalURL();
				if (!ProfilerGlobalData.URL_NOT_AVAILABLE_MSG.equals(url)) {
					// try to retrieve the file from server
					RemoteDebugger.requestRemoteFile(new OpenRemoteFileContentRequestor(), data.getAbsoluteFileName(),
							data.getLineNumber(), url);
				}
			}
		} catch (CoreException e) {
			MessageDialog.openError(fView.getViewer().getControl().getShell(),
					PHPProfileUIMessages.getString("ExecutionFlowActionGroup.2"), //$NON-NLS-1$
					NLS.bind(PHPProfileUIMessages.getString("ExecutionFlowActionGroup.3"), data.getLocalFileName())); //$NON-NLS-1$
		}
	}

	public void viewFunctionDeclarationInEditor(ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Object element = sSelection.getFirstElement();
			if (element instanceof ExecutionFlowTreeElement) {
				ExecutionFlowTreeElement executionFlowElement = (ExecutionFlowTreeElement) element;
				viewFunctionDeclarationInEditor(executionFlowElement);
			}
		}
	}

	class CollapseAllAction extends Action {

		public CollapseAllAction() {
			super(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_0")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_0")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_0")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_COLLAPSE_ALL));
		}

		@Override
		public void run() {
			fView.getViewer().getControl().setRedraw(false);
			fView.getViewer().collapseAll();
			fView.getViewer().getControl().setRedraw(true);
		}
	}

	class ExpandAllAction extends Action {

		public ExpandAllAction() {
			super(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_1")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_1")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_1")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_EXPAND_ALL));
		}

		@Override
		public void run() {
			fView.getViewer().getControl().setRedraw(false);
			fView.getViewer().expandAll();
			fView.getViewer().getControl().setRedraw(true);
		}
	}

	class SortAction extends Action implements IMenuCreator {

		private IPreferenceStore fStore;
		private Menu fMenu;

		public SortAction() {
			super(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_2"), AS_DROP_DOWN_MENU); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_2")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_2")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_SORT));
			setMenuCreator(this);
			fStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
			fView.getViewer()
					.setSorter(new ExecutionFlowSorter(fStore.getInt(PreferenceKeys.EXECUTION_FLOW_SORT_ORDER)));
		}

		@Override
		public void run() {
			final TreeViewer viewer = fView.getViewer();
			((ExecutionFlowSorter) viewer.getSorter()).setMode(fStore.getInt(PreferenceKeys.EXECUTION_FLOW_SORT_ORDER));
			BusyIndicator.showWhile(viewer.getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					viewer.getControl().setRedraw(false);
					viewer.refresh();
					viewer.getControl().setRedraw(true);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#dispose()
		 */
		@Override
		public void dispose() {
			if (fMenu != null) {
				fMenu.dispose();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets
		 * .Control)
		 */
		@Override
		public Menu getMenu(Control parent) {
			if (fMenu != null) {
				fMenu.dispose();
			}

			fMenu = new Menu(parent);
			ActionContributionItem item = new ActionContributionItem(new SortByAction(
					PHPProfileUIMessages.getString("ExecutionFlowActionGroup_3"), ExecutionFlowSorter.BY_ORDER)); //$NON-NLS-1$
			item.fill(fMenu, -1);
			item = new ActionContributionItem(
					new SortByAction(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_4"), //$NON-NLS-1$
							ExecutionFlowSorter.BY_DURATION_TIME));
			item.fill(fMenu, -1);
			return fMenu;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets
		 * .Menu)
		 */
		@Override
		public Menu getMenu(Menu parent) {
			// TODO Auto-generated method stub
			return null;
		}

		class SortByAction extends Action {
			private int fMode;

			public SortByAction(String label, int mode) {
				super(label);
				setDescription(label);
				setToolTipText(label);

				fMode = mode;
				setChecked(fStore.getInt(PreferenceKeys.EXECUTION_FLOW_SORT_ORDER) == fMode);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				fStore.setValue(PreferenceKeys.EXECUTION_FLOW_SORT_ORDER, fMode);
				SortAction.this.run();
			}
		}
	}

	class ViewFunctionCallAction extends Action {

		public ViewFunctionCallAction() {
			super(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_6")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_6")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_6")); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			viewFunctionCallInEditor(fView.getViewer().getSelection());
		}
	}

	class ViewFunctionDeclarationAction extends Action {

		public ViewFunctionDeclarationAction() {
			super(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_7")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_7")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionFlowActionGroup_7")); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			viewFunctionDeclarationInEditor(fView.getViewer().getSelection());
		}
	}
}
