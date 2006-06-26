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
package org.eclipse.php.debug.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;

/**
 * Sets default values for PHP Debug preferences
 */
public class PHPDebugCorePreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(PHPDebugPlugin.getDefault().getBundle().getSymbolicName());

		// formatting preferences
		node.putBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, false);
		node.putBoolean(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE, true);
		node.putBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, true);
		node.putBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, false);
		node.putBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, true);
		node.putInt(PHPDebugCorePreferenceNames.DEBUG_PORT, 10000);
		node.put(PHPDebugCorePreferenceNames.DEDAULT_URL, "http://localhost");

		PHPDebugPlugin.getDefault().getPluginPreferences().setDefault(IPHPConstants.PHP_DEBUG_PARAMETERS_INITIALIZER, "org.eclipse.php.debug.core.defaultInitializer"); //$NON-NLS-1$
	}
}
