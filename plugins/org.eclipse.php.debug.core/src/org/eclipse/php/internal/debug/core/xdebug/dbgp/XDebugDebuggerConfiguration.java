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

	public static final String ID = "org.eclipse.php.debug.core.xdebugDebugger"; //$NON-NLS-1$

	private static final String REMOTE_ENABLE = "remote_enable"; //$NON-NLS-1$
	private static final String EXTENSION_MODULE_ID = "Xdebug"; //$NON-NLS-1$

	/**
	 * Constructs a new XDebugDebuggerConfiguration.
	 */
	public XDebugDebuggerConfiguration() {
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
	public void openConfigurationDialog(final Shell parentShell) {
		new XDebugConfigurationDialog(this, parentShell).open();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #getPort()
	 */
	public int getPort() {
		return XDebugPreferenceMgr.getPort();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #setPort(int)
	 */
	public void setPort(int port) {
		XDebugPreferenceMgr.setPort(preferences, port);
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 *      getScriptLaunchDelegateClass()
	 */
	public String getScriptLaunchDelegateClass() {
		return XDebugExeLaunchConfigurationDelegate.class.getName();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 *      getWebLaunchDelegateClass()
	 */
	public String getWebLaunchDelegateClass() {
		return XDebugWebLaunchConfigurationDelegate.class.getName();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #applyDefaults()
	 */
	public void applyDefaults() {
		XDebugPreferenceMgr.applyDefaults(preferences);
		save();
	}

	/**
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 *      AbstractDebuggerConfiguration #validate()
	 */
	public IStatus validate(PHPexeItem item) {
		File executable = item.getExecutable();
		PHPexes.changePermissions(executable);
		if (isInstalled(item, EXTENSION_MODULE_ID))
			return Status.OK_STATUS;
		return new Status(IStatus.WARNING, PHPDebugPlugin.ID,
				PHPDebugCoreMessages.XDebugDebuggerConfiguration_XDebugNotInstalledError);
	}

}