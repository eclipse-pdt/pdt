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
package org.eclipse.php.profile.core.data;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Profiler function data.
 */
public class ProfilerFunctionData {

	private static final Pattern EVALD_CODE_PATTERN = Pattern.compile(".*\\(\\d+\\) : eval\\(\\)'d code"); //$NON-NLS-1$

	private String fFileName;
	private String fLocalFileName;
	private String fAbsoluteFileName;
	private String fFunctionName;
	private String fClassName;
	private int fLineNumber;
	private int fID;
	private int fOwnTimeSeconds;
	private int fOwnTimeMicroseconds;
	private int fTotalTimeSeconds;
	private int fTotalTimeMicroseconds;
	private int fCallsCount;
	private double CONVERTION = 1000000.0;

	public ProfilerFunctionData() {
	}

	public ProfilerFunctionData(String fileName) {
		setFileName(fileName);
	}

	public ProfilerFunctionData(String fileName, String functionName, int lineNumber, int calledID, int ownSecond,
			int ownMicorsecond, int totalSecond, int totalMicorsecond, int callCount) {
		setFileName(fileName);
		setFunctionName(functionName);
		setLineNumber(lineNumber);
		setID(calledID);
		setOwnTimeSeconds(ownSecond);
		setOwnTimeMicroseconds(ownMicorsecond);
		setTotalTimeSeconds(totalSecond);
		setTotalTimeMicroseconds(totalMicorsecond);
		setCallsCount(callCount);
	}

	/**
	 * Sets the function name
	 */
	public void setFunctionName(String functionName) {
		if (EVALD_CODE_PATTERN.matcher(functionName).matches()) {
			fFunctionName = "eval()"; //$NON-NLS-1$
		} else {
			int i = functionName.indexOf("::"); //$NON-NLS-1$
			if (i != -1) {
				setClassName(functionName.substring(0, i));
				this.fFunctionName = functionName.substring(i + 2);
			} else {
				this.fFunctionName = functionName;
			}
		}
	}

	/**
	 * Sets the class name
	 */
	public void setClassName(String className) {
		this.fClassName = className;
	}

	/**
	 * Returns function name
	 */
	public String getFunctionName() {
		return fFunctionName;
	}

	/**
	 * Returns class name
	 */
	public String getClassName() {
		return fClassName;
	}

	/**
	 * Sets the file name
	 */
	public void setFileName(String fileName) {
		File f = new File(fileName);
		this.fFileName = f.getName();
		this.fAbsoluteFileName = fileName;
	}

	/**
	 * Returns file name
	 */
	public String getFileName() {
		return fFileName;
	}

	/**
	 * Sets local file name
	 * 
	 * @param String
	 *            file name
	 */
	public void setLocalFileName(String name) {
		this.fLocalFileName = name;
	}

	/**
	 * Returns local file name
	 * 
	 * @return String file name
	 */
	public String getLocalFileName() {
		return this.fLocalFileName;
	}

	/**
	 * Returns file name
	 */
	public String getAbsoluteFileName() {
		return fAbsoluteFileName;
	}

	/**
	 * Sets the function line number
	 */
	public void setLineNumber(int lineNumber) {
		this.fLineNumber = lineNumber;
	}

	/**
	 * Returns function line number
	 */
	public int getLineNumber() {
		return fLineNumber;
	}

	/**
	 * Sets the function id
	 */
	public void setID(int id) {
		this.fID = id;
		// System.out.println(fileName + "," + functionName+ "="+ id);
	}

	/**
	 * Returns function id
	 */
	public int getID() {
		return fID;
	}

	/**
	 * Sets own time - seconds
	 */
	public void setOwnTimeSeconds(int ownTimeSeconds) {
		this.fOwnTimeSeconds = ownTimeSeconds;
	}

	/**
	 * Returns own time - seconds
	 */
	public int getOwnTimeSeconds() {
		return fOwnTimeSeconds;
	}

	/**
	 * Sets own time - microseconds
	 */
	public void setOwnTimeMicroseconds(int ownTimeMicroseconds) {
		this.fOwnTimeMicroseconds = ownTimeMicroseconds;
	}

	/**
	 * Returns own time - microseconds
	 */
	public int getOwnTimeMicroseconds() {
		return fOwnTimeMicroseconds;
	}

	/**
	 * Sets the total time in seconds
	 */
	public void setTotalTimeSeconds(int totalTimeSeconds) {
		this.fTotalTimeSeconds = totalTimeSeconds;
	}

	/**
	 * Returns total time - seconds
	 */
	public int getTotalTimeSeconds() {
		return fTotalTimeSeconds;
	}

	/**
	 * Sets the total time - microseconds
	 */
	public void setTotalTimeMicroseconds(int totalTimeMicroseconds) {
		if (totalTimeMicroseconds == 0 && fTotalTimeSeconds == 0) { // some
			// problem
			// with the
			// data.
			// initial
			// with own
			// time
			fTotalTimeSeconds = fOwnTimeSeconds;
			this.fTotalTimeMicroseconds = fOwnTimeMicroseconds;
		} else {
			this.fTotalTimeMicroseconds = totalTimeMicroseconds;
		}
	}

	/**
	 * Returns total time - microseconds
	 */
	public int getTotalTimeMicroseconds() {
		return fTotalTimeMicroseconds;
	}

	/**
	 * Sets the calls count
	 */
	public void setCallsCount(int callsCount) {
		this.fCallsCount = callsCount;
	}

	/**
	 * Returns calls count
	 */
	public int getCallsCount() {
		return fCallsCount;
	}

	/**
	 * Gets the total time as calcuated from s and ms
	 * 
	 * @return the double value of the calculation
	 */
	public double getTotalTime() {
		double totalTime = (fTotalTimeSeconds * CONVERTION + fTotalTimeMicroseconds) / CONVERTION;
		return totalTime;

	}

	/**
	 * Gets the total time as calcuated from s and ms
	 * 
	 * @return the double value of the calculation
	 */
	public double getTotalTimeInMilli() {
		return getTotalTime() * 1000;

	}

	/**
	 * Gets the own time as calcuated from s and ms
	 * 
	 * @return the double value of the calculation in seconds
	 */
	public double getOwnTime() {
		double ownTime = (fOwnTimeSeconds * CONVERTION + fOwnTimeMicroseconds) / CONVERTION;
		return ownTime;
	}

	/**
	 * Gets the own time as calcuated from s and ms
	 * 
	 * @return the double value of the calculation in milliseconds
	 */
	public double getOwnTimeInMilli() {
		return getOwnTime() * 1000;
	}

	@Override
	public String toString() {
		if (fClassName != null) {
			return fClassName + "::" + fFunctionName; //$NON-NLS-1$
		}
		return fFunctionName;
	}

}
