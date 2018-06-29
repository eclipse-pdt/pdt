/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Profiler class data.
 */
public class ProfilerClassData {

	private List<ProfilerFunctionData> fMethods = new ArrayList<>();
	private String fName;
	private double totalOwnTime = 0f;

	public ProfilerClassData() {
	}

	public ProfilerClassData(String name) {
		fName = name;
	}

	/**
	 * Sets name of the class
	 * 
	 * @param name
	 *            of the class
	 */
	public void setName(String name) {
		fName = name;
	}

	/**
	 * Returns name of the class
	 * 
	 * @return name of the classd
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Adds method data to this class
	 * 
	 * @param method
	 *            data
	 */
	public void addMethod(ProfilerFunctionData method) {
		fMethods.add(method);
		totalOwnTime += method.getOwnTime();
	}

	/**
	 * Returns methods of this class
	 * 
	 * @return methods
	 */
	public ProfilerFunctionData[] getMethods() {
		ProfilerFunctionData[] mfd = new ProfilerFunctionData[fMethods.size()];
		fMethods.toArray(mfd);
		return mfd;
	}

	/**
	 * Returns sum of all methods own times
	 * 
	 * @return total own time
	 */
	public double getTotalOwnTime() {
		return totalOwnTime;
	}

	/**
	 * Returns sum of all methods own times in milliseconds
	 * 
	 * @return total own time
	 */
	public double getTotalOwnTimeInMilli() {
		return totalOwnTime * 1000;
	}

	@Override
	public String toString() {
		return fName;
	}
}
