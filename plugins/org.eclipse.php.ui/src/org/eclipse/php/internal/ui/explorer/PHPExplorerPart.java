/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * PHP Explorer view part to display the projects, contained files and referenced folders/libraries.
 * The view displays those in a "file-system oriented" manner, and not in a "model oriented" manner.
 * 
 * @author apeled, nirc
 *
 */
public class PHPExplorerPart extends ScriptExplorerPart {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#setFlatLayout(boolean)
	 * 
	 * Always displays in hierarchical mode, never flat.
	 */
	@Override
	public void setFlatLayout(boolean enable) {
		// TODO Auto-generated method stub
		super.setFlatLayout(false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#createContentProvider()
	 */
	@Override
	public ScriptExplorerContentProvider createContentProvider() {
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);
		if (getRootMode() == ScriptExplorerPart.PROJECTS_AS_ROOTS) {
			return new PHPExplorerContentProvider(showCUChildren) {
				protected IPreferenceStore getPreferenceStore() {
					return DLTKUIPlugin.getDefault().getPreferenceStore();
				}
			};
		} else {
			return new WorkingSetAwarePHPExplorerContentProvider(showCUChildren, getWorkingSetModel()) {
				protected IPreferenceStore getPreferenceStore() {
					return DLTKUIPlugin.getDefault().getPreferenceStore();
				}
			};
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#createLabelProvider()
	 */
	@Override
	protected ScriptExplorerLabelProvider createLabelProvider() {
		final IPreferenceStore store = DLTKUIPlugin.getDefault().getPreferenceStore();
		return new PHPExplorerLabelProvider(getContentProvider(), store);
	}

}
