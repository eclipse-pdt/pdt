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
package org.eclipse.php.debug.ui.launching;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.server.ui.ServerTab;
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

	protected void initializeExtensionControls(ILaunchConfiguration configuration) {
		try {
			isOpenInBrowser = configuration.getAttribute(IPHPConstants.OPEN_IN_BROWSER, PHPDebugPlugin.getOpenInBrowserOption());
			openBrowser.setSelection(isOpenInBrowser);
		} catch (Exception e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IPHPConstants.OPEN_IN_BROWSER, isOpenInBrowser);
		configuration.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, true); // Always run with debug info
	}

	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	protected void createServerSelectionControl(Composite parent) {
		PHPDebugPlugin.createDefaultPHPServer();
		super.createServerSelectionControl(parent);
	}

	protected void initializeURLControl(String contextRoot, String fileName) {
		if (server == null)
			return;

		if (server.getName().equals(IPHPConstants.Default_Server_Name)) {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = workspaceRoot.findMember(fileName);
			if (resource == null) {
				fURL.setText(super.computeURL(contextRoot, fileName));
				return;
			}
			IProject project = resource.getProject();
			String urlString = "";
			if (project == null) {
				urlString = PHPDebugPlugin.getWorkspaceURL();
			} else {
				urlString = PHPProjectPreferences.getDefaultServerURL(project);
			}

			if (urlString.equals(""))
				urlString = "http://localhost";

			StringBuffer url = new StringBuffer(urlString);

			url.append("/");
			url.append(contextRoot);
			if (contextRoot != "")
				url.append("/");
			url.append(fileName);

			fURL.setText(url.toString());
		} else {
			fURL.setText(super.computeURL(contextRoot, fileName));
		}

	}

	public String[] getRequiredNatures() {
		return LaunchUtil.getRequiredNatures();
	}

	public String[] getFileExtensions() {
		return LaunchUtil.getFileExtensions();
	}
}
