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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.util.HashMap;
import java.util.Map;

public class DBGpPreferences {
	// define DBGp protocol defaults
	public static final int DBGP_PORT_DEFAULT = 9000;
	public static final int DBGP_TIMEOUT_DEFAULT = 500;

	// define DBGp session defaults
	public static final int DBGP_MAX_DEPTH_DEFAULT = 3;
	public static final int DBGP_MAX_CHILDREN_DEFAULT = 31;
	public static final boolean DBGP_SHOW_GLOBALS_DEFAULT = true;
	public static final int DBGP_CAPTURE_DEFAULT = 0;

	// define the session property keys
	public static final String DBGP_MAX_DEPTH_PROPERTY = "MaxDepth"; //$NON-NLS-1$
	public static final String DBGP_SHOW_GLOBALS_PROPERTY = "ShowGlobals"; //$NON-NLS-1$
	public static final String DBGP_MAX_CHILDREN_PROPERTY = "MaxChildren"; //$NON-NLS-1$
	public static final String DBGP_CAPTURE_STDOUT_PROPERTY = "CaptureStdout"; //$NON-NLS-1$
	public static final String DBGP_CAPTURE_STDERR_PROPERTY = "CaptureStderr"; //$NON-NLS-1$

	private Map preferences = new HashMap();

	public void setValue(String name, int value) {
		preferences.put(name, value);
	}

	public void setValue(String name, boolean value) {
		preferences.put(name, value);
	}

	public int getInt(String name, int defaultValue) {
		Object intObj = preferences.get(name);
		if (intObj instanceof Integer) {
			return ((Integer) intObj).intValue();
		}
		return defaultValue;
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		Object boolObj = preferences.get(name);
		if (boolObj instanceof Boolean) {
			return ((Boolean) boolObj).booleanValue();
		}
		return defaultValue;
	}

}
