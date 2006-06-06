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

import java.util.HashMap;

import org.eclipse.php.core.util.HostsCollector;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.RemoteDebugger;

/**
 * Default debug parameters initializer.
 */
public class DefaultDebugParametersInitializer extends AbstractDebugParametersInitializer {

	private HashMap parameters;

	/**
	 * DefaultDebugParametersInitializer Constructor.
	 */
	public DefaultDebugParametersInitializer() {
		parameters = new HashMap(5);
	}

	/**
	 * Adds a debug parameter to this initializer.
	 * Adding the same key more then once will replace the value to the latest given one.
	 */
	public void addParameter(String key, Object value) {
		parameters.put(key, value);
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#generateQuery()
	 */
	public String generateQuery() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(START_DEBUG);
		buffer.append(1);
		buffer.append('&');
		buffer.append(DEBUG_PORT);
		buffer.append(getPort());
		if (isPassiveDebug()) {
			buffer.append('&');
			buffer.append(DEBUG_PASSIVE);
			buffer.append(1);
		}

		buffer.append('&');
		buffer.append(SEND_SESS_END);
		buffer.append(1);

		if (isWebServerDebugger()) {
			buffer.append('&');
			buffer.append(DEBUG_HOST);
			buffer.append(HostsCollector.getHosts());

			buffer.append('&');
			buffer.append(DEBUG_NO_CACHE);
			buffer.append(System.currentTimeMillis());
		}

		if (isFirstLineBreakpoint()) {
			buffer.append('&');
			buffer.append(DEBUG_STOP);
			buffer.append(1);
		}

		buffer.append('&');
		buffer.append(DEBUG_PROTOCOL);
		buffer.append(RemoteDebugger.PROTOCOL_ID);

		if (parameters.containsKey(IDebugParametersKeys.ORIGINAL_URL)) {
			buffer.append('&');
			buffer.append(ORIGINAL_URL);
			buffer.append((String) parameters.get(IDebugParametersKeys.ORIGINAL_URL));
		}

		if (parameters.containsKey(IDebugParametersKeys.SESSION_ID)) {
			buffer.append('&');
			buffer.append(DEBUG_SESSION_ID);
			buffer.append(parameters.get(IDebugParametersKeys.SESSION_ID));
		}

		return buffer.toString();
	}

	private int getPort() {
		Object port = parameters.get(IDebugParametersKeys.PORT);
		if (port == null || !(port instanceof Integer)) {
			PHPDebugPlugin.logErrorMessage("A port was not defined for the DefaultDebugParametersInitializer.");
			return 0;
		}
		return ((Integer) port).intValue();
	}

	private boolean isPassiveDebug() {
		return getBooleanValue(IDebugParametersKeys.PASSIVE_DEBUG);
	}

	private boolean isWebServerDebugger() {
		return getBooleanValue(IDebugParametersKeys.WEB_SERVER_DEBUGGER);
	}

	private boolean isFirstLineBreakpoint() {
		return getBooleanValue(IDebugParametersKeys.FIRST_LINE_BREAKPOINT);
	}

	private boolean getBooleanValue(String key) {
		Object value = parameters.get(key);
		if (value != null && value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		return false;
	}
}
