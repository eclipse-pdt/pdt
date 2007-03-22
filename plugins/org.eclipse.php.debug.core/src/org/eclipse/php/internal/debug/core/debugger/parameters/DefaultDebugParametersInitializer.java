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
package org.eclipse.php.internal.debug.core.debugger.parameters;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.util.HostsCollector;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * Default debug parameters initializer.
 */
public class DefaultDebugParametersInitializer extends AbstractDebugParametersInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.parameters.IDebugParametersInitializer#generateQueryParameters(org.eclipse.debug.core.ILaunch)
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

		if (ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode()) && getBooleanValue(launch.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT))) {
			parameters.put(DEBUG_STOP, "1");
		}
		String url = launch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		if (url != null) {
			parameters.put(ORIGINAL_URL, url);
		}
		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		if (launchConfiguration != null) {
			try {
				String sessionSetting = launchConfiguration.getAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_ALL_PAGES);
				if (IPHPConstants.DEBUGGING_ALL_PAGES.equals(sessionSetting)) {
					parameters.put(DEBUG_ALL_PAGES, "1");
				} else if (IPHPConstants.DEBUGGING_FIRST_PAGE.equals(sessionSetting)) {
					parameters.put(DEBUG_FIRST_PAGE, "1");
				} else if (IPHPConstants.DEBUGGING_START_FROM.equals(sessionSetting)) {
					parameters.put(DEBUG_START_URL, launchConfiguration.getAttribute(IPHPConstants.DEBUGGING_START_FROM_URL, ""));
					if (launchConfiguration.getAttribute(IPHPConstants.DEBUGGING_SHOULD_CONTINUE, false)) {
						parameters.put(DEBUG_CONTINUE, "1");
					}
				}
			} catch (CoreException ce) {
				Logger.logException(ce);
			}
		}
		String sessID = launch.getAttribute(IDebugParametersKeys.SESSION_ID);
		if (sessID != null) {
			parameters.put(DEBUG_SESSION_ID, sessID);
		}

		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#getRequestURL(org.eclipse.debug.core.ILaunch)
	 */
	public String getRequestURL(ILaunch launch) {
		String url = launch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		return url != null ? url.trim() : "";
	}

	public boolean getBooleanValue(String value) {
		if (value != null) {
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}
}
