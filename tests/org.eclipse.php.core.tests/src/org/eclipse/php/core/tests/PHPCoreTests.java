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
package org.eclipse.php.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.core.PHPVersion;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PHPCoreTests extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.core.tests";

	// The shared instance
	private static PHPCoreTests plugin;

	/**
	 * The constructor
	 */
	public PHPCoreTests() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		PHPCorePlugin.toolkitInitialized = true;
		TestUtils.disableColliders(ColliderType.ALL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// Save workspace to avoid errors after finishing tests.
		try {
			ResourcesPlugin.getWorkspace().save(true, new NullProgressMonitor());
		} catch (CoreException e) {
			Logger.logException(e);
		}
		TestUtils.enableColliders(ColliderType.ALL);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PHPCoreTests getDefault() {
		return plugin;
	}

	/**
	 * @deprecated use {@link TestUtils#compareContents(String, String)}
	 *             instead.
	 * 
	 *             Compares expected result with the actual.
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	@Deprecated
	public static String compareContents(String expected, String actual) {
		return TestUtils.compareContents(expected, actual);
	}

	/**
	 * @deprecated use
	 *             {@link TestUtils#compareContentsIgnoreWhitespace(String, String)}
	 *             instead.
	 * 
	 *             Compares expected result with the actual ignoring whitespace
	 *             characters
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	@Deprecated
	public static String compareContentsIgnoreWhitespace(String expected, String actual) {
		return TestUtils.compareContentsIgnoreWhitespace(expected, actual);
	}

	/**
	 * @deprecated use {@link TestUtils#waitForIndexer()} instead.
	 * 
	 *             Wait for indexer to finish its job.
	 */
	@Deprecated
	public static void waitForIndexer() {
		TestUtils.waitForIndexer();
	}

	/**
	 * @deprecated use {@link TestUtils#waitForAutoBuild()} instead.
	 * 
	 *             Wait for auto-build notification to occur, that is for the
	 *             auto-build to finish.
	 */
	@Deprecated
	public static void waitForAutoBuild() {
		TestUtils.waitForAutoBuild();
	}

	/**
	 * @deprecated use
	 *             {@link TestUtils#setProjectPhpVersion(IProject, PHPVersion)}
	 *             instead.
	 * 
	 *             Set project PHP version
	 * 
	 * @param project
	 * @param phpVersion
	 * @throws CoreException
	 */
	@Deprecated
	public static void setProjectPhpVersion(IProject project, PHPVersion phpVersion) throws CoreException {
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

}
