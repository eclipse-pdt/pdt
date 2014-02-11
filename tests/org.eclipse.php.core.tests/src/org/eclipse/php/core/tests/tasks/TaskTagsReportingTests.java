/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - Task tags
 *******************************************************************************/
package org.eclipse.php.core.tests.tasks;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.project.PHPNature;

public class TaskTagsReportingTests extends AbstractPDTTTest {
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	protected static final String CFG_CASE_SENSITIVE = "caseSensitive";
	protected static final String CFG_TAGS = "tags";
	protected static final String CFG_PRIORITIES = "priorities";
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/tasks/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/tasks/php5" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/tasks/php5" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/tasks/php5" });
	};

	protected IProject project;
	protected PdttFile pdttFile;
	protected IFile file;
	protected static int count;
	protected PHPVersion phpVersion;

	public void setUp() throws Exception {
		super.setUp();
		project = ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.getProject(
						"TaskReportingTestSuite-" + phpVersion.getAlias() + "-"
								+ ++count);
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		Map<String, String> cfg = new HashMap<String, String>();
		cfg.put(CFG_CASE_SENSITIVE, PHPCoreConstants.ENABLED);
		cfg.put(CFG_TAGS, PHPCoreConstants.DEFAULT_TASK_TAGS);
		cfg.put(CFG_PRIORITIES, PHPCoreConstants.DEFAULT_TASK_PRIORITIES);

		if (pdttFile.getConfig() != null) {
			for (Entry<String, String> entry : pdttFile.getConfig().entrySet()) {
				cfg.put(entry.getKey(), entry.getValue());
			}
		}
		PreferencesSupport preferencesSupport = new PreferencesSupport(
				PHPCorePlugin.ID, PHPCorePlugin.getDefault()
						.getPluginPreferences());
		preferencesSupport.setProjectSpecificPreferencesValue(
				PHPCoreConstants.TASK_CASE_SENSITIVE,
				cfg.get(CFG_CASE_SENSITIVE), project);
		preferencesSupport.setProjectSpecificPreferencesValue(
				PHPCoreConstants.TASK_TAGS, cfg.get(CFG_TAGS), project);
		preferencesSupport.setProjectSpecificPreferencesValue(
				PHPCoreConstants.TASK_PRIORITIES, cfg.get(CFG_PRIORITIES),
				project);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		PHPCoreTests.setProjectPhpVersion(project, phpVersion);

		file = createFile(pdttFile.getFile().trim());
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public void tearDown() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public TaskTagsReportingTests(PHPVersion phpVersion, PdttFile file,
			String description) {
		super(description);
		this.pdttFile = file;
		this.phpVersion = phpVersion;
	}

	public static TestSuite suite() {

		TestSuite main = new TestSuite("PHP Task Tags tests");

		for (Entry<PHPVersion, String[]> entry : TESTS.entrySet()) {
			final PHPVersion phpVersion = entry.getKey();
			TestSuite suite = new TestSuite(entry.getKey().getAlias());
			for (String testsDirectory : entry.getValue()) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						suite.addTest(new TaskTagsReportingTests(phpVersion,
								pdttFile, "TaskTest" + " - " + fileName));
					} catch (final Exception e) {
						// dummy test indicating PDTT file parsing failure
						suite.addTest(new TestCase(fileName) {
							protected void runTest() throws Throwable {
								throw e;
							}
						});
					}
				}
			}

			main.addTest(suite);
		}

		return main;
	}

	protected IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("test" + (count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}

	protected void runTest() throws Throwable {

		StringBuilder buf = new StringBuilder();

		IMarker[] markers = file.findMarkers(PHPCoreConstants.PHP_MARKER_TYPE,
				true, IResource.DEPTH_ZERO);
		List<IMarker> sorted = new ArrayList<IMarker>(Arrays.asList(markers));
		Collections.sort(sorted, new Comparator<IMarker>() {
			public int compare(IMarker o1, IMarker o2) {
				try {
					return ((Integer) o1.getAttribute(IMarker.CHAR_START))
							.compareTo((Integer) o2
									.getAttribute(IMarker.CHAR_END));
				} catch (NumberFormatException e) {
				} catch (CoreException e) {
				}

				return 0;
			}
		});
		for (IMarker marker : sorted) {
			buf.append("\n[line=");
			buf.append(marker.getAttribute(IMarker.LINE_NUMBER));
			buf.append(", start=");
			buf.append(marker.getAttribute(IMarker.CHAR_START));
			buf.append(", end=");
			buf.append(marker.getAttribute(IMarker.CHAR_END));
			buf.append(", priority=");
			buf.append(marker.getAttribute(IMarker.PRIORITY));
			buf.append("] ");
			buf.append(marker.getAttribute(IMarker.MESSAGE)).append('\n');
		}

		assertContents(pdttFile.getExpected(), buf.toString());
	}
}
