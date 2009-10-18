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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;

public class DebugScriptEndedNotification extends DebugMessageNotificationImpl
		implements IDebugNotificationMessage {

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

	public void deserialize(DataInputStream in) throws IOException {
		setStatus(in.readInt());
	}

	public int getType() {
		return 2002;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
	}
}
