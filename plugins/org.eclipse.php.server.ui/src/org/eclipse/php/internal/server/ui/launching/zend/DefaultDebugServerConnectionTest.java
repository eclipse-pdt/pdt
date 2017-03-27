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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestController;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestEvent;
import org.eclipse.php.internal.debug.core.zend.testConnection.IDebugServerTestListener;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.ui.Logger;
import org.eclipse.php.internal.server.ui.launching.Messages;
import org.eclipse.swt.widgets.Shell;

/**
 * Default implementation for Zend Debugger server connectivity test.
 */
public class DefaultDebugServerConnectionTest implements IDebugServerConnectionTest, IDebugServerTestListener {

	protected class TestPerformer implements IRunnableWithProgress {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse
		 * .core.runtime.IProgressMonitor)
		 */
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			DebugServerTestController.getInstance().addListener(DefaultDebugServerConnectionTest.this);
			monitor.beginTask(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testingConnectivity"), //$NON-NLS-1$
					IProgressMonitor.UNKNOWN);
			try {
				// Check existence of debugger owner and dummy.php
				monitor.subTask(NLS.bind(
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testingServerExistence"), //$NON-NLS-1$
						fServer.getName()));
				checkServer();
				if (monitor.isCanceled()) {
					return;
				}
				String[] clientHosts = getAllLocalHostsAddresses();
				// Check connection with any of provided client IPs
				for (String clientHost : clientHosts) {
					if (monitor.isCanceled()) {
						return;
					}
					fCurrentHost = clientHost;
					// Build query to call debugger via GET
					String debugQuery = generateDebugQuery(clientHost);
					monitor.subTask(NLS.bind(
							PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testingCommunication"), //$NON-NLS-1$
							clientHost));
					try {
						// Calling the debugger client test
						checkClient(monitor, debugQuery);
					} catch (SocketTimeoutException ste) {
						// Debugger caused timeout
						if (!fIsFinished) {
							fTimeoutServerList.add(clientHost);
							continue;
						}
					}
					// Go out if cancelled or finished
					if (monitor.isCanceled() || fIsFinished) {
						return;
					}
					/*
					 * The following condition test is due to immediate return,
					 * but the client host that was sent is unavailable, i.e the
					 * debugger will not return to 'Neon' version of debugger.
					 */
					if (!checkTimeout(monitor)) {
						break;
					}
					fTimeoutServerList.add(clientHost);
				}
				// Go out if cancelled, succeeded or finished
				if (monitor.isCanceled() || fIsFinished) {
					return;
				}
				// All client IPs caused timeout
				showCustomErrorDialog(
						addTimeOutsMessage(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest.1"))); //$NON-NLS-1$
			} catch (FileNotFoundException fnfe) {
				// dummy.php was not found
				String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
						PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", null);
				showCustomErrorDialog(NLS.bind(
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_theURLCouldNotBeFound"), fURL, //$NON-NLS-1$
						dummyFile));
				return;
			} catch (SocketTimeoutException ste) {
				// Timeout occurred when trying to connect to debugger owner
				showCustomErrorDialog(NLS
						.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
				return;
			} catch (ConnectException ce) {
				// Usually when firewall blocks
				showCustomErrorDialog(NLS.bind(
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), //$NON-NLS-1$
						fURL));
				return;
			} catch (IOException er) {
				// Server not found / server is down
				showCustomErrorDialog(NLS.bind(
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_webServerConnectionFailed"), //$NON-NLS-1$
						fURL));
				return;
			} catch (Exception ex) {
				// Something unexpected happened, report it
				Logger.logException(ex);
				showCustomErrorDialog(
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_Unexpected_error_occurred")); //$NON-NLS-1$
			}

			finally {
				DebugServerTestController.getInstance().removeListener(DefaultDebugServerConnectionTest.this);
			}
		}

		private boolean checkTimeout(IProgressMonitor monitor) throws InterruptedException {
			for (int i = 0; i < 10; i++) {
				// Go out if cancelled
				if (monitor.isCanceled())
					return false;
				Thread.sleep(DEFAULT_TIMEOUT / 10);
				if (fIsFinished) {
					return false;
				}
			}
			return true;
		}

		private void checkClient(IProgressMonitor monitor, String debugQuery) throws Exception {
			fClientTestLatch = new CountDownLatch(1);
			fClientTest = new ClientTest(debugQuery);
			fClientTest.schedule();
			while (!monitor.isCanceled() && !fIsFinished && fClientTest.getState() != Job.NONE) {
				try {
					fClientTestLatch.await(500, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					// Ignore
				}
			}
			if (fClientTest.exception != null)
				throw fClientTest.exception;
		}

		private void checkServer() throws MalformedURLException, IOException {
			InputStream inputStream = null;
			try {
				// Check base URL (http://HOST_NAME) and dummy file existence
				String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
						PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
						null);
				final URL checkURL = new URL(fURL + '/' + dummyFile); // $NON-NLS-1$
				URLConnection connection = checkURL.openConnection();
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(DEFAULT_TIMEOUT);
				inputStream = connection.getInputStream();
				/*
				 * this will fail when host not found and/or dummy.php not found
				 * (2 different exceptions)
				 */
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}

	}

	protected class ClientTest extends Job {

		protected String query;
		protected Exception exception;

		public ClientTest(String query) {
			super(""); //$NON-NLS-1$
			this.query = query;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
		 * IProgressMonitor)
		 */
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				perform();
			} catch (Exception e) {
				exception = e;
			} finally {
				fClientTestLatch.countDown();
			}
			return Status.OK_STATUS;
		}

		private void perform() throws MalformedURLException, IOException {
			InputStream inputStream = null;
			try {
				URL checkDebugURL = new URL(query);
				final URLConnection debugConnection = checkDebugURL.openConnection();
				debugConnection.setReadTimeout(DEFAULT_TIMEOUT);
				inputStream = debugConnection.getInputStream();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}

	}

	private final static int DEFAULT_TIMEOUT = 10000;
	protected Shell fShell;
	protected Server fServer;
	protected String fURL;
	protected Boolean fIsFinished = false;
	protected ProgressMonitorDialog fProgressDialog = null;
	protected List<String> fTimeoutServerList = new ArrayList<String>();
	protected ClientTest fClientTest;
	protected CountDownLatch fClientTestLatch;
	protected String fCurrentHost;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.debug.ui.IDebugServerConnectionTest#testConnection(org
	 * .eclipse.php.internal.server.core.Server, org.eclipse.swt.widgets.Shell)
	 */
	public void testConnection(Server server, Shell shell) {
		fServer = server;
		fShell = shell;
		fURL = server.getBaseURL();
		IRunnableWithProgress runnableWithProgress = new TestPerformer();
		fProgressDialog = new ProgressMonitorDialog(fShell);
		fProgressDialog.setBlockOnOpen(false);
		fProgressDialog.setCancelable(true);
		fProgressDialog.open();
		fProgressDialog.getShell()
				.setText(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_debugger_connection_test")); //$NON-NLS-1$
		try {
			fProgressDialog.run(true, true, runnableWithProgress);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.testConnection.
	 * IDebugServerTestListener
	 * #testEventReceived(org.eclipse.php.internal.debug.
	 * core.zend.testConnection.DebugServerTestEvent)
	 */
	public void testEventReceived(final DebugServerTestEvent e) {
		fIsFinished = true;
		// Release the latch as we have event
		fClientTestLatch.countDown();
		switch (e.getEventType()) {
		case DebugServerTestEvent.TEST_SUCCEEDED: {
			showSuccessDialog();
			break;
		}
		case DebugServerTestEvent.TEST_FAILED_DEBUGER_VERSION:
			MessageDialog.openError(fShell,
					PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), //$NON-NLS-1$
					PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_oldDebuggerVersion")); //$NON-NLS-1$
			break;
		case DebugServerTestEvent.TEST_TIMEOUT:
			showCustomErrorDialog(
					NLS.bind(PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_timeOutMessage"), fURL)); //$NON-NLS-1$
			break;
		}
	}

	/**
	 * Returns the last/current host that the test was performed for.
	 * 
	 * @return last/current host name
	 */
	public String getCurrentHost() {
		return fCurrentHost;
	}

	protected String generateDebugQuery(String host) {
		StringBuilder debugQuery = new StringBuilder();
		debugQuery.append(fURL);
		String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
				null);
		if (!dummyFile.startsWith("/")) { //$NON-NLS-1$
			dummyFile = "/" + dummyFile; //$NON-NLS-1$
		}
		debugQuery.append(dummyFile).append("?start_debug=1&debug_port="); //$NON-NLS-1$
		int port = getDebugPort();
		debugQuery.append(port);
		debugQuery.append("&debug_fastfile=1&debug_host="); //$NON-NLS-1$
		debugQuery.append(host + "&testConnection=true"); //$NON-NLS-1$
		boolean useSSL = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false);
		if (useSSL) {
			debugQuery.append("&use_ssl=1"); //$NON-NLS-1$
		}
		return debugQuery.toString();
	}

	protected String getPort() {
		String port = Integer.toString(PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID));
		// Set up custom port from server configuration
		int customPort = ZendDebuggerSettingsUtil.getDebugPort(fServer.getUniqueId());
		if (customPort != -1)
			port = String.valueOf(customPort);
		return port;
	}

	/**
	 * Returns debug port.
	 * 
	 * @return debug port
	 */
	protected int getDebugPort() {
		int port = -1;
		port = PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
		// Set up custom port from server configuration
		int customPort = ZendDebuggerSettingsUtil.getDebugPort(fServer.getUniqueId());
		if (customPort != -1) {
			port = customPort;
		}
		return port;
	}

	protected String[] getAllLocalHostsAddresses() {
		String hosts = PHPDebugPlugin.getDebugHosts();
		// Set up custom hosts from server configuration
		String customHosts = ZendDebuggerSettingsUtil.getDebugHosts(fServer.getUniqueId());
		if (!customHosts.isEmpty())
			hosts = customHosts;
		StringTokenizer tokenizer = new StringTokenizer(hosts, ", "); //$NON-NLS-1$
		List<String> list = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			list.add(tokenizer.nextToken());
		}
		return list.toArray(new String[list.size()]);
	}

