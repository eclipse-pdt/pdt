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
package org.eclipse.php.internal.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
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

		initializeAfterStart();
	}
	
	/**
	 * This method is used for later initialization. This trick should release plug-in start-up.
	 */
	void initializeAfterStart() {
		Job job = new Job("") {
			protected IStatus run(IProgressMonitor monitor) {
				
				// start the include path manager
				IncludePathManager.getInstance();

				// register the listener in charge of converting the projects - applies for projects being opened during work
				ResourcesPlugin.getWorkspace().addResourceChangeListener(fProjectConvertListener, IResourceChangeEvent.PRE_BUILD);
				
				// run the conversion over all the projects in the workspace - all open projects will be converted
				try {
					convertProjects();
				} catch (ModelException e) {
					log(e);
				} catch (CoreException e) {
					log(e);
				}
				
				return Status.OK_STATUS;
			}
		};
		job.schedule(Job.LONG);
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
			if (PHPToolkitUtil.isPhpProject(project)) {
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

	private final ListenerList shutdownListeners = new ListenerList();

	public void addShutdownListener(IShutdownListener listener) {
		shutdownListeners.add(listener);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {

		Object[] listeners = shutdownListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			((IShutdownListener) listeners[i]).shutdown();
		}
		shutdownListeners.clear();

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
	@SuppressWarnings("unchecked")
	public static Hashtable getOptions() {
		return ModelManager.getModelManager().getOptions();
	}

	/**
	 * Initializes DLTKCore internal structures to allow subsequent operations
	 * (such as the ones that need a resolved classpath) to run full speed. A
	 * client may choose to call this method in a background thread early after
	 * the workspace has started so that the initialization is transparent to
	 * the user.
	 * <p>
	 * However calling this method is optional. Services will lazily perform
	 * initialization when invoked. This is only a way to reduce initialization
	 * overhead on user actions, if it can be performed before at some
	 * appropriate moment.
	 * </p>
	 * <p>
	 * This initialization runs accross all Java projects in the workspace. Thus
	 * the workspace root scheduling rule is used during this operation.
	 * </p>
	 * <p>
	 * This method may return before the initialization is complete. The
	 * initialization will then continue in a background thread.
	 * </p>
	 * <p>
	 * This method can be called concurrently.
	 * </p>
	 * 
	 * @param monitor
	 *            a progress monitor, or <code>null</code> if progress reporting
	 *            and cancellation are not desired
	 * @exception CoreException
	 *                if the initialization fails, the status of the exception
	 *                indicates the reason of the failure
	 * @since 3.1
	 */
	public static void initializeAfterLoad(IProgressMonitor monitor) throws CoreException {
		try {
			if (monitor != null) {
				monitor.beginTask(CoreMessages.PHPCorePlugin_initializingPHPToolkit, 100);
			}
			// dummy query for waiting until the indexes are ready
			SearchEngine engine = new SearchEngine();
			IDLTKSearchScope scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit.getDefault());
			try {
				if (monitor != null) {
					monitor.subTask(CoreMessages.PHPCorePlugin_initializingSearchEngine);
				}
				engine.searchAllMethodNames("@$%#^".toCharArray(), SearchPattern.R_EXACT_MATCH, IDLTKSearchConstants.DECLARATIONS,
					scope, new MethodNameRequestor() {
					},
					// will not activate index query caches if indexes are
					// not ready, since it would take to long
					// to wait until indexes are fully rebuild
					IDLTKSearchConstants.CANCEL_IF_NOT_READY_TO_SEARCH,
					monitor == null ? null : new SubProgressMonitor(monitor, 49) // 49% of the time is spent in the dummy search
				);
			} catch (ModelException e) {
				// /search failed: ignore
			} catch (OperationCanceledException e) {
				if (monitor != null && monitor.isCanceled()) {
					throw e;
				}
				// else indexes were not ready: catch the exception so that jars
				// are still refreshed
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
	}
}
