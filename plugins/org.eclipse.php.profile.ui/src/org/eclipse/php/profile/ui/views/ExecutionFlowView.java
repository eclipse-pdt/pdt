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
package org.eclipse.php.profile.ui.views;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.actions.ExecutionFlowActionGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;

/**
 * Execution flow view.
 */
public class ExecutionFlowView extends AbstractProfilerFunctionsView
		implements IDoubleClickListener, IMenuListener, IProfileSessionListener {

	private TreeViewer fViewer;
	private String fColumnHeaders[] = new String[] { PHPProfileUIMessages.getString("ExecutionFlowView_0"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionFlowView_1"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionFlowView_2"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionFlowView_3") //$NON-NLS-1$
	};
	private int[] fColumnWidths = new int[] { 300, 200, 150, 150 };
	private int[] fNumericColumns = new int[] { 2, 3 };
	private ExecutionFlowActionGroup fActionSet;
	private Tree fTree;
	private Menu fContextMenu;
	private ProfilerDB fProfilerDB;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		fTree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		fTree.setLinesVisible(true);
		fTree.setHeaderVisible(true);

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
		}

		fViewer = new TreeViewer(fTree);
		fViewer.setContentProvider(new ExecutionFlowContentProvider());
		fViewer.setLabelProvider(new ExecutionFlowLabelProvider());

		fViewer.addDoubleClickListener(this);

		fActionSet = new ExecutionFlowActionGroup(this);
		fActionSet.fillActionBars(getViewSite().getActionBars());

		hookContextMenu();

		ProfileSessionsManager.addProfileSessionListener(this);
		setInput(ProfileSessionsManager.getCurrent());

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.EXECUTION_FLOW_VIEW);
	}

	@Override
	public void dispose() {
		if (fViewer != null) {
			fViewer.removeDoubleClickListener(this);
		}
		ProfileSessionsManager.removeProfileSessionListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public ProfilerDB getInput() {
		return fProfilerDB;
	}

	@Override
	public void setInput(ProfilerDB profilerDB) {
		if (fViewer == null || fViewer.getContentProvider() == null) {
			return;
		}
		if (fProfilerDB != profilerDB) {
			if (profilerDB != null) {
				ProfilerCallTrace callTrace = profilerDB.getCallTrace();
				if (callTrace != null) {
					ProfilerCallTraceLayer[] layers = callTrace.getLayers();

					ExecutionFlowTreeElement current = null;
					ExecutionFlowTreeElement root = new ExecutionFlowTreeElement();
					ArrayList<ExecutionFlowTreeElement> elements = new ArrayList<>();
					for (int i = 0; i < layers.length; ++i) {
						if (layers[i].getType() == ProfilerCallTraceLayer.ENTER) { // enter
							// function
							ExecutionFlowTreeElement element = new ExecutionFlowTreeElement(i);
							element.setData(profilerDB.getFunctionData(layers[i].getCalledID()));
							element.setLayer(layers[i]);
							if (current != null) {
								current.addChild(element);
								element.setParent(current);
							}
							current = element;
							elements.add(element);
						} else { // exit function
							if (current != null) {
								ProfilerCallTraceLayer currentLayer = current.getLayer();
								currentLayer.setDuration(layers[i].getTimestampSeconds(),
										layers[i].getTimestampMicroseconds());
								current.setDuration(currentLayer.getDurationInMilli());
								if (current.getParent() != null) {
									current = (ExecutionFlowTreeElement) current.getParent();
								}
							}
						}
					}

					if (current != null) { // current element is a top element
											// (main
						// function)

						double totalDuration = profilerDB.getGlobalData().getGlobalTimeInMilli();
						for (int i = 0; i < elements.size(); ++i) {
							ExecutionFlowTreeElement element = elements.get(i);
							element.setTimePercentage(element.getDuration() / totalDuration * 100);
						}

						root.addChild(current);
						current.setParent(root);

						fViewer.setInput(root);
					}
				}
			} else {
				fViewer.setInput(null);
			}

			fViewer.getControl().setRedraw(false);
			fViewer.refresh();
			fViewer.getControl().setRedraw(true);
			fProfilerDB = profilerDB;
		}
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		fActionSet.viewFunctionCallInEditor(event.getSelection());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.profile.ui.views.AbstractProfilerFunctionsView#getViewer( )
	 */
	@Override
	public TreeViewer getViewer() {
		return fViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse
	 * .jface.viewers.TreeExpansionEvent)
	 */
	public void treeCollapsed(TreeExpansionEvent event) {
		TreeElement element = (TreeElement) event.getElement();
		element.setExpanded(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse
	 * .jface.viewers.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent event) {
		TreeElement element = (TreeElement) event.getElement();
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
