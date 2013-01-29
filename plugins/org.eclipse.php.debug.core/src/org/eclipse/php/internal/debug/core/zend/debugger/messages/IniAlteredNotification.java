/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;

/**
 * This message is sent by Debugger when php.ini is altered.
 * 
 * @author Wojciech Galanciak, 2012
 */
public class IniAlteredNotification extends DebugMessageNotificationImpl
		implements IDebugNotificationMessage {

	private String name;
	private String oldValue;
	private String newValue;

	public void deserialize(DataInputStream in) throws IOException {
		setName(CommunicationUtilities.readString(in));
		setOldValue(CommunicationUtilities.readString(in));
		setNewValue(CommunicationUtilities.readString(in));
	}

	public int getType() {
		return 2011;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		CommunicationUtilities.writeString(out, getName());
		CommunicationUtilities.writeString(out, getOldValue());
		CommunicationUtilities.writeString(out, getNewValue());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
