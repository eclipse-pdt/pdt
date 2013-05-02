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
package org.eclipse.php.internal.server.ui.launching.zend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestController;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestEvent;
import org.eclipse.php.internal.debug.core.zend.testConnection.IDebugServerTestListener;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.ui.Logger;
import org.eclipse.swt.widgets.Shell;

public class DefaultDebugServerConnectionTest implements
		IDebugServerConnectionTest, IDebugServerTestListener {

	protected Shell fShell;
	protected Server fServer;
	protected String fURL;
	private Boolean isFinished = false;
	private ProgressMonitorDialog progressDialog = null;
	private final static int DEFAULT_TIMEOUT = 10000;
	private List<String> timeoutServerList = new ArrayList<String>();

	public void testConnection(Server server, Shell shell) {
		fServer = server;
		fShell = shell;
		fURL = server.getBaseURL();

		// check:
		// 1. server is available
		// 2. dummy.php exists
		// 3. check debugger communication
		// 4. debugger version
		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor
						.beginTask(
								PHPServerUIMessages
										.getString("DefaultDebugServerConnectionTest_testingConnectivity"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$

				try {
					// Check existence of both web server and dummy.php
					checkWebServerExistence();
					if (monitor.isCanceled()) {// exit point from the runnable
						// after clicking 'cancel'
						// button
						return;
					}

					String[] hosts = getAllLocalHostsAddresses();
					DebugServerTestController.getInstance().addListener(
							DefaultDebugServerConnectionTest.this);
					for (String clientHost : hosts) {
						if (monitor.isCanceled()) {
							return;
						}
						isFinished = false;
						// Build Query String to call debugger via GET
						String debugQuery = generateDebugQuery(clientHost);
						// Calling the debugger
						try {
							activateTestDebug(monitor, clientHost, debugQuery);
						} catch (SocketTimeoutException ste) {// debugger caused
							// timeout
							if (!isFinished) {
								timeoutServerList.add(clientHost);
								continue;
							}
						}
						// the following condition test is due to immediate
						// return, but the client host
						// that was sent is unavailable, i.e the debugger will
						// not return to Neon
						if (isFinished) {
							break;
						} else {
							if (!isTimeouted()) {
								break;
							}
							timeoutServerList.add(clientHost);
						}
					}
					if (!isFinished) {
						showCustomErrorDialog(addTimeOutsMessage(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest.1"))); //$NON-NLS-1$
					}
				} catch (FileNotFoundException fnfe) {// dummy.php was not found
					showCustomErrorDialog(NLS
							.bind(
									PHPServerUIMessages
											.getString("DefaultDebugServerConnectionTest_theURLCouldNotBeFound"), fURL)); //$NON-NLS-1$
					return;
				} catch (SocketTimeoutException ste) {
					if (!isFinished) {
						showCustomErrorDialog(NLS
								.bind(
										PHPServerUIMessages
												.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
						return;
					}
				} catch (ConnectException ce) {// usually when firewall blocks
					showCustomErrorDialog(NLS
							.bind(
									PHPServerUIMessages
											.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), fURL)); //$NON-NLS-1$
					return;
				} catch (IOException er) {// server not found / server is down
					showCustomErrorDialog(NLS
							.bind(
									PHPServerUIMessages
											.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), fURL)); //$NON-NLS-1$
					return;
				} finally {
					removeThisListener();
				}
			}

			private boolean isTimeouted() throws InterruptedException {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(DEFAULT_TIMEOUT / 10);
					if (isFinished) {
						return false;
					}
				}
				return true;
			}

			private void activateTestDebug(IProgressMonitor monitor,
					String clientHost, String debugQuery) throws IOException {
				monitor
						.subTask(NLS
								.bind(
										PHPServerUIMessages
												.getString("DefaultDebugServerConnectionTest_testingCommunication"), clientHost)); //$NON-NLS-1$
				InputStream inputStream = null;
				try {
					URL checkDebugURL = new URL(debugQuery);
					final URLConnection debugConnection = checkDebugURL
							.openConnection();
					debugConnection.setReadTimeout(DEFAULT_TIMEOUT);
					inputStream = debugConnection.getInputStream();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}

			private void checkWebServerExistence()
					throws MalformedURLException, IOException {
				InputStream inputStream = null;
				try {
					// 1. check base URL (http://HOST_NAME)
					// 2.check dummy file existence
					final URL checkURL = new URL(fURL + "/dummy.php"); //$NON-NLS-1$
					URLConnection connection = checkURL.openConnection();

					connection.setConnectTimeout(5000);
					connection.setReadTimeout(DEFAULT_TIMEOUT);
					inputStream = connection.getInputStream();// this will fail
					// when host not
					// found and/or
					// dummy.php not
					// found (2
					// different
					// exception
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}

		};
		progressDialog = new ProgressMonitorDialog(fShell);
		progressDialog.setBlockOnOpen(false);
		progressDialog.setCancelable(true);

		try {
			progressDialog.run(true, true, runnableWithProgress);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	protected void removeThisListener() {
		DebugServerTestController.getInstance().removeListener(this);
	}

	protected void showCustomErrorDialog(final String message) {
		removeThisListener();

		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				DefaultServerTestMessageDialog dialog = new DefaultServerTestMessageDialog(
						fShell,
						PHPServerUIMessages
								.getString("DefaultDebugServerConnectionTest_testDebugServer"), null, // accept //$NON-NLS-1$
						message, MessageDialog.ERROR,
						new String[] { IDialogConstants.OK_LABEL }, 0);
				dialog.open();
			}
		});
	}

	protected String generateDebugQuery(String host) {
		String urlToDebug = ""; //$NON-NLS-1$
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(fURL);
		queryBuilder.append("/dummy.php?start_debug=1&debug_port="); //$NON-NLS-1$
		String port = Integer.toString(PHPDebugPlugin
				.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID));

		queryBuilder.append(port);
		queryBuilder.append("&debug_fastfile=1&debug_host="); //$NON-NLS-1$

		queryBuilder.append(host + "&testConnection=true"); //$NON-NLS-1$
		urlToDebug = queryBuilder.toString();
		return urlToDebug;
	}

	private String[] getAllLocalHostsAddresses() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		String hosts = prefs.getString(PHPDebugCorePreferenceNames.CLIENT_IP);
		StringTokenizer tokenizer = new StringTokenizer(hosts, ", "); //$NON-NLS-1$
		List<String> list = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			list.add(tokenizer.nextToken());
		}

		return list.toArray(new String[list.size()]);
	}

	private void showSuccessMessage() {
		String message = PHPServerUIMessages
				.getString("DefaultDebugServerConnectionTest_success"); //$NON-NLS-1$
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message);

		if (timeoutServerList.size() > 0) {
			stringBuilder.append("\n"); //$NON-NLS-1$
			stringBuilder.append(PHPServerUIMessages
					.getString("DefaultDebugServerConnectionTest_however")); //$NON-NLS-1$
			stringBuilder.append(addTimeOutsMessage("")); //$NON-NLS-1$
		}

		MessageDialog
				.openInformation(
						fShell,
						PHPServerUIMessages
								.getString("DefaultDebugServerConnectionTest_testDebugServer"), stringBuilder.toString()); //$NON-NLS-1$
	}

	private String addTimeOutsMessage(String message) {
		String result = message;
		if (timeoutServerList.size() > 0) {
			Iterator<String> iter = timeoutServerList.iterator();
			StringBuilder stringBuilder = new StringBuilder();
			while (iter.hasNext()) {
				stringBuilder.append('-' + iter.next() + '\n');
			}
			stringBuilder
					.append(PHPServerUIMessages
							.getString("DefaultDebugServerConnectionTest_theClientHostIPs")); //$NON-NLS-1$
			result = result + stringBuilder.toString();
		}
		return result;
	}

	public void testEventReceived(final DebugServerTestEvent e) {
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				removeThisListener();
				if (e.getEventType() == DebugServerTestEvent.TEST_SUCCEEDED) {
					isFinished = true;
					showSuccessMessage();
				} else {
					switch (e.getEventType()) {
					case DebugServerTestEvent.TEST_FAILED_DEBUGER_VERSION:
						MessageDialog
								.openError(
										fShell,
										PHPServerUIMessages
												.getString("DefaultDebugServerConnectionTest_testDebugServer"), PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_oldDebuggerVersion")); //$NON-NLS-1$ //$NON-NLS-2$
						break;
					case DebugServerTestEvent.TEST_TIMEOUT:
						showCustomErrorDialog(NLS
								.bind(
										PHPServerUIMessages
												.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
						break;
					}
				}
			}
		});
	}
}
