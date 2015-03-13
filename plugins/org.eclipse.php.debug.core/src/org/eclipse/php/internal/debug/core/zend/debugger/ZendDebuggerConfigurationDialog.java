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
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.Bundle;

/**
 * Zend debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfigurationDialog extends
		AbstractDebuggerConfigurationDialog {

	protected Button fRunWithDebugInfo;
	protected Text fDebugTextBox;
	protected Text fClientIP;
	protected Text fDebugResponseTimeout;
	protected Button fUseNewProtocol;
	protected ZendDebuggerConfiguration zendDebuggerConfiguration;
	private Button autoModeButton;
	private Button manualModeButton;
	private Image titleImage;

	/**
	 * Constructs a new Zend debugger configuration dialog.
	 * 
	 * @param zendDebuggerConfiguration
	 * @param parentShell
	 */
	public ZendDebuggerConfigurationDialog(
			ZendDebuggerConfiguration zendDebuggerConfiguration,
			Shell parentShell) {
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
		addLabelControl(
				connectionSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_client_host_ip,
				PHPDebugCorePreferenceNames.CLIENT_IP);
		autoModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		autoModeButton
				.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_AutoMode);
		autoModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (autoModeButton.getSelection()) {
					// bring back a default value
					String value = DefaultScope.INSTANCE.getNode(
							PHPDebugPlugin.ID).get(
							PHPDebugCorePreferenceNames.CLIENT_IP, "127.0.0.1"); //$NON-NLS-1$
					fClientIP.setText(value);
				}
			}
		});
		manualModeButton = new Button(connectionSettingsGroup, SWT.RADIO);
		manualModeButton
				.setText(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_ManualMode);
		manualModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fClientIP.setEnabled(manualModeButton.getSelection());
			}
		});
		new Label(connectionSettingsGroup, SWT.NONE);
		fClientIP = addTextField(connectionSettingsGroup,
				PHPDebugCorePreferenceNames.CLIENT_IP, 0, 2);
		GridData gridData = (GridData) fClientIP.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		addLabelControl(connectionSettingsGroup,
				PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
		fDebugTextBox = addTextField(connectionSettingsGroup,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 6, 2);
		gridData = (GridData) fDebugTextBox.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fDebugTextBox.addModifyListener(new DebugPortValidateListener());
		addLabelControl(
				connectionSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_debug_response_timeout,
				PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT);
		fDebugResponseTimeout = addTextField(connectionSettingsGroup,
				PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT, 0, 2);
		gridData = (GridData) fDebugResponseTimeout.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(80);
		fDebugResponseTimeout
				.addModifyListener(new DebugResponseTimeoutListener());
		createNoteComposite(
				connectionSettingsGroup.getFont(),
				connectionSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Note_label,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_Note_text,
				3);
		// General settings
		Composite generalSettingsGroup = createSubsection(parent,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_General_settings_group);
		fRunWithDebugInfo = addCheckBox(
				generalSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_runWithDebugInfo,
				PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);
		fUseNewProtocol = addCheckBox(
				generalSettingsGroup,
				PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_useNewProtocol,
				PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, 0);
		// Initialize the dialog's values.
		internalInitializeValues();
		return connectionSettingsGroup;
	}

	private Image getDialogImage() {
		// TODO - whole dialog should be in debug UI plug-in
		ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
		Bundle bundle = Platform.getBundle("org.eclipse.php.debug.ui"); //$NON-NLS-1$
		URL url = null;
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path(
					"$nl$/icon/full/wizban/zend_debugger_conf_wiz.png"), null); //$NON-NLS-1$
			desc = ImageDescriptor.createFromURL(url);
			return desc.createImage();
		}
		return null;
	}

	private void internalInitializeValues() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fRunWithDebugInfo.setSelection(prefs
				.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fDebugTextBox.setText(Integer.toString(prefs
				.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)));
		IEclipsePreferences instanceScope = InstanceScope.INSTANCE
				.getNode(PHPDebugPlugin.ID);
		String customClientHosts = instanceScope.get(
				PHPDebugCorePreferenceNames.CLIENT_IP, null);
		autoModeButton.setSelection(customClientHosts == null);
		manualModeButton.setSelection(customClientHosts != null);
		fClientIP.setEnabled(customClientHosts != null);
		fClientIP.setText(PHPDebugPlugin.getDebugHosts());
		fDebugResponseTimeout.setText(Integer.toString(prefs
				.getInt(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT)));
		fUseNewProtocol.setSelection(prefs
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL));
	}

	protected void okPressed() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO,
				fRunWithDebugInfo.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT,
				fDebugTextBox.getText());
		IEclipsePreferences instanceScope = InstanceScope.INSTANCE
				.getNode(PHPDebugPlugin.ID);
		if (autoModeButton.getSelection()) {
			instanceScope.remove(PHPDebugCorePreferenceNames.CLIENT_IP);
		} else {
			instanceScope.put(PHPDebugCorePreferenceNames.CLIENT_IP,
					fClientIP.getText());
		}
		prefs.setValue(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT,
				Integer.parseInt(fDebugResponseTimeout.getText()));
		prefs.setValue(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL,
				fUseNewProtocol.getSelection());
		PHPDebugPlugin.getDefault().savePluginPreferences(); // save
		super.okPressed();
	}

	public class DebugPortValidateListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = Integer.valueOf(value);
				int i = iValue.intValue();
				if (i < 0 || i > 65535) {
					valid = false;
					errorMessage = PHPDebugCoreMessages.DebuggerConfigurationDialog_invalidPort;
				}
			} catch (NumberFormatException e1) {
				valid = false;
				errorMessage = PHPDebugCoreMessages.DebuggerConfigurationDialog_invalidPort;
			} catch (Exception e2) {
				valid = false;
				errorMessage = PHPDebugCoreMessages.DebuggerConfigurationDialog_invalidPort;
			}

			setErrorMessage(errorMessage);
			Button bt = getButton(IDialogConstants.OK_ID);
			if (bt != null) {
				bt.setEnabled(valid);
			}
		}
	}

	public class DebugResponseTimeoutListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = Integer.valueOf(value);
				int i = iValue.intValue();
				if (i < 5000) {
					valid = false;
					errorMessage = NLS
							.bind(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time,
									5000);
				}
			} catch (Exception exc) {
				valid = false;
				errorMessage = PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_invalid_response_time_exc;
			}

			setErrorMessage(errorMessage);
			Button bt = getButton(IDialogConstants.OK_ID);
			if (bt != null) {
				bt.setEnabled(valid);
			}
		}
	}
}
