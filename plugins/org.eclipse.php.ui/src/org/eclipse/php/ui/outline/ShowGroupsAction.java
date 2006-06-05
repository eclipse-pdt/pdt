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
package org.eclipse.php.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.PHPPluginImages;

public class ShowGroupsAction extends Action {

	public static final String PREF_SHOW_GROUPS = "ShowGroupsAction.show";

	private TreeViewer treeViewer;

	public ShowGroupsAction(String label, TreeViewer treeViewer) {
		super(label, AS_CHECK_BOX); //$NON-NLS-1$
		this.treeViewer = treeViewer;
		PHPPluginImages.setLocalImageDescriptors(this, "showGroups.gif"); //$NON-NLS-1$
		setToolTipText("Show Groups");
		PHPUiPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(ChangeOutlineModeAction.PREF_OUTLINEMODE))
				{
					setEnabled(isEnabled());
				}
				
			}});
		boolean show = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PREF_SHOW_GROUPS); //$NON-NLS-1$
		setChecked(show);
	}

	public void run() {
		IContentProvider contentProvider = treeViewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			PHPOutlineContentProvider phpContentProvider = (PHPOutlineContentProvider) contentProvider;
			phpContentProvider.setShowGroups(isChecked());
			treeViewer.refresh(false);
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(PREF_SHOW_GROUPS, isChecked());
		}
	}

	public boolean isEnabled() {
		IContentProvider contentProvider = treeViewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			PHPOutlineContentProvider phpContentProvider = (PHPOutlineContentProvider) contentProvider;
			return PHPOutlineContentProvider.MODE_PHP==phpContentProvider.getMode();
		}	
		return super.isEnabled();
	}
	
	

}
