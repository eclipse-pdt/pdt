/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core;

import java.util.Hashtable;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.osgi.framework.BundleContext;
 
/**
 * The main plugin class to be used in the desktop.
 */
public class PHPCorePlugin extends Plugin {

	public static final String ID = "org.eclipse.php.core"; //$NON-NLS-1$

	public static final int INTERNAL_ERROR = 10001;

	//The shared instance.
	private static PHPCorePlugin plugin;

	/**
	 * The constructor.
	 */
	public PHPCorePlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		Job delayedJob = new Job("Initializing PHP Toolkit") {
			protected IStatus run(IProgressMonitor monitor) {
				PHPWorkspaceModelManager.getInstance().startup();
				IncludePathVariableManager.instance().startUp();
				return Status.OK_STATUS;
			}			
		};
		delayedJob.setPriority(Job.LONG);
		delayedJob.schedule();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {

		try {
			PHPWorkspaceModelManager.getInstance().shutdown();
		} finally {
			super.stop(context);
			plugin = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPCorePlugin getDefault() {
		return plugin;
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHPCore plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

	public static final boolean isDebugMode;
	static {
		String value = Platform.getDebugOption("org.eclipse.php.core/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	public static void setIncludePathVariable(String name, IPath path, IProgressMonitor monitor) {
		IncludePathVariableManager.instance().putVariable(name, path);
	}

	public static String getPluginId() {
		return ID;
	}
	
	/**
	 * Helper method for returning one option value only. Equivalent to <code>(String)PhpCore.getOptions().get(optionName)</code>
	 * Note that it may answer <code>null</code> if this option does not exist.
	 * <p>
	 * For a complete description of the configurable options, see <code>getDefaultOptions</code>.
	 * </p>
	 *
	 * @param optionName the name of an option
	 * @return the String value of a given option
	 * @see PhpCore#getDefaultOptions()
	 * @see PhpCorePreferenceInitializer for changing default settings
	 * @since 2.0
	 */
	public static String getOption(String optionName) {
		return ModelManager.getModelManager().getOption(optionName);
	}

	/**
	 * Returns the table of the current options. Initially, all options have their default values,
	 * and this method returns a table that includes all known options.
	 * <p>For a complete description of the configurable options, see <code>getDefaultOptions</code>.</p>
	 * <p>Returns a default set of options even if the platform is not running.</p>
	 *
	 * @return table of current settings of all options
	 *   (key type: <code>String</code>; value type: <code>String</code>)
	 * @see #getDefaultOptions()
	 * @see JavaCorePreferenceInitializer for changing default settings
	 */
	public static Hashtable getOptions() {
		return ModelManager.getModelManager().getOptions();
	}
}
