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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IMarkerResolution;

/**
 * Quick fix to define a new system library (none were found).
 */
public class ShowPHPsPreferences implements IMarkerResolution {

	public ShowPHPsPreferences() {
		super();
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	public void run(IMarker marker) {
		IPreferencePage page = new PHPsPreferencePage();
		showPreferencePage(PHPsPreferencePage.ID, page); 
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	public String getLabel() {
		return PHPDebugUIMessages.ShowPHPsPreferencePageTitle; 
	}

	protected void showPreferencePage(String id, IPreferencePage page) {
		final IPreferenceNode targetNode = new PreferenceNode(id, page);

		PreferenceManager manager = new PreferenceManager();
		manager.addToRoot(targetNode);
		final PreferenceDialog dialog = new PreferenceDialog(PHPDebugUIPlugin
				.getActiveWorkbenchShell(), manager);
		final boolean[] result = new boolean[] { false };
		BusyIndicator.showWhile(PHPDebugUIPlugin.getStandardDisplay(),
				new Runnable() {
					public void run() {
						dialog.create();
						dialog.setMessage(targetNode.getLabelText());
						result[0] = (dialog.open() == Window.OK);
					}
				});
	}
}
