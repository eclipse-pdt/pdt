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
package org.eclipse.php.debug.core.debugger;

import org.eclipse.php.debug.core.communication.CommunicationAdministrator;
import org.eclipse.php.debug.core.communication.CommunicationClient;
import org.eclipse.php.debug.core.communication.DebuggerCommunicationKit;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

/**
 * @author michael
 *
 */
public interface IRemoteDebugger extends Debugger, CommunicationClient, CommunicationAdministrator {
	
	public DebuggerCommunicationKit getCommunicationKit();
	
	public IDebugHandler getDebugHandler ();
	
	public boolean go(GoResponseHandler responseHandler);
	
	public boolean isActive();
    
    public void closeConnection();
	
	public void closeDebugSession();
	
	public boolean stepOver(StepOverResponseHandler responseHandler);
	
	public boolean stepInto(StepIntoResponseHandler responseHandler);

	public void openConnection(int port);
	
	public IDebugResponseMessage sendCustomRequest (IDebugRequestMessage request);

	public void setDebugPort(int requestPort);
}
