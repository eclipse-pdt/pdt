/*******************************************************************************
 * Copyright (c) 2012 Zend Technologies Ltd. 
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0  
 * 
 * Contributors:
 *      Zend Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public class AddFilesResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		setStatus(in.readInt());
	}

	@Override
	public int getType() {
		return 1038;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}

}