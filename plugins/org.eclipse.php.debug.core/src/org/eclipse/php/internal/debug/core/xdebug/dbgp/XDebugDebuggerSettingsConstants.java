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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * Constants set for XDebug debugger owner settings.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerSettingsConstants {

	private XDebugDebuggerSettingsConstants() {
		// private constructor - constants only owner
	}

	public static final String PROP_CLIENT_PORT = "clientPort"; //$NON-NLS-1$
	public static final String PROP_PROXY_ENABLE = "proxyEnable"; //$NON-NLS-1$
	public static final String PROP_PROXY_IDE_KEY = "proxyIdeKey"; //$NON-NLS-1$
	public static final String PROP_PROXY_ADDRESS = "proxyAddress"; //$NON-NLS-1$

	public static final String DEFAULT_CLIENT_PORT = String
			.valueOf(PHPDebugPlugin.getDebugPort(XDebugDebuggerConfiguration.ID));
	public static final String DEFAULT_PROXY_ENABLE = "false"; //$NON-NLS-1$
	public static final String DEFAULT_PROXY_IDE_KEY = "ECLIPSE_DBGP_xxx"; //$NON-NLS-1$
	public static final String DEFAULT_PROXY_ADDRESS = ""; //$NON-NLS-1$

}
