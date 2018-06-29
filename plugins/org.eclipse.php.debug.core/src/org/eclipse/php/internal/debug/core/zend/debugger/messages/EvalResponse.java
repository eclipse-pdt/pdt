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
/*
 * EvalResponse.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class EvalResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private String result;

	/**
	 * Sets the result.
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Returns the result
	 */
	public String getResult() {
		return result;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setStatus(in.readInt());
		setResult(CommunicationUtilities.readString(in));
	}

	@Override
	public int getType() {
		return 1031;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
		CommunicationUtilities.writeString(out, getResult());
	}
}