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
public class DefaultDebugParametersInitializer implements IDebugParametersInitializer {

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
        buffer.append("start_debug=1");
        buffer.append("&debug_port=");
        buffer.append(getPort());
        if (isPassiveDebug()) {
            buffer.append("&debug_passive=1");
        }

        if (isWebServerDebugger()) {
            buffer.append("&debug_host=");
            buffer.append(HostsCollector.getHosts());

            buffer.append("&send_sess_end=1");
            buffer.append("&debug_no_cache=");
            buffer.append(System.currentTimeMillis());
        }

        if (isFirstLineBreakpoint()) {
            buffer.append("&debug_stop=1");
        }
        
        buffer.append("&debug_protocol=");
        buffer.append(RemoteDebugger.PROTOCOL_ID);
        
        if (parameters.containsKey(IDebugParametersKeys.ORIGINAL_URL)) {
        	buffer.append("&original_url=");
        	buffer.append((String)parameters.get(IDebugParametersKeys.ORIGINAL_URL));
        }
        
        return buffer.toString();
    }
	
	private int getPort() {
		Object port = parameters.get(IDebugParametersKeys.PORT);
		if (port == null || !(port instanceof Integer)) {
			PHPDebugPlugin.logErrorMessage("A port was not defined for the DefaultDebugParametersInitializer.");
			return 0;
		}
		return ((Integer)port).intValue();
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
			return ((Boolean)value).booleanValue();
		}
		return false;
	}

	public String getDebugHandler() {
		return null;
	}
	
	public void setDebugHandler(String id) {
	}
}
