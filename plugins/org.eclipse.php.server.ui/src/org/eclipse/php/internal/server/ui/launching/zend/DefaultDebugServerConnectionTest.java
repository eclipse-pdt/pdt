/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.launching.zend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestController;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestEvent;
import org.eclipse.php.internal.debug.core.zend.testConnection.IDebugServerTestListener;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.ui.Logger;
import org.eclipse.swt.widgets.Shell;

public class DefaultDebugServerConnectionTest implements IDebugServerConnectionTest, IDebugServerTestListener {

	protected Shell fShell;
	protected Server fServer;
	protected String fURL;
	private Boolean isFinished = false;
	private ProgressMonitorDialog progressDialog = null;
	private final static int DEFAULT_TIMEOUT = 10000;

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
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testingConnectivity"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				try {
					//Check existence of both web server and dummy.php
					checkWebServerExistence();

					//Build Query String to call debugger via GET
					String debugQuery = generateDebugQuery();

					//Calling the debugger
					activateTestDebug(debugQuery);
				} catch (FileNotFoundException fnfe) {//dummy.php was not found
					showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_theURLCouldNotBeFound"), fURL)); //$NON-NLS-1$
					removeThisListener();
					return;
				} catch (SocketTimeoutException ste) {
					if (!isFinished) {
						showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
						removeThisListener();
						return;
					}
				} catch (ConnectException ce) {//usually when firewall blocks
					showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), fURL)); //$NON-NLS-1$
					removeThisListener();
					return;
				} catch (IOException er) {//server not found / server is down
					showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), fURL)); //$NON-NLS-1$
					removeThisListener();
					return;
				}

				if (!isFinished) {
					Thread.sleep(DEFAULT_TIMEOUT / 2);
					if (isFinished) {
						showSuccessMessage();
					} else {
						showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
					}
					removeThisListener();
				}
			}

			private void activateTestDebug(String debugQuery) throws MalformedURLException, IOException {
				InputStream inputStream = null;
				try {
					URL checkDebugURL = new URL(debugQuery);
					final URLConnection debugConnection = checkDebugURL.openConnection();
					DebugServerTestController.getInstance().addListener(DefaultDebugServerConnectionTest.this);
					debugConnection.setReadTimeout(DEFAULT_TIMEOUT);
					debugConnection.getInputStream();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}

			private void checkWebServerExistence() throws MalformedURLException, IOException {
				InputStream inputStream = null;
				try {
					//1. check base URL (http://HOST_NAME)
					//2.check dummy file existence
					final URL checkURL = new URL(fURL + "/dummy.php"); //$NON-NLS-1$
					URLConnection connection = checkURL.openConnection();

					connection.setConnectTimeout(5000);
					connection.setReadTimeout(DEFAULT_TIMEOUT);
					inputStream = connection.getInputStream();//this will fail when host not found and/or dummy.php not found (2 different exception
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

	private void removeThisListener() {
		DebugServerTestController.getInstance().removeListener(this);
	}

	protected void showCustomErrorDialog(final String message) {
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				DefaultServerTestMessageDialog dialog = new DefaultServerTestMessageDialog(fShell, PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), null, // accept
					message, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
				dialog.open();
			}
		});
	}

	protected synchronized String generateDebugQuery() {
		String urlToDebug = "";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(fURL);
		queryBuilder.append("/dummy.php?start_debug=1&debug_port="); //$NON-NLS-1$
		String port = Integer.toString(PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID));

		queryBuilder.append(port);
		queryBuilder.append("&debug_fastfile=1&debug_host="); //$NON-NLS-1$
		String hosts = PHPDebugPlugin.getDebugHosts();
		queryBuilder.append(hosts + "&testConnection=true"); //$NON-NLS-1$
		urlToDebug = queryBuilder.toString();
		return urlToDebug;
	}

	private void showSuccessMessage() {
		MessageDialog.openInformation(fShell, PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_success")); //$NON-NLS-1$
	}

	public void testEventReceived(final DebugServerTestEvent e) {
		isFinished = true;
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				removeThisListener();
				if (e.getEventType() == DebugServerTestEvent.TEST_SUCCEEDED) {
					showSuccessMessage();
				} else {
					switch (e.getEventType()) {
						case DebugServerTestEvent.TEST_FAILED_DEBUGER_VERSION:
							MessageDialog.openError(fShell, PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_oldDebuggerVersion"));
							break;
						case DebugServerTestEvent.TEST_TIMEOUT:
							showCustomErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL));
							break;
					}
				}
			}
		});
	}
}
