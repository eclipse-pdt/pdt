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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;

public class CommunicationUtilities {

	public static void writeString(DataOutputStream out, String line)
			throws IOException {
		byte[] byteArray = line.getBytes();
		out.writeInt(byteArray.length);
		out.write(byteArray);
	}

	public static void writeStringAsBytes(DataOutputStream out, byte[] byteArray)
			throws IOException {
		out.writeInt(byteArray.length);
		out.write(byteArray);
	}

	public static void writeBreakpoint(DataOutputStream out,
			Breakpoint breakpoint) throws IOException {
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

	public static String readEncodedString(DataInputStream in, String encoding)
			throws IOException {
		byte[] byteArray = readStringAsBytes(in);
		String rv = getTextFromBytes(byteArray, encoding);
		return rv;
	}

	public static void writeEncodedString(DataOutputStream out, String line,
			String encoding) throws IOException {
		byte[] byteArray = getBytesFromText(line, encoding);
		out.writeInt(byteArray.length);
		out.write(byteArray);
	}

	public static final byte[] getBytesFromText(String text, String encoding) {
		try {
			return text.getBytes(encoding);
		} catch (Exception e) {
		}
		return text.getBytes();
	}

	public static final String getTextFromBytes(byte[] theBytes, String encoding) {
		try {
			return new String(theBytes, encoding);
		} catch (Exception e) {
		}
		return new String(theBytes);
	}

	public static byte[] readStringAsBytes(DataInputStream in)
			throws IOException {
		int size = in.readInt();
		byte[] byteArray = new byte[size];
		in.readFully(byteArray);
		return byteArray;
	}

	public static Breakpoint readBreakpoint(DataInputStream in)
			throws IOException {
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
