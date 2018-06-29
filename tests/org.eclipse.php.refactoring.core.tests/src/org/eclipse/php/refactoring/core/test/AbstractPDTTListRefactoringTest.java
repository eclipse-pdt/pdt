/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public abstract class AbstractPDTTListRefactoringTest extends AbstractRefactoringTest {

	protected String[] fileNames = null;
	protected final TestProject project;
	protected Map<String, PdttFileExt> filesMap = new LinkedHashMap<>();

	public AbstractPDTTListRefactoringTest(String[] fileNames) {
		project = createProject();
		this.fileNames = fileNames;
	}

	private TestProject createProject() {
		return new TestProject();
	}

	@PDTTList.BeforeList
	public void setUpListSuite() throws Exception {
		initFiles(fileNames);
	}

	@PDTTList.AfterList
	public void tearDownListSuite() throws Exception {
		project.delete();
	}

	protected void initFiles(String[] fileNames) throws Exception {
		for (final String fileName : fileNames) {
			final PdttFileExt pdttFile = new PdttFileExt(getBundle(), fileName);
			for (FileInfo testFile : pdttFile.getTestFiles()) {
				project.createFile(testFile.getName(), getContents(pdttFile, testFile));
			}
			filesMap.put(fileName, pdttFile);
		}
		TestUtils.waitForIndexer();
	}

	protected String getContents(PdttFileExt pdttFile, FileInfo testFile) {
		String data = testFile.getContents();
		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset < 0) {
			return data;
		}
		pdttFile.getConfig().put("start", String.valueOf(offset));
		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		return data;
	}

	protected void checkTestResult(PdttFileExt pdttFile) {
		TestUtils.waitForIndexer();
		List<FileInfo> files = pdttFile.getExpectedFiles();
		for (FileInfo expFile : files) {
			IFile file = project.findFile(expFile.getName());
			assertTrue(file.exists());
			try {
				PDTTUtils.assertContents(getContents(pdttFile, expFile), FileUtils.getContents(file));
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}

	@PDTTList.Context
	public static Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
