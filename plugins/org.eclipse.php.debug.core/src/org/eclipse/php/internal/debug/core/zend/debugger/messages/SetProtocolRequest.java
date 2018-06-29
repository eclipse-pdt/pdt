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

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;

public class SetProtocolRequest extends DebugMessageRequestImpl implements IDebugRequestMessage {

	private int fProtocolID;

	public void setProtocolID(int protocolID) {
		fProtocolID = protocolID;
	}

	public int getProtocolID() {
		return fProtocolID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.debugger.messages.IDebugMessage#
	 * deserialize(java.io.DataInputStream)
	 */
	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setProtocolID(in.readInt());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.messages.IDebugMessage#
	 * getType ()
	 */
	@Override
	public int getType() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.messages.IDebugMessage#
	 * serialize (java.io.DataOutputStream)
	 */
	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getProtocolID());
	}
}
