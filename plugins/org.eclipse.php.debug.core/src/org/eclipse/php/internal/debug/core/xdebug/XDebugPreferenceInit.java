/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;

public class XDebugPreferenceInit {

	public static void setDefaults() {
		IDELayer layer = IDELayerFactory.getIDELayer();
		Preferences prefs = layer.getPrefs();
		prefs.setDefault(XDebugUIAttributeConstants.XDEBUG_PREF_PORT, getPortDefault());
		prefs.setDefault(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS, showSuperGlobalsDefault());
		prefs.setDefault(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH, getDepthDefault());
		prefs.setDefault(XDebugUIAttributeConstants.XDEBUG_PREF_MULTISESSION, useMultiSessionDefault());		
	}

	public static int getDepthDefault() {
		return 3;
	}

	public static int getPortDefault() {
		return DBGpPreferences.DBGP_PORT_DEFAULT;
	}

	public static int getTimeoutDefault() {
		return DBGpPreferences.DBGP_TIMEOUT_DEFAULT;
	}

	public static boolean showSuperGlobalsDefault() {
		return true;
	}
	
	public static boolean useMultiSessionDefault() {
		return false;
	}

	/*
	public static String getDefaultServerURL() {
		return "http://localhost";
	}
	*/
}
