/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.test;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ComposerCoreTestPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.composer.tests";

	// The shared instance
	private static ComposerCoreTestPlugin plugin;

	/**
	 * The constructor
	 */
	public ComposerCoreTestPlugin() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PHPCorePlugin.toolkitInitialized = true;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ComposerCoreTestPlugin getDefault() {
		return plugin;
	}

	private static String getDiffError(String expected, String actual, int expectedDiff, int actualDiff) {
		StringBuilder errorBuf = new StringBuilder();
		errorBuf.append("\nEXPECTED:\n--------------\n");
		errorBuf.append(expected.substring(0, expectedDiff)).append("*****").append(expected.substring(expectedDiff));
		errorBuf.append("\n\nACTUAL:\n--------------\n");
		errorBuf.append(actual.substring(0, actualDiff)).append("*****").append(actual.substring(actualDiff));
		return errorBuf.toString();
	}

	/**
	 * Compares expected result with the actual.
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	public static String compareContents(String expected, String actual) {
		actual = actual.replaceAll("[\r\n]+", "\n").trim();
		expected = expected.replaceAll("[\r\n]+", "\n").trim();

		int expectedDiff = StringUtils.indexOfDifference(actual, expected);
		if (expectedDiff >= 0) {
			int actualDiff = StringUtils.indexOfDifference(expected, actual);
			return getDiffError(expected, actual, expectedDiff, actualDiff);
		}
		return null;
	}

	/**
	 * Compares expected result with the actual ingoring whitespace characters
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	public static String compareContentsIgnoreWhitespace(String expected, String actual) {
		String tmpExpected = expected;
		String tmpActual = actual;
		String diff = StringUtils.difference(tmpExpected, tmpActual);
		while (diff.length() > 0) {
			String diff2 = StringUtils.difference(tmpActual, tmpExpected);

			if (!Character.isWhitespace(diff.charAt(0)) && !Character.isWhitespace(diff2.charAt(0))) {
				int expectedDiff = StringUtils.indexOfDifference(tmpActual, tmpExpected)
						+ (expected.length() - tmpExpected.length());
				int actualDiff = StringUtils.indexOfDifference(tmpExpected, tmpActual)
						+ (actual.length() - tmpActual.length());
				return getDiffError(expected, actual, expectedDiff, actualDiff);
			}

			tmpActual = diff.trim();
			tmpExpected = diff2.trim();

			diff = StringUtils.difference(tmpExpected, tmpActual);
		}
		return null;
	}

	public static void waitForIndexer() {
		ModelManager.getModelManager().getIndexManager().waitUntilReady();
	}

	/**
	 * Wait for autobuild notification to occur, that is for the autbuild to
	 * finish.
	 */
	public static void waitForAutoBuild() {
		boolean wasInterrupted = false;
		do {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				wasInterrupted = false;
			} catch (OperationCanceledException e) {
				throw (e);
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}

	/**
	 * Set project PHP version
	 * 
	 * @param project
	 * @param phpVersion
	 * @throws CoreException
	 */
	public static void setProjectPhpVersion(IProject project, PHPVersion phpVersion) throws CoreException {
		if (phpVersion != ProjectOptions.getPHPVersion(project)) {
			ProjectOptions.setPHPVersion(phpVersion, project);
			waitForAutoBuild();
			waitForIndexer();
		}
	}
}
