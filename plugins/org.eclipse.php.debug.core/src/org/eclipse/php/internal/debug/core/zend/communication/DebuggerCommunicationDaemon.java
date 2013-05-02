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

import java.net.Socket;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;

/**
 * The debugger communication receiver holds a ServerSocket that remains open
 * for the entire Eclipse running session and accepts debug requests from remote
 * or local debuggers. Any changes in the preferences listening port definition
 * is reflected in this listener by re-initializing the ServerSocket to listen
 * on the new port.
 * 
 * @author Shalom Gibly
 */
public class DebuggerCommunicationDaemon extends
		AbstractDebuggerCommunicationDaemon implements ICommunicationDaemon {

	public static final String ZEND_DEBUGGER_ID = "org.eclipse.php.debug.core.zendDebugger"; //$NON-NLS-1$
	private IPropertyChangeListener portChangeListener;

	/**
	 * Constructs a new DebuggerCommunicationDaemon
	 */
	public DebuggerCommunicationDaemon() {
	}

	/**
	 * Initializes the ServerSocket and starts a listen thread. Also, initialize
	 * a preferences change listener for the port that is used by this daemon.
	 */
	public void init() {
		initDeamonChangeListener();
		super.init();
	}

	/**
	 * Initialize a daemon change listener
	 */
	protected void initDeamonChangeListener() {
		if (portChangeListener == null) {
			Preferences preferences = PHPDebugPlugin.getDefault()
					.getPluginPreferences();
			portChangeListener = new PortChangeListener();
			preferences.addPropertyChangeListener(portChangeListener);
		}
	}

	/**
	 * Returns the server socket port used for the debug requests listening
	 * thread.
	 * 
	 * @return The port specified in the preferences.
	 */
	public int getReceiverPort() {
		return PHPDebugPlugin.getDebugPort(ZEND_DEBUGGER_ID);
	}

	/**
	 * Starts a connection handling thread on the given Socket. This method can
	 * be overridden by extending classes to create a different debug connection
	 * threads. The connection thread itself should execute itself in a
	 * different thread in order to release the current thread.
	 * 
	 * @param socket
	 */
	protected void startConnectionThread(Socket socket) {
		// Handles the connection in a new thread
		new DebugConnectionThread(socket);
	}

	// A port change listener
	private class PortChangeListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(
					PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)) {
				resetSocket();
			}
		}
	}

	/**
	 * Returns the Zend's debugger ID.
	 * 
	 * @return The debugger ID that is using this daemon (e.g. Zend debugger
	 *         ID).
	 * @since PDT 1.0
	 */
	public String getDebuggerID() {
		return ZEND_DEBUGGER_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.debug.daemon.communication.ICommunicationDaemon#
	 * isDebuggerDaemon()
	 */
	public boolean isDebuggerDaemon() {
		return true;
	}
}
