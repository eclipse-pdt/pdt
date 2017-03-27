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

import java.net.Inet4Address;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.ui.DebugServerConnectionTestRegistry;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.core.util.NetworkMonitor;
import org.eclipse.php.internal.core.util.NetworkMonitor.IHostsValidationListener;
import org.eclipse.php.internal.core.util.NetworkUtil;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.ConfigureHostsDialog;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerHostProposalComputer;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Zend debugger settings section for PHP server.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class ZendDebuggerServerSettingsSection implements IDebuggerSettingsSection {

	private IDebuggerSettingsWorkingCopy settingsWorkingCopy;
	private CompositeFragment compositeFragment;
	private Composite settingsComposite;
	private Text clientIpText;
	private NetworkMonitor networkMonitor;
	private boolean skipNetworkMonitor = false;

	/**
	 * Constructor.
	 */
	public ZendDebuggerServerSettingsSection(final CompositeFragment compositeFragment,
			final Composite debuggerSettingsComposite, final IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		this.settingsWorkingCopy = settingsWorkingCopy;
		this.compositeFragment = compositeFragment;
		this.settingsComposite = debuggerSettingsComposite;
		this.networkMonitor = new NetworkMonitor();
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
	public void validate() {
		// Reset state
		compositeFragment.setMessage(compositeFragment.getDescription(), IMessageProvider.NONE);
		// Check errors
		String clientIp = (String) settingsWorkingCopy.getAttribute(PROP_CLIENT_IP);
		if (clientIp == null || clientIp.isEmpty()) {
			compositeFragment.setMessage(Messages.ZendDebuggerServerSettingsSection_Client_IP_is_missing,
					IMessageProvider.ERROR);
			return;
		}
		String clientPort = (String) settingsWorkingCopy.getAttribute(PROP_CLIENT_PORT);
		if (clientPort == null || clientPort.isEmpty()) {
			compositeFragment.setMessage(Messages.ZendDebuggerServerSettingsSection_Client_port_is_missing,
					IMessageProvider.ERROR);
			return;
		}
		String responseTime = (String) settingsWorkingCopy.getAttribute(PROP_RESPONSE_TIMEOUT);
		Integer responseTimeout = null;
		try {
			responseTimeout = Integer.valueOf(responseTime);
			int i = responseTimeout.intValue();
			if (i < 5000) {
				compositeFragment.setMessage(
						NLS.bind(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time, 5000),
						IMessageProvider.ERROR);
				return;
			}
		} catch (Exception exc) {
			compositeFragment.setMessage(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time_exc,
					IMessageProvider.ERROR);
			return;
		}

		// Check warnings
		String[] clientHosts = PHPDebugUtil.getZendHostsArray(clientIpText.getText());
		// Check invalid addresses
		if (!skipNetworkMonitor) {
			networkMonitor.validate(clientHosts, new IHostsValidationListener[] { new IHostsValidationListener() {
				public void validated(List<String> invalidAddresses) {
					if (!invalidAddresses.isEmpty()) {
						StringBuilder addresses = new StringBuilder();
						for (String address : invalidAddresses) {
							addresses.append((addresses.length() != 0 ? ", " : "") //$NON-NLS-1$ //$NON-NLS-2$
									+ '\'' + address + '\'');
						}
						String message;
						if (invalidAddresses.size() == 1) {
							message = MessageFormat.format(
									Messages.ZendDebuggerServerSettingsSection_Client_host_IP_might_be_invalid,
									addresses.toString());
						} else {
							message = MessageFormat.format(
									Messages.ZendDebuggerServerSettingsSection_Client_hosts_IPs_might_be_invalid,
									addresses.toString());
						}
						final String warningMessage = message;
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
							public void run() {
								if (!compositeFragment.isDisposed() && compositeFragment.isVisible()
										&& compositeFragment.isComplete())
									compositeFragment.setMessage(warningMessage, IMessageProvider.WARNING);
							}
						});
					} else {
						skipNetworkMonitor = true;
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
							public void run() {
								if (!compositeFragment.isDisposed() && compositeFragment.isVisible())
									validate();
							}
						});
					}
				}
			} });
		} else {
			skipNetworkMonitor = false;
		}
		// Check redundant hosts
		if (clientHosts.length > 1) {
			compositeFragment.setMessage(Messages.ZendDebuggerServerSettingsSection_Client_host_IPS_might_be_redundant,
					IMessageProvider.WARNING);
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
				&& !PHPLaunchUtilities.isDebugDaemonActive(portNumber, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
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
		IDebugServerConnectionTest[] tests = DebugServerConnectionTestRegistry.getTests(ZendDebuggerConfiguration.ID);
		Server server = (Server) compositeFragment.getData();
		int port = ZendDebuggerSettingsUtil.getDebugPort(settingsWorkingCopy.getOwnerId());
		Set<Integer> allDebugPorts = PHPDebugUtil.getDebugPorts(ZendDebuggerConfiguration.ID);
		AbstractDebuggerCommunicationDaemon tmpDaemon = null;
		if (!allDebugPorts.contains(port))
			tmpDaemon = DebuggerCommunicationDaemon.createDaemon(port);
		for (IDebugServerConnectionTest test : tests) {
			test.testConnection(server, PlatformUI.getWorkbench().getDisplay().getActiveShell());
		}
		if (tmpDaemon != null) {
			tmpDaemon.stopListen();
		}
	}

	protected void createContents() {
		// Main composite
		Composite settingsSection = new Composite(settingsComposite, SWT.NONE);
		GridLayout ssLayout = new GridLayout();
		ssLayout.marginHeight = 0;
		ssLayout.marginWidth = 0;
		settingsSection.setLayout(ssLayout);
		GridData sGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sGridData.horizontalSpan = 2;
		settingsSection.setLayoutData(sGridData);
		// Connection settings group
		createSettingsGroup(settingsSection);
	}

	private void createSettingsGroup(Composite settingsSection) {
		Group connectionGroup = new Group(settingsSection, SWT.NONE);
		connectionGroup.setFont(compositeFragment.getFont());
		GridLayout cgLayout = new GridLayout(3, false);
		connectionGroup.setLayout(cgLayout);
		GridData cgGridData = new GridData(GridData.FILL_HORIZONTAL);
		connectionGroup.setLayoutData(cgGridData);
		connectionGroup.setText(Messages.ZendDebuggerServerSettingsSection_Connection_settings);
		createConnectionSection(connectionGroup);
	}

	private void createConnectionSection(Group connectionGroup) {
		// Client IP
		Label clientIpLabel = new Label(connectionGroup, SWT.None);
		clientIpLabel.setText(Messages.ZendDebuggerServerSettingsSection_Client_IPs);
		clientIpText = new Text(connectionGroup, SWT.BORDER);
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
		String ipTextProposal = getIPsProposal();
		if (ipTextProposal != null) {
			clientIpText.setText(ipTextProposal);
		}
		Button configureIPs = SWTFactory.createPushButton(connectionGroup,
				Messages.ZendDebuggerServerSettingsSection_Configure_button, null);
		configureIPs.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				String clientIPs = getClientIPs(clientIpText.getText());
				if (!clientIPs.isEmpty()) {
					clientIpText.setText(clientIPs);
					validate();
				}
			}
		});
		// Client port
		Label clientPortLabel = new Label(connectionGroup, SWT.None);
		clientPortLabel.setText(Messages.ZendDebuggerServerSettingsSection_Client_port);
		final Text clientPortText = new Text(connectionGroup, SWT.BORDER);
		GridData cptLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		cptLayoutData.horizontalSpan = 2;
		clientPortText.setLayoutData(cptLayoutData);
		clientPortText.setText(settingsWorkingCopy.getAttribute(PROP_CLIENT_PORT));
		clientPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String port = clientPortText.getText();
				settingsWorkingCopy.setAttribute(PROP_CLIENT_PORT, port);
				validate();
			}
		});
		// Response timeout
		Label responseTimeoutLabel = new Label(connectionGroup, SWT.None);
		responseTimeoutLabel.setText(Messages.ZendDebuggerServerSettingsSection_Response_timeout);
		final Text responseTimeoutText = new Text(connectionGroup, SWT.BORDER);
		GridData rttLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		rttLayoutData.horizontalSpan = 2;
		responseTimeoutText.setLayoutData(rttLayoutData);
		responseTimeoutText.setText(settingsWorkingCopy.getAttribute(PROP_RESPONSE_TIMEOUT));
		responseTimeoutText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String responseTimeout = responseTimeoutText.getText();
				settingsWorkingCopy.setAttribute(PROP_RESPONSE_TIMEOUT, responseTimeout);
				validate();
			}
		});
	}

	private String getIPsProposal() {
		final Server server = (Server) compositeFragment.getData();
		// Check if server exists and is not registered yet (wizard context)
		if (server == null || ServersManager.findServer(server.getUniqueId()) != null)
			return null;
		final StringBuilder bestMatches = new StringBuilder();
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
			public void run() {
				String proposals = (new ZendDebuggerHostProposalComputer()).computeProposals(server);
				if (proposals != null)
					bestMatches.append(proposals);
			}
		});
		return bestMatches.length() != 0 ? bestMatches.toString() : null;
	}

	private String getClientIPs(String userHosts) {
		final List<Inet4Address> detectedIPs = new ArrayList<Inet4Address>();
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
			public void run() {
				// Reset network monitor to have latest results
				networkMonitor = new NetworkMonitor();
				detectedIPs.addAll(networkMonitor.getAllAddresses());
			}
		});
		String[] userHostsArray = PHPDebugUtil.getZendHostsArray(userHosts);
		List<Inet4Address> userIPs = new ArrayList<Inet4Address>();
		for (String userHost : userHostsArray) {
			Inet4Address address = NetworkUtil.getByName(userHost, 2000);
			if (address != null)
				userIPs.add(address);
		}
		ConfigureHostsDialog configureIPs = new ConfigureHostsDialog(userIPs, detectedIPs);
		int choice = configureIPs.open();
		if (choice != Window.OK)
			return ""; //$NON-NLS-1$
		List<Inet4Address> selectdIPs = configureIPs.getSelectedIPs();
		StringBuffer stringBuffer = new StringBuffer();
		Iterator<Inet4Address> ipsIterator = selectdIPs.iterator();
		if (ipsIterator.hasNext())
			stringBuffer.append(ipsIterator.next().getHostAddress());
		while (ipsIterator.hasNext()) {
			stringBuffer.append(", " + ipsIterator.next().getHostAddress()); //$NON-NLS-1$
		}
		return stringBuffer.toString();
	}

}
