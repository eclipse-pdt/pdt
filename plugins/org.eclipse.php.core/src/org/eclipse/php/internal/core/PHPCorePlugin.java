/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.util.ProjectBackwardCompatibilityUtil;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPCorePlugin extends Plugin {

	// listener used for converting 1.0.x projects into 2.0.x projects
	private ProjectConversionListener fProjectConvertListener = new ProjectConversionListener();

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
		// register the listener in charge of converting the projects - applies for projects being opened during work
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fProjectConvertListener, IResourceChangeEvent.PRE_BUILD);
		// run the conversion over all the projects in the workspace - all open projects will be converted
		convertProjects();
	}

	/*
	 * Listener on changed projects, used for converting them into PDT 2.0.x projects if needed
	 */
	private class ProjectConversionListener implements IResourceChangeListener {

		/*
		 * Gathers all the projects that changed in the workspace and sends them to the conversion method
		 * 
		 * (non-Javadoc)
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			IProject[] projects = new IProject[affectedChildren.length];
			for (int i = 0; i < affectedChildren.length; i++) {
				projects[i] = affectedChildren[i].getResource().getProject();
			}

			try {
				processProjects(projects);
			} catch (ModelException e) {
				Logger.logException(e);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}

	}

	/*
	 * Gathers all the projects in the workspace and sends them to the conversion method
	 */
	private void convertProjects() throws CoreException, ModelException {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		processProjects(projects);
	}

	/*
	 * Goes over the given projects and converts them
	 */
	private void processProjects(final IProject[] projects) throws CoreException, ModelException {
		ProjectsIterate: for (IProject project : projects) {
			// skip unaccessible projects
			if (!project.isAccessible()) {
				continue ProjectsIterate;
			}
			// verify that the project is a PHP project
			if (PHPModelUtil.isPhpProject(project)) {
				IProjectDescription projectDescription = project.getDescription();
				ICommand[] commands = projectDescription.getBuildSpec();
				// check if the Script Builder is installed
				for (int i = 0; i < commands.length; ++i) {
					if (commands[i].getBuilderName().equals(DLTKCore.BUILDER_ID)) {
						// when the builder exists - continue to the next project
						continue ProjectsIterate;
					}
				}
				// perform modifications only if the builder is not installed
				modifyProject(project);
			}
		}
	}

	/*
	 * Do the actual modifications on the project
	 */
	private void modifyProject(IProject project) throws CoreException, ModelException {
		final PHPNature phpNature = new PHPNature();

		// add the required builders and build paths as defined in the new PHP nature
		phpNature.setProject(project);
		phpNature.configure();

		IScriptProject scriptProject = DLTKCore.create(project);
		// merge the project build path with the old include path
		IBuildpathEntry[] existingPath = scriptProject.getRawBuildpath();

		ArrayList<IBuildpathEntry> newPath = new ArrayList<IBuildpathEntry>();
		if (existingPath != null) {
			newPath.addAll(Arrays.asList(existingPath));
		}
		IBuildpathEntry[] oldIncludePath = ProjectBackwardCompatibilityUtil.convertIncludePathForProject(project);
		if (oldIncludePath != null) {
			newPath.addAll(Arrays.asList(oldIncludePath));
		}
		scriptProject.setRawBuildpath(newPath.toArray(new IBuildpathEntry[newPath.size()]), new NullProgressMonitor());
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(fProjectConvertListener);
		plugin = null;
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
