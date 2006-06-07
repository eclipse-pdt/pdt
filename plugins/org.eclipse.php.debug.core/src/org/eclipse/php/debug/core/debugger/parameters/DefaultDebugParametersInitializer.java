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
package org.eclipse.php.debug.core.debugger.parameters;

import java.util.Hashtable;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.core.util.HostsCollector;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.RemoteDebugger;

/**
 * Default debug parameters initializer.
 */
public class DefaultDebugParametersInitializer extends AbstractDebugParametersInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#generateQueryParameters(org.eclipse.debug.core.ILaunch)
	 */
	public Hashtable generateQueryParameters(ILaunch launch) {
		Hashtable parameters = new Hashtable();
		parameters.put(START_DEBUG, "1");
		
		Object port = launch.getAttribute(IDebugParametersKeys.PORT);
		if (port != null) {
			parameters.put(DEBUG_PORT, port);
		} else {
			PHPDebugPlugin.logErrorMessage("A port was not defined for the DefaultDebugParametersInitializer.");
		}
		
		if (getBooleanValue(launch.getAttribute(IDebugParametersKeys.PASSIVE_DEBUG))) {
			parameters.put(DEBUG_PASSIVE, "1");
		}

		parameters.put(SEND_SESS_END, "1");

		if (getBooleanValue(launch.getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER))) {
			parameters.put(DEBUG_HOST, HostsCollector.getHosts());
			parameters.put(DEBUG_NO_CACHE, Long.toString(System.currentTimeMillis()));
		}

		if (getBooleanValue(launch.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT))) {
			parameters.put(DEBUG_STOP, "1");
		}

		parameters.put(DEBUG_PROTOCOL, Integer.toString(RemoteDebugger.PROTOCOL_ID));

		String url = launch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		if (url != null) {
			parameters.put(ORIGINAL_URL, url);
		}

		String sessID = launch.getAttribute(IDebugParametersKeys.SESSION_ID);
		if (sessID != null) {
			parameters.put(DEBUG_SESSION_ID, sessID);
		}
		
		return parameters;
	}

	public boolean getBooleanValue(String value) {
		if (value != null) {
			return Boolean.parseBoolean(value);
		}
		return false;
	}
}
