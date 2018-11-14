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

import static org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants.PROP_CLIENT_PORT;
import static org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants.PROP_PROXY_ADDRESS;
import static org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants.PROP_PROXY_ENABLE;
import static org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants.PROP_PROXY_IDE_KEY;

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandlersManager;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * XDebug debugger settings section for PHP server.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerServerSettingsSection implements IDebuggerSettingsSection {

	protected IDebuggerSettingsWorkingCopy settingsWorkingCopy;
	protected CompositeFragment compositeFragment;
	protected Composite settingsComposite;

	/**
	 * Creates new section.
	 */
	public XDebugDebuggerServerSettingsSection(final CompositeFragment compositeFragment,
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
		DBGpProxyHandlersManager.INSTANCE.registerHandler(settingsWorkingCopy.getOwnerId());
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
		// Check errors
		String clientPort = settingsWorkingCopy.getAttribute(PROP_CLIENT_PORT);
		if (clientPort == null || clientPort.isEmpty()) {
			compositeFragment.setMessage(Messages.XDebugDebuggerSettingsSection_Client_port_is_missing,
					IMessageProvider.ERROR);
			return;
		}
		Integer portNumber = null;
		try {
			portNumber = Integer.valueOf(clientPort);
			int i = portNumber.intValue();
			if (i < 1 || i > 65535) {
				compositeFragment.setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPortRange,
						IMessageProvider.ERROR);
				return;
			}
		} catch (NumberFormatException ex) {
			compositeFragment.setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort,
					IMessageProvider.ERROR);
			return;
		} catch (Exception e) {
			compositeFragment.setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort,
					IMessageProvider.ERROR);
			return;
		}
		// Check ports
		if (!PHPLaunchUtilities.isPortAvailable(portNumber)
				&& !PHPLaunchUtilities.isDebugDaemonActive(portNumber, XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID)) {
			compositeFragment.setMessage(
					MessageFormat.format(Messages.DebuggerCommonSettingsSection_Port_is_already_in_use, clientPort),
					IMessageProvider.WARNING);
			return;
		}
		boolean isProxyEnabled = Boolean.valueOf(settingsWorkingCopy.getAttribute(PROP_PROXY_ENABLE));
		if (isProxyEnabled) {
			String proxyIdeKey = settingsWorkingCopy.getAttribute(PROP_PROXY_IDE_KEY);
			if (proxyIdeKey == null || proxyIdeKey.isEmpty()) {
				compositeFragment.setMessage(Messages.XDebugDebuggerServerSettingsSection_IDE_key_is_missing,
						IMessageProvider.ERROR);
				return;
			}
			String proxyAddress = settingsWorkingCopy.getAttribute(PROP_PROXY_ADDRESS);
			if (proxyAddress == null || proxyAddress.isEmpty()) {
				compositeFragment.setMessage(Messages.XDebugDebuggerServerSettingsSection_Proxy_address_is_missing,
						IMessageProvider.ERROR);
				return;
			}
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
		connectionGroup.setLayout(cgLayout);
		GridData cgGridData = new GridData(GridData.FILL_HORIZONTAL);
		connectionGroup.setLayoutData(cgGridData);
		connectionGroup.setText(Messages.XDebugDebuggerSettingsSection_Connection_settings);
		// Client port
		Label clientPortLabel = new Label(connectionGroup, SWT.None);
		clientPortLabel.setText(Messages.XDebugDebuggerSettingsSection_Client_port);
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
		// Advanced sub-group
		Group advancedSubGroup = new Group(connectionGroup, SWT.NONE);
		advancedSubGroup.setLayout(new GridLayout(2, false));
		GridData dbgpGridData = new GridData(GridData.FILL_HORIZONTAL);
		dbgpGridData.horizontalSpan = 2;
		advancedSubGroup.setLayoutData(dbgpGridData);
		advancedSubGroup.setText(Messages.XDebugDebuggerSettingsSection_Advanced_group);
		// Enable DBGp proxy
		final Button enableProxy = new Button(advancedSubGroup, SWT.CHECK);
		GridData epGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		epGridData.horizontalSpan = 2;
		enableProxy.setLayoutData(epGridData);
		enableProxy.setText(Messages.XDebugDebuggerSettingsSection_Enable_DBGp_proxy);
		// Proxy IDE key
		Label proxyIdeKeyLabel = new Label(advancedSubGroup, SWT.None);
		proxyIdeKeyLabel.setText(Messages.XDebugDebuggerSettingsSection_Proxy_ide_key);
		final Text proxyIdeKeyText = new Text(advancedSubGroup, SWT.BORDER);
		GridData pikLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		proxyIdeKeyText.setLayoutData(pikLayoutData);
		proxyIdeKeyText.setText(settingsWorkingCopy.getAttribute(PROP_PROXY_IDE_KEY));
		proxyIdeKeyText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String ideKey = proxyIdeKeyText.getText();
				settingsWorkingCopy.setAttribute(PROP_PROXY_IDE_KEY, ideKey);
				validate();
			}
		});
		// Proxy address
		Label proxyAddressLabel = new Label(advancedSubGroup, SWT.None);
		proxyAddressLabel.setText(Messages.XDebugDebuggerSettingsSection_Proxy_address);
		final Text proxyAddressText = new Text(advancedSubGroup, SWT.BORDER);
		GridData patLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		proxyAddressText.setLayoutData(patLayoutData);
		proxyAddressText.setText(settingsWorkingCopy.getAttribute(PROP_PROXY_ADDRESS));
		proxyAddressText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String proxyAddress = proxyAddressText.getText();
				settingsWorkingCopy.setAttribute(PROP_PROXY_ADDRESS, proxyAddress);
				validate();
			}
		});
		// Set up enabled/disabled for proxy
		enableProxy.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				proxyIdeKeyText.setEnabled(enableProxy.getSelection());
				proxyAddressText.setEnabled(enableProxy.getSelection());
				settingsWorkingCopy.setAttribute(PROP_PROXY_ENABLE, String.valueOf(enableProxy.getSelection()));
				validate();
			}
		});
		boolean isProxyEnabled = Boolean.valueOf(settingsWorkingCopy.getAttribute(PROP_PROXY_ENABLE));
		enableProxy.setSelection(isProxyEnabled);
		proxyIdeKeyText.setEnabled(isProxyEnabled);
		proxyAddressText.setEnabled(isProxyEnabled);
	}

}
