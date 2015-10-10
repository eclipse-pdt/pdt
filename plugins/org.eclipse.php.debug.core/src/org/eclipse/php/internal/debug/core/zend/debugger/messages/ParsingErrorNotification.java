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
 * ParsingErrorNotification.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * @author erez
 */
public class ParsingErrorNotification extends DebugMessageNotificationImpl implements IDebugNotificationMessage {

	private int errorLevel = 0;
	private String fileName;
	private int lineNumber;
	private String errorText;

	public int getErrorLevel() {
		return this.errorLevel;
	}

	public void setErrorLevel(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getErrorText() {
		return this.errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void deserialize(DataInputStream in) throws IOException {
		setErrorLevel(in.readInt());
		setFileName(CommunicationUtilities.readString(in));
		setLineNumber(in.readInt());
		setErrorText(CommunicationUtilities.readString(in));
	}

	public int getType() {
		return 2006;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getErrorLevel());
		CommunicationUtilities.writeString(out, getFileName());
		out.writeInt(getLineNumber());
		CommunicationUtilities.writeString(out, getErrorText());
	}
}