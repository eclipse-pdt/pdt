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

import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;

public class GeneralUtils {

	public static DBGpPreferences createSessionPreferences() {
		DBGpPreferences sessionPrefs = new DBGpPreferences();
		int maxDepth = IDELayerFactory.getIDELayer().getPrefs().getInt(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH);
		if (1 == maxDepth) {
			XDebugPreferenceInit.setDefaults();
			maxDepth = XDebugPreferenceInit.getDepthDefault();
		}
		sessionPrefs.setValue(DBGpPreferences.DBGP_MAX_DEPTH_PROPERTY, maxDepth);

		boolean getSuperGlobals = IDELayerFactory.getIDELayer().getPrefs().getBoolean(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS);
		sessionPrefs.setValue(DBGpPreferences.DBGP_SHOW_GLOBALS_PROPERTY, getSuperGlobals);
		return sessionPrefs;
	}
}
