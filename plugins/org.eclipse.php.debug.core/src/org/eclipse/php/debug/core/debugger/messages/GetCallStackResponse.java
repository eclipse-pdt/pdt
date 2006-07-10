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
/*
 * GetCallStackResponse.java
 *
 * Created on 22 מרץ 2001, 20:07
 */

package org.eclipse.php.debug.core.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.communication.CommunicationUtilities;
import org.eclipse.php.debug.core.debugger.PHPstack;
import org.eclipse.php.debug.core.debugger.StackLayer;

/**
 * @author guy
 */
public class GetCallStackResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private PHPstack stack;

	/**
	 * Sets the PHPstack.
	 */
	public void setPHPstack(PHPstack stack) {
		this.stack = stack;
	}

	/**
	 * Returns the PHPstack .
	 */
	public PHPstack getPHPstack() {
		return stack;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		PHPstack stack = new PHPstack();
		int depth = in.readInt();
		for (int i = 0; i < depth; i++) {
			StackLayer layer = new StackLayer(i, CommunicationUtilities.readString(in), in.readInt(), CommunicationUtilities.readString(in), CommunicationUtilities.readString(in), in.readInt(), CommunicationUtilities.readString(in), getTransferEncoding());
			int params = in.readInt();
			for (int j = 0; j < params; j++) {
				layer.addVariable(CommunicationUtilities.readEncodedString(in, getTransferEncoding()), CommunicationUtilities.readEncodedString(in, getTransferEncoding()));
			}
			stack.addLayer(layer);
		}
		setPHPstack(stack);
	}

	public int getType() {
		return 1034;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
