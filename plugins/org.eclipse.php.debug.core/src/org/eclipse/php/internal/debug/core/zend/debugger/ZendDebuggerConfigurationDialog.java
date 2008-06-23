/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 *
 */
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Zend debugger configuration class.
 *
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	private Text fDebugTextBox;
	private Button fRunWithDebugInfo;
	private Text fClientIP;
	private ZendDebuggerConfiguration zendDebuggerConfiguration;

	/**
	 * Constructs a new Zend debugger configuration dialog.
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
		setTitle(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_zendDebuggerSettings);

		Composite composite = createSubsection(parent, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_zendDebugger);

		addLabelControl(composite, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
		fDebugTextBox = addTextField(composite, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 6, 2);
		GridData gridData = (GridData)fDebugTextBox.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(100);

		fDebugTextBox.addModifyListener(new DebugPortValidateListener());

		fRunWithDebugInfo = addCheckBox(composite, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_runWithDebugInfo, PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);

		addLabelControl(composite, "Client Host/IP:", PHPDebugCorePreferenceNames.CLIENT_IP);
		fClientIP = addTextField(composite, PHPDebugCorePreferenceNames.CLIENT_IP, 0, 2);
		gridData = (GridData)fClientIP.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(100);

		internalInitializeValues(); // Initialize the dialog's values.

		return composite;
	}

	private void internalInitializeValues() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fRunWithDebugInfo.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fDebugTextBox.setText(Integer.toString(prefs.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)));
		fClientIP.setText(prefs.getString(PHPDebugCorePreferenceNames.CLIENT_IP));
	}

	protected void okPressed() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, fDebugTextBox.getText());
		prefs.setValue(PHPDebugCorePreferenceNames.CLIENT_IP, fClientIP.getText());
		PHPDebugPlugin.getDefault().savePluginPreferences(); // save
		super.okPressed();
	}

	class DebugPortValidateListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = new Integer(value);
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
}
