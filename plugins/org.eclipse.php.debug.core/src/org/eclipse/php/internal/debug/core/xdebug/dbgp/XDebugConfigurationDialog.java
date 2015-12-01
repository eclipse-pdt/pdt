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

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.AbstractDebuggerConfigurationDialog;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;

/**
 * An XDebug configuration dialog for editing/viewing the XDebug debugger
 * configurations.
 * 
 * @author Shalom Gibly, Dave Kelsey
 */
public class XDebugConfigurationDialog extends AbstractDebuggerConfigurationDialog {

	// general options
	private Text portTextBox;
	private Button showGlobals;
	private Spinner variableDepth;
	private Spinner maxChildren;
	private Spinner maxData;
	private Button useMultiSession;
	private Combo acceptRemoteSession;

	// output capture options
	private Combo captureStdout;
	private Combo captureStderr;

	// proxy options
	private Button useProxy;
	private Text idekeyTextBox;
	private Text proxyTextBox;
	private Image titleImage;
	private XDebugDebuggerConfiguration xdebugDebuggerConfiguration;

	/**
	 * Constructs a new XDebug configuration dialog.
	 * 
	 * @param xdebugDebuggerConfiguration
	 * 
	 * @param parentShell
	 */
	public XDebugConfigurationDialog(XDebugDebuggerConfiguration xdebugDebuggerConfiguration, Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.xdebugDebuggerConfiguration = xdebugDebuggerConfiguration;
	}

	private void toggleProxyFields(boolean selection) {
		idekeyTextBox.setEnabled(selection);
		proxyTextBox.setEnabled(selection);
	}

