/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 *
 */
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.Inet4Address;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.core.util.NetworkMonitor;
import org.eclipse.php.internal.core.util.NetworkMonitor.IHostsValidationListener;
import org.eclipse.php.internal.core.util.NetworkUtil;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.BroadcastDaemon;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.communication.DaemonsRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Zend debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	protected ZendDebuggerConfiguration fDebuggerConfiguration;
	private Button fRunWithDebugInfo;
	private Text fDebugTextBox;
	private Text fClientIP;
	private Button fClientIPButton;
	private Text fDebugResponseTimeout;
	private Button fUseNewProtocol;
	private Button fAutoModeButton;
	private Button fManualModeButton;
	private Text fBroadcastPortText;
	private Text fDummyFileText;
	private Button fUseSSLButton;
	private NetworkMonitor fNetworkMonitor;
	private Composite fClientIpComposite;
	private Image fTitleImage;

	/**
	 * Constructs a new Zend debugger configuration dialog.
	 * 
	 * @param zendDebuggerConfiguration
	 * @param parentShell
	 */
	public ZendDebuggerConfigurationDialog(ZendDebuggerConfiguration zendDebuggerConfiguration, Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.fDebuggerConfiguration = zendDebuggerConfiguration;
		this.fNetworkMonitor = new NetworkMonitor();
	}

	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		parent = (Composite) super.createDialogArea(parent);
		GridData ptGridData = (GridData) parent.getLayoutData();
		ptGridData.widthHint = convertWidthInCharsToPixels(120);
		// Set dialog title. image, etc.
		getShell().setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Dialog_title);
		setTitle(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_zendDebuggerSettings);
		setMessage(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Dialog_description);
		fTitleImage = getDialogImage();
		if (fTitleImage != null)
			setTitleImage(fTitleImage);
		getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (fTitleImage != null)
					fTitleImage.dispose();
			}
		});
		// Connection settings
		Composite connectionSettingsGroup = createSubsection(parent,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Connection_settings_group);
		addLabelControl(connectionSettingsGroup, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_client_host_ip,
				PHPDebugCorePreferenceNames.CLIENT_IP);
		fAutoModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		fAutoModeButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_AutoMode);
		fAutoModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fAutoModeButton.getSelection()) {
					// bring back a default value
					String value = DefaultScope.INSTANCE.getNode(PHPDebugPlugin.ID)
							.get(PHPDebugCorePreferenceNames.CLIENT_IP, "127.0.0.1"); //$NON-NLS-1$
					fClientIP.setText(value);
					fClientIPButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Reload);
					fClientIpComposite.layout(true);
				}
			}
		});
		fManualModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		fManualModeButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_ManualMode);
		fManualModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fClientIP.setEnabled(fManualModeButton.getSelection());
				fClientIPButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Configure);
				fClientIpComposite.layout(true);
			}
		});
		new Label(connectionSettingsGroup, SWT.NONE);
		fClientIpComposite = new Composite(connectionSettingsGroup, SWT.NONE);
		fClientIpComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		GridLayout cicLayout = new GridLayout(3, false);
		cicLayout.marginHeight = 0;
		cicLayout.marginWidth = 0;
		fClientIpComposite.setLayout(cicLayout);
		fClientIP = addTextField(fClientIpComposite, PHPDebugCorePreferenceNames.CLIENT_IP, 0, 1);
		fClientIP.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		GridData gridData = (GridData) fClientIP.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fClientIPButton = SWTFactory.createPushButton(fClientIpComposite, null, null);
		fClientIPButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fAutoModeButton.getSelection()) {
					reloadIPs();
				} else if (fManualModeButton.getSelection()) {
					openIPsDialog();
				}
			}
		});
		addLabelControl(connectionSettingsGroup, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
		fDebugTextBox = addTextField(connectionSettingsGroup, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 6, 2);
		gridData = (GridData) fDebugTextBox.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fDebugTextBox.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				validate();
			}
		});
		addLabelControl(connectionSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_debug_response_timeout,
				PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT);
		fDebugResponseTimeout = addTextField(connectionSettingsGroup,
				PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT, 0, 2);
		gridData = (GridData) fDebugResponseTimeout.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fDebugResponseTimeout.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				validate();
			}
		});
		createNoteComposite(connectionSettingsGroup.getFont(), connectionSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Note_label,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Note_text, 3);
		// General settings
		Composite generalSettingsGroup = createSubsection(parent,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_General_settings_group);
		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Broadcast_port,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT);
		fBroadcastPortText = addTextField(generalSettingsGroup, PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT,
				6, 2);
		gridData = (GridData) fBroadcastPortText.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(100);
		fBroadcastPortText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				validate();
			}
		});

		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Dummy_file_name,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE);
		fDummyFileText = addTextField(generalSettingsGroup, PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, 20, 2);
		gridData = (GridData) fDummyFileText.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(100);

		fUseSSLButton = addCheckBox(generalSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_UseSSLEncryption,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, 0);
		fRunWithDebugInfo = addCheckBox(generalSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_runWithDebugInfo,
				PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);
		fUseNewProtocol = addCheckBox(generalSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_useNewProtocol,
				PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, 0);
		// Initialize the dialog's values.
		internalInitializeValues();
		return parent;
	}

	private Image getDialogImage() {
		// TODO - whole dialog should be in debug UI plug-in
		ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
		Bundle bundle = Platform.getBundle("org.eclipse.php.debug.ui"); //$NON-NLS-1$
		URL url = null;
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path("$nl$/icon/full/wizban/zend_debugger_conf_wiz.png"), null); //$NON-NLS-1$
			desc = ImageDescriptor.createFromURL(url);
			return desc.createImage();
		}
		return null;
	}

	private void internalInitializeValues() {
		IPreferencesService service = Platform.getPreferencesService();
		fRunWithDebugInfo.setSelection(
				service.getBoolean(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, false, null));
		fDebugTextBox.setText(Integer
				.toString(service.getInt(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 0, null)));
		IEclipsePreferences instanceScope = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID);
		String customClientHosts = instanceScope.get(PHPDebugCorePreferenceNames.CLIENT_IP, null);
		fAutoModeButton.setSelection(customClientHosts == null);
		fManualModeButton.setSelection(customClientHosts != null);
		fClientIP.setEnabled(customClientHosts != null);
		fClientIP.setText(PHPDebugPlugin.getDebugHosts());
		fDebugResponseTimeout.setText(Integer.toString(
				service.getInt(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT, 0, null)));
		fUseNewProtocol.setSelection(
				service.getBoolean(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, false, null));
		fBroadcastPortText.setText(Integer.toString(
				service.getInt(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, 0, null)));
		fDummyFileText.setText(
				service.getString(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, null, null));
		fUseSSLButton.setSelection(service.getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false, null));
		fClientIPButton.setText(
				fManualModeButton.getSelection() ? PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Configure
						: PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Reload);
	}

	private String getClientIPs(String userHosts) {
		final List<Inet4Address> detectedIPs = new ArrayList<Inet4Address>();
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
			public void run() {
				detectedIPs.addAll(fNetworkMonitor.getAllAddresses());
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

	private void reloadIPs() {
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
			public void run() {
				// Reset network monitor to have the latest results
				fNetworkMonitor = new NetworkMonitor();
				List<Inet4Address> addresses = new ArrayList<Inet4Address>();
				addresses.addAll(fNetworkMonitor.getPrivateAddresses());
				addresses.add(NetworkUtil.LOCALHOST);
				StringBuffer addressesString = new StringBuffer();
				for (Inet4Address address : addresses) {
					addressesString.append((addressesString.length() != 0 ? ", " : "") + address.getHostAddress());
				}
				fClientIP.setText(addressesString.toString());
			}
		});
		validate();
	}

	private void openIPsDialog() {
		String clientIPs = getClientIPs(fClientIP.getText());
		if (!clientIPs.isEmpty()) {
			fClientIP.setText(clientIPs);
			validate();
		}
	}

	protected void okPressed() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID);
		prefs.putBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, fDebugTextBox.getText());
		prefs.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT,
				Integer.parseInt(fBroadcastPortText.getText()));
		prefs.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, fDummyFileText.getText().trim());
		if (fAutoModeButton.getSelection()) {
			prefs.remove(PHPDebugCorePreferenceNames.CLIENT_IP);
		} else {
			prefs.put(PHPDebugCorePreferenceNames.CLIENT_IP, fClientIP.getText());
		}
		prefs.putInt(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT,
				Integer.parseInt(fDebugResponseTimeout.getText()));
		prefs.putBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, fUseNewProtocol.getSelection());
		prefs.putBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, fUseSSLButton.getSelection());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
		super.okPressed();
	}

	protected void validate() {
		// Reset state
		setMessage(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Dialog_description);
		// Check errors
		String debugPort = fDebugTextBox.getText();
		Integer debugPortNumber = null;
		try {
			debugPortNumber = Integer.valueOf(debugPort);
			int i = debugPortNumber.intValue();
			if (i < 1 || i > 65535) {
				setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPortRange, IMessageProvider.ERROR);
				return;
			}
		} catch (NumberFormatException ex) {
			setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort, IMessageProvider.ERROR);
			return;
		} catch (Exception e) {
			setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort, IMessageProvider.ERROR);
			return;
		}
		String broadcastPort = fBroadcastPortText.getText();
		Integer broadcastPortNumber = null;
		try {
			broadcastPortNumber = Integer.valueOf(broadcastPort);
			int i = broadcastPortNumber.intValue();
			if (i < 1 || i > 65535) {
				setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPortRange, IMessageProvider.ERROR);
				return;
			}
		} catch (NumberFormatException ex) {
			setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort, IMessageProvider.ERROR);
			return;
		} catch (Exception e) {
			setMessage(PHPDebugCoreMessages.DebugConfigurationDialog_invalidPort, IMessageProvider.ERROR);
			return;
		}
		String responseTime = fDebugResponseTimeout.getText();
		Integer responseTimeout = null;
		try {
			responseTimeout = Integer.valueOf(responseTime);
			int i = responseTimeout.intValue();
			if (i < 5000) {
				setMessage(NLS.bind(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time, 5000),
						IMessageProvider.ERROR);
				return;
			}
		} catch (Exception exc) {
			setMessage(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time_exc,
					IMessageProvider.ERROR);
			return;
		}
		// Check warnings
		String[] clientHosts = PHPDebugUtil.getZendHostsArray(fClientIP.getText());
		fNetworkMonitor.validate(clientHosts, new IHostsValidationListener[] { new IHostsValidationListener() {
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
								PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_ClientIPWarning,
								addresses.toString());
					} else {
						message = MessageFormat.format(
								PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_ClientIPsWarning,
								addresses.toString());
					}
					final String warningMessage = message;
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (!getShell().isDisposed() && getButton(IDialogConstants.OK_ID).isEnabled())
								setMessage(warningMessage, IMessageProvider.WARNING);
						}
					});
				}
			}
		} });
		if (!PHPLaunchUtilities.isPortAvailable(debugPortNumber) && !PHPLaunchUtilities
				.isDebugDaemonActive(debugPortNumber, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
			setMessage(NLS.bind(PHPDebugCoreMessages.DebugConfigurationDialog_PortInUse, debugPort),
					IMessageProvider.WARNING);
		}
		if (!PHPLaunchUtilities.isPortAvailable(broadcastPortNumber)) {
			for (ICommunicationDaemon daemon : DaemonsRegistry.getDaemons()) {
				if (daemon instanceof BroadcastDaemon && !((BroadcastDaemon) daemon).isListening(broadcastPortNumber)) {
					setMessage(NLS.bind(PHPDebugCoreMessages.DebugConfigurationDialog_PortInUse, broadcastPort),
							IMessageProvider.WARNING);
				}
			}
		}
	}

}
