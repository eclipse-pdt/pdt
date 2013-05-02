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
package org.eclipse.php.internal.server.ui.launching;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.core.tunneling.TunnelTester;
import org.eclipse.php.internal.server.ui.Logger;
import org.eclipse.php.internal.server.ui.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.progress.UIJob;

/**
 * A PHPServerAdvancedTab for selecting advanced debug options, such as 'Debug
 * all Pages', 'Start Debug from' etc.
 * 
 * @author Shalom Gibly
 */
public class PHPServerAdvancedTab extends AbstractLaunchConfigurationTab {

	// flag to be used to decide whether to enable combo in launch config dialog
	// after the user requests a launch, they cannot change it
	private static final String READ_ONLY = "read-only"; //$NON-NLS-1$

	private Button debugFirstPageBt;
	private Button debugAllPagesBt;
	private Button debugStartFromBt;
	private Button debugContinueBt;
	private Button resetBt;
	private Text debugFromTxt;
	protected Button openBrowser;
	protected WidgetListener listener;
	protected ILaunchConfiguration launchConfiguration;
	private Group tunnelGroup;
	private Composite sessionGroup;
	protected boolean isOpenInBrowser;
	private Button debugThroughTunnel;
	private Text userName;
	private Text password;
	private Button testButton;
	private CLabel testResultLabel;
	private Label nameLabel;
	private Label passwordLabel;
	private Combo fDebuggersCombo;
	private Button validateDebuggerBtn;
	private Button configureDebugger;
	private Button breakOnFirstLine;
	public boolean isTextModificationChange;

	private IDebugServerConnectionTest[] debugTesters = new IDebugServerConnectionTest[0];
	private Set<String> fDebuggerIds;

	/**
	 * Constructor
	 */
	public PHPServerAdvancedTab() {
		listener = new WidgetListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createDebuggerSelectionControl(composite);
		createBreakControl(composite);
		createAdvanceControl(composite);
		createExtensionControls(composite);

		Dialog.applyDialogFont(composite);
		setControl(composite);
	}

