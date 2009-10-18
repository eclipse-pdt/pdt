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
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationAdministrator;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationClient;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnectionThread;

/**
 * @author michael
 * 
 */
public interface IRemoteDebugger extends Debugger, CommunicationClient,
		CommunicationAdministrator, IDebugFeatures {

	public DebugConnectionThread getConnectionThread();

	public IDebugHandler getDebugHandler();

	public boolean go(GoResponseHandler responseHandler);

	public boolean isActive();

	public void closeConnection();

	public void closeDebugSession();

	public boolean stepOver(StepOverResponseHandler responseHandler);

	public boolean stepInto(StepIntoResponseHandler responseHandler);

	public IDebugResponseMessage sendCustomRequest(IDebugRequestMessage request);

	/**
	 * @return current protocol ID that is used in this debug session
	 */
	public int getCurrentProtocolID();

}