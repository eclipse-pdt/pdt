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
package org.eclipse.php.internal.server.ui.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.ui.launching.LaunchUtil;
import org.eclipse.php.internal.server.ui.ServerTab;
import org.eclipse.swt.widgets.Composite;

/**
 * PHP server tab that is displayed in the Run/Debug launch configuration tabs.
 * 
 * @author Robert G., Shalom G.
 */
public class PHPServerTab extends ServerTab {

	public PHPServerTab() {
		super();
	}

	public void createExtensionControls(Composite parent) {
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, true); // Always run with debug info
	}

	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	protected void createServerSelectionControl(Composite parent) {
		PHPDebugPlugin.createDefaultPHPServer();
		super.createServerSelectionControl(parent);
	}

	public String[] getRequiredNatures() {
		return LaunchUtil.getRequiredNatures();
	}

	public String[] getFileExtensions() {
		return LaunchUtil.getFileExtensions();
	}
}
