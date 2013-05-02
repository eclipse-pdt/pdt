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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * An XDebug configuration dialog for editing/viewing the XDebug debugger
 * configurations.
 * 
 * @author Shalom Gibly, Dave Kelsey
 */
public class XDebugConfigurationDialog extends
		AbstractDebuggerConfigurationDialog {

	private ComboListener comboListener;

	// general options
	private Text portTextBox;
	private Button showGlobals;
	private Spinner variableDepth;
	private Spinner maxChildren;
	private Button useMultiSession;
	private Combo acceptRemoteSession;

	// output capture options
	private Combo captureStdout;
	private Combo captureStderr;

	// proxy options
	private Button useProxy;
	private Text idekeyTextBox;
	private Text proxyTextBox;
	private XDebugDebuggerConfiguration xdebugDebuggerConfiguration;

	/**
	 * Constructs a new XDebug configuration dialog.
	 * 
	 * @param xdebugDebuggerConfiguration
	 * 
	 * @param parentShell
	 */
	public XDebugConfigurationDialog(
			XDebugDebuggerConfiguration xdebugDebuggerConfiguration,
			Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.xdebugDebuggerConfiguration = xdebugDebuggerConfiguration;
	}

	private void toggleProxyFields(boolean selection) {
		idekeyTextBox.setEnabled(selection);
		proxyTextBox.setEnabled(selection);
	}

	protected Control createDialogArea(Composite parent) {
		comboListener = new ComboListener();
		parent = (Composite) super.createDialogArea(parent);
		setTitle(PHPDebugCoreMessages.XDebugConfigurationDialog_mainTitle);
		Composite[] subsections = createSubsections(parent,
				PHPDebugCoreMessages.XDebugConfigurationDialog_generalGroup,
				PHPDebugCoreMessages.XDebugConfigurationDialog_captureGroup,
				PHPDebugCoreMessages.XDebugConfigurationDialog_proxyGroup);

		// main
		Composite mainSubSection = subsections[0];
		addLabelControl(mainSubSection,
				PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort,
				XDebugPreferenceMgr.XDEBUG_PREF_PORT); 
		portTextBox = addNumTextField(mainSubSection,
				XDebugPreferenceMgr.XDEBUG_PREF_PORT, 5, 2, false);
		showGlobals = addCheckBox(
				mainSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_showSuperGlobals,
				XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, 0);

		addLabelControl(mainSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_maxArrayDepth,
				XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH);
		variableDepth = addVariableLevel(mainSubSection,
				XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, 1, 150, 2);

		addLabelControl(mainSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_maxChildren,
				XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN);
		maxChildren = addVariableLevel(mainSubSection,
				XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, 1, 500, 2);

		useMultiSession = addCheckBox(mainSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_useMultisession,
				XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, 0);

		acceptRemoteSession = addComboField(mainSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSession,
				XDebugPreferenceMgr.remoteSessionOptions);

		// output capture
		Composite captureSubSection = subsections[1];
		captureStdout = addComboField(captureSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_captureStdout,
				XDebugPreferenceMgr.captureOutputOptions);
		captureStderr = addComboField(captureSubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_captureStderr,
				XDebugPreferenceMgr.captureOutputOptions);

		// proxy
		Composite proxySubSection = subsections[2];
		useProxy = addCheckBox(proxySubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_useProxy,
				XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, 0);
		useProxy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				toggleProxyFields(useProxy.getSelection());

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				toggleProxyFields(useProxy.getSelection());
			}
		});
		addLabelControl(proxySubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_idekey,
				XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY); 
		idekeyTextBox = addATextField(proxySubSection,
				XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, 100, 2);
		addLabelControl(proxySubSection,
				PHPDebugCoreMessages.XDebugConfigurationDialog_proxy,
				XDebugPreferenceMgr.XDEBUG_PREF_PROXY); 
		proxyTextBox = addATextField(proxySubSection,
				XDebugPreferenceMgr.XDEBUG_PREF_PROXY, 100, 2);

		GridData gridData = (GridData) proxyTextBox.getLayoutData();
		gridData.widthHint = convertWidthInCharsToPixels(90);

		internalInitializeValues(); // Initialize the dialog's values.

		return parent;
	}

	private Text addNumTextField(Composite parent, String key, int textLimit,
			int horizontalIndent, boolean isTimeout) {
		Text text = super
				.addTextField(parent, key, textLimit, horizontalIndent);
		text.addModifyListener(new NumFieldValidateListener(isTimeout));
		return text;
	}

	private Text addATextField(Composite parent, String key, int minWidth,
			int horizontalIndent) {
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

	private Combo addComboField(Composite parent, String text, String[] options) {
		addLabelControl(parent, text, null);
		Combo comboBox = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 1;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		comboBox.setLayoutData(data);
		comboBox.setItems(options);
		comboBox.addSelectionListener(comboListener);
		return comboBox;
	}

	/**
	 * Creates a subsection group.
	 * 
	 * @param parent
	 * @param label
	 * @return
	 */
	protected Composite[] createSubsections(Composite parent, String label,
			String label2, String label3) {
		// A cosmetic composite that will add a basic indent
		parent = new Composite(parent, SWT.NONE);
		parent.setLayout(new GridLayout(1, true));
		GridData data = new GridData(GridData.FILL_BOTH);
		parent.setLayoutData(data);

		// subsection 1
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);

		// subsection 2
		Group group2 = new Group(parent, SWT.SHADOW_NONE);
		group2.setText(label2);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group2.setLayoutData(data);
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 3;
		group2.setLayout(layout2);

		// subsection 3
		Group group3 = new Group(parent, SWT.SHADOW_NONE);
		group3.setText(label3);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group3.setLayoutData(data);
		GridLayout layout3 = new GridLayout();
		layout3.numColumns = 2;
		group3.setLayout(layout3);

		return new Group[] { group, group2, group3 };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		// TODO: move to preference manager
		Preferences prefs = XDebugPreferenceMgr.getPreferences();

		// general
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_PORT, portTextBox
				.getText());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS,
				showGlobals.getSelection());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH,
				variableDepth.getSelection());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, maxChildren
				.getSelection());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION,
				useMultiSession.getSelection());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION,
				acceptRemoteSession.getSelectionIndex());

		// capture output
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT,
				captureStdout.getSelectionIndex());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR,
				captureStderr.getSelectionIndex());

		// proxy
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, useProxy
				.getSelection());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, idekeyTextBox
				.getText());
		prefs.setValue(XDebugPreferenceMgr.XDEBUG_PREF_PROXY, proxyTextBox
				.getText());
		DBGpProxyHandler.instance.configure();

		PHPDebugPlugin.getDefault().savePluginPreferences(); // save
		super.okPressed();
	}

	// Initialize the dialog's values.
	private void internalInitializeValues() {
		// TODO: move to preference manager

		Preferences prefs = XDebugPreferenceMgr.getPreferences();

		int port = prefs.getInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT);
		if (0 == port) {
			XDebugPreferenceMgr.setDefaults();
			port = prefs.getInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT);
		}
		portTextBox.setText(Integer.toString(port));
		showGlobals.setSelection(prefs
				.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS));
		useMultiSession.setSelection(prefs
				.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION));
		variableDepth.setSelection(prefs
				.getInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH));
		maxChildren.setSelection(prefs
				.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN));
		acceptRemoteSession.select(prefs
				.getInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION));

		// capture output
		captureStdout.select(prefs
				.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT));
		captureStderr.select(prefs
				.getInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR));

		// proxy defaults
		boolean useProxyState = prefs
				.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY);
		useProxy.setSelection(useProxyState);
		String ideKey = prefs.getString(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY);
		if (ideKey == null || ideKey.length() == 0) {
			ideKey = DBGpProxyHandler.instance.generateIDEKey();
		}
		idekeyTextBox.setText(ideKey);
		proxyTextBox.setText(prefs
				.getString(XDebugPreferenceMgr.XDEBUG_PREF_PROXY));
		toggleProxyFields(useProxyState);
	}

	/**
	 * add a spinner control
	 * 
	 * @param parent
	 * @param key
	 * @param min
	 * @param max
	 * @param horizontalIndent
	 * @return
	 */
	private Spinner addVariableLevel(Composite parent, String key, int min,
			int max, int horizontalIndent) {
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

	class ComboListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub

			Object source = e.getSource();
			if (source == acceptRemoteSession) {

			} else if (source == captureStdout) {

			} else if (source == captureStderr) {

			}

		}

		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			Object source = e.getSource();
			if (source == acceptRemoteSession) {

			} else if (source == captureStdout) {

			} else if (source == captureStderr) {

			}

		}

	}

	/**
	 * numeric field validator class
	 * 
	 */
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
