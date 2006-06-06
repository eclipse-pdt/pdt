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
package org.eclipse.php.debug.daemon.communication;


/**
 * A communication daemon interface.
 */
public interface ICommunicationDaemon {
	
	/**
	 * Initializes this communication daemon
	 */
	public void init();
	
	/**
	 * Starts the listening thread for any incoming debug requests or responces.
	 */
	public void startListen();
	
	/**
	 * Stops the listening thread. 
	 * Any incoming request will not be treated.
	 */
	public void stopListen();

	/**
	 * Initialize a listen socket for debug requests. 
	 */
	public void resetSocket();
	
	/**
	 * Handle an error of Multiple Bindings, which means that the two daemon instances
	 * are trying to connect on the same port.
	 */
	public void handleMultipleBindingError();
	
	/**
	 * Checks whether this communication daemon is enabled
	 * @return boolean <code>true</code> if it's enabled, <code>false</code> otherwise.
	 */
	public boolean isEnabled();
}
