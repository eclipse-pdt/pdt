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
package org.eclipse.php.core.tests.performance.filenetwork;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;

public class FileNetworkTestsWrapper extends AbstractPDTTTest {
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/filenetwork/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		/* "/workspace/project/filenetwork/php5", */
		"/workspace/project/filenetwork/php53" });
	};

	protected IProject project;
	// protected IFile testFile;
	private PerformanceMonitor perfMonitor;

	public FileNetworkTestsWrapper() {
		super("");
	}

	protected static final String PROJECT = "filenetwork";
	protected IScriptProject SCRIPT_PROJECT;

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Auto File Network Tests");

		// final PHPVersion phpVersion = (PHPVersion) map
		// .get(ProjectSuite.PHP_VERSION);
		// for (String testsDirectory : TESTS.get(phpVersion)) {
		// testsDirectory = testsDirectory.replaceAll("project", map.get(
		// ProjectSuite.PROJECT).toString());
		// String[] fileNames = getFiles(testsDirectory,
		// PHPCorePerformanceTests.getDefault().getBundle(),
		// "properties");
		// if (fileNames.length > 0) {
		try {
			// Properties properties = new Properties();
			// InputStream inStream = new BufferedInputStream(
			// PHPCorePerformanceTests.getDefault().getBundle()
			// .getEntry(fileNames[0]).openStream());
			// properties.load(inStream);
			String[] fileNames = (String[]) map
					.get(ProjectSuite.REFERENCED_FILE);
			if (fileNames != null) {
				for (final String fileName : (String[]) map
						.get(ProjectSuite.REFERENCED_FILE)) {
					try {
						FileNetworkReferencedFilesTests test = new FileNetworkReferencedFilesTests(
								fileName) {

							protected void runTest() throws Throwable {
								testReferencedFiles(fileName);
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

			fileNames = (String[]) map.get(ProjectSuite.REFERENCING_FILE);
			if (fileNames != null) {
				for (final String fileName : (String[]) map
						.get(ProjectSuite.REFERENCING_FILE)) {
					try {
						FileNetworkReferencingFilesTests test = new FileNetworkReferencingFilesTests(
								fileName) {

							protected void runTest() throws Throwable {
								testReferencingFiles(fileName);
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

		} catch (final Exception e) {
		}
		// }
		// }
		return suite;
	}

	public void testReferencingFiles(String fileName) throws Exception {
		IFile file = project.getFile(fileName);
		final ISourceModule sourceModule = (ISourceModule) DLTKCore
				.create(file);
		perfMonitor.execute("PerformanceTests.testReferencingfiles" + "_"
				+ fileName, new Operation() {
			public void run() throws Exception {
				ReferenceTree tree = FileNetworkUtility
						.buildReferencingFilesTree(sourceModule, null);
				System.out.println(tree.toString());
			}
		}, 1, 10);
	}

	public void testReferencedFiles(String fileName) throws Exception {
		IFile file = project.getFile(fileName);
		final ISourceModule sourceModule = (ISourceModule) DLTKCore
				.create(file);
		perfMonitor.execute("PerformanceTests.testReferencedfiles" + "_"
				+ fileName, new Operation() {
			public void run() throws Exception {
				ReferenceTree tree = FileNetworkUtility
						.buildReferencedFilesTree(sourceModule, null);
				System.out.println(tree.toString());
			}
		}, 1, 10);
	}

	public class FileNetworkReferencedFilesTests extends AbstractPDTTTest {

		public FileNetworkReferencedFilesTests(String name) {
			super(name);
		}
	}

	public class FileNetworkReferencingFilesTests extends AbstractPDTTTest {

		public FileNetworkReferencingFilesTests(String name) {
			super(name);
		}
	}
}
