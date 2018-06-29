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
public class ContinueProcessFileNotification extends DebugMessageNotificationImpl implements IDebugNotificationMessage {

	@Override
	public void deserialize(DataInputStream in) throws IOException {
	}

	@Override
	public int getType() {
		return 2010;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
	}
}
