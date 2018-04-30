/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Zend Debugger profiler settings section for CLI profiling.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerProfileExeLaunchSettingsSection extends AbstractProfileExeLaunchSettingsSection {

	private Group fGeneralGroup;
	private Button fCodeCoverageButton;

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(IPHPDebugConstants.ATTR_ENABLE_CODE_COVERAGE, true);
	}

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		super.initialize(configuration);
		if (fGeneralGroup != null) {
			try {
				final boolean enableCodeCoverage = configuration
						.getAttribute(IPHPDebugConstants.ATTR_ENABLE_CODE_COVERAGE, false);
				fCodeCoverageButton.setSelection(enableCodeCoverage);
			} catch (CoreException e) {
				ProfilerUiPlugin.log(e);
			}
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (fGeneralGroup != null) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_ENABLE_CODE_COVERAGE,
					fCodeCoverageButton.getSelection());
		}
	}

	@Override
	protected void buildSection(Composite parent) {
		createGeneralGroup(parent);
	}

	protected void createGeneralGroup(Composite parent) {
		// Add the general group
		fGeneralGroup = new Group(parent, SWT.NONE);
		fGeneralGroup.setLayout(new GridLayout(1, false));
		fGeneralGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fGeneralGroup.setText(Messages.ZendDebuggerProfileExeLaunchSettingsSection_General_group_name);
		// Add the tunneling controls
		fCodeCoverageButton = new Button(fGeneralGroup, SWT.CHECK);
		fCodeCoverageButton.setText(Messages.ZendDebuggerProfileExeLaunchSettingsSection_Show_code_coverage);
		// Add widget listener
		fCodeCoverageButton.addSelectionListener(widgetListener);
	}

}
