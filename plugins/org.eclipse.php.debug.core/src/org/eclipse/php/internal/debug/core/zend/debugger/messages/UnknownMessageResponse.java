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

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public class UnknownMessageResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	int fOrigMessageType;

	/**
	 * Returns original message type as received by profiler
	 * 
	 * @return int original message type
	 */
	public int getOriginalMessageType() {
		return fOrigMessageType;
	}

	private void setOriginalMessageType(int origMessageType) {
		fOrigMessageType = origMessageType;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setOriginalMessageType(in.readInt());
	}

	@Override
	public int getType() {
		return 1000;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getOriginalMessageType());
	}
}
