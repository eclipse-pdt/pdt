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

import java.util.ResourceBundle;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPCorePlugin extends AbstractUIPlugin {

	public static final String ID = "org.eclipse.php.core"; //$NON-NLS-1$

	public static final int INTERNAL_ERROR = 10001;

	//The shared instance.
	private static PHPCorePlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

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

		PHPWorkspaceModelManager.getInstance().startup();
		IncludePathVariableManager.instance().startUp();
		
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
			resourceBundle = null;
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

}
