/*******************************************************************************
 * Copyright (c) 2009, 2014, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.phpmodelutils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class PHPModelUtilsTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();

	static {
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/phpmodelutils/php55" });
	};

	protected IProject project;
	protected IFile testFile = null;
	protected PHPVersion version;

	public PHPModelUtilsTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		this.project = TestUtils.createProject("PHPModelUtils_" + this.version.toString());
		TestUtils.setProjectPHPVersion(this.project, this.version);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(this.project);
	}

	@Test
	public void test(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		String methodName = pdttFile.getConfig().get("method");
		if (methodName == null) {
			throw new IllegalArgumentException("Missing method name in file " + fileName);
		}
		PreparedFile prepareFile = this.prepareFile(pdttFile.getFile());
		String actual;
		switch (methodName) {
		case "getFullName":
			actual = this.processGetFullName(pdttFile, prepareFile);
			break;
		default:
			throw new IllegalArgumentException("Unrecognized method name " + methodName + " in file " + fileName);
		}
		PDTTUtils.assertContents(pdttFile.getExpected(), actual);
	}

	@After
	public void after() throws Exception {
		if (this.testFile != null) {
			TestUtils.deleteFile(this.testFile);
			this.testFile = null;
		}
	}

	private class Placeholder {
		public final String value;
		public final int offset;

		public Placeholder(String value, int offset) {
			this.value = value;
			this.offset = offset;
		}
	}

	private class PreparedFile {
		public final List<Placeholder> placeholders;
		public final ISourceModule sourceModule;

		public PreparedFile(List<Placeholder> placeholders, ISourceModule sourceModule) {
			this.placeholders = placeholders;
			this.sourceModule = sourceModule;
		}
	}

	protected PreparedFile prepareFile(String originalPHPCode) throws Exception {
		List<Placeholder> placeholders = new ArrayList<Placeholder>();
		StringBuilder finalPHPCode = new StringBuilder();
		int offset = 0;
		for (;;) {
			int placeholderStart = originalPHPCode.indexOf(">>", offset);
			if (placeholderStart < 0) {
				break;
			}
			int placeholderEnd = originalPHPCode.indexOf("<<", placeholderStart);
			if (placeholderEnd < 0) {
				break;
			}
			finalPHPCode.append(originalPHPCode.subSequence(offset, placeholderStart));
			String placeholderValue = originalPHPCode.substring(placeholderStart + 2, placeholderEnd);
			placeholders.add(new Placeholder(placeholderValue, finalPHPCode.length()));
			finalPHPCode.append(placeholderValue);
			offset = placeholderEnd + 2;
		}
		finalPHPCode.append(originalPHPCode.substring(offset));
		this.testFile = TestUtils.createFile(this.project, "test.php", finalPHPCode.toString());
		TestUtils.waitForIndexer();
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(this.testFile);
		return new PreparedFile(placeholders, sourceModule);
	}

	private String processGetFullName(PdttFile pdttFile, PreparedFile preparedFile) {
		StringBuilder actual = new StringBuilder();
		for (Placeholder placeholder : preparedFile.placeholders) {
			actual.append(PHPModelUtils.getFullName(placeholder.value, preparedFile.sourceModule, placeholder.offset))
					.append("\n");
		}

		return actual.toString();
	}
}