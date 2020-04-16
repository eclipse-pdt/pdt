/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
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

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		PHPCorePlugin.toolkitInitialized = true;
		TestUtils.disableColliders(ColliderType.ALL);
	}

	@Override
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
}
