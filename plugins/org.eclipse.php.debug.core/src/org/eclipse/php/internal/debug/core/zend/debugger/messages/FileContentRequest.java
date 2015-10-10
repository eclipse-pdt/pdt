/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * Request a specific file.
 * 
 * @author guy
 */
public class FileContentRequest extends DebugMessageRequestImpl implements IDebugRequestMessage {

	protected String fileName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.debug.core.debugger.messages.IDebugMessage#getType()
	 */
	public int getType() {
		return 10001;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.debug.core.debugger.messages.IDebugMessage#serialize(java
	 * .io.DataOutputStream)
	 */
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		CommunicationUtilities.writeString(out, getFileName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.debug.core.debugger.messages.IDebugMessage#deserialize(
	 * java.io.DataInputStream)
	 */
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setFileName(CommunicationUtilities.readString(in));
	}

	/**
	 * Sets the file name.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the file name.
	 */
	public String getFileName() {
		return fileName;
	}

}