/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.PROP_CLIENT_IP;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.PROP_CLIENT_PORT;
import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.PROP_RESPONSE_TIMEOUT;

import java.util.Set;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.debug.ui.DebugServerConnectionTestRegistry;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.server.core.Server;
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
import org.eclipse.ui.PlatformUI;

/**
 * Zend debugger settings section for PHP server.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class ZendDebuggerServerSettingsSection implements
		IDebuggerSettingsSection {

	protected IDebuggerSettingsWorkingCopy settingsWorkingCopy;
	protected CompositeFragment compositeFragment;
	protected Composite settingsComposite;

	/**
	 * 
	 */
	public ZendDebuggerServerSettingsSection(
			CompositeFragment compositeFragment,
			final IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		this.settingsWorkingCopy = settingsWorkingCopy;
		this.compositeFragment = compositeFragment;
		this.settingsComposite = createComposite();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * getComposite()
	 */
	@Override
	public Composite getComposite() {
		return settingsComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#performOK
	 * ()
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
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#validate
	 * ()
	 */
	public void validate() {
		// Reset state
		compositeFragment.setMessage(compositeFragment.getDescription(),
				IMessageProvider.NONE);
		// Check errors
		String clientIp = (String) settingsWorkingCopy
				.getAttribute(PROP_CLIENT_IP);
		if (clientIp == null || clientIp.isEmpty()) {
			compositeFragment
					.setMessage(
							Messages.ZendDebuggerServerSettingsSection_Client_IP_is_missing,
							IMessageProvider.ERROR);
			return;
		}
		String clientPort = (String) settingsWorkingCopy
				.getAttribute(PROP_CLIENT_PORT);
		if (clientPort == null || clientPort.isEmpty()) {
			compositeFragment
					.setMessage(
							Messages.ZendDebuggerServerSettingsSection_Client_port_is_missing,
							IMessageProvider.ERROR);
			return;
		}
		String responseTimeout = (String) settingsWorkingCopy
				.getAttribute(PROP_RESPONSE_TIMEOUT);
		if (responseTimeout == null || responseTimeout.isEmpty()) {
			compositeFragment
					.setMessage(
							Messages.ZendDebuggerServerSettingsSection_Response_timeout_is_missing,
							IMessageProvider.ERROR);
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#canTest
	 * ()
	 */
	@Override
	public boolean canTest() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performTest()
	 */
	@Override
	public void performTest() {
		IDebugServerConnectionTest[] tests = DebugServerConnectionTestRegistry
				.getTests(ZendDebuggerConfiguration.ID);
		Server server = (Server) compositeFragment.getData();
		int port = ZendDebuggerSettingsUtil.getDebugPort(settingsWorkingCopy
				.getOwnerId());
		Set<Integer> allDebugPorts = PHPDebugUtil
				.getDebugPorts(ZendDebuggerConfiguration.ID);
		AbstractDebuggerCommunicationDaemon tmpDaemon = null;
		if (!allDebugPorts.contains(port))
			tmpDaemon = DebuggerCommunicationDaemon.createDaemon(port);
		for (IDebugServerConnectionTest test : tests) {
			test.testConnection(server, PlatformUI.getWorkbench().getDisplay()
					.getActiveShell());
		}
		if (tmpDaemon != null) {
			tmpDaemon.stopListen();
		}
	}

	protected Composite createComposite() {
		// Main composite
		Composite settingsComposite = new Composite(compositeFragment, SWT.NONE);
		GridLayout sLayout = new GridLayout();
		sLayout.marginHeight = 0;
		sLayout.marginWidth = 0;
		settingsComposite.setLayout(sLayout);
		GridData sGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		settingsComposite.setLayoutData(sGridData);
		// Connection group
		Group connectionGroup = new Group(settingsComposite, SWT.NONE);
		connectionGroup.setFont(compositeFragment.getFont());
		GridLayout cgLayout = new GridLayout(2, false);
		cgLayout.marginTop = 5;
		connectionGroup.setLayout(cgLayout);
		GridData cgGridData = new GridData(GridData.FILL_HORIZONTAL);
		connectionGroup.setLayoutData(cgGridData);
		connectionGroup
				.setText(Messages.ZendDebuggerServerSettingsSection_Connection_settings);
		// Client IP
		Label clientIpLabel = new Label(connectionGroup, SWT.None);
		clientIpLabel
				.setText(Messages.ZendDebuggerServerSettingsSection_Client_IPs);
		final Text clientIpText = new Text(connectionGroup, SWT.BORDER);
		GridData citLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		clientIpText.setLayoutData(citLayoutData);
		clientIpText.setText(settingsWorkingCopy.getAttribute(PROP_CLIENT_IP));
		clientIpText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String clientIp = clientIpText.getText();
				settingsWorkingCopy.setAttribute(PROP_CLIENT_IP, clientIp);
				validate();
			}
		});
		// Client port
		Label clientPortLabel = new Label(connectionGroup, SWT.None);
		clientPortLabel
				.setText(Messages.ZendDebuggerServerSettingsSection_Client_port);
		final Text clientPortText = new Text(connectionGroup, SWT.BORDER);
		GridData cptLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		clientPortText.setLayoutData(cptLayoutData);
		clientPortText.setText(settingsWorkingCopy
				.getAttribute(PROP_CLIENT_PORT));
		clientPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String port = clientPortText.getText();
				settingsWorkingCopy.setAttribute(PROP_CLIENT_PORT, port);
				validate();
			}
		});
		// Response timeout
		Label responseTimeoutLabel = new Label(connectionGroup, SWT.None);
		responseTimeoutLabel
				.setText(Messages.ZendDebuggerServerSettingsSection_Response_timeout);
		final Text responseTimeoutText = new Text(connectionGroup, SWT.BORDER);
		GridData rttLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		responseTimeoutText.setLayoutData(rttLayoutData);
		responseTimeoutText.setText(settingsWorkingCopy
				.getAttribute(PROP_RESPONSE_TIMEOUT));
		responseTimeoutText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String responseTimeout = responseTimeoutText.getText();
				settingsWorkingCopy.setAttribute(PROP_RESPONSE_TIMEOUT,
						responseTimeout);
				validate();
			}
		});
		// Initial validation
		validate();
		return settingsComposite;
	}

}
