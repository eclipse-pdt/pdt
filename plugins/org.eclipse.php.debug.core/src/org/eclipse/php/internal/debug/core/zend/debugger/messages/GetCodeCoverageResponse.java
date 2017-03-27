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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.communication.CommunicationUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;

/**
 * Get code coverage response.
 */
public class GetCodeCoverageResponse extends DebugMessageResponseImpl implements IDebugResponseMessage {

	private CodeCoverageData[] fCodeCoverageData;

	public CodeCoverageData[] getCodeCoverageData() {
		return fCodeCoverageData;
	}

	static protected boolean isDebugMode = System.getProperty("loggingDebug") != null; //$NON-NLS-1$

	public void deserialize(DataInputStream in) throws IOException {
		setID(in.readInt());
		int numFiles = in.readInt();
		fCodeCoverageData = new CodeCoverageData[numFiles];
		for (int i = 0; i < numFiles; ++i) {
			// reading file name:
			String fileName = CommunicationUtilities.readString(in);
			if (isDebugMode)
				System.out.println("Covered file: " + fileName); //$NON-NLS-1$

			// reading number of lines:
			int numberOfLines = in.readInt();
			if (isDebugMode)
				System.out.println("Number of lines (1): " + numberOfLines); //$NON-NLS-1$
			int numberOfBytes = numberOfLines / 8 + 1;
			if (isDebugMode)
				System.out.println("Number of bytes (1): " + numberOfBytes); //$NON-NLS-1$
			byte[] coverageBitmask = new byte[numberOfBytes];

			// reading covered bitmask:
			for (int j = 0; j < numberOfBytes; ++j) {
				coverageBitmask[j] = in.readByte();
			}

			fCodeCoverageData[i] = new CodeCoverageData(fileName, numberOfLines, coverageBitmask);

			// reading number of lines again:
			int numberOfLines2 = in.readInt();
			if (isDebugMode)
				System.out.println("Number of lines (2): " + numberOfLines2); //$NON-NLS-1$
			if (numberOfLines != numberOfLines2)
				throw new IOException("Old format code coverage responce."); //$NON-NLS-1$
			numberOfBytes = numberOfLines / 8 + 1;
			if (isDebugMode)
				System.out.println("Number of bytes (2): " + numberOfBytes); //$NON-NLS-1$
			byte[] significanceBitmask = new byte[numberOfBytes];

			// reading significant bitmask:
			for (int j = 0; j < numberOfBytes; ++j) {
				significanceBitmask[j] = in.readByte();
			}
			fCodeCoverageData[i].setSignificanceBitmask(significanceBitmask);

			// reading number of php lines:
			int numberOfPhpLines = in.readInt();
			if (isDebugMode)
				System.out.println("Number of php lines: " + numberOfPhpLines); //$NON-NLS-1$
			fCodeCoverageData[i].setPhpLinesNum(numberOfPhpLines);
		}
	}

	public int getType() {
		return 11014;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(getType());
		out.writeInt(getID());
		out.writeInt(getStatus());
	}
}
