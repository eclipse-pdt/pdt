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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.XDebugExeLaunchConfigurationDelegate;
import org.eclipse.php.internal.debug.core.launching.XDebugWebLaunchConfigurationDelegate;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.swt.widgets.Shell;

/**
 * XDebug's debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class XDebugDebuggerConfiguration extends AbstractDebuggerConfiguration {

	private static final String REMOTE_ENABLE = "remote_enable"; //$NON-NLS-1$
	private static final String EXTENSION_ID = "xdebug"; //$NON-NLS-1$

	/**
	 * Constructs a new XDebugDebuggerConfiguration.
	 */
	public XDebugDebuggerConfiguration() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * openConfigurationDialog(org.eclipse.swt.widgets.Shell)
	 */
	public void openConfigurationDialog(final Shell parentShell) {
		new XDebugConfigurationDialog(this, parentShell).open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #getPort()
	 */
	public int getPort() {
		return XDebugPreferenceMgr.getPort(preferences);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #setPort(int)
	 */
	public void setPort(int port) {
		XDebugPreferenceMgr.setPort(preferences, port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getScriptLaunchDelegateClass()
	 */
	public String getScriptLaunchDelegateClass() {
		return XDebugExeLaunchConfigurationDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getWebLaunchDelegateClass()
	 */
	public String getWebLaunchDelegateClass() {
		return XDebugWebLaunchConfigurationDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #applyDefaults()
	 */
	public void applyDefaults() {
		XDebugPreferenceMgr.applyDefaults(preferences);
		save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration
	 * #validate()
	 */
	public IStatus validate(PHPexeItem item) {
		File executable = item.getExecutable();
		try {
			PHPexes.changePermissions(executable);
			if (isInstalled(item, EXTENSION_ID)) {
				String output = null;
				File iniFile = item.getINILocation();
				if (iniFile != null) {
					output = PHPexeItem.exec(executable.getAbsolutePath(),
							"-c", iniFile.getAbsolutePath(), "--ri", //$NON-NLS-1$ //$NON-NLS-2$
							EXTENSION_ID);
				} else {
					output = PHPexeItem.exec(executable.getAbsolutePath(),
							"--ri", EXTENSION_ID); //$NON-NLS-1$
				}
				if (output != null) {
					String[] properties = output.split("\n"); //$NON-NLS-1$
					for (String property : properties) {
						String[] columns = property.split("=>"); //$NON-NLS-1$
						if (columns.length == 3
								&& (EXTENSION_ID + '.' + REMOTE_ENABLE)
										.equals(columns[0].trim())) {
							String value = columns[1].trim();
							if (!"on".equalsIgnoreCase(value)) { //$NON-NLS-1$
								return new Status(
										IStatus.WARNING,
										PHPDebugPlugin.ID,
										PHPDebugCoreMessages.XDebugDebuggerConfiguration_XDebugNotEnabledError);
							}
						}
					}
				}
			} else {
				return new Status(
						IStatus.WARNING,
						PHPDebugPlugin.ID,
						PHPDebugCoreMessages.XDebugDebuggerConfiguration_XDebugNotInstalledError);
			}
		} catch (IOException e) {
			PHPDebugPlugin.log(e);
		}
		return Status.OK_STATUS;
	}

}