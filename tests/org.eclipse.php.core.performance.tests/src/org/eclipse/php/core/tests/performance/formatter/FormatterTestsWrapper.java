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
package org.eclipse.php.core.tests.performance.formatter;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.format.PhpFormatProcessorImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class FormatterTestsWrapper extends AbstractPDTTTest {
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/formatter/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		// "/workspace/project/formatter/php5",
				"/workspace/project/formatter/php53" });
	};

	protected IFile testFile;
	protected IProject project;

	protected static final char OFFSET_CHAR = '|';
	private PerformanceMonitor perfMonitor;

	public FormatterTestsWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Formatter Tests");

		final PHPVersion phpVersion = (PHPVersion) map
				.get(ProjectSuite.PHP_VERSION);
		for (String testsDirectory : TESTS.get(phpVersion)) {
			testsDirectory = testsDirectory.replaceAll("project", map.get(
					ProjectSuite.PROJECT).toString());
			for (final String fileName : getPDTTFiles(testsDirectory,
					PHPCorePerformanceTests.getDefault().getBundle())) {
				try {
					final PdttFile pdttFile = new PdttFile(
							PHPCorePerformanceTests.getDefault().getBundle(),
							fileName);
					FormatterTests test = new FormatterTests(fileName) {
						protected void tearDown() throws Exception {
							if (testFile != null) {
								testFile.delete(true, null);
								testFile = null;
							}
						}

						protected void runTest() throws Throwable {

							executeLocator(pdttFile.getFile(), fileName);
						}
					};
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
	 * Creates test file with the specified content and calculates the offset at
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data
	 *            File data
	 * @param fileName
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected void executeLocator(String data, final String fileName)
			throws Exception {
		testFile = createFile(data.trim());
		IStructuredModel modelForEdit = StructuredModelManager
				.getModelManager().getModelForEdit(testFile);
		try {
			final IDocument document = modelForEdit.getStructuredDocument();
			String beforeFormat = document.get();

			final PhpFormatProcessorImpl formatter = new PhpFormatProcessorImpl();

			perfMonitor.execute("PerformanceTests.testFormatter" + "_"
					+ fileName, new Operation() {
				public void run() throws Exception {
					formatter.formatDocument(document, 0, document.getLength());
				}
			}, 1, 10);

			// change the document text as was before
			// the formatting
			document.set(beforeFormat);
			modelForEdit.save();
		} finally {
			if (modelForEdit != null) {
				modelForEdit.releaseFromEdit();
			}
		}
	}

	protected IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}

	public class FormatterTests extends AbstractPDTTTest {

		public FormatterTests(String description) {
			super(description);
		}

	}
}
