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
package org.eclipse.php.profile.ui.actions;

import org.eclipse.jface.action.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.dialogs.FiltersDialog;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilter;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFiltersRegistry;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.php.profile.ui.views.ExecutionStatisticsView;
import org.eclipse.php.profile.ui.views.TreeElement;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Execution statistics action group.
 */
public class ExecutionStatisticsActionGroup extends ActionGroup {

	private static final String GROUP_KEY = "ExecutionStatisticsActionGroup.group"; //$NON-NLS-1$

	ExecutionStatisticsView fView;
	private IPreferenceStore fStore;
	private CollapseAllAction fCollapseAllAction;
	private ExpandAllAction fExpandAllAction;
	private ShowAsPersentageAction fShowAsPercentageAction;
	private GroupByFileAction fGroupByFileAction;
	private GroupByClassAction fGroupByClassAction;
	private GroupByFunctionAction fGroupByFunctionAction;
	private OpenFunctionInvocationStatisticsAction fOpenFunctionInvocationStatisticsAction;
	private FilterAction fFilterAction;

	public ExecutionStatisticsActionGroup(ExecutionStatisticsView view) {
		fView = view;
		fStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
		fExpandAllAction = new ExpandAllAction();
		fCollapseAllAction = new CollapseAllAction();
		fShowAsPercentageAction = new ShowAsPersentageAction();
		fGroupByFileAction = new GroupByFileAction();
		fGroupByClassAction = new GroupByClassAction();
		fGroupByFunctionAction = new GroupByFunctionAction();
		fOpenFunctionInvocationStatisticsAction = new OpenFunctionInvocationStatisticsAction(fView);
		fFilterAction = new FilterAction();
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);

