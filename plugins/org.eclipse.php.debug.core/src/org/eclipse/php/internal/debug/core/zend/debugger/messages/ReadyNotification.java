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
 * ReadyNotification.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author guy
 */
public class ReadyNotification extends DebugMessageNotificationImpl implements
		IDebugNotificationMessage {

	private String fileName;
	private int lineNumber;

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

	/**
	 * Sets the line number.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * Returns the file name.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setFileName(CommunicationUtilities.readString(in));
		setLineNumber(in.readInt());
		in.readInt(); // read the 4 bytes of the watched-list length. this is 0
						// now.
	}

	public int getType() {
		return 2003;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		CommunicationUtilities.writeString(out, getFileName());
		out.writeInt(getLineNumber());
		out.writeInt(0);
	}
}