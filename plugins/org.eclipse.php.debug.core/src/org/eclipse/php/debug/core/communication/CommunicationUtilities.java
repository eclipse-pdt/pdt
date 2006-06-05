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
package org.eclipse.php.debug.core.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.Breakpoint;

public class CommunicationUtilities {

	public static void writeString(DataOutputStream out, String line) throws IOException {
		byte[] byteArray = line.getBytes();
		out.writeInt(byteArray.length);
		out.write(byteArray);
	}

	public static void writeBreakpoint(DataOutputStream out, Breakpoint breakpoint) throws IOException {
		out.writeShort(breakpoint.getType());
		out.writeShort(breakpoint.getLifeTime());
		if (breakpoint.getConditionalFlag()) {
			writeString(out, breakpoint.getExpression());
			if (!breakpoint.getStaticFlag()) {
				return;
			}
		}
		writeString(out, breakpoint.getFileName());
		if (breakpoint.getStaticFlag()) {
			int lineNumber = breakpoint.getLineNumber();
			out.writeInt(lineNumber);
		}
	}

	public static String readString(DataInputStream in) throws IOException {
		return new String(readStringAsBytes(in));
	}

	public static byte[] readStringAsBytes(DataInputStream in) throws IOException {
		int size = in.readInt();
		byte[] byteArray = new byte[size];
		in.readFully(byteArray);
		return byteArray;
	}

	public static Breakpoint readBreakpoint(DataInputStream in) throws IOException {
		Breakpoint breakPoint = new Breakpoint();
		breakPoint.setType(in.readShort());
		breakPoint.setLifeTime(in.readShort());
		if (breakPoint.getType() == Breakpoint.ZEND_CONDITIONAL_BREAKPOINT) {
			breakPoint.setExpression(readString(in));
		}
		breakPoint.setFileName(readString(in));
		if (breakPoint.getType() == Breakpoint.ZEND_STATIC_BREAKPOINT)
			breakPoint.setLineNumber(in.readInt());
		return breakPoint;
	}
}
