/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

/**
 * Zend debugger configuration class.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class ZendDebuggerConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	private Text fDebugTextBox;
	private Button fRunWithDebugInfo;
	private ZendDebuggerConfiguration zendDebuggerConfiguration;

	/**
	 * Constructs a new Zend debugger configuration dialog.
	 * @param zendDebuggerConfiguration 
	 * @param parentShell
	 */
	public ZendDebuggerConfigurationDialog(ZendDebuggerConfiguration zendDebuggerConfiguration, Shell parentShell) {
		super(parentShell);
		this.zendDebuggerConfiguration = zendDebuggerConfiguration;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#getInitialSize()
	 */
	protected Point getInitialSize() {
		Point p = super.getInitialSize();
		p.y -= 50;
		return p;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		setTitle(PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_zendDebuggerSettings);
		Composite composite = createSubsection(parent, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_zendDebugger);
		addLabelControl(composite, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT);
		fDebugTextBox = addTextField(composite, PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 6, 2);
		fDebugTextBox.addModifyListener(new DebugPortValidateListener());
		fRunWithDebugInfo = addCheckBox(composite, PHPDebugCoreMessages.ZendDebuggerConfigurationDialog_runWithDebugInfo, PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);
		internalInitializeValues(); // Initialize the dialog's values.
		return composite;
	}

	private void internalInitializeValues() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fRunWithDebugInfo.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fDebugTextBox.setText(Integer.toString(prefs.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, fDebugTextBox.getText());
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
