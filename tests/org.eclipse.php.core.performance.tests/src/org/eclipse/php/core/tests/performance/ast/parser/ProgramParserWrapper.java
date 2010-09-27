package org.eclipse.php.core.tests.performance.ast.parser;

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

public class ProgramParserWrapper extends AbstractPDTTTest {
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/project/programparser/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
		// "/workspace/project/programparser/php5",
				"/workspace/project/programparser/php53" });
	};

	private IFile testFile;
	private IProject project;
	private PerformanceMonitor perfMonitor;

	public ProgramParserWrapper() {
		super("");
	}

	public Test suite(final Map map) {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				map.get(ProjectSuite.PROJECT).toString());
		perfMonitor = PHPCorePerformanceTests.getPerformanceMonitor();
		TestSuite suite = new TestSuite("Auto Program Parser Tests");
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
					ProgramParser test = new ProgramParser(phpVersion
							.getAlias()
							+ " - /" + fileName) {

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
							executeParser(pdttFile.getFile(), fileName);
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
	protected void executeParser(String data, final String fileName)
			throws Exception {

		testFile = project.getFile("pdttest/test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_ONE, null);
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		perfMonitor.execute("PerformanceTests.testProgramParser" + "_"
				+ fileName, new Operation() {
			public void run() throws Exception {
				Util.createProgramFromSource(testFile);
			}
		}, 1, 10);
	}

	public class ProgramParser extends AbstractPDTTTest {

		public ProgramParser(String description) {
			super(description);
		}

	}
}
