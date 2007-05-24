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
package org.eclipse.php.internal.debug.core.communication;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.swt.widgets.Display;

/**
 * The debugger communication receiver holds a ServerSocket that remains open for the entire
 * Eclipse running session and accepts debug requests from remote or local debuggers.
 * Any changes in the preferences listening port definition is reflected in this listener by
 * re-initializing the ServerSocket to listen on the new port.
 * 
 * @author Shalom Gibly
 */
public class DebuggerCommunicationDaemon implements ICommunicationDaemon {

	protected Object lock = new Object();
	protected ServerSocket serverSocket;
	protected boolean isAlive;
	private IPropertyChangeListener portChangeListener;
	private Thread listenerThread;

	/**
	 * Constructs a new DebuggerCommunicationDaemon
	 */
	public DebuggerCommunicationDaemon() {
	}

	/**
	 * Initializes the ServerSocket and starts a listen thread. Also, initialize a preferences
	 * change listener for the port that is used by this daemon.
	 */
	public void init() {
		initDeamonChangeListener();
		resetSocket();
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
	 * Starts the listening thread for any incoming debug requests (responces).
	 */
	public void startListen() {
		synchronized (lock) {
			if (!isAlive && serverSocket != null) {
				startListenThread();
			}
		}
	}

	/**
	 * Stops the listening thread. 
	 * Any incoming request will not be treated.
	 */
	public void stopListen() {
		synchronized (lock) {
			isAlive = false;
			if (serverSocket != null) {
				try {
					if (!serverSocket.isClosed()) {
						serverSocket.close();
					}
				} catch (SocketException se) {
					// do nothing in this case
				} catch (IOException e) {
					Logger.logException("Problem while closing the debugger ServerSocket.", e);
				} finally {
					serverSocket = null;
				}
			}
		}
		try {
			// Wait for the listener thread to die.
			// Wait, at most, 2 seconds.
			if (listenerThread != null) {
				listenerThread.join(2000);
			}
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Returns true if this daemon is listening for communication requests.
	 * @return True, if the daemon is listening; False, otherwise.
	 */
	public boolean isListening() {
		synchronized (lock) {
			return isAlive;
		}
	}
	
	/**
	 * Initialize the ServerSocket to listen for debug requests on a specified port. 
	 * The port is defined in the workspace preferences.
	 * 
	 * @return True, if the reset did not yield any errors; False, otherwise.
	 */
	public boolean resetSocket() {
		stopListen();
		int port = getReceiverPort();
		try {
			synchronized (lock) {
				serverSocket = new ServerSocket(port);
				startListen();
				return true;
			}
		} catch (BindException exc) {
			handleMultipleBindingError();
		} catch (IOException e) {
			Logger.logException("Error while restting the socket for the debug requests.", e);
		}
		return false;
	}

	public void handleMultipleBindingError() {
		final int port = getReceiverPort();
		Logger.log(Logger.ERROR, "The debug port " + port + " is in use. Please select a different port for the debugger.");
		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				final String message = MessageFormat.format(PHPDebugCoreMessages.Port_Error_Message_Message, new String[] { String.valueOf(port) });
				MessageDialog.openWarning(display.getActiveShell(), PHPDebugCoreMessages.Port_Error_Message_Title, message);
			}
		});
	}

	/**
	 * Returns the server socket port used for the debug requests listening thread. 
	 * @return The port specified in the preferences.
	 */
	protected int getReceiverPort() {
		return PHPProjectPreferences.getDebugPort(null);
	}

	/**
	 * Starts a connection handling thread on the given Socket. 
	 * This method can be overriden by extending classes to create a different debug connection threads.
	 * The connection thread itself should execute itself in a different thread in order to 
	 * release the current thread.
	 * 
	 * @param socket
	 */
	protected void startConnectionThread(Socket socket) {
		// Handles the connection in a new thread
		new DebugConnectionThread(socket);
	}

	/**
	 * Starts the listening thread.
	 * If the thread is already started, nothing should happen.
	 */
	private void startListenThread() {
		synchronized (lock) {
			if (isAlive) {
				return;
			}
			isAlive = true;
		}
		String port = " - Port: " + ((serverSocket != null) ? String.valueOf(serverSocket.getLocalPort()) : "??");
		listenerThread = new Thread(new ReceiverThread(), "PHP Debugger ReceiverThread " + port);
		listenerThread.start();
	}

	/*
	 * The thread responsible of listening for debug requests.
	 * On every debug request, a new thread of DebugConnectionThread is created and 
	 * a debug session is initialized.
	 */
	private class ReceiverThread implements Runnable {
		public void run() {
			try {
				while (isAlive) {
					Socket socket = serverSocket.accept();
					startConnectionThread(socket);
				}
			} catch (IOException e) {
				synchronized (lock) {
					if (isAlive) {
						Logger.logException("Error while listening to incoming debug requests. Listen thread terminated!", e);
						isAlive = false;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.daemon.communication.ICommunicationDaemon#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	// A port change listener
	private class PortChangeListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(PHPDebugCorePreferenceNames.DEBUG_PORT)) {
				resetSocket();
			}
		}
	}
}
