/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnection;

/**
 * @author michael
 * 
 */
public interface IRemoteDebugger extends Debugger, CommunicationClient, CommunicationAdministrator, IDebugFeatures {

	public DebugConnection getConnection();

	public IDebugHandler getDebugHandler();

	public byte[] getFileContent(String fileName);

	@Override
	public boolean go(GoResponseHandler responseHandler);

	@Override
	public boolean isActive();

	public void closeConnection();

	public void closeDebugSession();

	@Override
	public boolean stepOver(StepOverResponseHandler responseHandler);

	@Override
	public boolean stepInto(StepIntoResponseHandler responseHandler);

	public IDebugResponseMessage sendCustomRequest(IDebugRequestMessage request);

	/**
	 * @return current protocol ID that is used in this debug session
	 */
	public int getCurrentProtocolID();

}