		IToolBarManager toolbarManager = actionBars.getToolBarManager();
		toolbarManager.add(fFilterAction);
		toolbarManager.add(new Separator());
		toolbarManager.add(fExpandAllAction);
		toolbarManager.add(fCollapseAllAction);
		toolbarManager.add(new Separator());
		toolbarManager.add(fShowAsPercentageAction);
		toolbarManager.add(new Separator(GROUP_KEY));
		toolbarManager.appendToGroup(GROUP_KEY, fGroupByFileAction);
		toolbarManager.appendToGroup(GROUP_KEY, fGroupByClassAction);
		toolbarManager.appendToGroup(GROUP_KEY, fGroupByFunctionAction);
		toolbarManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		updateGroups();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		ISelection selection = fView.getViewer().getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			TreeElement element = (TreeElement) ((IStructuredSelection) selection).getFirstElement();
			Object data = element.getData();
			if (data instanceof ProfilerFunctionData) {
				menu.add(fOpenFunctionInvocationStatisticsAction);
			} else if (data instanceof ProfilerFileData && ((ProfilerFileData) data).getCodeCoverageData() != null) {
				// menu.add(fOpenCodeCoverageViewAction);
			}
		}
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void updateGroups() {
		int groupBy = fStore.getInt(PreferenceKeys.EXECUTION_VIEW_GROUP_BY);
		fGroupByFileAction.setChecked(groupBy == ProfilerUIConstants.GROUP_BY_FILE);
		fGroupByClassAction.setChecked(groupBy == ProfilerUIConstants.GROUP_BY_CLASS);
		fGroupByFunctionAction.setChecked(groupBy == ProfilerUIConstants.GROUP_BY_FUNCTION);
	}

	class ShowAsPersentageAction extends Action {

		public ShowAsPersentageAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_0"), AS_CHECK_BOX); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_0")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_0")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_PERCENTAGE));
			setChecked(fStore.getBoolean(PreferenceKeys.EXECUTION_VIEW_SHOW_AS_PERCENTAGE));
		}

		@Override
		public void run() {
			final TreeViewer viewer = fView.getViewer();
			final boolean on = isChecked();
			fStore.setValue(PreferenceKeys.EXECUTION_VIEW_SHOW_AS_PERCENTAGE, on);

			BusyIndicator.showWhile(viewer.getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					viewer.getControl().setRedraw(false);
					viewer.refresh();
					viewer.getControl().setRedraw(true);
				}
			});
		}
	}

	class CollapseAllAction extends Action {
		public CollapseAllAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_1")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_1")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_1")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_COLLAPSE_ALL));
		}

		@Override
		public void run() {
			BusyIndicator.showWhile(fView.getViewer().getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					fView.getViewer().getControl().setRedraw(false);
					fView.getViewer().collapseAll();
					fView.getViewer().getControl().setRedraw(true);
					fView.storeExpandedElements();
				}
			});
		}
	}

	class ExpandAllAction extends Action {

		public ExpandAllAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_2")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_2")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_2")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_EXPAND_ALL));
		}

		@Override
		public void run() {
			BusyIndicator.showWhile(fView.getViewer().getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					fView.getViewer().getControl().setRedraw(false);
					fView.getViewer().expandAll();
					fView.getViewer().getControl().setRedraw(true);
					fView.storeExpandedElements();
				}
			});
		}
	}

	class GroupByFileAction extends Action {

		public GroupByFileAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_3"), AS_CHECK_BOX); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_3")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_3")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_GROUP_BY_FILE));
		}

		@Override
		public void run() {
			if (isChecked()) {
				fStore.setValue(PreferenceKeys.EXECUTION_VIEW_GROUP_BY, ProfilerUIConstants.GROUP_BY_FILE);
				BusyIndicator.showWhile(fView.getViewer().getControl().getDisplay(), new Runnable() {
					@Override
					public void run() {
						fView.getViewer().getControl().setRedraw(false);
						fView.getViewer().refresh();
						fView.getViewer().getControl().setRedraw(true);
						fView.restoreExpandedElements();
					}
				});
			}
			updateGroups();
		}
	}

	class GroupByFunctionAction extends Action {
		public GroupByFunctionAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_4"), AS_CHECK_BOX); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_4")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_4")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_GROUP_BY_FUNCTION));
		}

		@Override
		public void run() {
			if (isChecked()) {
				fStore.setValue(PreferenceKeys.EXECUTION_VIEW_GROUP_BY, ProfilerUIConstants.GROUP_BY_FUNCTION);
				BusyIndicator.showWhile(fView.getViewer().getControl().getDisplay(), new Runnable() {
					@Override
					public void run() {
						fView.getViewer().getControl().setRedraw(false);
						fView.getViewer().refresh();
						fView.getViewer().getControl().setRedraw(true);
						fView.restoreExpandedElements();
					}
				});
			}
			updateGroups();
		}
	}

	class GroupByClassAction extends Action {
		public GroupByClassAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_5"), AS_CHECK_BOX); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_5")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_5")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_GROUP_BY_CLASS));
		}

		@Override
		public void run() {
			if (isChecked()) {
				fStore.setValue(PreferenceKeys.EXECUTION_VIEW_GROUP_BY, ProfilerUIConstants.GROUP_BY_CLASS);
				BusyIndicator.showWhile(fView.getViewer().getControl().getDisplay(), new Runnable() {
					@Override
					public void run() {
						fView.getViewer().getControl().setRedraw(false);
						fView.getViewer().refresh();
						fView.getViewer().getControl().setRedraw(true);
						fView.restoreExpandedElements();
					}
				});
			}
			updateGroups();
		}
	}

	class FilterAction extends Action implements IMenuCreator {
		private Menu fMenu;
		private ExecutionStatisticsFilter[] fFilters;
		private IPreferenceStore fPreferenceStore;
		private ExecutionStatisticsFilter fNoFilter;

		public FilterAction() {
			super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_6"), AS_DROP_DOWN_MENU); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_6")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup_6")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_FILTER));
			setMenuCreator(this);
			fPreferenceStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
			fFilters = ExecutionStatisticsFiltersRegistry.getFilters();
			fNoFilter = new ExecutionStatisticsFilter();
			fNoFilter.setName(""); //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			editFilters();
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

		private void removeOldFilters() {
			ViewerFilter[] filters = fView.getViewer().getFilters();
			for (int i = 0; i < filters.length; ++i) {
				if (filters[i] instanceof ExecutionStatisticsFilter) {
					fView.getViewer().removeFilter(filters[i]);
				}
			}
		}

		private void editFilters() {
			FiltersDialog dialog = new FiltersDialog(fView.getSite().getShell());
			if (dialog.open() == Window.OK) {
				fFilters = dialog.getFilters();
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
			ActionContributionItem item;

			for (int i = 0; i < FilterAction.this.fFilters.length; ++i) {
				item = new ActionContributionItem(new ApplyFilterAction(FilterAction.this.fFilters[i]));
				item.fill(fMenu, -1);
			}
			item = new ActionContributionItem(new ApplyFilterAction(fNoFilter,
					PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup.7"))); //$NON-NLS-1$
			item.fill(fMenu, -1);

			new Separator().fill(fMenu, -1);
			item = new ActionContributionItem(new EditFiltersAction());
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

		class EditFiltersAction extends Action {
			public EditFiltersAction() {
				super(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup.0")); //$NON-NLS-1$
				setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup.1")); //$NON-NLS-1$
				setToolTipText(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup.2")); //$NON-NLS-1$
				setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_FILTER));
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				FilterAction.this.editFilters();
			}
		}

		class ApplyFilterAction extends Action {
			private ExecutionStatisticsFilter fFilter;

			public ApplyFilterAction(ExecutionStatisticsFilter filter) {
				this(filter, filter.getName());
			}

			public ApplyFilterAction(ExecutionStatisticsFilter filter, String filterName) {
				super("  " + filterName, AS_RADIO_BUTTON); //$NON-NLS-1$
				setDescription(PHPProfileUIMessages.getString("ExecutionStatisticsActionGroup.5")); //$NON-NLS-1$
				setToolTipText(filterName);
				fFilter = filter;
				if (fFilter.getName().equals(FilterAction.this.fPreferenceStore
						.getString(PreferenceKeys.EXECUTION_STATISTICS_SELECTED_FILTER))) {
					setChecked(true);
				}
			}

			@Override
			public void run() {
				FilterAction.this.removeOldFilters();

				if (fFilter.getName().length() > 0) {
					fView.getViewer().addFilter(fFilter);
				}
				FilterAction.this.fPreferenceStore.setValue(PreferenceKeys.EXECUTION_STATISTICS_SELECTED_FILTER,
						fFilter.getName());
			}
		}
	}
}
