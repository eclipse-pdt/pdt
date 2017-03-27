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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsListener;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants;

/**
 * The debugger communication receiver holds a ServerSocket that remains open
 * for the entire Eclipse running session and accepts debug requests from remote
 * or local debuggers. Any changes in the preferences listening port definition
 * is reflected in this listener by re-initializing the ServerSocket to listen
 * on the new port.
 * 
 * @author Shalom Gibly
 */
public class DebuggerCommunicationDaemon implements ICommunicationDaemon {

	public static final String ZEND_DEBUGGER_ID = "org.eclipse.php.debug.core.zendDebugger"; //$NON-NLS-1$

	private static class CommunicationDaemon extends AbstractDebuggerCommunicationDaemon {

		private int port;

		/**
		 * Constructs a new DebuggerCommunicationDaemon
		 */
		public CommunicationDaemon(int port) {
			this.port = port;
			init();
		}

		/**
		 * Returns the server socket port used for the debug requests listening
		 * thread.
		 * 
		 * @return The port specified in the preferences.
		 */
		public int getReceiverPort() {
			return port;
		}

		/**
		 * Returns the Zend's debugger ID.
		 * 
		 * @return The debugger ID that is using this daemon (e.g. Zend debugger
		 *         ID).
		 * @since PDT 1.0
		 */
		public String getDebuggerID() {
			return DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID;
		}

		public boolean isDebuggerDaemon() {
			return true;
		}

		/**
		 * Initialize a ServerSocket or a SSLServerSocket to listen for debug
		 * requests on a specified port. The port and the SSL definitions are
		 * defined in the workspace preferences.
		 * 
		 * @return True, if the reset did not yield any errors; False,
		 *         otherwise.
		 */
		public boolean resetSocket() {
			stopListen();
			int port = getReceiverPort();
			try {
				synchronized (lock) {
					if (useSSL) {
						SSLServerSocket sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault()
								.createServerSocket(port);
						sslServerSocket.setEnabledCipherSuites(sslServerSocket.getSupportedCipherSuites());
						serverSocket = sslServerSocket;
					} else {
						serverSocket = new ServerSocket(port);
					}
					startListen();
					return true;
				}
			} catch (BindException exc) {
				handleMultipleBindingError();
			} catch (IOException e) {
				Logger.logException(e);
			}
			return false;
		}

		/**
		 * Starts a connection on the given Socket. This method can be
		 * overridden by extending classes to create a different debug
		 * connection.
		 * 
		 * @param socket
		 */
		protected synchronized void startConnection(Socket socket) {
			new DebugConnection(socket);
		}

	}

	private List<AbstractDebuggerCommunicationDaemon> daemons = new ArrayList<AbstractDebuggerCommunicationDaemon>();

	private IPreferenceChangeListener defaultPortListener = null;

	private IDebuggerSettingsListener debuggerSettingsListener = null;

	private IPreferenceChangeListener sslChangeListener;

	private static boolean useSSL;

