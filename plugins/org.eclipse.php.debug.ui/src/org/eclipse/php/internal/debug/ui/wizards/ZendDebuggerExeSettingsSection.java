/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.PROP_CLIENT_PORT;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Zend debugger settings section for PHP executable.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerExeSettingsSection implements IDebuggerSettingsSection {

	protected IDebuggerSettingsWorkingCopy settingsWorkingCopy;
	protected CompositeFragment compositeFragment;
	protected Composite settingsComposite;
	protected IStatus debuggerStatus;

	/**
	 * 
	 */
	public ZendDebuggerExeSettingsSection(final CompositeFragment compositeFragment,
			final Composite debuggerSettingsComposite, final IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		this.settingsWorkingCopy = settingsWorkingCopy;
		this.compositeFragment = compositeFragment;
		this.settingsComposite = debuggerSettingsComposite;
		createContents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performOK ()
	 */
	@Override
	public boolean performOK() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * validate ()
	 */
	@Override
	public void validate() {
		// Reset state
		compositeFragment.setMessage(compositeFragment.getDescription(), IMessageProvider.NONE);
		if (debuggerStatus == null) {
			debuggerStatus = Status.OK_STATUS;
			PHPexeItem phpExe = (PHPexeItem) compositeFragment.getData();
			AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry.getDebuggersConfigurations();
			for (AbstractDebuggerConfiguration debugger : debuggers) {
				if (phpExe.getDebuggerID().equals(debugger.getDebuggerId())) {
					debuggerStatus = debugger.validate(phpExe);
				}
			}
		}
		// Check errors
		if (debuggerStatus.getSeverity() == IStatus.ERROR) {
			compositeFragment.setMessage(debuggerStatus.getMessage(), IMessageProvider.ERROR);
			return;
		}
		String clientPort = settingsWorkingCopy.getAttribute(PROP_CLIENT_PORT);
		if (clientPort == null || clientPort.isEmpty()) {
			compositeFragment.setMessage(Messages.ZendDebuggerExeSettingsSection_Client_port_is_missing,
					IMessageProvider.ERROR);
			return;
		}
		// Check warnings
		if (debuggerStatus.getSeverity() == IStatus.WARNING) {
			compositeFragment.setMessage(debuggerStatus.getMessage(), IMessageProvider.WARNING);
			return;
		}
		int port = Integer.valueOf(clientPort);
		if (!PHPLaunchUtilities.isPortAvailable(port)
				&& !PHPLaunchUtilities.isDebugDaemonActive(port, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
			compositeFragment.setMessage(
					MessageFormat.format(Messages.DebuggerCommonSettingsSection_Port_is_already_in_use, clientPort),
					IMessageProvider.WARNING);
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * canTest ()
	 */
	@Override
	public boolean canTest() {
		// Maybe in the future...
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performTest()
	 */
	@Override
	public void performTest() {
		// Nothing to perform yet
	}

	protected void createContents() {
		// Connection group
		Group connectionGroup = new Group(settingsComposite, SWT.NONE);
		connectionGroup.setFont(compositeFragment.getFont());
		GridLayout cgLayout = new GridLayout(2, false);
		cgLayout.marginTop = 5;
		connectionGroup.setLayout(cgLayout);
		GridData cgGridData = new GridData(GridData.FILL_HORIZONTAL);
		connectionGroup.setLayoutData(cgGridData);
		connectionGroup.setText(Messages.ZendDebuggerExeSettingsSection_Connection_settings);
		// Client port
		Label clientPortLabel = new Label(connectionGroup, SWT.None);
		clientPortLabel.setText(Messages.ZendDebuggerExeSettingsSection_Client_port);
		final Text clientPortText = new Text(connectionGroup, SWT.BORDER);
		GridData cptLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		clientPortText.setLayoutData(cptLayoutData);
		clientPortText.setText(settingsWorkingCopy.getAttribute(PROP_CLIENT_PORT));
		clientPortText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String port = clientPortText.getText();
				settingsWorkingCopy.setAttribute(PROP_CLIENT_PORT, port);
				validate();
			}
		});
	}

}
