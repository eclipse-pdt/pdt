/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.debug.core.xdebug.communication;

import java.net.Socket;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.xdebug.XDebugUIAttributeConstants;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;

/**
 * XDebug communication daemon.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class XDebugCommunicationDaemon extends AbstractDebuggerCommunicationDaemon {

	public static final String XDEBUG_DEBUGGER_ID = "org.eclipse.php.debug.core.xdebugDebugger";
	private PortChangeListener portChangeListener;

	/**
	 * An XDebug communication daemon.
	 */
	public XDebugCommunicationDaemon() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon#init()
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
			Preferences preferences = PHPDebugPlugin.getDefault().getPluginPreferences();
			portChangeListener = new PortChangeListener();
			preferences.addPropertyChangeListener(portChangeListener);
		}
	}

	/**
	 * Returns the server socket port used for the debug requests listening thread. 
	 * @return The port specified in the preferences.
	 */
	public int getReceiverPort() {
		return PHPDebugPlugin.getDebugPort(XDEBUG_DEBUGGER_ID);
	}

	/**
	 * Returns the XDebug debugger ID.
	 * 
	 * @return The debugger ID that is using this daemon (e.g. XDebug).
	 * @since PDT 1.0
	 */
	public String getDebuggerID() {
		return XDEBUG_DEBUGGER_ID;
	}

	/**
	 * Returns if this daemon is a debugger daemon. 
	 * In this case, always return true.
	 */
	public boolean isDebuggerDaemon() {
		return true;
	}

	/**
	 * Starts a connection handling thread on the given Socket. 
	 * 
	 * @param socket
	 */
	protected void startConnectionThread(Socket socket) {
		if (DBGpLogger.debugSession()) {
			DBGpLogger.debug("Connection established: " + socket.toString());
		}
		DBGpSession session = new DBGpSession(socket);
		if (session.isActive()) {
			if (!DBGpSessionHandler.getInstance().fireSessionAdded(session)) {
				// session was not taken, throw it away
				session.endSession();
			}
		}
	}

	/*
	 * A property change listener which resets the server socket listener on every XDebug port change.
	 */
	private class PortChangeListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(XDebugUIAttributeConstants.XDEBUG_PREF_PORT)) {
				resetSocket();
			}
		}
	}
}
