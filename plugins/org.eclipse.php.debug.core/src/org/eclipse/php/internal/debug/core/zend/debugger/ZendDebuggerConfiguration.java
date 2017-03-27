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

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.launching.PHPWebPageLaunchDelegate;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.swt.widgets.Shell;

/**
 * Zend's debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfiguration extends AbstractDebuggerConfiguration {

	public static final String ID = "org.eclipse.php.debug.core.zendDebugger"; //$NON-NLS-1$

	private static final String EXTENSION_MODULE_ID = "Zend Debugger"; //$NON-NLS-1$

	/**
	 * Constructs a new ZendDebuggerConfiguration.
	 */
	public ZendDebuggerConfiguration() {
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #getModuleId()
	 */
	@Override
	public String getModuleId() {
		return EXTENSION_MODULE_ID;
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 *      openConfigurationDialog(org.eclipse.swt.widgets.Shell)
	 */
	public void openConfigurationDialog(Shell parentShell) {
		new ZendDebuggerConfigurationDialog(this, parentShell).open();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #getPort()
	 */
	public int getPort() {
		return Platform.getPreferencesService().getInt(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT,
				0, null);
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #setPort(int)
	 */
	public void setPort(int port) {
		preferences.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, port);
	}

	/**
	 * @return port for broadcasting Studio settings to the ToolBar or Zend GUI
	 */
	public int getBroadcastPort() {
		return Platform.getPreferencesService().getInt(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, 0, null);
	}

	/**
	 * @param broadcastPort
	 *            Port for broadcasting Studio settings to the ToolBar or Zend
	 *            GUI
	 */
	public void setBroadcastPort(int broadcastPort) {
		preferences.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, broadcastPort);
	}

	/**
	 * @return dummy PHP file name
	 */
	public String getDummyFile() {
		return Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, null, null);
	}

	/**
	 * @param dummyFile
	 *            dummy PHP file name
	 */
	public void setDummyFile(String dummyFile) {
		preferences.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, dummyFile);
	}

	public boolean isUseNewProtocol() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, true, null);
	}

	public void setUNewProtocol(boolean enable) {
		preferences.putBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, enable);
	}

	/**
	 * @return whether to use SSL encryption when debugging
	 */
	public boolean isUseSSL() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, true, null);
	}

	/**
	 * @param useSSL
	 *            whether to use SSL encryption when debugging
	 */
	public void setUseSSL(boolean useSSL) {
		preferences.putBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, useSSL);
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#getScriptLaunchDelegateClass()
	 */
	public String getScriptLaunchDelegateClass() {
		return PHPExecutableLaunchDelegate.class.getName();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#getWebLaunchDelegateClass()
	 */
	public String getWebLaunchDelegateClass() {
		return PHPWebPageLaunchDelegate.class.getName();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 *      #applyDefaults()
	 */
	public void applyDefaults() {
		setPort(defaultPreferences.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 0));
		preferences.put(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO,
				defaultPreferences.get(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, null));
		setBroadcastPort(defaultPreferences.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, 0));
		setDummyFile(defaultPreferences.get(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, null));
		setUseSSL(defaultPreferences.getBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false));
		save();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 *      #validate()
	 */
	public IStatus validate(PHPexeItem item) {
		File executable = item.getExecutable();
		PHPexes.changePermissions(executable);
		if (isInstalled(item, EXTENSION_MODULE_ID))
			return Status.OK_STATUS;
		return new Status(IStatus.WARNING, PHPDebugPlugin.ID,
				PHPDebugCoreMessages.ZendDebuggerConfiguration_ZendDebuggerNotInstalledError);
	}

}
