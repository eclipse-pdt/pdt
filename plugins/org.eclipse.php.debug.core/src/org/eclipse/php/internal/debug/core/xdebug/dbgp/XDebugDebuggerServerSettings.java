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

import static org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY;
import static org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr.XDEBUG_PREF_PROXY;
import static org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;

/**
 * PHP server dedicated settings for XDebug debugger.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerServerSettings extends AbstractDebuggerSettings {

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public XDebugDebuggerServerSettings(String ownerId) {
		super(ownerId);
	}

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public XDebugDebuggerServerSettings(String ownerId, Map<String, String> attributes) {
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
		return XDebugDebuggerConfiguration.ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getKind()
	 */
	@Override
	public DebuggerSettingsKind getKind() {
		return DebuggerSettingsKind.PHP_SERVER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings
	 * #createAttributes()
	 */
	protected Map<String, String> createAttributes() {
		XDebugDebuggerConfiguration debuggerConf = (XDebugDebuggerConfiguration) PHPDebuggersRegistry
				.getDebuggerConfiguration(getDebuggerId());
		// Set up initial attributes
		Map<String, String> attributes = new HashMap<String, String>();
		String clientPort = debuggerConf.getAttribute(XDebugPreferenceMgr.XDEBUG_PREF_PORT);
		attributes.put(PROP_CLIENT_PORT, clientPort != null ? clientPort : DEFAULT_CLIENT_PORT);
		String useProxy = String.valueOf(XDebugPreferenceMgr.useProxy());
		attributes.put(PROP_PROXY_ENABLE, useProxy != null ? useProxy : DEFAULT_PROXY_ENABLE);
		String ideKey = debuggerConf.getAttribute(XDEBUG_PREF_IDEKEY);
		attributes.put(PROP_PROXY_IDE_KEY, ideKey != null ? ideKey : DEFAULT_PROXY_IDE_KEY);
		String proxyAddress = debuggerConf.getAttribute(XDEBUG_PREF_PROXY);
		attributes.put(PROP_PROXY_ADDRESS, proxyAddress != null ? proxyAddress : DEFAULT_PROXY_ADDRESS);
		return attributes;
	}

}
