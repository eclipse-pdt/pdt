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
/*
 * EvalRequest.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class EvalRequest extends DebugMessageRequestImpl implements
		IDebugRequestMessage {

	private String command;

	/**
	 * Sets the command.
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Returns the command.
	 */
	public String getCommand() {
		return command;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setCommand(CommunicationUtilities.readEncodedString(in,
				getTransferEncoding()));
	}

	public int getType() {
		return 31;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		CommunicationUtilities.writeEncodedString(out, getCommand(),
				getTransferEncoding());
	}
}