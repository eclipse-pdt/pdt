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
package org.eclipse.php.internal.debug.core.daemon;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.Logger;

/**
 * The debugger communication receiver holds a ServerSocket that remains open
 * for the entire Eclipse running session and accepts debug requests from remote
 * or local debuggers.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public abstract class AbstractDebuggerCommunicationDaemon implements
		ICommunicationDaemon {

	protected Object lock = new Object();
	protected ServerSocket serverSocket;
	protected boolean isAlive;
	protected Thread listenerThread;
	private boolean isInitialized;

	/**
	 * Constructs a new AbstractDebuggerCommunicationDaemon
	 */
	public AbstractDebuggerCommunicationDaemon() {
	}

	/**
	 * Initializes the ServerSocket and starts a listen thread. Also, initialize
	 * a preferences change listener for the port that is used by this daemon.
	 */
	public void init() {
		resetSocket();
	}

	/**
	 * Starts the listening thread for any incoming debug requests (responces).
	 */
	public void startListen() {
		synchronized (lock) {
			if (!isAlive && serverSocket != null) {
				startListenThread();
			} else {
				isInitialized = true;
			}
		}
	}

	/**
	 * Stops the listening thread. Any incoming request will not be treated.
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
					Logger.logException(
							"Problem while closing the debugger ServerSocket.", //$NON-NLS-1$
							e);
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
	 * 
	 * @return True, if the daemon is listening; False, otherwise.
	 */
	public boolean isListening() {
		synchronized (lock) {
			return isAlive;
		}
	}

	/**
	 * Initialize the ServerSocket to listen for debug requests on a specified
	 * port. The port is defined in the workspace preferences.
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
			Logger.logException(
					"Error while restting the socket for the debug requests.", //$NON-NLS-1$
					e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.debug.daemon.communication.ICommunicationDaemon#
	 * handleMultipleBindingError()
	 */
	public void handleMultipleBindingError() {
		final int port = getReceiverPort();
		Logger.log(
				Logger.ERROR,
				"The debug port " //$NON-NLS-1$
						+ port
						+ " is in use. Please select a different port for the debugger."); //$NON-NLS-1$
	}

	/**
	 * Returns the server socket port used for the debug requests listening
	 * thread.
	 * 
	 * @return The port specified in the preferences.
	 */
	public abstract int getReceiverPort();

	/**
	 * Starts a connection handling thread on the given Socket. This method
	 * should be overridden by extending classes to create a different debug
	 * connection threads. The connection thread itself should execute itself in
	 * a different thread in order to release the current thread.
	 * 
	 * @param socket
	 */
	protected abstract void startConnectionThread(Socket socket);

	/**
	 * Returns the debugger ID that is using this communication daemon.
	 * 
	 * @return The debugger ID that is using this daemon.
	 * @since PDT 1.0
	 */
	public abstract String getDebuggerID();

	/**
	 * Starts the listening thread. If the thread is already started, nothing
	 * should happen.
	 */
	protected void startListenThread() {
		synchronized (lock) {
			if (isAlive) {
				return;
			}
			isAlive = true;
		}
		String port = " - Port: " //$NON-NLS-1$
				+ ((serverSocket != null) ? String.valueOf(serverSocket
						.getLocalPort()) : "??"); //$NON-NLS-1$
		listenerThread = new Thread(new ReceiverThread(),
				"PHP Debugger Daemon Thread " + port); //$NON-NLS-1$
		listenerThread.setDaemon(true);
		listenerThread.start();
	}

	public boolean isInitialized() {
		synchronized (lock) {
			return isInitialized;
		}
	}

	/*
	 * The thread responsible of listening for debug requests. On every debug
	 * request, a new thread of DebugConnectionThread is created and a debug
	 * session is initialized.
	 */
	private class ReceiverThread implements Runnable {
		public void run() {
			isInitialized = true;
			try {
				while (isAlive) {
					Socket socket = serverSocket.accept();
					socket.setReceiveBufferSize(1024 * 128);
					socket.setSendBufferSize(1024 * 128);
					startConnectionThread(socket);
				}
			} catch (IOException e) {
				synchronized (lock) {
					if (isAlive) {
						Logger.logException(
								"Error while listening to incoming debug requests. Listen thread terminated!", //$NON-NLS-1$
								e);
						isAlive = false;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.daemon.communication.ICommunicationDaemon
	 * #isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}
}
