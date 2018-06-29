/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import static org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames.CLIENT_IP;
import static org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT;
import static org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;

/**
 * PHP server dedicated settings for Zend debugger.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerServerSettings extends AbstractDebuggerSettings {

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public ZendDebuggerServerSettings(String ownerId) {
		super(ownerId);
	}

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public ZendDebuggerServerSettings(String ownerId, Map<String, String> attributes) {
		super(ownerId, attributes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#
	 * getDebuggerId ()
	 */
	@Override
	public String getDebuggerId() {
		// TODO Auto-generated method stub
		return ZendDebuggerConfiguration.ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getKind()
	 */
	@Override
	public DebuggerSettingsKind getKind() {
		return DebuggerSettingsKind.PHP_SERVER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings
	 * #createAttributes()
	 */
	@Override
	protected Map<String, String> createAttributes() {
		ZendDebuggerConfiguration debuggerConf = (ZendDebuggerConfiguration) PHPDebuggersRegistry
				.getDebuggerConfiguration(getDebuggerId());
		// Set up initial attributes
		Map<String, String> attributes = new HashMap<>();
		String clientIp = debuggerConf.getAttribute(CLIENT_IP);
		attributes.put(PROP_CLIENT_IP, clientIp != null ? clientIp : DEFAULT_CLIENT_IP);
		String clientPort = debuggerConf.getAttribute(ZEND_DEBUG_PORT);
		attributes.put(PROP_CLIENT_PORT, clientPort != null ? clientPort : DEFAULT_CLIENT_PORT);
		String responseTimeout = debuggerConf.getAttribute(DEBUG_RESPONSE_TIMEOUT);
		attributes.put(PROP_RESPONSE_TIMEOUT, responseTimeout != null ? responseTimeout : DEBUG_RESPONSE_TIMEOUT);
		return attributes;
	}

}
