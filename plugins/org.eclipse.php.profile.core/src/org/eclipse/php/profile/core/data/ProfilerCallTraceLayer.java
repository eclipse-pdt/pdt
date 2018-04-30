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

/**
 * Profiler call trace layer.
 */
public class ProfilerCallTraceLayer {

	public static final int ENTER = 1; // enter the function
	public static final int EXIT = 0; // exit the function
	private static final int CONVERTION = 1000000;

	private int fType;
	private int fLineNumber;
	private int fCalledID;
	private int fTimestampSeconds;
	private int fTimestampMicroseconds;
	private int fDurationSecond = 0;
	private int fDurationMicrosecond = 0;

	public ProfilerCallTraceLayer() {
	}

	public ProfilerCallTraceLayer(int type, int lineNumber, int calledID, int timestampSeconds,
			int timestampMicroseconds) {
		setType(type);
		setLine(lineNumber);
		setCalledID(calledID);
		setTimestampSeconds(timestampSeconds);
		setTimestampMicroseconds(timestampMicroseconds);
	}

	public ProfilerCallTraceLayer(int type, int lineNumber, int calledID, int timestampSeconds,
			int timestampMicroseconds, int durationSeconds, int durationMicroSeconds) {
		setType(type);
		setLine(lineNumber);
		setCalledID(calledID);
		setTimestampSeconds(timestampSeconds);
		setTimestampMicroseconds(timestampMicroseconds);
		fDurationSecond = durationSeconds;
		fDurationMicrosecond = durationMicroSeconds;
	}

	/**
	 * Returns the type
	 */
	public int getType() {
		return fType;
	}

	/**
	 * Sets the type
	 */
	public void setType(int type) {
		this.fType = type;
	}

	/**
	 * Returns the line number
	 */
	public int getLineNumber() {
		return fLineNumber;
	}

	/**
	 * Returns the calledID
	 */
	public int getCalledID() {
		return fCalledID;
	}

	/**
	 * Returns the timestamp - seconds
	 */
	public int getTimestampSeconds() {
		return fTimestampSeconds;
	}

	/**
	 * Sets the timestamp - microseconds
	 */
	public void setTimestampSeconds(int timestampSeconds) {
		this.fTimestampSeconds = timestampSeconds;
	}

	/**
	 * Returns the timestamp - microseconds
	 */
	public int getTimestampMicroseconds() {
		return fTimestampMicroseconds;
	}

	/**
	 * Sets the timestamp - microseconds
	 */
	public void setTimestampMicroseconds(int timestampMicroseconds) {
		this.fTimestampMicroseconds = timestampMicroseconds;
	}

	/**
	 * get the duration: exit-enter = in seconds
	 */
	public int getDurationSeconds() {
		return fDurationSecond;
	}

	/**
	 * Gets the duration after calculating it with seconds and microseconds.
	 * 
	 * @return the double value of the calculation in seconds
	 */
	public double getDuration() {
		double duration = ((double) fDurationSecond * CONVERTION + fDurationMicrosecond) / CONVERTION;
		return duration;
	}

	/**
	 * Gets the duration after calculating it with seconds and microseconds.
	 * 
	 * @return the double value of the calculation in milliseconds
	 */
	public double getDurationInMilli() {
		return getDuration() * 1000;
	}

	/**
	 * Sets the duration in according to the timestamp(enter time) and the exit time
	 * 
	 * @param exitTimeSeconds
	 *                                 - the exit time from the function in seconds
	 * @param exitTimeMicroseconds
	 *                                 - the exit time from the function in
	 *                                 microseconds
	 */
	public void setDuration(long exitTimeSeconds, long exitTimeMicroseconds) {
		long startSecond = fTimestampSeconds;
		long startMSecond = fTimestampMicroseconds;
		long startTime = startMSecond + startSecond * CONVERTION;
		long endTime = exitTimeMicroseconds + exitTimeSeconds * CONVERTION;
		int duration = (int) (endTime - startTime);
		this.fDurationSecond = duration / CONVERTION;
		this.fDurationMicrosecond = duration % CONVERTION;
	}

	/**
	 * get the duration: exit-enter = in microseconds
	 */
	public int getDurationMicroeconds() {
		return fDurationMicrosecond;
	}

	/**
	 * Sets the line number
	 */
	public void setLine(int lineNumber) {
		this.fLineNumber = lineNumber;
	}

	/**
	 * Sets the calledID
	 */
	public void setCalledID(int calledID) {
		this.fCalledID = calledID;
	}
}
