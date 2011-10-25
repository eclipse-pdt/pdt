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
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.internal.ui.scriptview.WorkingSetDropAdapter;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.ui.actions.ConfigureWorkingSetAction;
import org.eclipse.php.internal.ui.actions.PHPExplorerActionGroup;
import org.eclipse.php.internal.ui.dnd.PHPNavigatorDropAdapter;
import org.eclipse.php.internal.ui.dnd.PHPViewerDropSupport;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider.IncludePathContainer;
import org.eclipse.php.internal.ui.util.NamespaceNode;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import org.eclipse.ui.views.navigator.NavigatorDropAdapter;

/**
 * PHP Explorer view part to display the projects, contained files and
 * referenced folders/libraries. The view displays those in a
 * "file-system oriented" manner, and not in a "model oriented" manner.
 * 
 * @author apeled, ncohen
 * 
 */
public class PHPExplorerPart extends ScriptExplorerPart {

	protected class PHPExplorerElementSorter extends ModelElementSorter {
		private static final int INCLUDE_PATH_CONTAINER = 59;

		public int category(Object element) {
			if (element instanceof IncludePathContainer)
				return INCLUDE_PATH_CONTAINER;
			else
				return super.category(element);
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			// Put Include Path node to the bottom:
			if (e1 instanceof IncludePath || e2 instanceof IncludePath) {
				return -1;
			}

			if (e1 instanceof NamespaceNode && e2 instanceof NamespaceNode) {
				return ((NamespaceNode) e1).getElementName().compareTo(
						((NamespaceNode) e2).getElementName());
			}

			// Fix #256585 - sort by resource name
			Object c1 = e1;
			if (e1 instanceof ISourceModule) {
				c1 = ((ISourceModule) e1).getResource();
			}
			Object c2 = e2;
			if (e2 instanceof ISourceModule) {
				c2 = ((ISourceModule) e2).getResource();
			}
			if (c1 != null && c2 != null) {
				return super.compare(viewer, c1, c2);
			}
			return super.compare(viewer, e1, e2);
		}
	}

	protected class PHPExplorerWorkingSetAwareModelElementSorter extends
			PHPExplorerElementSorter {

		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof IWorkingSet || e2 instanceof IWorkingSet)
				return 0;

			return super.compare(viewer, e1, e2);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#setFlatLayout
	 * (boolean)
	 * 
	 * Always displays in hierarchical mode, never flat.
	 */
	@Override
	public void setFlatLayout(boolean enable) {
		super.setFlatLayout(false);
	}

	protected void configWorkingSet() {
		ScriptExplorerActionGroup actionSet = getActionGroup();
		WorkingSetModel workingSetModel = getWorkingSetModel();
		if (actionSet != null) {

			actionSet.getWorkingSetActionGroup().setWorkingSetModel(
					workingSetModel);
		}

		ConfigureWorkingSetAction action = new ConfigureWorkingSetAction(
				getSite());
		action.setWorkingSetModel(workingSetModel);
		action.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#
	 * createContentProvider()
	 */
	@Override
	public ScriptExplorerContentProvider createContentProvider() {
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);
		if (getRootMode() == ScriptExplorerPart.PROJECTS_AS_ROOTS) {
			return new PHPExplorerContentProvider(showCUChildren) {
				protected IPreferenceStore getPreferenceStore() {
					return DLTKUIPlugin.getDefault().getPreferenceStore();
				}
			};
		} else {
			return new WorkingSetAwarePHPExplorerContentProvider(
					showCUChildren, getWorkingSetModel()) {
				protected IPreferenceStore getPreferenceStore() {
					return DLTKUIPlugin.getDefault().getPreferenceStore();
				}
			};
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#
	 * createLabelProvider()
	 */
	@Override
	protected ScriptExplorerLabelProvider createLabelProvider() {
		final IPreferenceStore store = DLTKUIPlugin.getDefault()
				.getPreferenceStore();
		return new PHPExplorerLabelProvider(getContentProvider(), store);
	}

	/**
	 * Overriding DTLK original setComerator, and setting
	 * "includePathContainer - aware" comparators
	 */
	@Override
	protected void setComparator() {
		if (showWorkingSets()) {
			PHPExplorerWorkingSetAwareModelElementSorter comparator = new PHPExplorerWorkingSetAwareModelElementSorter();
			getTreeViewer().setComparator(comparator);
		} else {
			ModelElementSorter comparator = new PHPExplorerElementSorter();
			getTreeViewer().setComparator(comparator);
		}
	}

	@Override
	protected ScriptExplorerActionGroup getActionGroup() {
		/*
		 * setting our own PDT action group, based on DLTK's ScriptExplorer
		 * action-group, but also adding "include path" actions
		 */
		return new PHPExplorerActionGroup(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createPartControl(parent);
	}

	private void initDragAndDrop() {
		int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK
				| DND.DROP_DEFAULT;

		Transfer[] transfers = new Transfer[] {
				LocalSelectionTransfer.getInstance(),
				FileTransfer.getInstance(), PluginTransfer.getInstance() };

		TreeViewer viewer = getTreeViewer();
		// viewer.addDragSupport(ops, transfers, new
		// NavigatorDragAdapter(viewer));
		NavigatorDropAdapter adapter = new PHPNavigatorDropAdapter(viewer);
		adapter.setFeedbackEnabled(true);
		viewer.addDropSupport(ops, transfers, adapter);
	}

	protected void initDrop() {
		PHPViewerDropSupport dropSupport = new PHPViewerDropSupport(
				getTreeViewer());
		dropSupport.addDropTargetListener(new WorkingSetDropAdapter(this));
		dropSupport.start();
	}
}
