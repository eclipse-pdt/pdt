/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.ui.launching.LaunchUtil;
import org.eclipse.php.internal.server.ui.ServerLaunchConfigurationTab;
import org.eclipse.swt.widgets.Composite;

/**
 * PHP server tab that is displayed in the Run/Debug launch configuration tabs.
 * 
 * @author Robert G., Shalom G.
 */
public class PHPWebPageLaunchConfigurationTab extends ServerLaunchConfigurationTab {

	public PHPWebPageLaunchConfigurationTab() {
		super();
	}

	@Override
	public void createExtensionControls(Composite parent) {
	}

	@Override
	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true); // Always
																					// run
																					// with
																					// debug
																					// info
	}

	@Override
	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	@Override
	protected void createServerSelectionControl(Composite parent) {
		super.createServerSelectionControl(parent);
	}

	@Override
	public String[] getRequiredNatures() {
		return LaunchUtil.getRequiredNatures();
	}

	@Override
	public String[] getFileExtensions() {
		return LaunchUtil.getFileExtensions();
	}
}
