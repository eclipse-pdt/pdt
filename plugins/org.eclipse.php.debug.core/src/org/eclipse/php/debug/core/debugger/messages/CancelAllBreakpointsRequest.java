/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/*
 * CancelAllBreakpointsRequest.java
 *
 * Created on 14 מאי 2000, 19:03
 */

package org.eclipse.php.debug.core.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author guy
 */
public class CancelAllBreakpointsRequest extends DebugMessageRequestImpl implements IDebugRequestMessage {

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
	}

	public int getType() {
		return 23;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
	}
}