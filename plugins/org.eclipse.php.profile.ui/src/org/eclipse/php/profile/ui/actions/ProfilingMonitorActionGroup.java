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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.php.profile.ui.views.AbstractProfilerView;
import org.eclipse.php.profile.ui.views.ProfilingMonitorElement;
import org.eclipse.php.profile.ui.views.ProfilingMonitorSorter;
import org.eclipse.php.profile.ui.views.ProfilingMonitorViewElement;
import org.eclipse.php.profile.ui.wizards.ExportSessionWizard;
import org.eclipse.php.profile.ui.wizards.HTMLReportWizard;
import org.eclipse.php.profile.ui.wizards.ImportSessionWizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Profiler monitoring action group.
 */
public class ProfilingMonitorActionGroup extends ActionGroup {

	private TreeViewer fTreeViewer;
	private DeleteAction fDeleteAction;
	private ImportAction fImportAction;
	private ExportAction fExportAction;
	private OpenViewAction fOpenViewAction;
	private ReportViewAction fReportViewAction;
	private SortAction fSortAction;

	public ProfilingMonitorActionGroup(TreeViewer treeViewer) {
		fTreeViewer = treeViewer;

		fDeleteAction = new DeleteAction();
		fImportAction = new ImportAction();
		fExportAction = new ExportAction();
		fOpenViewAction = new OpenViewAction();
		fReportViewAction = new ReportViewAction();
		fSortAction = new SortAction();

		updateSelectionDependentActions(fTreeViewer.getSelection());
	}

	public void updateSelectionDependentActions(ISelection selection) {
		fDeleteAction.update(selection);
		fExportAction.update(selection);
		fOpenViewAction.update(selection);
		fReportViewAction.update(selection);
	}

