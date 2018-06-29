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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.ui.editor.OpenRemoteFileContentRequestor;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.profile.core.data.ProfilerClassData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.actions.ExecutionStatisticsActionGroup;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilter;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFiltersRegistry;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;

/**
 * Execution statistics view.
 */
public class ExecutionStatisticsView extends AbstractProfilerFunctionsView
		implements IDoubleClickListener, ITreeViewerListener, IMenuListener, IProfileSessionListener {

	private Tree fTree;
	private TreeViewer fTreeViewer;
	private String[] fColumnHeaders = { PHPProfileUIMessages.getString("ExecutionStatisticsView_1"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsView_2"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsView_3"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsView_4"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsView_5"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsView_6"), //$NON-NLS-1$
	};
	private int[] fColumnWidths = new int[] { 200, 130, 150, 130, 130, 130 };
	private int[] fNumericColumns = new int[] { 1, 2, 3, 4, 5 };

	private ExecutionStatisticsActionGroup fActionSet;
	private ExecutionStatisticsSorter fSorter;
	private Menu fContextMenu;
	private ProfilerDB fProfilerDB;
	private IPreferenceStore fPreferenceStore;

	public ExecutionStatisticsView() {
		fPreferenceStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
	}

	private void createTable(Composite parent) {

		fTree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		fTree.setLinesVisible(true);
		fTree.setHeaderVisible(true);

		fSorter = new ExecutionStatisticsSorter();
		for (int i = 0; i < fColumnHeaders.length; ++i) {
			TreeColumn tableColumn = new TreeColumn(fTree, SWT.LEFT, i);
			tableColumn.setText(fColumnHeaders[i]);
			tableColumn.setWidth(fColumnWidths[i]);

			for (int c = 0; c < fNumericColumns.length; ++c) {
				if (fNumericColumns[c] == i) {
					tableColumn.setAlignment(SWT.RIGHT);
					break;
				}
			}

			final int field = i;
			tableColumn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fSorter.setColumn(field);

					TreeColumn[] tableColumns = fTree.getColumns();
					for (int i = 0; i < fColumnHeaders.length; ++i) {
						tableColumns[i].setImage(null);
					}
					if (fSorter.getOrder() == ProfilerUIConstants.SORT_ASCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_ASCENDING));
					} else if (fSorter.getOrder() == ProfilerUIConstants.SORT_DESCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_DESCENDING));
					}

					BusyIndicator.showWhile(fTree.getDisplay(), new Runnable() {
						@Override
						public void run() {
							fTreeViewer.getControl().setRedraw(false);
							fTreeViewer.refresh();
							fTreeViewer.getControl().setRedraw(true);
							restoreExpandedElements();
						}
					});
				}
			});
		}

		fTreeViewer = new TreeViewer(fTree);
		// fTreeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		fTreeViewer.setContentProvider(new ExecutionStatisticsContentProvider());
		fTreeViewer.setLabelProvider(new ExecutionStatisticsLabelProvider());
		fTreeViewer.setSorter(fSorter);

		String selectedFilter = fPreferenceStore.getString(PreferenceKeys.EXECUTION_STATISTICS_SELECTED_FILTER);
		if (selectedFilter.length() > 0) {
			ExecutionStatisticsFilter filter = ExecutionStatisticsFiltersRegistry.getFilterByName(selectedFilter);
			if (filter != null) {
				fTreeViewer.addFilter(filter);
			}
		}

		fTreeViewer.addDoubleClickListener(this);
		fTreeViewer.addTreeListener(this);

		fActionSet = new ExecutionStatisticsActionGroup(this);
		fActionSet.fillActionBars(getViewSite().getActionBars());

		hookContextMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		createTable(parent);
		ProfileSessionsManager.addProfileSessionListener(this);
		setInput(ProfileSessionsManager.getCurrent());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.EXECUTION_STATISTICS_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (fTreeViewer != null) {
			fTreeViewer.removeDoubleClickListener(this);
		}
		ProfileSessionsManager.removeProfileSessionListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.views.AbstractProfilerView#getInput()
	 */
	@Override
	public ProfilerDB getInput() {
		return fProfilerDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.views.AbstractProfilerView#setInput(org.
	 * eclipse. php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void setInput(ProfilerDB profilerDB) {
		if (fTreeViewer == null || fTreeViewer.getContentProvider() == null) {
			return;
		}
		if (fProfilerDB != profilerDB) {
			if (profilerDB != null) {
				ProfilerFileData[] files = profilerDB.getFiles();
				SimpleHTMLPresentableTreeElement root = new SimpleHTMLPresentableTreeElement();
				for (int i = 0; i < files.length; ++i) {
					SimpleHTMLPresentableTreeElement fileItem = new SimpleHTMLPresentableTreeElement(root, files[i]);
					fileItem.setExpanded(true);
					root.addChild(fileItem);

					ProfilerClassData[] classes = files[i].getClasses();
					for (int j = 0; j < classes.length; ++j) {
						SimpleHTMLPresentableTreeElement classItem = new SimpleHTMLPresentableTreeElement(fileItem,
								classes[j]);
						classItem.setExpanded(true);
						fileItem.addChild(classItem);

						ProfilerFunctionData[] methods = classes[j].getMethods();
						for (int k = 0; k < methods.length; ++k) {
							SimpleHTMLPresentableTreeElement element = new SimpleHTMLPresentableTreeElement(classItem,
									methods[k]);
							classItem.addChild(element);
						}
					}
					ProfilerFunctionData[] functions = files[i].getFunctions();
					for (int j = 0; j < functions.length; ++j) {
						if (functions[j].getClassName() == null) {
							SimpleHTMLPresentableTreeElement element = new SimpleHTMLPresentableTreeElement(fileItem,
									functions[j]);
							element.setExpanded(false);
							fileItem.addChild(element);
						}
					}
				}
				fTreeViewer.setInput(root);
			} else {
				fTreeViewer.setInput(null);
			}

			BusyIndicator.showWhile(fTreeViewer.getControl().getDisplay(), new Runnable() {
				@Override
				public void run() {
					fTreeViewer.getControl().setRedraw(false);
					fTreeViewer.refresh();
					fTreeViewer.getControl().setRedraw(true);
					storeExpandedElements();
				}
			});

			fProfilerDB = profilerDB;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse
	 * .jface.viewers.DoubleClickEvent)
	 */
	@Override
	public void doubleClick(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			SimpleHTMLPresentableTreeElement ExecutionStatisticsTreeElement = (SimpleHTMLPresentableTreeElement) sSelection
					.getFirstElement();
			Object element = ExecutionStatisticsTreeElement.getData();
			if (element instanceof ProfilerFunctionData) {
				ProfilerFunctionData data = (ProfilerFunctionData) element;
				try {
					if (data.getLocalFileName() == null
							|| EditorUtility.openLocalFile(data.getLocalFileName(), data.getLineNumber()) == null) {
						String url = fProfilerDB.getGlobalData().getOriginalURL();
						if (!ProfilerGlobalData.URL_NOT_AVAILABLE_MSG.equals(url)) {
							// try to retrieve the file from server
							RemoteDebugger.requestRemoteFile(new OpenRemoteFileContentRequestor(),
									data.getAbsoluteFileName(), data.getLineNumber(), url);
						}
					}
				} catch (CoreException e) {
					MessageDialog.openError(fTreeViewer.getControl().getShell(),
							PHPProfileUIMessages.getString("ExecutionStatisticsView.0"), //$NON-NLS-1$
							NLS.bind(PHPProfileUIMessages.getString("ExecutionStatisticsView.1"), //$NON-NLS-1$
									data.getLocalFileName()));
				}
			} else {
				boolean expanded = !fTreeViewer.getExpandedState(ExecutionStatisticsTreeElement);
				fTreeViewer.setExpandedState(ExecutionStatisticsTreeElement, expanded);
				fTreeViewer.getControl().setRedraw(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.profile.ui.views.AbstractProfilerFunctionsView#getViewer( )
	 */
	@Override
	public TreeViewer getViewer() {
		return fTreeViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse
	 * .jface.viewers.TreeExpansionEvent)
	 */
	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		SimpleHTMLPresentableTreeElement element = (SimpleHTMLPresentableTreeElement) event.getElement();
		element.setExpanded(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse
	 * .jface.viewers.TreeExpansionEvent)
	 */
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		SimpleHTMLPresentableTreeElement element = (SimpleHTMLPresentableTreeElement) event.getElement();
		element.setExpanded(true);
	}

	public void hookContextMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		fContextMenu = menuManager.createContextMenu(fTree);
		fTree.setMenu(fContextMenu);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * currentSessionChanged(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void currentSessionChanged(final ProfilerDB current) {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				setInput(current);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionAdded(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionAdded(ProfilerDB db) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionRemoved(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionRemoved(ProfilerDB db) {
	}
}
