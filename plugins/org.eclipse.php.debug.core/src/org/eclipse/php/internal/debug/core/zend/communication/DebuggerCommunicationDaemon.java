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

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
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

	// A port change listener
	private class PortChangeListener implements IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals(
					PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT)) {
				resetSocket();
			}
		}
	}

	private IPreferenceChangeListener portChangeListener;

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
	 * Returns the server socket port used for the debug requests listening
	 * thread.
	 * 
	 * @return The port specified in the preferences.
	 */
	public int getReceiverPort() {
		return PHPDebugPlugin.getDebugPort(ZEND_DEBUGGER_ID);
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

	/**
	 * Initialize a daemon change listener
	 */
	protected void initDeamonChangeListener() {
		if (portChangeListener == null) {
			IEclipsePreferences preferences = InstanceScope.INSTANCE
					.getNode(PHPDebugPlugin.ID);
			portChangeListener = new PortChangeListener();
			preferences.addPreferenceChangeListener(portChangeListener);
		}
	}

	/**
	 * Starts a connection on the given Socket. This method can be overridden by
	 * extending classes to create a different debug connection.
	 * 
	 * @param socket
	 */
	protected synchronized void startConnection(Socket socket) {
		new DebugConnection(socket);
	}

}
