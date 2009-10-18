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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;

/**
 * This message is sent by the PDT to the Debugger after it has received
 * {@link StartProcessFileNotification}, and all needed preparations to the
 * proceeding with new file (like: breakpoints, path mapper, etc...) where done.
 * 
 * @author michael
 */
public class ContinueProcessFileNotification extends
		DebugMessageNotificationImpl implements IDebugNotificationMessage {

	public void deserialize(DataInputStream in) throws IOException {
	}

	public int getType() {
		return 2010;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
	}
}
