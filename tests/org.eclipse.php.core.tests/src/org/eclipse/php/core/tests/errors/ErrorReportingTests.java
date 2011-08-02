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
package org.eclipse.php.core.tests.errors;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;

public class ErrorReportingTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/errors/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/errors/php53" });
	};

	protected static IProject project;
	protected static IFile testFile;
	protected static int count;

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("ErrorReportingTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
	}

	public static void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public ErrorReportingTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Error Reporting Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);

						phpVerSuite.addTest(new ErrorReportingTests("/"
								+ fileName) {

							protected void setUp() throws Exception {
								PHPCoreTests.setProjectPhpVersion(project,
										phpVersion);
								project.build(
										IncrementalProjectBuilder.FULL_BUILD,
										null);

								PHPCoreTests.waitForIndexer();
								PHPCoreTests.waitForAutoBuild();
							}

							protected void tearDown() throws Exception {
								if (testFile != null) {
									try {
										testFile.delete(true, null);
									} catch (Exception e) {
									}
									testFile = null;
								}
							}

							protected void runTest() throws Throwable {
								IFile file = createFile(pdttFile.getFile());

								StringBuilder buf = new StringBuilder();

								IMarker[] markers = file.findMarkers(
										DefaultProblem.MARKER_TYPE_PROBLEM,
										true, IResource.DEPTH_ZERO);
								for (IMarker marker : markers) {
									buf.append("\n[line=");
									buf.append(marker
											.getAttribute(IMarker.LINE_NUMBER));
									buf.append(", start=");
									buf.append(marker
											.getAttribute(IMarker.CHAR_START));
									buf.append(", end=");
									buf.append(marker
											.getAttribute(IMarker.CHAR_END));
									buf.append("] ");
									buf.append(
											marker.getAttribute(IMarker.MESSAGE))
											.append('\n');
								}
								assertContents(pdttFile.getExpected(),
										buf.toString());
							}
						});
					} catch (final Exception e) {
						phpVerSuite.addTest(new TestCase(fileName) { // dummy
																		// test
									// indicating
									// PDTT
									// file parsing
									// failure
									protected void runTest() throws Throwable {
										throw e;
									}
								});
					}
				}
			}
			suite.addTest(phpVerSuite);
		}

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				setUpSuite();
			}

			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};

		return setup;
	}

	protected static IFile createFile(String data) throws Exception {

		testFile = project.getFile("ErrorReportingTests_" + (++count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
		return testFile;
	}
}
