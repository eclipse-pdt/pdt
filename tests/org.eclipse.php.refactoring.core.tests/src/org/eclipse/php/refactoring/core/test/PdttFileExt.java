/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.core.tests.PdttFile;
import org.osgi.framework.Bundle;

public class PdttFileExt extends PdttFile {
	List<FileInfo> testFiles;
	List<FileInfo> expectedFiles;

	public List<FileInfo> getTestFiles() {
		if (testFiles == null) {
			testFiles = new ArrayList<FileInfo>();
		}
		return testFiles;
	}

	public List<FileInfo> getExpectedFiles() {
		if (expectedFiles == null) {
			expectedFiles = new ArrayList<FileInfo>();
		}
		return expectedFiles;
	}

	public PdttFileExt(Bundle testBundle, String fileName) throws Exception {
		super(testBundle, fileName);
	}

	public PdttFileExt(String fileName) throws Exception {
		super(fileName);
	}

	/**
	 * This callback is called while processing state section
	 * @param state
	 * @param line
	 * @throws Exception 
	 */
	@Override
	protected void onState(STATES state, String line) throws Exception {
		switch (state) {
			case FILE:
				onFileState(line);
				return;
			case EXPECT:
				onExpectState(line);
				return;
		}
		super.onState(state, line);
	}

	private void onExpectState(String line) {
		if (line.startsWith("FILENAME://")) {
			String fileName = line.substring(11);
			FileInfo info = new FileInfo(fileName);
			getExpectedFiles().add(info);
		} else {
			FileInfo currInfo = getExpectedFiles().get(expectedFiles.size() - 1);
			if(currInfo.getContents().length() !=0){
				currInfo.appendContents("\n");
			}
			currInfo.appendContents(line);
		}
	}

	private void onFileState(String line) {
		if (line.startsWith("FILENAME://")) {
			String fileName = line.substring(11);
			FileInfo info = new FileInfo(fileName);
			getTestFiles().add(info);
		} else {
			FileInfo currInfo = getTestFiles().get(testFiles.size() - 1);
			if(currInfo.getContents().length() !=0){
				currInfo.appendContents("\n");
			}
			currInfo.appendContents(line);
		}
	}
}