	private void doOpenView(ProfilingMonitorViewElement viewElement) {
		IWorkbenchPage page = ProfilerUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IViewPart view = page.showView(viewElement.getViewId());
			if (view instanceof AbstractProfilerView) {
				((AbstractProfilerView) view).setInput(viewElement.getParent().getProfilerDB());
			}
		} catch (PartInitException e) {
			ProfilerUiPlugin.log(e);
		}
	}

	public void handlerDoubleClick(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Object element = sSelection.getFirstElement();
			if (element instanceof ProfilingMonitorElement) {
				boolean expanded = !fTreeViewer.getExpandedState(element);
				fTreeViewer.setExpandedState(element, expanded);
				fTreeViewer.getControl().setRedraw(true);
			} else if (element instanceof ProfilingMonitorViewElement) {
				doOpenView((ProfilingMonitorViewElement) element);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.
	 * IActionBars )
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);

		IToolBarManager toolbarManager = actionBars.getToolBarManager();
		toolbarManager.add(fDeleteAction);
		toolbarManager.add(new Separator());
		toolbarManager.add(fSortAction);
		toolbarManager.add(new Separator());

		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), fDeleteAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		ISelection selection = fTreeViewer.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element == null) {
				menu.add(fImportAction);
			} else if (element instanceof ProfilingMonitorElement) {
				menu.add(fDeleteAction);
				menu.add(new Separator());
				menu.add(fImportAction);
				menu.add(fExportAction);
				menu.add(new Separator());
				menu.add(fReportViewAction);
			} else if (element instanceof ProfilingMonitorViewElement) {
				menu.add(fOpenViewAction);
				menu.add(new Separator());
				menu.add(fReportViewAction);
			}
		}
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private Object[] getSelectedElements(ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			return sSelection.toArray();
		}
		return null;
	}

	private boolean profileSessionsSelected(ISelection selection) {
		Object[] elements = getSelectedElements(selection);
		boolean selected = elements.length > 0;
		for (int i = 0; i < elements.length; ++i) {
			if (!(elements[i] instanceof ProfilingMonitorElement)) {
				selected = false;
			}
		}
		return selected;
	}

	class DeleteAction extends Action {
		public DeleteAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.0")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.1")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.2")); //$NON-NLS-1$
			ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
			setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
			setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		}

		@Override
		public void run() {
			if (MessageDialog.openConfirm(fTreeViewer.getTree().getShell(),
					PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.25"), //$NON-NLS-1$
					PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.20"))) { //$NON-NLS-1$
				Object[] elements = getSelectedElements(fTreeViewer.getSelection());
				for (int i = 0; i < elements.length; ++i) {
					if (elements[i] instanceof ProfilingMonitorElement) {
						ProfileSessionsManager.removeSession(((ProfilingMonitorElement) elements[i]).getProfilerDB());
					}
				}
			}
		}

		public void update(ISelection selection) {
			setEnabled(profileSessionsSelected(selection));
		}
	}

	class ImportAction extends Action {
		public ImportAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.3")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.4")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ETOOL_IMPORT_WIZ));
		}

		@Override
		public void run() {
			ImportSessionWizard wizard = new ImportSessionWizard();
			wizard.init(PlatformUI.getWorkbench(), null);

			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.create();
			dialog.open();
		}
	}

	class ExportAction extends Action {
		public ExportAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.5")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.6")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ETOOL_EXPORT_WIZ));
		}

		@Override
		public void run() {
			ExportSessionWizard wizard = new ExportSessionWizard();
			wizard.init(PlatformUI.getWorkbench(), null);

			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.create();
			dialog.open();
		}

		public void update(ISelection selection) {
			setEnabled(profileSessionsSelected(selection));
		}
	}

	class OpenViewAction extends Action {
		public OpenViewAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.7")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.8")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			ISelection selection = fTreeViewer.getSelection();
			if (selection != null && selection instanceof IStructuredSelection) {
				Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof ProfilingMonitorViewElement) {
					doOpenView((ProfilingMonitorViewElement) element);
				}
			}
		}

		public void update(ISelection selection) {
			boolean enabled = false;
			if (selection != null && selection instanceof IStructuredSelection) {
				Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof ProfilingMonitorViewElement) {
					enabled = true;
				}
			}
			setEnabled(enabled);
		}
	}

	class ReportViewAction extends Action {
		public ReportViewAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.13")); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.14")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_OBJ_REPORT));
		}

		@Override
		public void run() {
			HTMLReportWizard wizard = new HTMLReportWizard();
			wizard.init(PlatformUI.getWorkbench(), null);

			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.create();
			dialog.open();
		}

		public void update(ISelection selection) {
			boolean enabled = false;
			if (selection != null && selection instanceof IStructuredSelection) {
				IStructuredSelection sSelection = ((IStructuredSelection) selection);
				if (sSelection.size() == 1) {
					Object element = sSelection.getFirstElement();
					if (element instanceof ProfilingMonitorViewElement || element instanceof ProfilingMonitorElement) {
						enabled = true;
					}
				}
			}
			setEnabled(enabled);
		}
	}

	class SortAction extends Action implements IMenuCreator {

		private IPreferenceStore fStore;
		private Menu fMenu;

		public SortAction() {
			super(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.15"), AS_DROP_DOWN_MENU); //$NON-NLS-1$
			setDescription(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.16")); //$NON-NLS-1$
			setToolTipText(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.17")); //$NON-NLS-1$
			setImageDescriptor(ProfilerUIImages.getImageDescriptor(ProfilerUIImages.IMG_ELCL_SORT));
			setMenuCreator(this);

			fStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
			fTreeViewer
					.setSorter(new ProfilingMonitorSorter(fStore.getInt(PreferenceKeys.PROFILING_MONITOR_SORT_ORDER)));
		}

		@Override
		public void run() {
			((ProfilingMonitorSorter) fTreeViewer.getSorter())
					.setMode(fStore.getInt(PreferenceKeys.PROFILING_MONITOR_SORT_ORDER));
			BusyIndicator.showWhile(fTreeViewer.getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					fTreeViewer.refresh();
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
					PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.18"), ProfilingMonitorSorter.BY_DATE)); //$NON-NLS-1$
			item.fill(fMenu, -1);
			item = new ActionContributionItem(
					new SortByAction(PHPProfileUIMessages.getString("ProfilingMonitorActionGroup.19"), //$NON-NLS-1$
							ProfilingMonitorSorter.BY_FILENAME));
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
				setChecked(fStore.getInt(PreferenceKeys.PROFILING_MONITOR_SORT_ORDER) == fMode);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				fStore.setValue(PreferenceKeys.PROFILING_MONITOR_SORT_ORDER, fMode);
				SortAction.this.run();
			}
		}
	}

}
