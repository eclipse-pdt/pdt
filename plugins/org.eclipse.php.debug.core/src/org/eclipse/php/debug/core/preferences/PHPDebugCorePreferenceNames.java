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

import org.eclipse.php.debug.core.PHPDebugPlugin;

/**
 * Common preference keys used by PHP Debug
 * 
 */

public class PHPDebugCorePreferenceNames {
	private PHPDebugCorePreferenceNames() {
		// empty private constructor so users cannot instantiate class
	}

	private static final String Debug_ID = PHPDebugPlugin.getID();

	public static final String STOP_AT_FIRST_LINE = Debug_ID + "stop_at_first_line_string";//$NON-NLS-1$
	public static final String USE_PHP_DEBUG_PERSPECTIVE = Debug_ID + "USE_PHP_DEBUG_PERSPECTIVE";//$NON-NLS-1$
	public static final String DEBUG_PORT = Debug_ID + "debug_port";//$NON-NLS-1$

	public static final String INSTALLED_PHP_NAMES = Debug_ID + "installedPHPNames";//$NON-NLS-1$
	public static final String INSTALLED_PHP_LOCATIONS = Debug_ID + "installedPHPLocations";//$NON-NLS-1$
	public static final String DEFAULT_PHP = Debug_ID + "defaultPHP";//$NON-NLS-1$

	public static final String DIALOG_COLUMN_WIDTH = Debug_ID + "phpdebug.dialog.columnwidth";//$NON-NLS-1$
	public static final String RUN_WITH_DEBUG_INFO = Debug_ID + "run_with_debug_info";//$NON-NLS-1$
	public static final String AUTO_SAVE_DIRTY = Debug_ID + "auto_save_dirty";//$NON-NLS-1$
	public static final String OPEN_DEBUG_VIEWS = Debug_ID + "open_debug_views";//$NON-NLS-1$
	public static final String DEDAULT_URL = Debug_ID + "default_url";//$NON-NLS-1$
	public static final String TRANSFER_ENCODING = Debug_ID + "transfer_encoding";//$NON-NLS-1$

}
