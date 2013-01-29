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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.launching.PHPWebPageLaunchDelegate;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.swt.widgets.Shell;

/**
 * Zend's debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfiguration extends AbstractDebuggerConfiguration {

	/**
	 * Constructs a new ZendDebuggerConfiguration.
	 */
	public ZendDebuggerConfiguration() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * openConfigurationDialog(org.eclipse.swt.widgets.Shell)
	 */
	public void openConfigurationDialog(Shell parentShell) {
		new ZendDebuggerConfigurationDialog(this, parentShell).open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #getPort()
	 */
	public int getPort() {
		return preferences.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #setPort(int)
	 */
	public void setPort(int port) {
		preferences.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, port);
	}

	public boolean isUseNewProtocol() {
		return preferences.getBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL);
	}

	public void setUNewProtocol(boolean enable) {
		preferences.setValue(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, enable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getScriptLaunchDelegateClass()
	 */
	public String getScriptLaunchDelegateClass() {
		return PHPExecutableLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getWebLaunchDelegateClass()
	 */
	public String getWebLaunchDelegateClass() {
		return PHPWebPageLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #applyDefaults()
	 */
	public void applyDefaults() {
		setPort(preferences
				.getDefaultInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT));
		preferences
				.setValue(
						PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO,
						preferences
								.getDefaultBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		save();
	}
}
