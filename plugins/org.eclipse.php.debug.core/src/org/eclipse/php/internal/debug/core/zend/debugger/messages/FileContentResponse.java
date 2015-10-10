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

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * Specific file content response.
 * 
 * @author guy
 */
public class FileContentResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	public static final int FAILURE = -1;
	public static final int SUCCESS = 0;
	public static final int FILES_IDENTICAL = 302;

	private byte content[] = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.debug.core.debugger.messages.IDebugMessage#getType()
	 */
	public int getType() {
		return 11001;
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
		out.writeInt(getStatus());
		byte fileContent[] = getContent();
		int fileContentLength = 0;
		if (fileContent != null) {
			fileContentLength = fileContent.length;
		}
		out.writeInt(fileContentLength);
		if (fileContent != null) {
			out.write(fileContent); // this returns the content as bytes
		}
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
		setStatus(in.readInt());
		setContent(CommunicationUtilities.readStringAsBytes(in));
	}

	/**
	 * Sets content.
	 * 
	 * @param content
	 */
	public void setContent(byte content[]) {
		this.content = content;
	}

	/**
	 * Returns content.
	 * 
	 * @return content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Returns content as string.
	 * 
	 * @return content as string
	 */
	public String getContentAsString() {
		return content.toString();
	}

}