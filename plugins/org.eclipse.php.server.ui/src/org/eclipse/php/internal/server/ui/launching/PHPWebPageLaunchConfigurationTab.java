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
package org.eclipse.php.internal.server.ui.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.ui.launching.LaunchUtil;
import org.eclipse.php.internal.server.ui.ServerLaunchConfigurationTab;
import org.eclipse.swt.widgets.Composite;

/**
 * PHP server tab that is displayed in the Run/Debug launch configuration tabs.
 * 
 * @author Robert G., Shalom G.
 */
public class PHPWebPageLaunchConfigurationTab extends
		ServerLaunchConfigurationTab {

	public PHPWebPageLaunchConfigurationTab() {
		super();
	}

	public void createExtensionControls(Composite parent) {
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		configuration
				.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true); // Always
																				// run
																				// with
																				// debug
																				// info
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