	/**
	 * Create the advanced control.
	 * 
	 * @param composite
	 */
	protected void createAdvanceControl(Composite composite) {
		// == Groups ==
		tunnelGroup = new Group(composite, SWT.NONE);
		tunnelGroup.setLayout(new GridLayout(1, false));
		tunnelGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tunnelGroup.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.1")); //$NON-NLS-1$

		Group browserGroup = new Group(composite, SWT.NONE);
		browserGroup.setLayout(new GridLayout(1, false));
		browserGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browserGroup.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.2")); //$NON-NLS-1$

		// == Controls ==
		// Add the tunneling controls
		PixelConverter converter = new PixelConverter(composite);
		debugThroughTunnel = new Button(tunnelGroup, SWT.CHECK);
		debugThroughTunnel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.3")); //$NON-NLS-1$
		Composite credentialsComposite = new Composite(tunnelGroup, SWT.NONE);
		credentialsComposite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 20;
		credentialsComposite.setLayoutData(data);
		nameLabel = new Label(credentialsComposite, SWT.NONE);
		nameLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.4")); //$NON-NLS-1$
		userName = new Text(credentialsComposite, SWT.BORDER | SWT.SINGLE);
		data = new GridData();
		data.widthHint = converter.convertHorizontalDLUsToPixels(150);
		userName.setLayoutData(data);
		passwordLabel = new Label(credentialsComposite, SWT.NONE);
		passwordLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.5")); //$NON-NLS-1$
		password = new Text(credentialsComposite, SWT.PASSWORD | SWT.BORDER
				| SWT.SINGLE);
		data = new GridData();
		data.widthHint = converter.convertHorizontalDLUsToPixels(150);
		password.setLayoutData(data);
		final Composite testConnectionComposite = new Composite(
				credentialsComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		testConnectionComposite.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		testConnectionComposite.setLayoutData(data);
		testButton = new Button(testConnectionComposite, SWT.PUSH);
		testButton.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.6")); //$NON-NLS-1$
		testResultLabel = new CLabel(testConnectionComposite, SWT.NONE);
		testResultLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		testButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Run a test for the connection
				testTunnelConnection();
			}
		});
		testResultLabel.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				Object messageData = testResultLabel.getData("info"); //$NON-NLS-1$
				if (messageData != null) {
					MessageDialog.openInformation(getShell(),
							PHPServerUIMessages.getString("PHPServerAdvancedTab.8"), messageData.toString()); //$NON-NLS-1$
				}
			}
		});
		debugThroughTunnel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				boolean selection = b.getSelection();
				updateTunnelComponents(selection);
				updateLaunchConfigurationDialog();
			}
		});

		// Add the Browser group controls
		openBrowser = new Button(browserGroup, SWT.CHECK);
		openBrowser.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.9")); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		openBrowser.setLayoutData(data);

		sessionGroup = new Composite(browserGroup, SWT.NONE);
		sessionGroup.setLayout(new GridLayout(3, false));
		sessionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		openBrowser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				isOpenInBrowser = b.getSelection();
				if (!isOpenInBrowser) {
					debugFirstPageBt.setSelection(true);
					debugAllPagesBt.setSelection(false);
				} else {
					debugFirstPageBt.setSelection(false);
					debugAllPagesBt.setSelection(true);
				}
				debugStartFromBt.setSelection(false);
				debugContinueBt.setSelection(false);
				enableSessionSettingButtons(isOpenInBrowser
						&& ILaunchManager.DEBUG_MODE
								.equals(getLaunchConfigurationDialog()
										.getMode()));
				updateLaunchConfigurationDialog();
			}
		});

		// Add the Session group controls
		debugAllPagesBt = createRadioButton(sessionGroup, PHPServerUIMessages.getString("PHPServerAdvancedTab.10")); //$NON-NLS-1$
		data = (GridData) debugAllPagesBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;

		debugFirstPageBt = createRadioButton(sessionGroup,
				PHPServerUIMessages.getString("PHPServerAdvancedTab.11")); //$NON-NLS-1$
		data = (GridData) debugFirstPageBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;

		debugStartFromBt = createRadioButton(sessionGroup, PHPServerUIMessages.getString("PHPServerAdvancedTab.12")); //$NON-NLS-1$
		data = (GridData) debugStartFromBt.getLayoutData();
		data.horizontalIndent = 20;

		debugFromTxt = new Text(sessionGroup, SWT.SINGLE | SWT.BORDER);
		debugFromTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		resetBt = createPushButton(sessionGroup, PHPServerUIMessages.getString("PHPServerAdvancedTab.13"), null); //$NON-NLS-1$
		resetBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (launchConfiguration != null) {
					try {
						debugFromTxt.setText(launchConfiguration.getAttribute(
								Server.BASE_URL, "")); //$NON-NLS-1$
					} catch (CoreException e1) {
					}
				}
			}
		});

		debugContinueBt = createCheckButton(sessionGroup,
				PHPServerUIMessages.getString("PHPServerAdvancedTab.15")); //$NON-NLS-1$
		data = (GridData) debugContinueBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 40;

		// Add listeners
		debugStartFromBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateDebugFrom();
			}
		});

		updateDebugFrom();

		// Add widget listeners
		debugFirstPageBt.addSelectionListener(listener);
		debugAllPagesBt.addSelectionListener(listener);
		debugContinueBt.addSelectionListener(listener);
		debugStartFromBt.addSelectionListener(listener);
		debugFromTxt.addModifyListener(listener);
		debugThroughTunnel.addSelectionListener(listener);
		userName.addModifyListener(listener);
		password.addModifyListener(listener);

		KeyListener userInputListener = new KeyListener() {
			public void keyReleased(KeyEvent e) {
				testResultLabel.setText(""); //$NON-NLS-1$
				testButton.setEnabled((userName.getText().trim().length() > 0));
			}

			public void keyPressed(KeyEvent e) {
				testResultLabel.setText(""); //$NON-NLS-1$
			}
		};
		userName.addKeyListener(userInputListener);
		password.addKeyListener(userInputListener);
	}

	protected void createDebuggerSelectionControl(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.18")); //$NON-NLS-1$
		GridLayout ly = new GridLayout(1, false);
		ly.marginHeight = 0;
		ly.marginWidth = 0;
		group.setLayout(ly);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite debuggerServerComp = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout(4, false);
		debuggerServerComp.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		debuggerServerComp.setLayoutData(data);
		Font font = parent.getFont();
		debuggerServerComp.setFont(font);

		// Add the debuggers combo
		Label label = new Label(debuggerServerComp, SWT.WRAP);
		data = new GridData(GridData.BEGINNING);
		data.widthHint = 100;
		label.setLayoutData(data);
		label.setFont(font);
		label.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.19")); //$NON-NLS-1$

		fDebuggersCombo = new Combo(debuggerServerComp, SWT.SINGLE | SWT.BORDER
				| SWT.READ_ONLY);
		fDebuggersCombo.setFont(font);
		fDebuggersCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fDebuggersCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				boolean isXDebug = isXdebug();
				openBrowser.setEnabled(!isXDebug);
				sessionGroup.setVisible(!isXDebug);
				openBrowser.setSelection(isXDebug
						|| debugFirstPageBt.getEnabled());
				if (isXDebug) {
					openBrowser.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.20")); //$NON-NLS-1$
				} else {
					openBrowser.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.21")); //$NON-NLS-1$
				}
				updateLaunchConfigurationDialog();
				updateDebugServerTesters();
			}
		});

		validateDebuggerBtn = createPushButton(debuggerServerComp, PHPServerUIMessages.getString("PHPServerAdvancedTab.22"), null); //$NON-NLS-1$
		validateDebuggerBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				updateDebugServerTesters();
				String serverName = null;
				try {
					serverName = launchConfiguration.getAttribute(Server.NAME,
							(String) null);
				} catch (CoreException e) {
					// TODO handle
				}
				if (serverName != null) {
					Server server = ServersManager.getServer(serverName);
					for (IDebugServerConnectionTest debugServerTester : debugTesters) {
						debugServerTester.testConnection(server, getShell());
					}
				}
			}
		});

		configureDebugger = createPushButton(debuggerServerComp,
				PHPServerUIMessages.getString(PHPServerUIMessages.getString("PHPServerAdvancedTab.23")), null); //$NON-NLS-1$
		configureDebugger.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				handleConfigureDebuggerSelected();
			}
		});

		// initialize the debuggers list
		fillDebuggers();
	}

	// In case this is a debug mode, display 'Break on first line' attribute
	// checkbox.
	protected void createBreakControl(Composite parent) {

		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.24")); //$NON-NLS-1$
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		breakOnFirstLine = createCheckButton(group, PHPServerUIMessages.getString("PHPServerAdvancedTab.25")); //$NON-NLS-1$
		breakOnFirstLine.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		// Disables/Enables all the controls according the the debug mode.
		String mode = getLaunchConfigurationDialog().getMode();
		boolean isDebugMode = ILaunchManager.DEBUG_MODE.equals(mode);
		breakOnFirstLine.setEnabled(isDebugMode);
	}

	protected void handleConfigureDebuggerSelected() {
		String selectedDebugger = getSelectedDebuggerId();

		AbstractDebuggerConfiguration[] debuggersConfigurations = PHPDebuggersRegistry
				.getDebuggersConfigurations();
		for (AbstractDebuggerConfiguration debuggerConfig : debuggersConfigurations) {
			if (debuggerConfig.getDebuggerId().equals(selectedDebugger)) {
				debuggerConfig.openConfigurationDialog(Display.getDefault()
						.getActiveShell());
			}
		}
	}

	/**
	 * Populates the debuggers with the debuggers defined in the workspace.
	 */
	protected void fillDebuggers() {
		fDebuggerIds = PHPDebuggersRegistry.getDebuggersIds();
		for (String id : fDebuggerIds) {
			// Insert the debuggers names
			fDebuggersCombo.add(PHPDebuggersRegistry.getDebuggerName(id));
		}
		// Select the default debugger
		String defaultName = PHPDebuggersRegistry
				.getDebuggerName(PHPDebuggersRegistry.getDefaultDebuggerId());
		int index = fDebuggersCombo.indexOf(defaultName);
		if (index > -1) {
			fDebuggersCombo.select(index);
		} else if (fDebuggersCombo.getItemCount() > 0) {
			fDebuggersCombo.select(0);
		}
	}

	/**
	 * Update the tunnel components enablement state. This is called on
	 * initiation and when a user check/uncheck the enable button.
	 * 
	 * @param enabled
	 */
	protected void updateTunnelComponents(boolean enabled) {
		testResultLabel.setText(""); //$NON-NLS-1$
		setEnabled(enabled, userName, password, nameLabel, passwordLabel,
				testResultLabel);
		testButton
				.setEnabled(enabled && userName.getText().trim().length() > 0);
	}

	/**
	 * Set multiple control enablement state.
	 * 
	 * @param enabled
	 * @param controls
	 */
	protected void setEnabled(boolean enabled, Control... controls) {
		for (Control c : controls) {
			c.setEnabled(enabled);
		}
	}

	private void enableSessionSettingButtons(boolean isOpenInBrowser) {
		// also check for debug mode.
		String mode = getLaunchConfigurationDialog().getMode();
		isOpenInBrowser = isOpenInBrowser
				&& ILaunchManager.DEBUG_MODE.equals(mode);
		debugFirstPageBt.setEnabled(isOpenInBrowser);
		debugAllPagesBt.setEnabled(isOpenInBrowser);
		debugStartFromBt.setEnabled(isOpenInBrowser);
		debugContinueBt.setEnabled(false);
		resetBt.setEnabled(false);
		debugFromTxt.setEnabled(false);
	}

	/**
	 * Test a connection with the user name and password that are currently
	 * typed in their designated boxes. We assume here that the validation of
	 * the dialog already eliminated a situation where the Test button is
	 * enabled when there is a missing user-name or password.
	 */
	private void testTunnelConnection() {
		testButton.setEnabled(false);
		testResultLabel.setForeground(Display.getDefault().getSystemColor(
				SWT.COLOR_BLUE));
		testResultLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.27")); //$NON-NLS-1$
		testResultLabel.setCursor(Display.getDefault().getSystemCursor(
				SWT.CURSOR_WAIT));
		testResultLabel.setData("info", null); //$NON-NLS-1$
		Job connectionTest = new UIJob(PHPServerUIMessages.getString("PHPServerAdvancedTab.29")) { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					String remoteHost = PHPLaunchUtilities
							.getDebugHost(launchConfiguration);
					int port = PHPLaunchUtilities
							.getDebugPort(launchConfiguration);
					if (remoteHost == null || remoteHost.length() == 0
							|| port < 0) {
						// The host was not yet set in the launch configuration.
						testButton.setEnabled(true);
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_RED));
						if (port > -1) {
							testResultLabel
									.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.30")); //$NON-NLS-1$
							testResultLabel
									.setData(
											"info", //$NON-NLS-1$
											PHPServerUIMessages.getString("PHPServerAdvancedTab.32")); //$NON-NLS-1$
						} else {
							testResultLabel
									.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.33")); //$NON-NLS-1$
							testResultLabel
									.setData(
											"info", //$NON-NLS-1$
											PHPServerUIMessages.getString("PHPServerAdvancedTab.35")); //$NON-NLS-1$
						}
					}

					testResultLabel.setCursor(Display.getDefault()
							.getSystemCursor(SWT.CURSOR_WAIT));
					IStatus connectionStatus = TunnelTester.test(remoteHost,
							userName.getText().trim(), password.getText()
									.trim(), port, port);
					testButton.setEnabled(true);
					testResultLabel.setCursor(null);
					if (connectionStatus.isOK()) {
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.36")); //$NON-NLS-1$
					} else if (connectionStatus.isMultiStatus()) {
						// A case where the connection indicate that it was
						// successful, however, we were still not able to verify
						// that
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_YELLOW));
						testResultLabel
								.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.37")); //$NON-NLS-1$
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
						// Update the password fields in case the multi status
						// also contains a password change information
						IStatus[] children = connectionStatus.getChildren();
						if (children != null) {
							for (IStatus child : children) {
								if (child.getSeverity() == IStatus.INFO
										&& child.getCode() == TunnelTester.PASSWORD_CHANGED_CODE) {
									password.setText(child.getMessage());
									break;
								}
							}
						}
					} else if (connectionStatus.getSeverity() == IStatus.WARNING) {
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel
								.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.39")); //$NON-NLS-1$
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
					} else if (connectionStatus.getSeverity() == IStatus.INFO) {
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.41")); //$NON-NLS-1$
						// update the password field in case that the info
						// indicated a password change.
						if (connectionStatus.getCode() == TunnelTester.PASSWORD_CHANGED_CODE) {
							password.setText(connectionStatus.getMessage());
						}
					} else if (connectionStatus.getSeverity() == IStatus.ERROR) {
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_RED));
						testResultLabel
								.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.42")); //$NON-NLS-1$
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
					}
				} catch (OperationCanceledException oce) {
					testButton.setEnabled(true);
					testResultLabel.setCursor(null);
					testResultLabel.setForeground(null);
					testResultLabel.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.44")); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		};
		connectionTest.setUser(true);
		connectionTest.setPriority(Job.LONG);
		connectionTest.schedule();
	}

	/**
	 * Override this method to add more widgets to this tab.
	 * 
	 * @param composite
	 */
	protected void createExtensionControls(Composite composite) {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return PHPServerUIMessages.getString("PHPServerAdvancedTab.45"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse
	 * .debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		launchConfiguration = configuration;
		initializeDebuggerControl(configuration);
		boolean isXdebugger = isXdebug();
		try {
			boolean isUsingTunnel = configuration.getAttribute(
					IPHPDebugConstants.USE_SSH_TUNNEL, false);
			debugThroughTunnel.setSelection(isUsingTunnel);
			updateTunnelComponents(isUsingTunnel);
			if (isUsingTunnel) {
				userName.setText(configuration.getAttribute(
						IPHPDebugConstants.SSH_TUNNEL_USER_NAME, "")); //$NON-NLS-1$
				if (userName.getText().length() > 0) {
					// Load the password from the Secured Storage
					try {
						password.setText(PHPLaunchUtilities
								.getSecurePreferences(
										PHPLaunchUtilities
												.getDebugHost(launchConfiguration))
								.get(userName.getText(), "")); //$NON-NLS-1$
					} catch (StorageException e) {
						Logger.logException(
								"Error accessing the secured storage", e); //$NON-NLS-1$
						password.setText(""); //$NON-NLS-1$
					}
				} else {
					password.setText(""); //$NON-NLS-1$
				}
			}
			// Zend debugger have the option not to use a browser to start a
			// session. Since XDebug seems to start only
			// with a browser instance, we check for it and enable it anyway.
			isOpenInBrowser = isXdebugger
					|| configuration.getAttribute(
							IPHPDebugConstants.OPEN_IN_BROWSER,
							PHPDebugPlugin.getOpenInBrowserOption());
			// isUsingExternalBrowser = internalWebBrowserAvailable
			// && configuration.getAttribute(
			// IPHPDebugConstants.USE_INTERNAL_BROWSER, false);
			openBrowser.setSelection(isOpenInBrowser);
			if (isXdebugger) {
				openBrowser.setEnabled(false);
			}

			sessionGroup.setVisible(!isXdebugger);

			String debugSetting = configuration.getAttribute(
					IPHPDebugConstants.DEBUGGING_PAGES,
					IPHPDebugConstants.DEBUGGING_ALL_PAGES);
			if (IPHPDebugConstants.DEBUGGING_ALL_PAGES.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(true);
				debugStartFromBt.setSelection(false);
			} else if (IPHPDebugConstants.DEBUGGING_FIRST_PAGE
					.equals(debugSetting)) {
				debugFirstPageBt.setSelection(true);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(false);
			} else if (IPHPDebugConstants.DEBUGGING_START_FROM
					.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(true);
				boolean shouldContinue = configuration.getAttribute(
						IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE, false);
				debugContinueBt.setSelection(shouldContinue);
			}
			String startFromURL = configuration.getAttribute(
					IPHPDebugConstants.DEBUGGING_START_FROM_URL, ""); //$NON-NLS-1$
			debugFromTxt.setText(startFromURL);
			updateDebugFrom();
			// in case we are dealing with XDebug, enable the browser control
			// anyway and do not restrict to debug mode
			enableSessionSettingButtons(isXdebugger
					|| (isOpenInBrowser && ILaunchManager.DEBUG_MODE
							.equals(getLaunchConfigurationDialog().getMode())));
			if (breakOnFirstLine != null) {
				// init the breakpoint settings
				breakOnFirstLine.setSelection(configuration.getAttribute(
						IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
						PHPDebugPlugin.getStopAtFirstLine()));
			}
		} catch (CoreException e) {
		}
		isValid(configuration);
	}

	protected void initializeDebuggerControl(ILaunchConfiguration configuration) {
		try {
			String debuggerID = configuration.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, ""); //$NON-NLS-1$
			if (debuggerID != null && !debuggerID.equals("")) { //$NON-NLS-1$
				fDebuggersCombo.setText(PHPDebuggersRegistry
						.getDebuggerName(debuggerID));
			} else {
				selectDefaultDebugger(configuration);
			}
			// flag should only be set if launch has been attempted on the
			// config
			if (configuration.getAttribute(READ_ONLY, false)) {
				fDebuggersCombo.setEnabled(false);
			}
		} catch (Exception e) {
		}
	}

	/*
	 * Select the default debugger for this launch configuration.
	 */
	private void selectDefaultDebugger(ILaunchConfiguration configuration)
			throws CoreException {
		if (fDebuggersCombo != null && fDebuggersCombo.getItemCount() > 0) {
			String projectName = configuration.getAttribute(
					IPHPDebugConstants.PHP_Project, (String) null);
			IProject project = null;
			if (projectName != null) {
				project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
			}
			String defaultDebuggerID = PHPProjectPreferences
					.getDefaultDebuggerID(project);
			String debuggerName = PHPDebuggersRegistry
					.getDebuggerName(defaultDebuggerID);
			int nameIndex = fDebuggersCombo.indexOf(debuggerName);
			if (nameIndex > -1) {
				fDebuggersCombo.select(nameIndex);
			} else {
				fDebuggersCombo.select(0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractLaunchConfigurationTab#activated(org.eclipse
	 * .debug.core.ILaunchConfigurationWorkingCopy )
	 */
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		// hide/show the session group in case the debugger type was modified in
		// the 'main' tab
		boolean isXDebug = isXdebug();
		sessionGroup.setVisible(!isXDebug);
		openBrowser.setEnabled(!isXDebug);
		if (isXDebug) {
			openBrowser.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.54")); //$NON-NLS-1$
		} else {
			openBrowser.setText(PHPServerUIMessages.getString("PHPServerAdvancedTab.55")); //$NON-NLS-1$
		}
	}

	/*
	 * Aptana addition - Check to see if this is a XDebug configuration. This
	 * value will be used to determine the options to display in this dialog.
	 */
	private boolean isXdebug() {
		return XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID
				.equals(getSelectedDebuggerId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse
	 * .debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		launchConfiguration = configuration;
		configuration.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
				getSelectedDebuggerId());
		configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
				breakOnFirstLine.getSelection());
		configuration.setAttribute(IPHPDebugConstants.USE_SSH_TUNNEL,
				debugThroughTunnel.getSelection());
		if (debugThroughTunnel.getSelection()) {
			configuration.setAttribute(IPHPDebugConstants.SSH_TUNNEL_USER_NAME,
					userName.getText().trim());
			// We save a hash of the password and not the real one. This is only
			// used to allow an apply when a password change happens.
			// The real password saving is done through the secured storage
			// right after that line.
			String passwordDigest = MD5.digest(password.getText().trim());
			if (passwordDigest == null) {
				// as a default, use the string hash.
				passwordDigest = String.valueOf(password.getText().trim()
						.hashCode());
			}
			configuration.setAttribute(IPHPDebugConstants.SSH_TUNNEL_PASSWORD,
					passwordDigest);

			// Save to secured storage
			try {
				// Note: At this point we write to the secure storage at any
				// apply.
				// This might put in the storage some un-needed keys, so we also
				// scan the launch configurations on startup
				// and make sure that the storage contains only what we need.
				if (!isTextModificationChange) {
					// We'll save to the secured storage only if the change was
					// done outside text fields (that might contains the changes
					// in the user-name and password as we type them).
					// This flag will be off when the apply button is actually
					// clicked (or when other widgets are triggering the apply
					// call).
					PHPLaunchUtilities.getSecurePreferences(
							PHPLaunchUtilities
									.getDebugHost(launchConfiguration))
							.put(userName.getText(), password.getText().trim(),
									true /* encrypt */);
				}
			} catch (StorageException e) {
				Logger.logException("Error saving to the secured storage", e); //$NON-NLS-1$
			}
		} else {
			configuration.setAttribute(IPHPDebugConstants.SSH_TUNNEL_USER_NAME,
					""); //$NON-NLS-1$
			configuration.setAttribute(IPHPDebugConstants.SSH_TUNNEL_PASSWORD,
					""); //$NON-NLS-1$
		}
		configuration.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER,
				isOpenInBrowser);
		// configuration.setAttribute(IPHPDebugConstants.USE_INTERNAL_BROWSER,
		// internalBrowser.getSelection());
		if (isOpenInBrowser) {
			if (debugAllPagesBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
						IPHPDebugConstants.DEBUGGING_ALL_PAGES);
			} else if (debugFirstPageBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
						IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
			} else {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
						IPHPDebugConstants.DEBUGGING_START_FROM);
				configuration.setAttribute(
						IPHPDebugConstants.DEBUGGING_START_FROM_URL,
						debugFromTxt.getText());
				configuration.setAttribute(
						IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE,
						debugContinueBt.getSelection());
			}
		} else {
			// Allow only debug-first-page
			configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
					IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
		}
		applyExtension(configuration);
		isTextModificationChange = false; // reset this flag here.
		updateDebugServerTesters();
	}

	/**
	 * Override this method to perform the apply in the extending classes.
	 * 
	 * @param configuration
	 */
	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		launchConfiguration = configuration;
		setErrorMessage(null);
		configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
				IPHPDebugConstants.DEBUGGING_ALL_PAGES);
		try {
			selectDefaultDebugger(configuration);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse
	 * .debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig) {
		launchConfiguration = launchConfig;
		setMessage(null);
		setErrorMessage(null);
		if (debugThroughTunnel.getSelection()) {
			boolean valid = userName.getText().trim().length() > 0;
			testButton.setEnabled(valid);
			if (!valid) {
				setErrorMessage(PHPServerUIMessages.getString("PHPServerAdvancedTab.59")); //$NON-NLS-1$
				return false;
			}
		}
		if (debugStartFromBt.getSelection()) {
			if (debugFromTxt.getText().trim().equals("")) { //$NON-NLS-1$
				setErrorMessage(PHPServerUIMessages.getString("PHPServerAdvancedTab.61")); //$NON-NLS-1$
				return false;
			}
			try {
				new URL(debugFromTxt.getText());
			} catch (MalformedURLException mue) {
				setErrorMessage(PHPServerUIMessages.getString("PHPServerAdvancedTab.62")); //$NON-NLS-1$
				return false;
			}
		}
		return isValidExtension(launchConfig);
	}

	/**
	 * Override this method to perform the isValid in the extending classes.
	 * 
	 * @param launchConfig
	 * @return true, if the extention is in a valid state.
	 */
	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	/**
	 * Returns the id of the selected debugger.
	 * 
	 * @return The debugger's id.
	 */
	private String getSelectedDebuggerId() {
		int selectedIndex = fDebuggersCombo.getSelectionIndex();
		String debuggerId = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID; // default
		if (selectedIndex > -1 && fDebuggerIds.size() > selectedIndex) {
			debuggerId = fDebuggerIds.toArray()[selectedIndex].toString();
		}
		return debuggerId;
	}

	private void updateDebugServerTesters() {
		int selectedDebuggerIndex = fDebuggersCombo.getSelectionIndex();
		final String currentDebuggerType = fDebuggersCombo
				.getItem(selectedDebuggerIndex);
		debugTesters = retrieveAllServerTestExtensions(currentDebuggerType);
		if (debugTesters.length == 0) {
			validateDebuggerBtn.setEnabled(false);
		} else {
			validateDebuggerBtn.setEnabled(true);
		}
	}

	private IDebugServerConnectionTest[] retrieveAllServerTestExtensions(
			final String currentDebuggerType) {
		String debugServerTestExtensionName = "org.eclipse.php.debug.ui.debugServerConnectionTest"; //$NON-NLS-1$
		Map<String, IDebugServerConnectionTest> filtersMap = new HashMap<String, IDebugServerConnectionTest>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(debugServerTestExtensionName);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if ("debugServerTest".equals(element.getName())) { //$NON-NLS-1$
				String debuggerTypeName = elements[i]
						.getAttribute("debuggerTypeName"); //$NON-NLS-1$
				String overridesIds = elements[i].getAttribute("overridesId"); //$NON-NLS-1$
				if (debuggerTypeName.equals(currentDebuggerType)) {// must be
																	// equal to
																	// the
																	// current
																	// selected
																	// type
					String id = element.getAttribute("id"); //$NON-NLS-1$
					if (!filtersMap.containsKey(id)) {
						if (overridesIds != null) {
							StringTokenizer st = new StringTokenizer(
									overridesIds, ", "); //$NON-NLS-1$
							while (st.hasMoreTokens()) {
								filtersMap.put(st.nextToken(), null);
							}
						}
						try {
							filtersMap
									.put(id,
											(IDebugServerConnectionTest) element
													.createExecutableExtension("class")); //$NON-NLS-1$
						} catch (CoreException e) {
							PHPDebugPlugin.log(e);
						}
					}

				}
			}
		}
		Collection<IDebugServerConnectionTest> l = filtersMap.values();
		while (l.remove(null))
			; // remove null elements
		debugTesters = l.toArray(new IDebugServerConnectionTest[l.size()]);
		return debugTesters;
	}

	// Update the 'debug from' related widgets
	private void updateDebugFrom() {
		if (launchConfiguration != null
				&& debugFromTxt.getText().trim().equals("")) { //$NON-NLS-1$
			try {
				debugFromTxt.setText(launchConfiguration.getAttribute(
						Server.BASE_URL, "")); //$NON-NLS-1$
			} catch (CoreException e) {
			}
		}
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					boolean debugFromSelected = debugStartFromBt.getSelection();
					debugFromTxt.setEnabled(debugFromSelected);
					debugContinueBt.setEnabled(debugFromSelected);
					resetBt.setEnabled(debugFromSelected);
				} catch (SWTException se) {
					// Just in case the widget was disposed (cases such as the
					// configuration deletion).
				}
			}
		});
	}

	protected class WidgetListener extends SelectionAdapter implements
			ModifyListener {
		public void modifyText(ModifyEvent e) {
			// mark that this was a text modification change, so that the apply
			// will not save to the secured storage.
			isTextModificationChange = true;
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	}
}
