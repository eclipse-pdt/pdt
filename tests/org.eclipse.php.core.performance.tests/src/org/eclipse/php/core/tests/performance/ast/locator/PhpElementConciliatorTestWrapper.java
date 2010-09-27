package org.eclipse.php.core.tests.performance.ast.locator;

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
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.performance.PHPCorePerformanceTests;
import org.eclipse.php.core.tests.performance.PerformanceMonitor;
import org.eclipse.php.core.tests.performance.PerformanceMonitor.Operation;
import org.eclipse.php.core.tests.performance.ProjectSuite;
import org.eclipse.php.core.tests.performance.Util;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;

public class PhpElementConciliatorTestWrapper extends AbstractPDTTTest {
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/locator/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		// "/workspace/project/locator/php5",
				"/workspace/project/locator/php53" });
	};

	protected IFile testFile;
	protected IProject project;

	protected static final char OFFSET_CHAR = '|';
	private PerformanceMonitor perfMonitor;

	public PhpElementConciliatorTestWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Locator Tests");

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
					PhpElementConciliatorTest test = new PhpElementConciliatorTest(
							fileName) {
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

		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}
		offset = data.lastIndexOf(OFFSET_CHAR);
		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);

		testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_ONE, null);
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		final Program astRoot = Util.createProgramFromSource(testFile);

		assertNotNull(astRoot);
		// ASTNode selectedNode = NodeFinder.perform(astRoot, offset, 0);
		final int finalOffset = offset;
		perfMonitor.execute("PerformanceTests.testPhpElementConciliator" + "_"
				+ fileName, new Operation() {
			public void run() throws Exception {
				NodeFinder.perform(astRoot, finalOffset, 0);
			}
		}, 1, 10);
	}

	public class PhpElementConciliatorTest extends AbstractPDTTTest {

		public PhpElementConciliatorTest(String description) {
			super(description);
		}
	}
}
