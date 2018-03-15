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

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.core.tests.filenetwork.FileUtil;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ComposerCoreTestPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.composer.tests"; //$NON-NLS-1$

	// The shared instance
	private static ComposerCoreTestPlugin plugin;

	/**
	 * The constructor
	 */
	public ComposerCoreTestPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PHPCorePlugin.toolkitInitialized = true;
		TestUtils.disableColliders(ColliderType.ALL);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		TestUtils.enableColliders(ColliderType.ALL);
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
		errorBuf.append("\nEXPECTED:\n--------------\n"); //$NON-NLS-1$
		errorBuf.append(expected.substring(0, expectedDiff)).append("*****").append(expected.substring(expectedDiff)); //$NON-NLS-1$
		errorBuf.append("\n\nACTUAL:\n--------------\n"); //$NON-NLS-1$
		errorBuf.append(actual.substring(0, actualDiff)).append("*****").append(actual.substring(actualDiff)); //$NON-NLS-1$
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
		actual = actual.replaceAll("[\r\n]+", "\n").trim(); //$NON-NLS-1$ //$NON-NLS-2$
		expected = expected.replaceAll("[\r\n]+", "\n").trim(); //$NON-NLS-1$ //$NON-NLS-2$

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

	public static void copyProjectFiles(IProject project) throws IOException {
		FileUtil.copyDirectory(new File(FileLocator
				.toFileURL(ComposerCoreTestPlugin.getDefault().getBundle().getEntry("/workspace/" + project.getName())) //$NON-NLS-1$
				.getFile()), new File(project.getLocationURI()));
	}

}
