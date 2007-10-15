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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPElementSorter;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

public class SortAction extends Action {

	public static final String PREF_IS_SORTED = "SortingAction.isChecked"; //$NON-NLS-1$
	private TreeViewer treeViewer;
	private PHPElementSorter fSorter;

	public SortAction(TreeViewer treeViewer) {
		super();

		fSorter = new PHPElementSorter();
		fSorter.setUsingCategories(false);
		fSorter.setUsingLocation(true);

		this.treeViewer = treeViewer;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.SORTING_OUTLINE_ACTION);
		setText(PHPUIMessages.getString("PHPOutlinePage_Sort_label"));
		PHPPluginImages.setLocalImageDescriptors(this, "alphab_sort_co.gif"); //$NON-NLS-1$
		setToolTipText(PHPUIMessages.getString("PHPOutlinePage_Sort_tooltip"));
		setDescription(PHPUIMessages.getString("PHPOutlinePage_Sort_description"));
		treeViewer.setSorter(fSorter);

		boolean checked = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PREF_IS_SORTED); //$NON-NLS-1$
		valueChanged(checked, false);
	}

	public void run() {
		valueChanged(isChecked(), true);
	}

	private void valueChanged(final boolean on, boolean store) {
		setChecked(on);
		BusyIndicator.showWhile(treeViewer.getControl().getDisplay(), new Runnable() {
			public void run() {
				fSorter.setUsingLocation(!on);
				treeViewer.refresh();
				treeViewer.expandToLevel(2);
			}
		});

		if (store)
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(PREF_IS_SORTED, on); //$NON-NLS-1$
	}
}
