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
package org.eclipse.php.internal.debug.core.zend.debugger;

import static org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames.*;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;
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
	public ZendDebuggerExeSettings(IUniqueIdentityElement owner) {
		super(owner);
	}

	/**
	 * See {@link AbstractDebuggerSettings}.
	 */
	public ZendDebuggerExeSettings(IUniqueIdentityElement owner,
			Map<String, String> attributes) {
		super(owner, attributes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getDebuggerId
	 * ()
	 */
	@Override
	public String getDebuggerId() {
		return ZendDebuggerConfiguration.ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getKind()
	 */
	@Override
	public DebuggerSettingsKind getKind() {
		return DebuggerSettingsKind.PHP_EXE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettings
	 * #createAttributes()
	 */
	protected Map<String, String> createAttributes() {
		ZendDebuggerConfiguration debuggerConf = (ZendDebuggerConfiguration) PHPDebuggersRegistry
				.getDebuggerConfiguration(getDebuggerId());
		// Set up initial attributes
		Map<String, String> attributes = new HashMap<String, String>();
		String clientPort = debuggerConf.getAttribute(ZEND_DEBUG_PORT);
		attributes.put(PROP_CLIENT_PORT, clientPort != null ? clientPort
				: DEFAULT_CLIENT_PORT);
		return attributes;
	}

}
