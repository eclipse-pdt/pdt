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
/*
 * DebugError.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * @author guy
 */
public class DebugError implements Cloneable {

	public static final int DEFAULT_ID = -1;

	private int id;
	private int code;
	private String fileName;
	private int lineNumber;
	private String text;

	public static boolean isNotice(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 3 || errorIndex == 10;
	}

	public static boolean isWarning(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 1 || errorIndex == 5 || errorIndex == 7
				|| errorIndex == 9;
	}

	public static boolean isError(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 0 || errorIndex == 2 || errorIndex == 4
				|| errorIndex == 6 || errorIndex == 8;
	}

	public static boolean isStrict(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 11;
	}

	private static String codeToName[] = new String[] { "Debug Error", //$NON-NLS-1$
			"Debug Warning", "Parsing Error", "Notice", "Fatal Error", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"Core Warning", "Compile Error", "Compile Warning", "User Error", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"User Warning", "User Notice", "Debug Strict (PHP 5)" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static int getErrorIndex(int code) {
		int index = (int) (Math.log(code) / Math.log(2));
		return index;
	}

	private static String getErrorName(int code) {
		int index = getErrorIndex(code);
		if (index < codeToName.length) {
			return codeToName[index];
		}
		return codeToName[3]; // In this case, display a "Notice" by default.
	}

	/**
	 * Creates new DebugError
	 */
	public DebugError() {
		id = -1;
		code = 1;
		fileName = ""; //$NON-NLS-1$
		lineNumber = -1;
		text = ""; //$NON-NLS-1$
	}

	public DebugError(int code, String fileName, int lineNumber, String text) {
		this();
		this.code = code;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.text = text;
	}

	/**
	 * Sets the DebugError id.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Returns the DebugError id.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the DebugError code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Returns the DebugError code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns the DebugError code name.
	 */
	public String getCodeName() {
		return getErrorName(code);
	}

	/**
	 * Sets the file name.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the file display name. Note : This name should not be used as the
	 * source file path to open the file Use getFullPathName() instead.
	 */
	public String getFileDisplayName() {
		IPath filePath = new Path(fileName);
		if (filePath.segmentCount() > 1
				&& filePath.segment(filePath.segmentCount() - 2).equals(
						"Untitled_Documents")) { //$NON-NLS-1$
			return filePath.lastSegment();
		}
		return fileName;
	}

	/**
	 * Returns the full path of the file
	 * 
	 * @return
	 */
	public String getFullPathName() {
		return fileName;
	}

	/**
	 * Sets the DebugError line number .
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * Returns the DebugError line number.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * Sets the DebugError text.
	 */
	public void setErrorText(String text) {
		this.text = text;
	}

	/**
	 * Returns the DebugError text.
	 */
	public String getErrorText() {
		return text;
	}

	public Object clone() throws CloneNotSupportedException {
		return (DebugError) super.clone();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getCodeName());
		if (lineNumber >= 0) {
			buffer.append(": "); //$NON-NLS-1$
			buffer.append(getFileDisplayName());
			buffer.append(" line "); //$NON-NLS-1$
			// buffer.append(lineNumber + 1);
			buffer.append(lineNumber);
		}
		buffer.append(" - " + getErrorText()); //$NON-NLS-1$
		return buffer.toString();
	}

	public int getErrorTextLength() {
		return (" - " + getErrorText()).length(); //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DebugError other = (DebugError) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}