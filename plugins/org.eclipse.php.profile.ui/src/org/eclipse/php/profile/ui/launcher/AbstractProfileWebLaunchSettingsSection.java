/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import java.security.MessageDigest;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.server.core.tunneling.TunnelTester;
import org.eclipse.php.profile.ui.launcher.AbstractPHPLaunchConfigurationProfilerTab.StatusMessage;
import org.eclipse.php.profile.ui.launcher.AbstractPHPLaunchConfigurationProfilerTab.WidgetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

/**
 * Abstract implementation of profiler launch settings section that corresponds
 * to PHP web launch configuration type.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractProfileWebLaunchSettingsSection
		implements IProfilerLaunchSettingsSection {

	protected static class Digester {

		/**
		 * Returns a MD5 digest in a hex format for the given string.
		 * 
		 * @param content
		 *            The string to digest
		 * @return MD5 digested string in a hex format; null, in case of an
		 *         error or a null input
		 */
		public static String digest(String content) {
			if (content == null) {
				return null;
			}
			if (content.length() == 0) {
				return ""; //$NON-NLS-1$
			}
			String passwordDigest = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
				md.reset();
				md.update(content.getBytes());
				byte digest[] = md.digest();
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < digest.length; i++) {
					String hex = Integer.toHexString(0xff & digest[i]);
					if (hex.length() == 1) {
						buffer.append('0');
					}
					buffer.append(hex);
				}
				passwordDigest = buffer.toString();
			} catch (Exception e) {
				Logger.logException("Message digest error", e); //$NON-NLS-1$
			}
			if (passwordDigest == null) {
				return null;
			}
			return passwordDigest;
		}
	}

	protected WidgetListener widgetListener;
	protected Group tunnelGroup;
	protected Button profileThroughTunnel;
	protected Label nameLabel;
	protected Text userName;
	protected Label passwordLabel;
	protected Text password;
	protected Button testButton;
	protected CLabel testResultLabel;
	private ILaunchConfiguration configuration;
	private boolean isSSHCredentialsChange;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * createSection(org.eclipse.swt.widgets.Composite,
	 * org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab.WidgetListener)
	 */
	@Override
	public final void createSection(Composite parent,
			WidgetListener widgetListener) {
		this.widgetListener = widgetListener;
		buildSection(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * initialize(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initialize(ILaunchConfiguration configuration) {
		this.configuration = configuration;
		if (tunnelGroup != null) {
			try {
				boolean isUsingTunnel = configuration
						.getAttribute(IPHPDebugConstants.USE_SSH_TUNNEL, false);
				profileThroughTunnel.setSelection(isUsingTunnel);
				updateTunnelComponents(isUsingTunnel);
				if (isUsingTunnel && tunnelGroup != null) {
					userName.setText(configuration.getAttribute(
							IPHPDebugConstants.SSH_TUNNEL_USER_NAME, "")); //$NON-NLS-1$
					if (userName.getText().length() > 0) {
						// Load the password from the Secured Storage
						try {
							password.setText(PHPLaunchUtilities
									.getSecurePreferences(PHPLaunchUtilities
											.getDebugHost(getConfiguration()))
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
			} catch (CoreException e) {
			}
		}
		isValid(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (tunnelGroup != null) {
			configuration.setAttribute(IPHPDebugConstants.USE_SSH_TUNNEL,
					profileThroughTunnel.getSelection());
			if (profileThroughTunnel.getSelection()) {
				configuration.setAttribute(
						IPHPDebugConstants.SSH_TUNNEL_USER_NAME,
						userName.getText().trim());
				/*
				 * We save a hash of the password and not the real one. This is
				 * only used to allow an apply when a password change happens.
				 * The real password saving is done through the secured storage
				 * right after that line.
				 */
				String passwordDigest = Digester
						.digest(password.getText().trim());
				if (passwordDigest == null) {
					// As a default, use the string hash.
					passwordDigest = String
							.valueOf(password.getText().trim().hashCode());
				}
				configuration.setAttribute(
						IPHPDebugConstants.SSH_TUNNEL_PASSWORD, passwordDigest);
				// Save to secured storage
				try {
					/*
					 * Note: At this point we write to the secure storage at any
					 * apply. This might put in the storage some un-needed keys,
					 * so we also scan the launch configurations on startup and
					 * make sure that the storage contains only what we need.
					 */
					if (!isSSHCredentialsChange) {
						/*
						 * We'll save to the secured storage only if the change
						 * was done outside text fields (that might contains the
						 * changes in the user-name and password as we type
						 * them). This flag will be off when the apply button is
						 * actually clicked (or when other widgets are
						 * triggering the apply call).
						 */
						PHPLaunchUtilities
								.getSecurePreferences(PHPLaunchUtilities
										.getDebugHost(getConfiguration()))
								.put(userName.getText(),
										password.getText().trim(),
										true /* encrypt */);
					}
				} catch (StorageException e) {
					Logger.logException("Error saving to the secured storage", //$NON-NLS-1$
							e);
				}
			} else {
				configuration.setAttribute(
						IPHPDebugConstants.SSH_TUNNEL_USER_NAME, ""); //$NON-NLS-1$
				configuration.setAttribute(
						IPHPDebugConstants.SSH_TUNNEL_PASSWORD, ""); //$NON-NLS-1$
			}
		}
		isSSHCredentialsChange = false; // Reset this flag here.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#isValid(
	 * org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		if (tunnelGroup != null) {
			if (profileThroughTunnel.getSelection()) {
				boolean valid = userName.getText().trim().length() > 0;
				testButton.setEnabled(valid);
				if (!valid) {
					return new StatusMessage(IMessageProvider.ERROR,
							Messages.AbstractProfileWebLaunchSettingsSection_Missing_SSH_user_name);
				}
			}
		}
		return new StatusMessage(IMessageProvider.NONE, ""); //$NON-NLS-1$
	}

	protected void buildSection(Composite parent) {
		createTunnelGroup(parent);
	}

	protected void createTunnelGroup(Composite composite) {
		// Add the tunnel group
		tunnelGroup = new Group(composite, SWT.NONE);
		tunnelGroup.setLayout(new GridLayout(1, false));
		tunnelGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tunnelGroup.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_SSH_tunnel);
		// Add the tunneling controls
		profileThroughTunnel = new Button(tunnelGroup, SWT.CHECK);
		profileThroughTunnel.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_Profile_through_SSH_tunnel);
		Composite credentialsComposite = new Composite(tunnelGroup, SWT.NONE);
		credentialsComposite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 20;
		credentialsComposite.setLayoutData(data);
		nameLabel = new Label(credentialsComposite, SWT.NONE);
		nameLabel.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_User_name);
		userName = new Text(credentialsComposite, SWT.BORDER | SWT.SINGLE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 200;
		userName.setLayoutData(data);
		userName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isSSHCredentialsChange = true;
				updateTunnelComponents(true);
			}
		});
		passwordLabel = new Label(credentialsComposite, SWT.NONE);
		passwordLabel.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_Password);
		password = new Text(credentialsComposite,
				SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 200;
		password.setLayoutData(data);
		password.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isSSHCredentialsChange = true;
				updateTunnelComponents(true);
			}
		});
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
		testButton.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_Test_connection);
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
					MessageDialog.openInformation(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getShell(),
							Messages.AbstractProfileWebLaunchSettingsSection_SSH_tunnel_test,
							messageData.toString());
				}
			}
		});
		profileThroughTunnel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				boolean selection = b.getSelection();
				updateTunnelComponents(selection);
			}
		});
		// Register widget listener for triggering changes
		userName.addModifyListener(widgetListener);
		password.addModifyListener(widgetListener);
		profileThroughTunnel.addSelectionListener(widgetListener);
	}

	protected ILaunchConfiguration getConfiguration() {
		return configuration;
	}

	protected void updateTunnelComponents(boolean enabled) {
		testResultLabel.setText(""); //$NON-NLS-1$
		setEnabled(enabled, userName, password, nameLabel, passwordLabel,
				testResultLabel);
		testButton
				.setEnabled(enabled && userName.getText().trim().length() > 0);
	}

	protected void setEnabled(boolean enabled, Control... controls) {
		for (Control c : controls) {
			c.setEnabled(enabled);
		}
	}

	/**
	 * Test a connection with the user name and password that are currently
	 * typed in their designated boxes. We assume here that the validation of
	 * the dialog already eliminated a situation where the Test button is
	 * enabled when there is a missing user-name or password.
	 */
	protected void testTunnelConnection() {
		testButton.setEnabled(false);
		testResultLabel.setForeground(
				Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		testResultLabel.setText(
				Messages.AbstractProfileWebLaunchSettingsSection_Testing_connection);
		testResultLabel.setCursor(
				Display.getDefault().getSystemCursor(SWT.CURSOR_WAIT));
		testResultLabel.setData("info", null); //$NON-NLS-1$
		Job connectionTest = new UIJob(
				Messages.AbstractProfileWebLaunchSettingsSection_SSH_tunnel_test) {
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					String remoteHost = PHPLaunchUtilities
							.getDebugHost(getConfiguration());
					int port = PHPLaunchUtilities
							.getDebugPort(getConfiguration());
					if (remoteHost == null || remoteHost.length() == 0
							|| port < 0) {
						// The host was not yet set in the launch configuration.
						testButton.setEnabled(true);
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_RED));
						if (port > -1) {
							testResultLabel.setText(
									Messages.AbstractProfileWebLaunchSettingsSection_Missing_host);
							testResultLabel.setData("info", //$NON-NLS-1$
									Messages.AbstractProfileWebLaunchSettingsSection_Missing_host_address);
						} else {
							testResultLabel.setText(
									Messages.AbstractProfileWebLaunchSettingsSection_Error);
							testResultLabel.setData("info", //$NON-NLS-1$
									Messages.AbstractProfileWebLaunchSettingsSection_Could_not_determine_port);
						}
					}
					testResultLabel.setCursor(Display.getDefault()
							.getSystemCursor(SWT.CURSOR_WAIT));
					IStatus connectionStatus = TunnelTester.test(remoteHost,
							userName.getText().trim(),
							password.getText().trim(), port, port);
					testButton.setEnabled(true);
					testResultLabel.setCursor(null);
					if (connectionStatus.isOK()) {
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel.setText(
								Messages.AbstractProfileWebLaunchSettingsSection_Successfully_connected);
					} else if (connectionStatus.isMultiStatus()) {
						/*
						 * A case where the connection indicate that it was
						 * successful, however, we were still not able to verify
						 * that.
						 */
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_YELLOW));
						testResultLabel.setText(
								Messages.AbstractProfileWebLaunchSettingsSection_Undetermined);
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
						/*
						 * Update the password fields in case the multi status
						 * also contains a password change information.
						 */
						IStatus[] children = connectionStatus.getChildren();
						if (children != null) {
							for (IStatus child : children) {
								if (child.getSeverity() == IStatus.INFO && child
										.getCode() == TunnelTester.PASSWORD_CHANGED_CODE) {
									password.setText(child.getMessage());
									break;
								}
							}
						}
					} else if (connectionStatus
							.getSeverity() == IStatus.WARNING) {
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel.setText(
								Messages.AbstractProfileWebLaunchSettingsSection_Connected_with_warnings);
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
					} else if (connectionStatus.getSeverity() == IStatus.INFO) {
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_GREEN));
						testResultLabel.setText(
								Messages.AbstractProfileWebLaunchSettingsSection_Connected_with_warnings);
						/*
						 * Update the password field in case that the info
						 * indicated a password change.
						 */
						if (connectionStatus
								.getCode() == TunnelTester.PASSWORD_CHANGED_CODE) {
							password.setText(connectionStatus.getMessage());
						}
					} else if (connectionStatus
							.getSeverity() == IStatus.ERROR) {
						testResultLabel.setCursor(Display.getDefault()
								.getSystemCursor(SWT.CURSOR_HAND));
						testResultLabel.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_DARK_RED));
						testResultLabel.setText(
								Messages.AbstractProfileWebLaunchSettingsSection_Failed_to_connect);
						testResultLabel.setData("info", //$NON-NLS-1$
								connectionStatus.getMessage());
					}
				} catch (OperationCanceledException oce) {
					testButton.setEnabled(true);
					testResultLabel.setCursor(null);
					testResultLabel.setForeground(null);
					testResultLabel.setText(
							Messages.AbstractProfileWebLaunchSettingsSection_Canceled);
				}
				return org.eclipse.core.runtime.Status.OK_STATUS;
			}
		};
		connectionTest.setUser(true);
		connectionTest.setPriority(Job.LONG);
		connectionTest.schedule();
	}

}
