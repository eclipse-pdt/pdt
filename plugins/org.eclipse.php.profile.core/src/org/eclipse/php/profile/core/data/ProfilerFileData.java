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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;

/**
 * Profiler file data.
 */
public class ProfilerFileData {
	private String fName;
	private String fLocalName;
	private int fFunctionsCount;
	private double fTotalOwnTime = 0f;
	private List<ProfilerFunctionData> fFunctions = new ArrayList<>();
	private Map<String, ProfilerClassData> fClasses = new HashMap<>();
	private CodeCoverageData fCodeCoverageData;

	public ProfilerFileData() {
	}

	public ProfilerFileData(String name) {
		this.setName(name);
	}

	public ProfilerFileData(String fileName, String localFileName, int functionsCount, double totalOwnTime,
			List<ProfilerFunctionData> functions) {
		this.setName(fileName);
		fLocalName = localFileName;
		fFunctionsCount = functionsCount;
		fTotalOwnTime = totalOwnTime;
		fFunctions = functions;
		for (int i = 0; i < functions.size(); ++i) {
			addClass(functions.get(i));
		}
	}

	/**
	 * Sets the file name
	 */
	public void setName(String name) {
		this.fName = name;
	}

	/**
	 * Returns file name
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Sets local file name
	 * 
	 * @param String
	 *            file name
	 */
	public void setLocalName(String name) {
		this.fLocalName = name;
	}

	/**
	 * Returns local file name
	 * 
	 * @return String file name
	 */
	public String getLocalName() {
		return this.fLocalName;
	}

	/**
	 * Sets the functions count
	 */
	public void setFunctionsCount(int functionsCount) {
		this.fFunctionsCount = functionsCount;
	}

	/**
	 * Returns functions count
	 */
	public int getFunctionsCount() {
		return fFunctionsCount;
	}

	/**
	 * add function to functions list
	 * 
	 * @param function
	 */
	public void addFunction(ProfilerFunctionData function) {
		fFunctions.add(function);
		fTotalOwnTime += function.getOwnTime();
		addClass(function);
	}

	/**
	 * Returns the file functions
	 * 
	 * @return function data
	 */
	public ProfilerFunctionData[] getFunctions() {
		ProfilerFunctionData[] pfd = new ProfilerFunctionData[fFunctions.size()];
		fFunctions.toArray(pfd);
		return pfd;
	}

	/**
	 * Returns the file classes
	 * 
	 * @return class data
	 */
	public ProfilerClassData[] getClasses() {
		ProfilerClassData[] pcd = new ProfilerClassData[fClasses.size()];
		fClasses.values().toArray(pcd);
		return pcd;
	}

	public double getTotalOwnTime() {
		return fTotalOwnTime;
	}

	public double getTotalOwnTimeInMilli() {
		return fTotalOwnTime * 1000;
	}

	@Override
	public String toString() {
		return this.fName;
	}

	public void setCodeCoverageData(CodeCoverageData codeCoverageData) {
		fCodeCoverageData = codeCoverageData;
	}

	public CodeCoverageData getCodeCoverageData() {
		return fCodeCoverageData;
	}

	/**
	 * Add class data
	 */
	private void addClass(ProfilerFunctionData function) {
		String className = function.getClassName();
		if (className != null) {
			if (fClasses.containsKey(className)) {
				ProfilerClassData classData = fClasses.get(className);
				classData.addMethod(function);
			} else {
				ProfilerClassData classData = new ProfilerClassData(className);
				classData.addMethod(function);
				fClasses.put(className, classData);
			}
		}
	}
}