	protected String addTimeOutsMessage(String message) {
		String result = message;
		if (fTimeoutServerList.size() > 0) {
			Iterator<String> iter = fTimeoutServerList.iterator();
			StringBuilder stringBuilder = new StringBuilder();
			while (iter.hasNext()) {
				stringBuilder.append("\u2022 " + iter.next() + '\n'); //$NON-NLS-1$
			}
			result = result + stringBuilder.toString();
		}
		return result;
	}

	protected void showCustomErrorDialog(final String message) {
		fShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				DefaultServerTestMessageDialog dialog = new DefaultServerTestMessageDialog(fShell,
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), null, // accept //$NON-NLS-1$
						message, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
				dialog.open();
			}
		});
	}

	protected void showSuccessDialog() {
		String message = MessageFormat.format(Messages.DebugServerConnectionTest_test_successfull, fCurrentHost);
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message);
		if (getAllLocalHostsAddresses().length > 1) {
			stringBuilder.append('\n');
			stringBuilder.append(Messages.DebugServerConnectionTest_some_IPs_seems_to_be_redundant);
			StringBuilder addresses = new StringBuilder();
			for (String address : getAllLocalHostsAddresses()) {
				if (!address.equals(fCurrentHost)) {
					addresses.append((addresses.length() != 0 ? ", " : "") //$NON-NLS-1$ //$NON-NLS-2$
							+ '\'' + address + '\'');
				}
			}
			stringBuilder.append(addresses.toString());
		}
		fShell.getDisplay().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(fShell,
						PHPServerUIMessages.getString("DefaultDebugServerConnectionTest_testDebugServer"), //$NON-NLS-1$
						stringBuilder.toString());
			}
		});
	}

}
