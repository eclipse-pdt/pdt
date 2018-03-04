/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * Get profiler call trace request message.
 */
public class GetProfilerCallTraceRequest extends DebugMessageRequestImpl implements IDebugRequestMessage {

	@Override
	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
	}

	@Override
	public int getType() {
		return 10013;
	}

	@Override
	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
	}
}