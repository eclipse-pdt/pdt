/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.swt.widgets.Shell;

public class DefaultDebugServerConnectionTest implements IDebugServerConnectionTest, IDebugServerTestListener {

	protected Shell fShell;
	private String fURL;
	private Boolean isFinished = false;
	private ProgressMonitorDialog progressDialog = null;
	private final static int DEFAULT_TIMEOUT = 10000;

	public void testConnection(String url, Shell shell) {
		fURL = url;
		fShell = shell;

		// check:
		// 1. server is available
		// 2. dummy.php exists 
		// 3. check debugger communication
		// 4. debugger version

		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testingConnectivity"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				try {
					//1. check base URL (http://HOST_NAME)
					//2.check dummy file existence
					final URL checkURL = new URL(fURL + "/dummy.php"); //$NON-NLS-1$
					URLConnection connection = checkURL.openConnection();

					InputStream inputStream = null;

					Timer timer = new Timer();
					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							if (!isFinished) {
								DebugServerTestController.getInstance().notifyTestListeners(new DebugServerTestEvent(fURL, DebugServerTestEvent.TEST_TIMEOUT));
							}
						}
					};
					timer.schedule(task, DEFAULT_TIMEOUT);
					inputStream = connection.getInputStream();//this will fail when host not found and/or dummy.php not found (2 different exception
					inputStream.close();

					///Build Query String to call debugger via GET
					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append(fURL);
					queryBuilder.append("/dummy.php?start_debug=0&debug_port="); //$NON-NLS-1$
					String port = Integer.toString(PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID));
					queryBuilder.append(port);
					queryBuilder.append("&debug_fastfile=1&debug_host="); //$NON-NLS-1$
					String hosts = PHPDebugPlugin.getDebugHosts();
					queryBuilder.append(hosts + "&testConnection=true"); //$NON-NLS-1$
					final String urlToDebug = queryBuilder.toString();

					//Calling the debugger
					URL checkDebugURL = new URL(urlToDebug);
					URLConnection debugConnection = checkDebugURL.openConnection();
					DebugServerTestController.getInstance().addListener(DefaultDebugServerConnectionTest.this);
					debugConnection.getInputStream();//this should activate the debugger

					synchronized (isFinished) {
						if (!isFinished) {
							isFinished.wait();
						}
					}

				} catch (FileNotFoundException fnfe) {//dummy.php was not found
					showErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_theURLCouldNotBeFound"), fURL)); //$NON-NLS-1$
				} catch (IOException er) {//server not found / server is down
					showErrorDialog(NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), fURL)); //$NON-NLS-1$
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

	protected void showErrorDialog(final String message) {
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(fShell, PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), message); //$NON-NLS-1$
			}
		});
	}

	public void testEventReceived(final DebugServerTestEvent e) {
		synchronized (isFinished) {
			isFinished.notifyAll();
			isFinished = true;
		}
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				DebugServerTestController.getInstance().removeListener(DefaultDebugServerConnectionTest.this);
				if (e.getEventType() == DebugServerTestEvent.TEST_SUCCEEDED) {
					MessageDialog.openInformation(fShell, PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), e.getEventMessage()); //$NON-NLS-1$
				} else {
					showErrorDialog(e.getEventMessage());
				}
			}
		});
	}
}
