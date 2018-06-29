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

import static org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.DEFAULT_CLIENT_PORT;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.PROP_CLIENT_PORT;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;

/**
 * PHP executable dedicated settings for Zend debugger.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerExeSettings extends AbstractDebuggerSettings {

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public ZendDebuggerExeSettings(String ownerId) {
		super(ownerId);
	}

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public ZendDebuggerExeSettings(String ownerId, Map<String, String> attributes) {
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
		return ZendDebuggerConfiguration.ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getKind()
	 */
	@Override
	public DebuggerSettingsKind getKind() {
		return DebuggerSettingsKind.PHP_EXE;
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
		String clientPort = debuggerConf.getAttribute(ZEND_DEBUG_PORT);
		attributes.put(PROP_CLIENT_PORT, clientPort != null ? clientPort : DEFAULT_CLIENT_PORT);
		return attributes;
	}

}
