/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.debug.ui;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.preferences.phps.PHPExeVerifier;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.BackingStoreException;

/**
 * This class is intended to perform early startup of a debugger plug-ins. The
 * main goal of PDT debugger plug-ins early startup is to trigger the background
 * thread that performs all of the heavy weight operations right after the
 * workbench startup.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class DebugEarlyStartup implements IStartup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		// Overwrite "Wait for ongoing build to complete before launching"
		IEclipsePreferences phpPrefs = PHPDebugPlugin.getInstancePreferences();
		boolean overwriteWaitForBuild = phpPrefs.getBoolean(PHPDebugCorePreferenceNames.OVERWRITE_WAIT_FOR_BUILD, true);
		if (overwriteWaitForBuild) {
			// It will be done only once for the instance scope
			phpPrefs.putBoolean(PHPDebugCorePreferenceNames.OVERWRITE_WAIT_FOR_BUILD, false);
			IEclipsePreferences debugPrefs = InstanceScope.INSTANCE.getNode(DebugUIPlugin.getUniqueIdentifier());
			debugPrefs.put(IInternalDebugUIConstants.PREF_WAIT_FOR_BUILD, MessageDialogWithToggle.PROMPT);
			try {
				debugPrefs.flush();
				phpPrefs.flush();
			} catch (BackingStoreException e) {
			}
		}
		// Verify all of the available PHP executables
		PHPExeVerifier.verify(PHPexes.getInstance().getAllItems());
	}

}
