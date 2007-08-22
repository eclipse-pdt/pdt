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
package org.eclipse.php.internal.debug.core.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;

/**
 * Sets default values for PHP Debug preferences
 */
public class PHPDebugCorePreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		//		IEclipsePreferences node = new DefaultScope().getNode(PHPDebugPlugin.getDefault().getBundle().getSymbolicName());
		Preferences preferences = PHPDebugPlugin.getDefault().getPluginPreferences();
		// formatting preferences
		preferences.setDefault(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, true);
		preferences.setDefault(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, true);
		preferences.setDefault(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, true);
		preferences.setDefault(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, true);
		preferences.setDefault(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 10000);
		preferences.setDefault(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, "UTF-8");
		preferences.setDefault(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, "UTF-8");
		preferences.setDefault(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, PHPExecutableLaunchDelegate.class.getName());
		preferences.setDefault(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID); // The default is Zend's debugger
		preferences.setDefault(IPHPConstants.PHP_DEBUG_PARAMETERS_INITIALIZER, "org.eclipse.php.debug.core.defaultInitializer"); //$NON-NLS-1$
	}
}
