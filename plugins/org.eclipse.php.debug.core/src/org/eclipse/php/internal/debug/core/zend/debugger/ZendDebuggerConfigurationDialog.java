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

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.BroadcastDaemon;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.communication.DaemonsRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Zend debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	protected Button fRunWithDebugInfo;
	protected Text fDebugTextBox;
	protected Text fClientIP;
	protected Text fDebugResponseTimeout;
	protected Button fUseNewProtocol;
	protected ZendDebuggerConfiguration zendDebuggerConfiguration;
	protected Text fBroadcastPortText;
	protected Text fDummyFileText;
	private Button autoModeButton;
	private Button manualModeButton;
	private Image titleImage;

	/**
	 * Constructs a new Zend debugger configuration dialog.
	 * 
	 * @param zendDebuggerConfiguration
	 * @param parentShell
	 */
	public ZendDebuggerConfigurationDialog(ZendDebuggerConfiguration zendDebuggerConfiguration, Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.zendDebuggerConfiguration = zendDebuggerConfiguration;
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
		titleImage = getDialogImage();
		if (titleImage != null)
			setTitleImage(titleImage);
		getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (titleImage != null)
					titleImage.dispose();
			}
		});
		// Connection settings
		Composite connectionSettingsGroup = createSubsection(parent,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Connection_settings_group);
		addLabelControl(connectionSettingsGroup, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_client_host_ip,
				PHPDebugCorePreferenceNames.CLIENT_IP);
		autoModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		autoModeButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_AutoMode);
		autoModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (autoModeButton.getSelection()) {
					// bring back a default value
					String value = DefaultScope.INSTANCE.getNode(PHPDebugPlugin.ID)
							.get(PHPDebugCorePreferenceNames.CLIENT_IP, "127.0.0.1"); //$NON-NLS-1$
					fClientIP.setText(value);
				}
			}
		});
		manualModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		manualModeButton.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_ManualMode);
		manualModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fClientIP.setEnabled(manualModeButton.getSelection());
			}
		});
		new Label(connectionSettingsGroup, SWT.NONE);
		fClientIP = addTextField(connectionSettingsGroup, PHPDebugCorePreferenceNames.CLIENT_IP, 0, 2);
		GridData gridData = (GridData) fClientIP.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		addLabelControl(connectionSettingsGroup, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
		fDebugTextBox = addTextField(connectionSettingsGroup, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 6, 2);
		gridData = (GridData) fDebugTextBox.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fDebugTextBox.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
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
			public void modifyText(ModifyEvent e) {
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
			public void modifyText(ModifyEvent event) {
				validate();
			}
		});
		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Dummy_file_name,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE);
		fDummyFileText = addTextField(generalSettingsGroup, PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, 20, 2);
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
		autoModeButton.setSelection(customClientHosts == null);
		manualModeButton.setSelection(customClientHosts != null);
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
	}

	protected void okPressed() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID);
		prefs.putBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, fDebugTextBox.getText());
		prefs.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT,
				Integer.parseInt(fBroadcastPortText.getText()));
		prefs.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, fDummyFileText.getText().trim());
		if (autoModeButton.getSelection()) {
			prefs.remove(PHPDebugCorePreferenceNames.CLIENT_IP);
		} else {
			prefs.put(PHPDebugCorePreferenceNames.CLIENT_IP, fClientIP.getText());
		}
		prefs.putInt(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT,
				Integer.parseInt(fDebugResponseTimeout.getText()));
		prefs.putBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, fUseNewProtocol.getSelection());
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
