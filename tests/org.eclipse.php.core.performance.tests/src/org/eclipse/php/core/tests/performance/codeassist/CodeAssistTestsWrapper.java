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
package org.eclipse.php.core.tests.performance.codeassist;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;

public class CodeAssistTestsWrapper extends AbstractPDTTTest {

	protected static final char OFFSET_CHAR = '|';
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] {
				"/workspace/project/codeassist/php5/exclusive",
				"/workspace/project/codeassist/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		/* "/workspace/project/codeassist/php5", */
		"/workspace/project/codeassist/php53" });
	};

	protected IProject project;
	protected IFile testFile;
	private PerformanceMonitor perfMonitor;

	public static void tearDownSuite() throws Exception {
	}

	public CodeAssistTestsWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Auto Code Assist Tests");

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
					CodeAssistTests test = new CodeAssistTests(fileName) {

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
									"PerformanceTests.testCodeAssist" + "_"
											+ fileName, new Operation() {
										public void run() throws Exception {
											CompletionProposal[] proposals = getProposals(pdttFile
													.getFile());
										}
									}, 1, 10);
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
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected int createFile(String data) throws Exception {
		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}

		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);

		testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_ONE, null);
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);

		PHPCoreTests.waitForIndexer();

		return offset;
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	public CompletionProposal[] getProposals(String data) throws Exception {
		int offset = createFile(data);
		return getProposals(offset);
	}

	public CompletionProposal[] getProposals(int offset) throws ModelException {
		return getProposals(getSourceModule(), offset);
	}

	public static CompletionProposal[] getProposals(ISourceModule sourceModule,
			int offset) throws ModelException {
		final List<CompletionProposal> proposals = new LinkedList<CompletionProposal>();
		sourceModule.codeComplete(offset, new CompletionRequestor() {
			public void accept(CompletionProposal proposal) {
				proposals.add(proposal);
			}
		});
		return (CompletionProposal[]) proposals
				.toArray(new CompletionProposal[proposals.size()]);
	}

	public class CodeAssistTests extends AbstractPDTTTest {

		public CodeAssistTests(String description) {
			super(description);
		}

	}
}