	private Image getDialogImage() {
		// TODO - whole dialog should be in debug UI plug-in
		ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
		Bundle bundle = Platform.getBundle("org.eclipse.php.debug.ui"); //$NON-NLS-1$
		URL url = null;
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path("$nl$/icon/full/wizban/xdebug_conf_wiz.png"), null); //$NON-NLS-1$
			desc = ImageDescriptor.createFromURL(url);
			return desc.createImage();
		}
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);
		GridData ptGridData = (GridData) parent.getLayoutData();
		ptGridData.widthHint = convertWidthInCharsToPixels(120);
		// Set dialog title. image, etc.
		getShell().setText(PHPDebugCoreMessages.XDebugConfigurationDialog_Dialog_title);
		setTitle(PHPDebugCoreMessages.XDebugConfigurationDialog_mainTitle);
		setMessage(PHPDebugCoreMessages.XDebugConfigurationDialog_Dialog_description);
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
		// Create main groups
		Composite[] subsections = createSubsections(parent,
				PHPDebugCoreMessages.XDebugConfigurationDialog_Connection_settings_group,
				PHPDebugCoreMessages.XDebugConfigurationDialog_General_settings_group);
		// Connection settings
		Composite connectionSettingsGroup = subsections[0];
		addLabelControl(connectionSettingsGroup, PHPDebugCoreMessages.DebuggerConfigurationDialog_debugPort,
				XDebugPreferenceMgr.XDEBUG_PREF_PORT);
		portTextBox = addNumTextField(connectionSettingsGroup, XDebugPreferenceMgr.XDEBUG_PREF_PORT, 5, 2, false);
		Group proxyGroup = new Group(connectionSettingsGroup, SWT.SHADOW_NONE);
		proxyGroup.setText(PHPDebugCoreMessages.XDebugConfigurationDialog_proxyGroup);
		GridData pgGridata = new GridData(SWT.FILL, SWT.FILL, true, true);
		pgGridata.horizontalSpan = 2;
		proxyGroup.setLayoutData(pgGridata);
		GridLayout pgLayout = new GridLayout();
		pgLayout.numColumns = 2;
		proxyGroup.setLayout(pgLayout);
		useProxy = addCheckBox(proxyGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_useProxy,
				XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, 0);
		useProxy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				toggleProxyFields(useProxy.getSelection());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				toggleProxyFields(useProxy.getSelection());
			}
		});
		addLabelControl(proxyGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_idekey,
				XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY);
		idekeyTextBox = addATextField(proxyGroup, XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, 100, 2);
		addLabelControl(proxyGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_proxy,
				XDebugPreferenceMgr.XDEBUG_PREF_PROXY);
		proxyTextBox = addATextField(proxyGroup, XDebugPreferenceMgr.XDEBUG_PREF_PROXY, 100, 2);
		createNoteComposite(connectionSettingsGroup.getFont(), connectionSettingsGroup,
				PHPDebugCoreMessages.XDebugConfigurationDialog_Note_label,
				PHPDebugCoreMessages.XDebugConfigurationDialog_Note_text, 3);
		// General settings
		Composite generalSettingsGroup = subsections[1];
		acceptRemoteSession = addComboField(generalSettingsGroup,
				PHPDebugCoreMessages.XDebugConfigurationDialog_remoteSession, XDebugPreferenceMgr.remoteSessionOptions);
		useMultiSession = addCheckBox(generalSettingsGroup,
				PHPDebugCoreMessages.XDebugConfigurationDialog_useMultisession,
				XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, 0);
		showGlobals = addCheckBox(generalSettingsGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_showSuperGlobals,
				XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, 0);
		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_maxArrayDepth,
				XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH);
		variableDepth = addVariableLevel(generalSettingsGroup, XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, 1, 150, 2);
		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_maxChildren,
				XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN);
		maxChildren = addVariableLevel(generalSettingsGroup, XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, 1, 500, 2);
		addLabelControl(generalSettingsGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_MaxData,
				XDebugPreferenceMgr.XDEBUG_PREF_DATA);
		maxData = addVariableLevel(generalSettingsGroup, XDebugPreferenceMgr.XDEBUG_PREF_DATA, 1, Integer.MAX_VALUE, 2);
		Group captureGroup = new Group(generalSettingsGroup, SWT.SHADOW_NONE);
		captureGroup.setText(PHPDebugCoreMessages.XDebugConfigurationDialog_captureGroup);
		GridData cGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		cGridData.horizontalSpan = 2;
		captureGroup.setLayoutData(cGridData);
		GridLayout cLayout = new GridLayout();
		cLayout.numColumns = 3;
		captureGroup.setLayout(cLayout);
		captureStdout = addComboField(captureGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_captureStdout,
				XDebugPreferenceMgr.captureOutputOptions);
		captureStderr = addComboField(captureGroup, PHPDebugCoreMessages.XDebugConfigurationDialog_captureStderr,
				XDebugPreferenceMgr.captureOutputOptions);
		// Initialize the dialog's values.
		internalInitializeValues();
		return parent;
	}

	private Text addNumTextField(Composite parent, String key, int textLimit, int horizontalIndent, boolean isTimeout) {
		Text text = super.addTextField(parent, key, textLimit, horizontalIndent);
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		return text;
	}

	private Text addATextField(Composite parent, String key, int minWidth, int horizontalIndent) {
		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = horizontalIndent;
		data.grabExcessHorizontalSpace = true;
		data.minimumWidth = minWidth;
		textBox.setLayoutData(data);
		return textBox;
	}

	private Combo addComboField(Composite parent, String text, String[] options) {
		addLabelControl(parent, text, null);
		Combo comboBox = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		comboBox.setLayoutData(data);
		comboBox.setItems(options);
		return comboBox;
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
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
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

		return new Group[] { group, group2 };
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		// TODO: move to preference manager
		IEclipsePreferences prefs = PHPDebugPlugin.getInstancePreferences();

		// general
		prefs.put(XDebugPreferenceMgr.XDEBUG_PREF_PORT, portTextBox.getText());
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, showGlobals.getSelection());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, variableDepth.getSelection());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, maxChildren.getSelection());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_DATA, maxData.getSelection());
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, useMultiSession.getSelection());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION, acceptRemoteSession.getSelectionIndex());

		// capture output
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT, captureStdout.getSelectionIndex());
		prefs.putInt(XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR, captureStderr.getSelectionIndex());

		// proxy
		prefs.putBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, useProxy.getSelection());
		prefs.put(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, idekeyTextBox.getText());
		prefs.put(XDebugPreferenceMgr.XDEBUG_PREF_PROXY, proxyTextBox.getText());

		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
		super.okPressed();
	}

	// Initialize the dialog's values.
	private void internalInitializeValues() {
		IPreferencesService service = Platform.getPreferencesService();

		portTextBox.setText(
				Integer.toString(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_PORT, 9000, null)));
		showGlobals.setSelection(
				service.getBoolean(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_SHOWSUPERGLOBALS, false, null));
		useMultiSession.setSelection(
				service.getBoolean(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION, false, null));
		variableDepth
				.setSelection(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_ARRAYDEPTH, 0, null));
		maxChildren.setSelection(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_CHILDREN, 0, null));
		maxData.setSelection(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_DATA, 0, null));
		acceptRemoteSession
				.select(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_REMOTESESSION, 0, null));

		// capture output
		captureStdout.select(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDOUT, 0, null));
		captureStderr.select(service.getInt(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_CAPTURESTDERR, 0, null));

		// proxy defaults
		boolean useProxyState = service.getBoolean(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY, false,
				null);
		useProxy.setSelection(useProxyState);
		String ideKey = service.getString(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY, "", null);
		if (ideKey == null || ideKey.length() == 0) {
			ideKey = ""; //$NON-NLS-1$
		}
		idekeyTextBox.setText(ideKey);
		proxyTextBox.setText(service.getString(PHPDebugPlugin.ID, XDebugPreferenceMgr.XDEBUG_PREF_PROXY, "", null)); //$NON-NLS-1$
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
	private Spinner addVariableLevel(Composite parent, String key, int min, int max, int horizontalIndent) {
		Spinner spin = new Spinner(parent, SWT.VERTICAL);
		spin.setData(key);
		spin.setMinimum(min);
		spin.setMaximum(max);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = horizontalIndent;
		data.horizontalSpan = 2;
		spin.setLayoutData(data);
		spin.setIncrement(1);
		spin.setPageIncrement(3);
		return spin;
	}

	protected void validate() {
		// Reset state
		setMessage(PHPDebugCoreMessages.XDebugConfigurationDialog_Dialog_description);
		String debugPort = portTextBox.getText();
		Integer portNumber = null;
		try {
			portNumber = Integer.valueOf(debugPort);
			int i = portNumber.intValue();
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
		// Check warnings
		if (!PHPLaunchUtilities.isPortAvailable(portNumber)
				&& !PHPLaunchUtilities.isDebugDaemonActive(portNumber, XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID)) {
			setMessage(NLS.bind(PHPDebugCoreMessages.DebugConfigurationDialog_PortInUse, debugPort),
					IMessageProvider.WARNING);
		}
	}

}
