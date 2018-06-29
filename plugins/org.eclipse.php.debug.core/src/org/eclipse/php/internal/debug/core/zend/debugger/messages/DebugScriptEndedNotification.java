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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;

public class DebugScriptEndedNotification extends DebugMessageNotificationImpl implements IDebugNotificationMessage {

	private int status;

	private int protocolID;

	/**
	 * Stets the server protocol id.
	 */
	public void setServerProtocol(int serverProtocolID) {
		this.protocolID = serverProtocolID;
	}

	/**
	 * Returns the server protocol ID.
	 */
	public int getServerProtocolID() {
		return protocolID;
	}

	/**
	 * Sets the status
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Returns the status
	 */
	public int getStatus() {
		return status;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setStatus(in.readInt());
	}

	@Override
	public int getType() {
		return 2002;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
	}
}
