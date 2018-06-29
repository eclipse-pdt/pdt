/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Zend Debugger profiler settings section for web profiling.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerProfileWebLaunchSettingsSection extends AbstractProfileWebLaunchSettingsSection {

	private Group browserGroup;
	private Button openBrowserButton;

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		super.initialize(configuration);
		boolean isOpenInBrowser;
		try {
			isOpenInBrowser = configuration.getAttribute(IPHPDebugConstants.OPEN_IN_BROWSER,
					PHPDebugPlugin.getOpenInBrowserOption());
			openBrowserButton.setSelection(isOpenInBrowser);
		} catch (CoreException e) {
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		boolean isOpenInBrowser = openBrowserButton.getSelection();
		configuration.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, isOpenInBrowser);
	}

	@Override
	protected void buildSection(Composite parent) {
		super.buildSection(parent);
		createBrowserGroup(parent);
	}

	protected void createBrowserGroup(Composite parent) {
		browserGroup = new Group(parent, SWT.NONE);
		browserGroup.setLayout(new GridLayout(1, false));
		browserGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browserGroup.setText(Messages.ZendDebuggerProfileWebLaunchSettingsSection_Browser);
		// Add the Browser group controls
		openBrowserButton = new Button(browserGroup, SWT.CHECK);
		openBrowserButton.setText(Messages.ZendDebuggerProfileWebLaunchSettingsSection_Open_in_browser);
		openBrowserButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Add widget listeners
		openBrowserButton.addSelectionListener(widgetListener);
	}

}
