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
package org.eclipse.php.debug.daemon.communication;

/**
 * A communication daemon interface.
 * 
 * @author Shalom Gibly
 */
public interface ICommunicationDaemon {

	/**
	 * Initializes this communication daemon
	 */
	public void init();

	/**
	 * Returns true if this daemon is listening for communication requests.
	 * 
	 * @return True, if the daemon is listening; False, otherwise.
	 */
	public boolean isListening();

	/**
	 * Starts the listening thread for any incoming debug requests or responces.
	 */
	public void startListen();

	/**
	 * Stops the listening thread. Any incoming request will not be treated.
	 */
	public void stopListen();

	/**
	 * Initialize a listen socket for debug requests.
	 * 
	 * @return True, if the reset did not yield any errors; False, otherwise.
	 * 
	 */
	public boolean resetSocket();

	/**
	 * Handle an error of Multiple Bindings, which means that the two daemon
	 * instances are trying to connect on the same port.
	 */
	public void handleMultipleBindingError();

	/**
	 * Checks whether this communication daemon is enabled
	 * 
	 * @return boolean <code>true</code> if it's enabled, <code>false</code>
	 *         otherwise.
	 */
	public boolean isEnabled();

	/**
	 * Returns the debugger ID that is using this communication daemon.
	 * 
	 * @return The debugger ID that is using this daemon.
	 * @since PDT 1.0
	 */
	public String getDebuggerID();

	/**
	 * Returns true if the daemon is a debugger daemon. This method should
	 * return true if the daemon instance is a debugger daemon listener. Not all
	 * of the registered daemons are necessarily debugger daemons, thus, this
	 * method is required to identify the ones that are debugger-related.
	 * 
	 * @return True, iff this daemon is a debugger daemon.
	 */
	public boolean isDebuggerDaemon();

	/**
	 * @since 2.2
	 */
	public boolean isInitialized();
}
