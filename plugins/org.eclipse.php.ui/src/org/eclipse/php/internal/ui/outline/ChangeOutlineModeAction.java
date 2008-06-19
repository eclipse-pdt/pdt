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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

public class ChangeOutlineModeAction extends Action {

	private int mode;
	private PHPContentOutlineConfiguration contentOutlineConfiguration;
	private TreeViewer viewer;
	
	public ChangeOutlineModeAction(String label,int mode, PHPContentOutlineConfiguration contentOutlineConfiguration, TreeViewer viewer)
	{
		super(label, AS_RADIO_BUTTON); //$NON-NLS-1$
		this.mode=mode;
		this.contentOutlineConfiguration=contentOutlineConfiguration;
		this.viewer=viewer;
		int prefMode= PHPUiPlugin.getDefault().getPreferenceStore().getInt(PreferenceConstants.PREF_OUTLINEMODE); //$NON-NLS-1$
		setChecked(prefMode==mode);
	}
	
	public void run() {
		
		contentOutlineConfiguration.setMode(mode);
		contentOutlineConfiguration.getContentProvider(viewer);
		viewer.refresh(false);
		PHPUiPlugin.getDefault().getPreferenceStore().setValue(PreferenceConstants.PREF_OUTLINEMODE, mode);
	}
	
}