	/*
	 * Listen to any changes in the SSL preference.
	 */
	private class SSLChangeListener implements IPreferenceChangeListener {
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA)) {
				Object newValueObj = event.getNewValue();
				if (newValueObj != null) {
					if (newValueObj instanceof String)
						setUseSSL(Boolean.valueOf((String) newValueObj));
					else if (newValueObj instanceof Boolean)
						setUseSSL((Boolean) newValueObj);
				}
				if (newValueObj == null)
					setUseSSL(false);
			}
		}
	}

	// A port change listener
	private class DefaultPortListener implements IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)) {
				reset();
			}
		}
	}

	private class DebuggerSettingsListener implements IDebuggerSettingsListener {

		@Override
		public void settingsAdded(IDebuggerSettings settings) {
			if (getDebuggerID().equals(settings.getDebuggerId()))
				reset();
		}

		@Override
		public void settingsRemoved(IDebuggerSettings settings) {
			if (getDebuggerID().equals(settings.getDebuggerId()))
				reset();
		}

		@Override
		public void settingsChanged(PropertyChangeEvent[] events) {
			for (PropertyChangeEvent event : events) {
				IDebuggerSettings settings = (IDebuggerSettings) event.getSource();
				if (getDebuggerID().equals(settings.getDebuggerId())
						&& event.getProperty().equals(ZendDebuggerSettingsConstants.PROP_CLIENT_PORT)) {
					reset();
				}
			}
		}

	}

	/**
	 * Creates and returns communication daemon that listens on given port. This
	 * method is dedicated to be used if there is a need to create a temporary
	 * daemon e.g. for communication test purposes. Clients should remember to
	 * call #stopListen() on daemon after it is not longer used.
	 * 
	 * @param port
	 * @return new communication daemon
	 */
	public static AbstractDebuggerCommunicationDaemon createDaemon(int port) {
		return new CommunicationDaemon(port);
	}

	/**
	 * Returns if there is a use of SSL encryption for this connection.
	 * 
	 * @return True, iff there is a SSL use; False, otherwise.
	 */
	public synchronized boolean isUsingSSL() {
		return useSSL;
	}

	/**
	 * Set the use of SSL encryption for the connection.
	 * 
	 * @param enable
	 */
	public synchronized void setUseSSL(boolean enable) {
		if (enable == isUsingSSL()) {
			return;
		}
		// useSSLConnectionChanged = true;
		useSSL = enable;
		resetSocket();
	}

	@Override
	public void init() {
		useSSL = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false);
		registerListeners();
		reset();
	}

	@Override
	public boolean isListening(int port) {
		for (ICommunicationDaemon daemon : daemons)
			if (daemon.isListening(port))
				return true;
		return false;
	}

	@Override
	public void startListen() {
		for (ICommunicationDaemon daemon : daemons)
			daemon.startListen();
	}

	@Override
	public void stopListen() {
		unregisterListeners();
		for (ICommunicationDaemon daemon : daemons)
			daemon.stopListen();
	}

	@Override
	public boolean resetSocket() {
		boolean allReset = true;
		for (ICommunicationDaemon daemon : daemons)
			if (!daemon.resetSocket())
				allReset = false;
		return allReset;
	}

	@Override
	public void handleMultipleBindingError() {
		// Won't be called
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getDebuggerID() {
		return ZEND_DEBUGGER_ID;
	}

	@Override
	public boolean isDebuggerDaemon() {
		return true;
	}

	@Override
	public boolean isInitialized() {
		for (ICommunicationDaemon daemon : daemons)
			if (!daemon.isInitialized())
				return false;
		return true;
	}

	private void registerListeners() {
		if (defaultPortListener == null) {
			defaultPortListener = new DefaultPortListener();
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).addPreferenceChangeListener(defaultPortListener);
		}
		if (debuggerSettingsListener == null) {
			debuggerSettingsListener = new DebuggerSettingsListener();
			DebuggerSettingsManager.INSTANCE.addSettingsListener(debuggerSettingsListener);
		}
		if (sslChangeListener == null) {
			sslChangeListener = new SSLChangeListener();
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).addPreferenceChangeListener(sslChangeListener);
		}
	}

	private void unregisterListeners() {
		if (defaultPortListener != null) {
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).removePreferenceChangeListener(defaultPortListener);
		}
		if (debuggerSettingsListener != null) {
			DebuggerSettingsManager.INSTANCE.removeSettingsListener(debuggerSettingsListener);
		}
		if (sslChangeListener != null) {
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).removePreferenceChangeListener(sslChangeListener);
		}
	}

	private synchronized void reset() {
		Set<Integer> ports = PHPDebugUtil.getDebugPorts(getDebuggerID());
		List<AbstractDebuggerCommunicationDaemon> daemonsToRemove = new ArrayList<AbstractDebuggerCommunicationDaemon>();
		// Shutdown daemons that should not listen anymore
		for (AbstractDebuggerCommunicationDaemon daemon : daemons) {
			boolean isRedundant = true;
			for (int port : ports) {
				if (daemon.getReceiverPort() == port) {
					isRedundant = false;
					break;
				}
			}
			if (isRedundant) {
				daemon.stopListen();
				daemonsToRemove.add(daemon);
			}
		}
		daemons.removeAll(daemonsToRemove);
		// Start new daemons if there should be any
		for (int port : ports) {
			boolean isRunning = false;
			for (AbstractDebuggerCommunicationDaemon daemon : daemons) {
				if (daemon.getReceiverPort() == port) {
					isRunning = true;
					break;
				}
			}
			if (!isRunning) {
				AbstractDebuggerCommunicationDaemon newDaemon = new CommunicationDaemon(port);
				daemons.add(newDaemon);
			}
		}
	}

}
