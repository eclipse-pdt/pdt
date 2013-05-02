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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.util.PHPPluginImages;

public class ShowGroupsAction extends Action {

	public static final String PREF_SHOW_GROUPS = "ShowGroupsAction.show"; //$NON-NLS-1$

	private TreeViewer treeViewer;
	private IPropertyChangeListener propertyChangeListener;

	public ShowGroupsAction(String label, TreeViewer treeViewer) {
		super(label, AS_CHECK_BOX); 
		this.treeViewer = treeViewer;
		PHPPluginImages.setLocalImageDescriptors(this, "showGroups.gif"); //$NON-NLS-1$
		setToolTipText(PHPUIMessages.ShowGroupsAction_1); 

		propertyChangeListener = new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(
						PreferenceConstants.PREF_OUTLINEMODE)) {
					setEnabled(isEnabled());
				}

			}
		};
		PHPUiPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(propertyChangeListener);
		boolean show = PHPUiPlugin.getDefault().getPreferenceStore()
				.getBoolean(PREF_SHOW_GROUPS); 
		setChecked(show);
	}

	public void dispose() {
		PHPUiPlugin.getDefault().getPreferenceStore()
				.removePropertyChangeListener(propertyChangeListener);
	}

	public void run() {
		IContentProvider contentProvider = treeViewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			PHPOutlineContentProvider phpContentProvider = (PHPOutlineContentProvider) contentProvider;
			// phpContentProvider.setShowGroups(isChecked());
			treeViewer.refresh(false);
			treeViewer.expandToLevel(2);
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(
					PREF_SHOW_GROUPS, isChecked());
		}
	}

	public boolean isEnabled() {
		// IContentProvider contentProvider = treeViewer.getContentProvider();
		// if (contentProvider instanceof PHPOutlineContentProvider) {
		// PHPOutlineContentProvider phpContentProvider =
		// (PHPOutlineContentProvider) contentProvider;
		// return PHPOutlineContentProvider.MODE_PHP ==
		// phpContentProvider.getMode();
		// }
		return super.isEnabled();
	}

}
