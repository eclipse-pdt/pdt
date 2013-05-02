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

import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.dltk.ui.viewsupport.SourcePositionSorter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.outline.PHPOutlineContentProvider.UseStatementsNode;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

public class SortAction extends Action {

	public static final String PREF_IS_SORTED = "SortingAction.isChecked"; //$NON-NLS-1$
	private TreeViewer treeViewer;
	private ModelElementSorter fComparator = new ModelElementSorter() {
		public int compare(org.eclipse.jface.viewers.Viewer viewer, Object e1,
				Object e2) {

			if (e1 instanceof UseStatementsNode) {
				return -1;
			} else if (e2 instanceof UseStatementsNode) {
				return 1;
			} else {
				return super.compare(viewer, e1, e2);
			}
		};
	};
	private SourcePositionSorter fSourcePositonComparator = new SourcePositionSorter();

	public SortAction(TreeViewer treeViewer) {
		super();

		this.treeViewer = treeViewer;
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(this, IPHPHelpContextIds.OUTLINE_VIEW);
		setText(PHPUIMessages.PHPOutlinePage_Sort_label);
		PHPPluginImages.setLocalImageDescriptors(this, "alphab_sort_co.gif"); //$NON-NLS-1$
		setToolTipText(PHPUIMessages.PHPOutlinePage_Sort_tooltip);
		setDescription(PHPUIMessages.PHPOutlinePage_Sort_description);

		boolean checked = PHPUiPlugin.getDefault().getPreferenceStore()
				.getBoolean(PREF_IS_SORTED); 
		valueChanged(checked, false);
	}

	public void run() {
		valueChanged(isChecked(), true);
	}

	private void valueChanged(final boolean on, boolean store) {
		setChecked(on);
		BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
				new Runnable() {
					public void run() {
						if (on) {
							treeViewer.setComparator(fComparator);
							// fDropSupport.setFeedbackEnabled(false);
						} else {
							treeViewer.setComparator(fSourcePositonComparator);
							// fDropSupport.setFeedbackEnabled(true);
						}
					}
				});

		if (store)
			PHPUiPlugin.getDefault().getPreferenceStore()
					.setValue(PREF_IS_SORTED, on); 
	}
}
