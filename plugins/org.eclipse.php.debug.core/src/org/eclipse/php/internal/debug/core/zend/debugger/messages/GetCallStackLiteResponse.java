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
 * This class is like {@link GetCallStackResponse}, with only difference: it
 * doesn't contain function parameters.
 * 
 * @author michael
 * @deprecated
 */
public class GetCallStackLiteResponse extends DebugMessageResponseImpl
		implements IDebugResponseMessage {

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
			StackLayer layer = new StackLayer(i, CommunicationUtilities
					.readString(in), in.readInt(), CommunicationUtilities
					.readString(in), CommunicationUtilities.readString(in), in
					.readInt(), CommunicationUtilities.readString(in),
					getTransferEncoding());
			stack.addLayer(layer);
		}
		setPHPstack(stack);
	}

	public int getType() {
		return 1037;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
