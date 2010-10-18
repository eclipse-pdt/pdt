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
package org.eclipse.php.core.tests.performance.markoccurrence;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.core.tests.performance.Util;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.search.IOccurrencesFinder;
import org.eclipse.php.internal.core.search.OccurrencesFinderFactory;

public class MarkOccurrenceTestsWrapper extends AbstractPDTTTest {

	// public static String PROJECT;
	protected static final char OFFSET_CHAR = '|';
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/markoccurrence/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		/* "/workspace/project/markoccurrence/php5", */
		"/workspace/project/markoccurrence/php53" });
	};

	protected IFile testFile;
	protected IProject project;
	private PerformanceMonitor perfMonitor;

	public MarkOccurrenceTestsWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Auto Mark Occurrence Tests");

		// for (final PHPVersion phpVersion : TESTS.keySet()) {
		// TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());
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
					MarkOccurrenceTests test = new MarkOccurrenceTests(
							phpVersion.getAlias() + " - /" + fileName) {

						protected void setUp() throws Exception {
							PHPCoreTests.setProjectPhpVersion(project,
									phpVersion);
							pdttFile.applyPreferences();
						}

						protected void tearDown() throws Exception {
							if (testFile != null) {
								testFile.delete(true, null);
								testFile = null;
							}
						}

						protected void runTest() throws Throwable {
							runMarkOccurrence(pdttFile.getFile(), fileName);
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
	protected void runMarkOccurrence(String data, final String fileName)
			throws Exception {

		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}
		List<Integer> starts = new ArrayList<Integer>();
		int startIndex = -1;
		while ((startIndex = data.indexOf('%', startIndex + 1)) >= 0) {
			starts.add(startIndex);
		}
		if (starts.size() % 2 != 0) {
			throw new IllegalArgumentException("% must be paired");
		}
		List<Integer> newStarts = new ArrayList<Integer>();
		for (int i = 0; i < starts.size(); i++) {
			int oldstart = starts.get(i) - i;
			if (oldstart > offset) {
				oldstart--;
			}
			newStarts.add(oldstart);
		}
		// replace the offset character
		data = data.replaceAll("%", "");

		offset = data.lastIndexOf(OFFSET_CHAR);
		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);

		testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_ONE, null);
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		Program astRoot = Util.createProgramFromSource(testFile);
		ASTNode selectedNode = NodeFinder.perform(astRoot, offset, 0);
		if (selectedNode != null
				&& (selectedNode instanceof Identifier || (isScalarButNotInString(selectedNode)))) {
			int type = PhpElementConciliator.concile(selectedNode);
			if (markOccurrencesOfType(type)) {
				final IOccurrencesFinder finder = OccurrencesFinderFactory
						.getOccurrencesFinder(type);
				if (finder != null) {
					if (finder.initialize(astRoot, selectedNode) == null) {
						perfMonitor.execute(
								"PerformanceTests.testMarkOccurrence" + "_"
										+ fileName, new Operation() {
									public void run() throws Exception {
										finder.getOccurrences();
									}
								}, 1, 10);
					}
				}
			}
		}
		// return locations;
	}

	/**
	 * Returns is the occurrences of the type should be marked.
	 * 
	 * @param type
	 *            One of the {@link PhpElementConciliator} constants integer
	 *            type.
	 * @return True, if the type occurrences should be marked; False, otherwise.
	 */
	public static boolean markOccurrencesOfType(int type) {
		switch (type) {
		case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
		case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
		case PhpElementConciliator.CONCILIATOR_FUNCTION:
		case PhpElementConciliator.CONCILIATOR_CLASSNAME:
		case PhpElementConciliator.CONCILIATOR_CONSTANT:
		case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
			return true;
		case PhpElementConciliator.CONCILIATOR_UNKNOWN:
		case PhpElementConciliator.CONCILIATOR_PROGRAM:
		default:
			return false;
		}
	}

	/**
	 * Checks whether or not the node is a scalar and return true only if the
	 * scalar is not part of a string
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isScalarButNotInString(ASTNode node) {
		return (node.getType() == ASTNode.SCALAR)
				&& (node.getParent().getType() != ASTNode.QUOTE);
	}

	public class MarkOccurrenceTests extends AbstractPDTTTest {
		public MarkOccurrenceTests(String description) {
			super(description);
		}
	}
	// protected static ISourceModule getSourceModule() {
	// return DLTKCore.createSourceModuleFrom(testFile);
	// }
}
