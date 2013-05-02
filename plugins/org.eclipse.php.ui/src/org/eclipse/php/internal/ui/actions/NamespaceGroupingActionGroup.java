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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

public class NamespaceGroupingActionGroup extends ActionGroup {

	private IPreferenceStore fStore;
	private TreeViewer fViewer;

	public NamespaceGroupingActionGroup(TreeViewer viewer) {
		fViewer = viewer;
		fStore = PHPUiPlugin.getDefault().getPreferenceStore();
	}

	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		fillViewMenu(actionBar.getMenuManager());
	}

	private void fillViewMenu(IMenuManager viewMenu) {
		viewMenu.add(new Separator("namespaces")); //$NON-NLS-1$
		viewMenu.add(new GroupByNamespacesAction());
	}

	class GroupByNamespacesAction extends Action {

		public GroupByNamespacesAction() {
			super(Messages.NamespaceGroupingActionGroup_0, AS_CHECK_BOX);
			setDescription(Messages.NamespaceGroupingActionGroup_2);
			setToolTipText(Messages.NamespaceGroupingActionGroup_2);

			// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
			// IStudioHelpContextIds.EXECUTION_STATISTICS_VIEW);
			// TODO : add help context

			setChecked(fStore
					.getBoolean(PreferenceConstants.EXPLORER_GROUP_BY_NAMESPACES));
		}

		public void run() {
			final boolean on = isChecked();
			fStore.setValue(PreferenceConstants.EXPLORER_GROUP_BY_NAMESPACES,
					on);

			BusyIndicator.showWhile(fViewer.getControl().getDisplay(),
					new Runnable() {
						public void run() {
							fViewer.getControl().setRedraw(false);
							fViewer.refresh();
							fViewer.getControl().setRedraw(true);
						}
					});
		}
	}
}
