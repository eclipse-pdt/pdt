/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugMessageRequestImpl;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;

/**
 * Get profiler file request message.
 */
public class GetProfilerFileRequest extends DebugMessageRequestImpl implements IDebugRequestMessage {

	public static final int TYPE = 42;

	private int fileNumber;

	/**
	 * Returns the file number
	 */
	public int getFileNumber() {
		return fileNumber;
	}

	/**
	 * Sets the file number
	 */
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
	}

	@Override
	public int getType() {
		return 10012;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getFileNumber());
	}
}
