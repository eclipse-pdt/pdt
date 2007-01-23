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
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class ChangeOutlineModeAction extends Action {

	public static final String PREF_OUTLINEMODE="ChangeOutlineModeAction.selectedMode";

	
	private int mode;
	private TreeViewer treeViewer;
	
	public ChangeOutlineModeAction(String label,int mode, TreeViewer treeViewer)
	{
		super(label, AS_RADIO_BUTTON); //$NON-NLS-1$
		this.mode=mode;
		this.treeViewer=treeViewer;
		int prefMode= PHPUiPlugin.getDefault().getPreferenceStore().getInt(PREF_OUTLINEMODE); //$NON-NLS-1$
		setChecked(prefMode==mode);
	}
	
	public void run() {
		IContentProvider contentProvider = treeViewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			PHPOutlineContentProvider phpContentProvider = (PHPOutlineContentProvider) contentProvider;
			phpContentProvider.setMode(mode);
			treeViewer.refresh(false);
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(PREF_OUTLINEMODE,mode);
		}
	}
	
}
