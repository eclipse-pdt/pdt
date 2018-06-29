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
 * GetCallStackResponse.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.PHPstack;
import org.eclipse.php.internal.debug.core.zend.debugger.StackLayer;

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

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		PHPstack stack = new PHPstack();
		int depth = in.readInt();
		for (int i = 0; i < depth; i++) {
			StackLayer layer = new StackLayer(i, CommunicationUtilities.readString(in), in.readInt(),
					CommunicationUtilities.readString(in), CommunicationUtilities.readString(in), in.readInt(),
					CommunicationUtilities.readString(in), getTransferEncoding());
			int params = in.readInt();
			for (int j = 0; j < params; j++) {
				layer.addVariable(CommunicationUtilities.readEncodedString(in, getTransferEncoding()),
						CommunicationUtilities.readStringAsBytes(in));
			}
			stack.addLayer(layer);
		}
		setPHPstack(stack);
	}

	@Override
	public int getType() {
		return 1034;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
