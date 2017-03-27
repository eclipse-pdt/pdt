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
package org.eclipse.php.internal.debug.core.zend.debugger.parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.core.debugger.parameters.IWebDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * Default debug parameters initializer.
 */
public class DefaultDebugParametersInitializer extends AbstractDebugParametersInitializer
		implements IWebDebugParametersInitializer {

	// Advanced parameters
	public static final String USE_SSL = "use_ssl"; //$NON-NLS-1$
	public static final String CODE_COVERAGE = "debug_coverage"; //$NON-NLS-1$
	public static final String DEBUG_NO_REMOTE = "no_remote"; //$NON-NLS-1$
	public static final String DEBUG_USE_REMOTE = "use_remote"; //$NON-NLS-1$
	public static final String DEBUG_FASTFILE = "debug_fastfile"; //$NON-NLS-1$
	public static final String START_PROFILE = "start_profile"; //$NON-NLS-1$

	// Marker flags
	public static final String GET_FILE_CONTENT = "get_file_content"; //$NON-NLS-1$
	public static final String LINE_NUMBER = "line_number"; //$NON-NLS-1$
	public static final String IS_DEBUG_URL = "isDebugURL"; //$NON-NLS-1$
	public static final String IS_PROFILE_URL = "isProfileURL"; //$NON-NLS-1$
	public static final String DEBUG_LINE_BP = "debug_line_bp"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.parameters.
	 * IDebugParametersInitializer
	 * #generateQueryParameters(org.eclipse.debug.core.ILaunch)
	 */
	public Hashtable<String, String> getDebugParameters(ILaunch launch) {
		Hashtable<String, String> parameters = new Hashtable<String, String>();
		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		parameters.put(START_DEBUG, "1"); //$NON-NLS-1$

		String port = launch.getAttribute(IDebugParametersKeys.PORT);
		if (port != null) {
			parameters.put(DEBUG_PORT, port);
		} else {
			PHPDebugPlugin.logErrorMessage("A port was not defined for the DefaultDebugParametersInitializer."); //$NON-NLS-1$
		}

		if (getBooleanValue(launch.getAttribute(IDebugParametersKeys.PASSIVE_DEBUG))) {
			parameters.put(DEBUG_PASSIVE, "1"); //$NON-NLS-1$
		}

		parameters.put(SEND_SESS_END, "1"); //$NON-NLS-1$

		if (getBooleanValue(launch.getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER))) {
			String debugHosts = PHPDebugPlugin.getDebugHosts();
			// Set up custom server debug hosts if any
			if (launchConfiguration != null) {
				try {
					Server server = ServersManager.getServer(launchConfiguration.getAttribute(Server.NAME, "")); //$NON-NLS-1$
					String customHosts = ZendDebuggerSettingsUtil.getDebugHosts(server.getUniqueId());
					if (!customHosts.isEmpty())
						debugHosts = customHosts;
				} catch (CoreException ce) {
					Logger.logException(ce);
				}
			}
			parameters.put(DEBUG_HOST, debugHosts);

			parameters.put(DEBUG_NO_CACHE, Long.toString(System.currentTimeMillis()));
		}

		if (ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())
				&& getBooleanValue(launch.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT))) {
			parameters.put(DEBUG_STOP, "1"); //$NON-NLS-1$
		}
		String url = launch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		if (url != null) {
			parameters.put(ORIGINAL_URL, url);
		}
		if (launchConfiguration != null) {
			try {
				String sessionSetting = launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
						IPHPDebugConstants.DEBUGGING_ALL_PAGES);
				if (IPHPDebugConstants.DEBUGGING_ALL_PAGES.equals(sessionSetting)) {
					parameters.put(DEBUG_ALL_PAGES, "1"); //$NON-NLS-1$
				} else if (IPHPDebugConstants.DEBUGGING_FIRST_PAGE.equals(sessionSetting)) {
					parameters.put(DEBUG_FIRST_PAGE, "1"); //$NON-NLS-1$
				} else if (IPHPDebugConstants.DEBUGGING_START_FROM.equals(sessionSetting)) {
					parameters.put(DEBUG_START_URL,
							launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_START_FROM_URL, "")); //$NON-NLS-1$
					if (launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE, false)) {
						parameters.put(DEBUG_CONTINUE, "1"); //$NON-NLS-1$
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
		// Disable Z-Ray
		parameters.put(ZRAY_DISABLE, "1"); //$NON-NLS-1$

		String isExecutableLaunch = launch.getAttribute(IDebugParametersKeys.EXECUTABLE_LAUNCH);
		if (isExecutableLaunch == null || !Boolean.valueOf(isExecutableLaunch).booleanValue()) {
			// Advanced session settings
			if (launchConfiguration != null) {
				try {
					if (launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false)) {
						// do not allow access to local files
						parameters.put(DEBUG_NO_REMOTE, "1"); //$NON-NLS-1$
					} else {
						parameters.put(DEBUG_USE_REMOTE, "1"); //$NON-NLS-1$
					}
				} catch (CoreException e) {
				}
			}
		} else {
			// Debug agains PHP exe also using dummy.php:
			parameters.put(DEBUG_USE_REMOTE, "1"); //$NON-NLS-1$
		}

		String codeCoverageFlag = launch.getAttribute(IPHPDebugConstants.DEBUGGING_COLLECT_CODE_COVERAGE);
		if (codeCoverageFlag != null) {
			if (Integer.parseInt(codeCoverageFlag) > 0)
				parameters.put(CODE_COVERAGE, codeCoverageFlag); // $NON-NLS-1$
		}
		// Set the use_ssl for any local or remote debug configuration
		boolean useSSL = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false);
		if (useSSL) {
			parameters.put(USE_SSL, "1"); //$NON-NLS-1$
		}
		parameters.put(DEBUG_FASTFILE, "1"); //$NON-NLS-1$
		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.debug.core.debugger.parameters.
	 * IDebugParametersInitializer
	 * #getRequestURL(org.eclipse.debug.core.ILaunch)
	 */
	public URL getRequestURL(ILaunch launch) {
		String url = launch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			Logger.logException("Malformed URL: " + url, e); //$NON-NLS-1$
		}
		return null;
	}

	public boolean getBooleanValue(String value) {
		if (value != null) {
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public Hashtable<String, String> getRequestCookies(ILaunch launch) {
		return null;
	}

	public Hashtable<String, String> getRequestHeaders(ILaunch launch) {
		return null;
	}

	public String getRequestMethod(ILaunch launch) {
		return null;
	}

	public Hashtable<String, String> getRequestParameters(ILaunch launch) {
		return null;
	}

	public String getRequestRawData(ILaunch launch) {
		return null;
	}
}
