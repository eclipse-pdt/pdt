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
package org.eclipse.php.core.tests.performance.selection;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.core.tests.performance.codeassist.CodeAssistPdttFile;
import org.eclipse.php.internal.core.PHPVersion;

public class SelectionEngineTestsWrapper extends AbstractPDTTTest {

	protected static final char SELECTION_CHAR = '|';
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/selection/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		/* "/workspace/project/selection/php5", */
		"/workspace/project/selection/php53" });
	};

	protected IProject project;
	protected IFile testFile;
	private PerformanceMonitor perfMonitor;

	public static void tearDownSuite() throws Exception {
	}

	public SelectionEngineTestsWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Auto Selection Engine Tests");

		final PHPVersion phpVersion = (PHPVersion) map
				.get(ProjectSuite.PHP_VERSION);
		for (String testsDirectory : TESTS.get(phpVersion)) {
			testsDirectory = testsDirectory.replaceAll("project", map.get(
					ProjectSuite.PROJECT).toString());
			for (final String fileName : getPDTTFiles(testsDirectory,
					PHPCorePerformanceTests.getDefault().getBundle())) {
				try {
					final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(
							PHPCorePerformanceTests.getDefault().getBundle(),
							fileName);
					SelectionEngineTests test = new SelectionEngineTests(
							fileName) {

						protected void setUp() throws Exception {
						}

						protected void tearDown() throws Exception {
							if (testFile != null) {
								testFile.delete(true, null);
								testFile = null;
							}
						}

						protected void runTest() throws Throwable {
							perfMonitor.execute(
									"PerformanceTests.testSelectionEngine"
											+ "_" + fileName, new Operation() {
										public void run() throws Exception {
											IModelElement[] elements = getSelection(pdttFile
													.getFile());
										}
									}, 1, 10);
						}
					};
					// test.project = project;
					// test.perfMonitor = perfMonitor;
					suite.addTest(test);
				} catch (final Exception e) {
					suite.addTest(new TestCase(fileName) { // dummy
								// test
								// indicating
								// PDTT
								// file
								// parsing
								// failure
								protected void runTest() throws Throwable {
									throw e;
								}
							});
				}
			}
		}
		return suite;
	}

	/**
	 * Creates test file with the specified content and calculates the source
	 * range for the selection. Selection characters themself are stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected SourceRange createFile(String data) throws Exception {
		int left = data.indexOf(SELECTION_CHAR);
		if (left == -1) {
			throw new IllegalArgumentException(
					"Selection characters are not set");
		}
		// replace the left character
		data = data.substring(0, left) + data.substring(left + 1);

		int right = data.indexOf(SELECTION_CHAR);
		if (right == -1) {
			throw new IllegalArgumentException("Selection is not closed");
		}
		data = data.substring(0, right) + data.substring(right + 1);

		testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_ONE, null);

		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
		PHPCoreTests.waitForIndexer();

		return new SourceRange(left, right - left);
	}

	protected IModelElement[] getSelection(String data) throws Exception {
		SourceRange range = createFile(data);
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		IModelElement[] elements = sourceModule.codeSelect(range.getOffset(),
				range.getLength());
		return elements;
	}

	// protected static ISourceModule getSourceModule() {
	// return DLTKCore.createSourceModuleFrom(testFile);
	// }

	public class SelectionEngineTests extends AbstractPDTTTest {

		public SelectionEngineTests(String description) {
			super(description);
		}

	}
}
