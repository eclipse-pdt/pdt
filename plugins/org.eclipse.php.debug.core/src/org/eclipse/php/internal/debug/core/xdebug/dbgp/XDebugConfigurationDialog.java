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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceInit;
import org.eclipse.php.internal.debug.core.xdebug.XDebugUIAttributeConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * An XDebug configuration dialog for editing/viewing the XDebug debugger configurations.
 * 
 * @author Shalom Gibly, Dave Kelsey
 */
public class XDebugConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	private Text portTextBox;
	//	private Text timeoutTextBox;
	private Button showGlobals;
	private Spinner variableDepth;
	private XDebugDebuggerConfiguration xdebugDebuggerConfiguration;

	/**
	 * Constructs a new XDebug configuration dialog. 
	 * @param xdebugDebuggerConfiguration 
	 * 
	 * @param parentShell
	 */
	public XDebugConfigurationDialog(XDebugDebuggerConfiguration xdebugDebuggerConfiguration, Shell parentShell) {
		super(parentShell);
		this.xdebugDebuggerConfiguration = xdebugDebuggerConfiguration;
	}

	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		setTitle(PHPDebugCoreMessages.XDebugConfigurationDialog_xdebugSettings);
		Composite composite = createSubsection(parent, "Xdebug/DBGp"); //$NON-NLS-1$
		addLabelControl(composite, "Debug port", XDebugUIAttributeConstants.XDEBUG_PREF_PORT); //$NON-NLS-1$
		portTextBox = addNumTextField(composite, XDebugUIAttributeConstants.XDEBUG_PREF_PORT, 5, 2, false);
		//		addLabelControl(composite, PHPDebugCoreMessages.XDebugConfigurationDialog_debugPortTimeout, XDebugUIAttributeConstants.XDEBUG_PREF_TIMEOUT);
		//		timeoutTextBox = addNumTextField(composite, XDebugUIAttributeConstants.XDEBUG_PREF_TIMEOUT, 5, 2, true);
		//		addLabelControl(composite, "Default server URL", XDebugUIAttributeConstants.XDEBUG_PREF_DEFAULTSERVERURL); //$NON-NLS-1$
		//		defaultServerURLBox = addDefaultServerURLTextField(composite, XDebugUIAttributeConstants.XDEBUG_PREF_DEFAULTSERVERURL, 2);
		showGlobals = addCheckBox(composite, PHPDebugCoreMessages.XDebugConfigurationDialog_showSuperGlobals, XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS, 0);
		addLabelControl(composite, PHPDebugCoreMessages.XDebugConfigurationDialog_maxArrayDepth, XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH);
		variableDepth = addVariableLevel(composite, XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH, 1, 100, 2);
		internalInitializeValues(); // Initialize the dialog's values.
		return composite;
	}

	private Text addNumTextField(Composite parent, String key, int textLimit, int horizontalIndent, boolean isTimeout) {
		Text text = super.addTextField(parent, key, textLimit, horizontalIndent);
		text.addModifyListener(new NumFieldValidateListener(isTimeout));
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		// Save the preferences
		IDELayer layer = IDELayerFactory.getIDELayer();
		Preferences prefs = layer.getPrefs();

		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_PORT, portTextBox.getText());
		//		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_TIMEOUT, timeoutTextBox.getText());

		// No need, it will be handled by the XDebugCommunicationDaemon.
		//		try {
		//			int port = Integer.parseInt(portTextBox.getText());
		//			int timeout = Integer.parseInt(timeoutTextBox.getText());
		//			DBGpSessionHandler.setGlobalHandlerProperties(port, timeout);
		//		} catch (NumberFormatException nfe) {
		//		}

		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS, showGlobals.getSelection());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH, variableDepth.getSelection());
		//		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_DEFAULTSERVERURL, defaultServerURLBox.getText());
		PHPDebugPlugin.getDefault().savePluginPreferences(); // save
		super.okPressed();
	}

	// Initialize the dialog's values.
	private void internalInitializeValues() {
		IDELayer layer = IDELayerFactory.getIDELayer();
		Preferences prefs = layer.getPrefs();
		int port = prefs.getInt(XDebugUIAttributeConstants.XDEBUG_PREF_PORT);
		if (0 == port) {
			XDebugPreferenceInit.setDefaults();
			port = prefs.getInt(XDebugUIAttributeConstants.XDEBUG_PREF_PORT);
		}
		portTextBox.setText(Integer.toString(port));
		//		int timeout = prefs.getInt(XDebugUIAttributeConstants.XDEBUG_PREF_TIMEOUT);
		//		timeoutTextBox.setText(Integer.toString(timeout));
		showGlobals.setSelection(prefs.getBoolean(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS));
		variableDepth.setSelection(prefs.getInt(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH));
		//		defaultServerURLBox.setText(prefs.getString(XDebugUIAttributeConstants.XDEBUG_PREF_DEFAULTSERVERURL));
	}

	private Spinner addVariableLevel(Composite parent, String key, int min, int max, int horizontalIndent) {
		Spinner spin = new Spinner(parent, SWT.VERTICAL);
		spin.setData(key);
		spin.setMinimum(min);
		spin.setMaximum(max);
		GridData data = new GridData();
		data.horizontalIndent = horizontalIndent;
		data.horizontalSpan = 2;
		spin.setLayoutData(data);
		spin.setIncrement(1);
		spin.setPageIncrement(3);
		return spin;
	}

	class NumFieldValidateListener implements ModifyListener {

		private boolean timeoutField;

		NumFieldValidateListener(boolean isTimeout) {
			timeoutField = isTimeout;
		}

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = new Integer(value);
				int i = iValue.intValue();
				if (!timeoutField) {

					if (i <= 0 || i > 65535) {
						valid = false;
						errorMessage = PHPDebugCoreMessages.XDebugConfigurationDialog_invalidPortRange;
					}
				} else {
					if (i < 10 || i > 100000) {
						valid = false;
						errorMessage = PHPDebugCoreMessages.XDebugConfigurationDialog_invalidTimeout;
					}
				}
			} catch (NumberFormatException e1) {
				valid = false;
				if (!timeoutField) {
					errorMessage = PHPDebugCoreMessages.DebuggerConfigurationDialog_invalidPort;
				} else {
					errorMessage = PHPDebugCoreMessages.XDebugConfigurationDialog_invalidTimeoutValue;
				}
			}

			setErrorMessage(errorMessage);
			Button bt = getButton(IDialogConstants.OK_ID);
			if (bt != null) {
				bt.setEnabled(valid);
			}
		}
	}
}
