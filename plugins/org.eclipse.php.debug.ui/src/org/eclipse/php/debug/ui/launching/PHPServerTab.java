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
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.server.ui.ServerTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class PHPServerTab extends ServerTab {

	protected Button runWithDebugger;
	protected Button openBrowser;
	protected boolean isRunWithDebugInfo;
	protected boolean isOpenInBrowser;
	private String mode;

	public PHPServerTab() {
		super();
	}

	public void createExtensionControls(Composite parent) {
		createBreakControl(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 1;
		composite.setLayout(layout);
		composite.setLayoutData(data);

		// Add the 'Open in Browser' checkbox.
		openBrowser = new Button(composite, SWT.CHECK);
		openBrowser.setText(PHPDebugUIMessages.PHPdebug_open_in_browser);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		openBrowser.setLayoutData(gd);
		openBrowser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				isOpenInBrowser = b.getSelection();
				updateLaunchConfigurationDialog();
			}
		});

		// Add the 'Run With Debug Info' checkbox in case we are in a 'Run' launch mode.
		mode = getLaunchConfigurationDialog().getMode();
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			runWithDebugger = new Button(composite, SWT.CHECK);
			runWithDebugger.setText(PHPDebugUIMessages.PHPexe_Run_With_Debug_Info);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			runWithDebugger.setLayoutData(gd);

			runWithDebugger.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent se) {
					Button b = (Button) se.getSource();
					isRunWithDebugInfo = b.getSelection();
					updateLaunchConfigurationDialog();
				}
			});
		}
	}

	// In case this is a debug mode, display checkboxes to override the 'Break on first line' attribute.
	protected void createBreakControl(Composite parent) {
		String mode = getLaunchConfigurationDialog().getMode();
		if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			Group group = new Group(parent, SWT.NONE);
			group.setText("Breakpoint");
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			group.setLayout(layout);
			group.setLayoutData(gridData);

			overrideBreakpiontSettings = createCheckButton(group, "Override project/workspace 'Break at First Line' setting");
			breakOnFirstLine = createCheckButton(group, "Break at First Line");
			GridData data = (GridData) breakOnFirstLine.getLayoutData();
			data.horizontalIndent = 20;

			overrideBreakpiontSettings.addSelectionListener(fListener);
			breakOnFirstLine.addSelectionListener(fListener);
		}
	}

	protected void initializeExtensionControls(ILaunchConfiguration configuration) {
		try {
			if (overrideBreakpiontSettings != null) {
				// init the breakpoint settings
				boolean isOverrideBreakpointSetting = configuration.getAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, false);
				overrideBreakpiontSettings.setSelection(isOverrideBreakpointSetting);
				breakOnFirstLine.setEnabled(isOverrideBreakpointSetting);
				breakOnFirstLine.setSelection(configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false));
			}
			isOpenInBrowser = configuration.getAttribute(IPHPConstants.OPEN_IN_BROWSER, PHPDebugPlugin.getOpenInBrowserOption());
			openBrowser.setSelection(isOpenInBrowser);
			if (runWithDebugger != null) {
				isRunWithDebugInfo = configuration.getAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin.getDebugInfoOption());
				runWithDebugger.setSelection(isRunWithDebugInfo);
			}
		} catch (Exception e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		if (overrideBreakpiontSettings != null) {
			configuration.setAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, overrideBreakpiontSettings.getSelection());
			configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, breakOnFirstLine.getSelection());
		}
		configuration.setAttribute(IPHPConstants.OPEN_IN_BROWSER, isOpenInBrowser);
		if (runWithDebugger != null) {
			configuration.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, isRunWithDebugInfo);
		}
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
				super.initializeURLControl(contextRoot, fileName);
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
			super.initializeURLControl(contextRoot, fileName);
		}

	}

	public String[] getRequiredNatures() {
		return LaunchUtil.getRequiredNatures();
	}

	public String[] getFileExtensions() {
		return LaunchUtil.getFileExtensions();
	}
}
