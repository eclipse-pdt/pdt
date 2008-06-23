/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
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
		return errorIndex == 1 || errorIndex == 5 || errorIndex == 7 || errorIndex == 9;
	}

	public static boolean isError(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 0 || errorIndex == 2 || errorIndex == 4 || errorIndex == 6 || errorIndex == 8;
	}

	public static boolean isStrict(DebugError debugError) {
		int errorIndex = getErrorIndex(debugError.getCode());
		return errorIndex == 11;
	}

	private static String codeToName[] = new String[] { "Debug Error", "Debug Warning", "Parsing Error", "Notice", "Fatal Error", "Core Warning", "Compile Error", "Compile Warning", "User Error", "User Warning", "User Notice", "Debug Strict (PHP 5)" };

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
		fileName = "";
		lineNumber = -1;
		text = "";
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
	 * Returns the file display name.
	 * Note : This name should not be used as the source file path to open the file
	 *        Use getFullPathName() instead.
	 */
	public String getFileDisplayName() {
		IPath filePath = new Path(fileName);
		if (filePath.segmentCount() > 1 && filePath.segment(filePath.segmentCount() - 2).equals("Untitled_Documents")){
			return filePath.lastSegment();
		}
		return fileName;
	}
	
	/**
	 * Returns the full path of the file
	 * @return
	 */
	public String getFullPathName(){
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
			buffer.append(": ");
			buffer.append(getFileDisplayName());
			buffer.append(" line ");
			//            buffer.append(lineNumber + 1);
			buffer.append(lineNumber);
		}
		buffer.append(" - " + getErrorText());
		return buffer.toString();
	}

	public int getErrorTextLength() {
		return (" - " + getErrorText()).length();
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof DebugError) {
			DebugError other = (DebugError) obj;
			result = (code == other.code) && (fileName.equals(other.fileName)) && (lineNumber == other.lineNumber) && (text.equals(other.text));
		}
		return result;
	}

}