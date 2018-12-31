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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * XDebug profiler settings section for web profiling (not supported yet).
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugProfileWebLaunchSettingsSection extends AbstractProfileWebLaunchSettingsSection {
	private Button useTrigger;

	private Text triggerValue;

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		super.initialize(configuration);
		try {
			useTrigger.setSelection(configuration.getAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER, true));
			triggerValue.setText(configuration.getAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER_VALUE, "")); //$NON-NLS-1$

		} catch (CoreException e) {
		}

		updateTriggerValueState();
	}

	@Override
	protected void buildSection(Composite parent) {
		// super.buildSection(parent);
		Group triggerGroup = new Group(parent, SWT.NONE);
		triggerGroup.setLayout(new GridLayout(1, false));
		triggerGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		useTrigger = new Button(triggerGroup, SWT.CHECK);
		useTrigger.setText(Messages.XDebugProfileWebLaunchSettingsSection_0);
		useTrigger.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateTriggerValueState();
			}
		});
		useTrigger.addSelectionListener(widgetListener);

		Composite sub = new Composite(triggerGroup, SWT.NONE);
		sub.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 20;
		sub.setLayoutData(data);

		new Label(sub, SWT.NONE).setText(Messages.XDebugProfileWebLaunchSettingsSection_1);
		triggerValue = new Text(sub, SWT.BORDER | SWT.SINGLE);
		triggerValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		triggerValue.addModifyListener(widgetListener);
	}

	private void updateTriggerValueState() {
		if (!useTrigger.getSelection()) {
			triggerValue.setText(""); //$NON-NLS-1$
		}
		triggerValue.setEnabled(useTrigger.getSelection());
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER, useTrigger.getSelection());
		configuration.setAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER_VALUE, triggerValue.getText());
	}

}
