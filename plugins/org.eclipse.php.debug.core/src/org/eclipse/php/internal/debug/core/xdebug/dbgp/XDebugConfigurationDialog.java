/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.net.InetAddress;

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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * An XDebug configuration dialog for editing/viewing the XDebug debugger configurations.
 * 
 * @author Shalom Gibly, Dave Kelsey
 */
public class XDebugConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	private Text portTextBox;
	private Button showGlobals;
	private Spinner variableDepth;
	private Button useMultiSession;
	private Button useProxy;
	private Text idekeyTextBox;
	private Text proxyTextBox;
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

	private void toggleProxyFields(boolean selection) {
		idekeyTextBox.setEnabled(selection);
		proxyTextBox.setEnabled(selection);	
	}

	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		setTitle(PHPDebugCoreMessages.XDebugConfigurationDialog_xdebugSettings);
		Composite[] subsections = createSubsections(parent, PHPDebugCoreMessages.XDebugConfigurationDialog_xdebugSettings,
														PHPDebugCoreMessages.XDebugConfigurationDialog_proxyGroup);
		
		Composite mainSubSection = subsections[0];		
		addLabelControl(mainSubSection, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort, XDebugUIAttributeConstants.XDEBUG_PREF_PORT); //$NON-NLS-1$
		portTextBox = addNumTextField(mainSubSection, XDebugUIAttributeConstants.XDEBUG_PREF_PORT, 5, 2, false);
		showGlobals = addCheckBox(mainSubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_showSuperGlobals, XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS, 0);
		addLabelControl(mainSubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_maxArrayDepth, XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH);
		variableDepth = addVariableLevel(mainSubSection, XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH, 1, 150, 2);		
		useMultiSession = addCheckBox(mainSubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_useMultisession, XDebugUIAttributeConstants.XDEBUG_PREF_MULTISESSION, 0);
		
		Composite proxySubSection = subsections[1];
		useProxy = addCheckBox(proxySubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_useProxy, XDebugUIAttributeConstants.XDEBUG_PREF_USEPROXY, 0);
		useProxy.addSelectionListener(new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
				toggleProxyFields(useProxy.getSelection());
				
			}

		public void widgetDefaultSelected(SelectionEvent e) {
			toggleProxyFields(useProxy.getSelection());
		}	
		});
		addLabelControl(proxySubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_idekey, XDebugUIAttributeConstants.XDEBUG_PREF_IDEKEY); //$NON-NLS-1$
		idekeyTextBox = addATextField(proxySubSection, XDebugUIAttributeConstants.XDEBUG_PREF_IDEKEY, 100, 2);
		addLabelControl(proxySubSection, PHPDebugCoreMessages.XDebugConfigurationDialog_proxy, XDebugUIAttributeConstants.XDEBUG_PREF_PROXY); //$NON-NLS-1$
		proxyTextBox = addATextField(proxySubSection, XDebugUIAttributeConstants.XDEBUG_PREF_PROXY, 100, 2);
		
		internalInitializeValues(); // Initialize the dialog's values.
		return parent;
	}

	private Text addNumTextField(Composite parent, String key, int textLimit, int horizontalIndent, boolean isTimeout) {
		Text text = super.addTextField(parent, key, textLimit, horizontalIndent);
		text.addModifyListener(new NumFieldValidateListener(isTimeout));
		return text;
	}
	
	private Text addATextField(Composite parent, String key, int minWidth, int horizontalIndent) {
		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = horizontalIndent;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.minimumWidth = minWidth;
		textBox.setLayoutData(data);
		return textBox;
	}	
	
	/**
	 * Creates a subsection group.
	 * 
	 * @param parent
	 * @param label
	 * @return
	 */
	protected Composite[] createSubsections(Composite parent, String label, String label2) {
		// A cosmetic composite that will add a basic indent
		parent = new Composite(parent, SWT.NONE);
		parent.setLayout(new GridLayout(1, true));
		GridData data = new GridData(GridData.FILL_BOTH);
		parent.setLayoutData(data);

		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		
		Group group2 = new Group(parent, SWT.SHADOW_NONE);
		group2.setText(label2);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group2.setLayoutData(data);
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		group2.setLayout(layout2);
		return new Group[] {group, group2};
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		// Save the preferences
		IDELayer layer = IDELayerFactory.getIDELayer();
		Preferences prefs = layer.getPrefs();

		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_PORT, portTextBox.getText());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS, showGlobals.getSelection());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH, variableDepth.getSelection());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_MULTISESSION, useMultiSession.getSelection());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_USEPROXY, useProxy.getSelection());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_IDEKEY, idekeyTextBox.getText());
		prefs.setValue(XDebugUIAttributeConstants.XDEBUG_PREF_PROXY, proxyTextBox.getText());
		DBGpProxyHandler.instance.configure();
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
		showGlobals.setSelection(prefs.getBoolean(XDebugUIAttributeConstants.XDEBUG_PREF_SHOWSUPERGLOBALS));
		useMultiSession.setSelection(prefs.getBoolean(XDebugUIAttributeConstants.XDEBUG_PREF_MULTISESSION));
		
		variableDepth.setSelection(prefs.getInt(XDebugUIAttributeConstants.XDEBUG_PREF_ARRAYDEPTH));
		boolean useProxyState = prefs.getBoolean(XDebugUIAttributeConstants.XDEBUG_PREF_USEPROXY);
		useProxy.setSelection(useProxyState);
		String ideKey = prefs.getString(XDebugUIAttributeConstants.XDEBUG_PREF_IDEKEY);
		if (ideKey == null || ideKey.length() == 0) {
			ideKey = DBGpProxyHandler.instance.generateIDEKey();
		}
		idekeyTextBox.setText(ideKey);
		proxyTextBox.setText(prefs.getString(XDebugUIAttributeConstants.XDEBUG_PREF_PROXY));
		toggleProxyFields(useProxyState);
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
