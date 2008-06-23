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
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.build.FullPhpProjectBuildVisitor;
import org.eclipse.php.internal.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.internal.core.util.project.observer.ProjectRemovedObserversAttacher;
import org.eclipse.wst.sse.core.utils.StringUtils;

/*
 * This is a singleton object that contains a MAP of Project to PHPProjectModels. It is bootstrap by the PHPCorePlugin and sets itself as a IResourceListener in order to correctly update the Model when Resources are created and saved.
 */
public class PHPWorkspaceModelManager implements ModelListener {

	// this is a singleton
	protected static final PHPWorkspaceModelManager instance = new PHPWorkspaceModelManager();

	private PHPWorkspaceModelManager() {
	}

	/**
	 * @return the singelton instance
	 */
	public static PHPWorkspaceModelManager getInstance() {
		return instance;
	}

	protected final static HashMap models = new HashMap();
	protected static PHPProjectModel defaultModel = new PHPProjectModel();

	/**
	 * Model listeners
	 */
	private final static Set<ModelListener> modelListeners = new HashSet<ModelListener>(3);
	private final static Map workspaceModelListeners = Collections.synchronizedMap(new HashMap(2));
	private final static Set<IWorkspaceModelListener> globalWorkspaceModelListeners = new HashSet<IWorkspaceModelListener>(2);

	/*
	 * This call is used to populate the model from the content of the project
	 */
	public void startup() {
		initGlobalModelListeners();

		attachProjectOpenObserver();

		runBuild();
	}

	private void initGlobalModelListeners() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.workspaceModelListener"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			if (element.getName().equals("workspaceModelListener")) { //$NON-NLS-1$
				WorkspaceModelListenerProxy modelListenerProxy = new WorkspaceModelListenerProxy(element);
				IWorkspaceModelListener listener = modelListenerProxy.getListener();
				addWorkspaceModelListener(listener);
			}
		}
	}

	private class WorkspaceModelListenerProxy {
		private final IConfigurationElement element;
		private IWorkspaceModelListener listener;

		public WorkspaceModelListenerProxy(IConfigurationElement element) {
			this.element = element;
		}

		public IWorkspaceModelListener getListener() {
			if (listener == null) {
				SafeRunner.run(new ISafeRunnable() {
					public void run() throws Exception {
						listener = (IWorkspaceModelListener) element.createExecutableExtension("class"); //$NON-NLS-1$
					}

					public void handleException(Throwable exception) {
						Logger.log(Logger.ERROR, "Error creation PhpModel for extension-point org.eclipse.php.internal.core.workspaceModelListener");
					}
				});
			}
			return listener;
		}
	}

	private void attachProjectOpenObserver() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				IResourceDelta resourceDelta = event.getDelta();
				if (resourceDelta == null) {
					return;
				}
				IResourceDelta[] affectedChildren = resourceDelta.getAffectedChildren(IResourceDelta.CHANGED);
				for (IResourceDelta element : affectedChildren) {
					resourceDelta = element;
					IResource resource = resourceDelta.getResource();
					IProject project = (IProject) resource;
					int eventFlags = resourceDelta.getFlags();
					if ((eventFlags & IResourceDelta.OPEN) != 0) {
						// could be an OPEN or CLOSE
						if (project.isOpen()) {
							try {
								if (project.hasNature(PHPNature.ID)) {
									runBuild(project);
								}
							} catch (CoreException e) {
							}
						}
					}
				}
			}
		});
	}

	public void runBuild(final IProject project) {
		WorkspaceJob cleanJob = new WorkspaceJob(NLS.bind(CoreMessages.getString("PHPWorkspaceModelManager_4"), project.getName())) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					PHPWorkspaceModelManager.getInstance().getModelForProject(project, true, false);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		cleanJob.setPriority(Job.LONG);
		cleanJob.setUser(false);
		cleanJob.schedule();
	}

	private void runBuild() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		final ArrayList<IProject> phpProjects = new ArrayList<IProject>();
		for (IProject project : projects) {
			if (!project.isOpen()) {
				continue;
			}
			try {
				if (project.hasNature(PHPNature.ID)) {
					phpProjects.add(project);
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
		
		WorkspaceJob cleanJob = new WorkspaceJob(CoreMessages.getString("PHPWorkspaceModelManager_5")) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask(CoreMessages.getString("PHPWorkspaceModelManager_5"), phpProjects.size());

					for (IProject project : phpProjects) {
						if (project.isOpen()) {
							PHPWorkspaceModelManager.getInstance().getModelForProject(project, true, false);
							if (monitor.isCanceled()) {
								break;
							}
						}
						monitor.worked(1);
					}
//					instance.putModel(ExternalFilesRegistry.getInstance().getExternalFilesProject(), defaultModel, false);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		cleanJob.setPriority(Job.LONG);
		cleanJob.setUser(false);
		cleanJob.schedule();
	}

	/*
	 * Called when the workbench is shutting down in order to cache the model
	 */
	public void shutdown() {
		// serialize the Models
		IProject[] projects = listProjects();
		for (IProject element : projects) {
			// By removing each of the models we cause for a model dispose.
			// The model dispose, in its turn, will save a cache snapshot of its state.
			removeModel(element);
		}
		// remove as listener
		// IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// workspace.removeResourceChangeListener(this);

	}

	public final static PHPProjectModel getDefaultPHPProjectModel() {
		return defaultModel;
	}

	public IProject getProjectForFileData(PHPFileData fileData, IProject defaultProject) {
		if (fileData == null)
			return null;
		IResource res = PHPModelUtil.getResource(fileData);
		IProject project;
		if (res != null) {
//			if (res instanceof ExternalFileWrapper) {
//				return getDefaultPHPProjectModel().getProject();
//			}
			project = res.getProject();
			if (project.isAccessible())
				return project;
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ArrayList projects = new ArrayList(Arrays.asList(root.getProjects()));
		if (defaultProject != null) { // rearrange the projects
			projects.remove(defaultProject);
			projects.add(0, defaultProject);
		}
		String filenameOS = new Path(fileData.getName()).toOSString();
		for (Iterator i = projects.iterator(); i.hasNext();) {
			project = (IProject) i.next();
			PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
			if (model != null) {
				fileData = model.getFileData(filenameOS);
				if (fileData != null)
					return project;
			}
		}
		return null;
	}

	public PHPFileData getModelForFile(String filename, boolean forceCreation) {

		Path path = new Path(filename);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(path.segment(0));
		PHPProjectModel projModel;
		if (!project.isAccessible()) {
			project = null;
			projModel = null;
		} else {
			projModel = getModelForProject(project, forceCreation);
		}

		PHPFileData fileData = null;

		if (projModel == null) { // possibly external resource
			IProject[] projects = root.getProjects();
			String filenameOS = new Path(filename).toOSString();
			for (int i = 0; i < projects.length; ++i) {
				project = projects[i];
				if (!project.exists() || !project.isAccessible())
					continue;
				PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(projects[i]);
				if (model != null) {
					String projectPath = "";
					IPath location = projects[i].getLocation();
					if (location == null) {
						projectPath = projects[i].getLocationURI().toString();
					} else {
						projectPath = projects[i].getLocation().toOSString();
					}

					String modelFilename;
					if (filenameOS.startsWith(projectPath)) {
						modelFilename = new Path(StringUtils.replace(filenameOS, projectPath, "")).toPortableString(); //$NON-NLS-1$
					} else {
						modelFilename = filenameOS;
					}
					fileData = model.getFileData(modelFilename);
					if (fileData != null)
						break;
				}
			}
		} else {
			fileData = projModel.getFileData(filename);
		}
		if (fileData == null && forceCreation && projModel != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file.exists()) {
				addFileToModel(file);
				fileData = projModel.getFileData(filename);
			}
		}
		return fileData;
	}

	private PHPFileData getModelForExternalFile(IFile externalFile) {
		if (!PHPModelUtil.isPhpFile(externalFile)) {
			return null;
		}
		PHPFileData fileData = null;
		PHPProjectModel externalProjectModel = getDefaultPHPProjectModel();

		// use full path to distinguish between files with the same name (same project model...)
		fileData = externalProjectModel.getFileData(externalFile.getFullPath().toOSString());
		if (fileData == null) {
			externalProjectModel.addFileToModel(externalFile);
			fileData = externalProjectModel.getFileData(externalFile.getFullPath().toOSString());
		}
		return fileData;
	}

	public PHPFileData getModelForFile(IFile file, boolean forceCreation) {
		PHPProjectModel projModel = getModelForProject(file.getProject(), forceCreation);
		if (projModel == null) {
//			if (file instanceof ExternalFileWrapper && ExternalFilesRegistry.getInstance().isEntryExist(file.getFullPath().toOSString())) {
//				return getModelForExternalFile(file);
//			}
			return null;
		}

		String filename = file.getFullPath().toString();
		PHPFileData fileData = projModel.getFileData(filename);
		if (fileData == null && forceCreation) {
			addFileToModel(file);
			fileData = projModel.getFileData(filename);
		}
		return fileData;
	}

	public PHPFileData getModelForFile(String filename) {
		IPath path = Path.fromOSString(filename);
		IFile file;
//		if (ExternalFilesRegistry.getInstance().isEntryExist(path.toOSString())) {
//			file = ExternalFilesRegistry.getInstance().getFileEntry(path.toOSString());
//		} else {
//			file = ExternalFileWrapper.createFile(path.toOSString());
//		}
		PHPFileData result = null;
		result = getModelForFile(filename, false);
//		if (result == null && file != null) {
//			if (ExternalFilesRegistry.getInstance().isEntryExist(file)) {
//				result = getModelForExternalFile(file);
//			}
//		}
		return result;
	}

	public PHPProjectModel getModelForProject(final IProject project, boolean forceCreation) {
		//any requests not coming from separated job should use getModelForProject with fork = true.
		return getModelForProject(project, forceCreation, true);
	}

	/**
	 * @param fork use different job when building a project. (in order not to run in UI thread).
	 * by default use true, use false only if you're running from non-UI job
	 */
	private PHPProjectModel getModelForProject(final IProject project, boolean forceCreation, boolean fork) {
		PHPProjectModel projectModel = (PHPProjectModel) models.get(project);
		if (projectModel == null && forceCreation) {
			synchronized (project) {
				projectModel = (PHPProjectModel) models.get(project);
				if (projectModel == null) {

					if (project.isOpen() && project.exists() && project.isAccessible()) {
						boolean hasNature;
						try {
							//support both RSE and PHP projects
							//this is to provide the model+outline to php files from RSE, such as FTP etc...
							hasNature = project.hasNature(PHPCoreConstants.RSE_TEMP_PROJECT_NATURE_ID) || project.hasNature(PHPNature.ID);
						} catch (CoreException e) {
							PHPCorePlugin.log(e);
							return null;
						}
						if (hasNature) {
							projectModel = new PHPProjectModel();
							putModel(project, projectModel, fork);
							attachProjectCloseObserver(project);
						}
					}/* else if (!project.exists() && project.equals(ExternalFilesRegistry.getInstance().getExternalFilesProject())) {
						projectModel = getDefaultPHPProjectModel();
						putModel(project, projectModel, fork);
						attachProjectCloseObserver(project);
					}*/
				}
			}
		}
		return projectModel;
	}

	private void attachProjectCloseObserver(final IProject project) {
		ProjectRemovedObserversAttacher.getInstance().addProjectClosedObserver(project, new IProjectClosedObserver() {
			public void closed() {
				removeModel(project);
			}
		});
	}

	public PHPProjectModel getModelForProject(IProject project) {

		return getModelForProject(project, false);
	}

	public IProject getProjectForModel(PHPProjectModel model) {
//		if (model.equals(getDefaultPHPProjectModel())) {
//			return ExternalFilesRegistry.getInstance().getExternalFilesProject();
//		}

		for (Iterator iter = models.keySet().iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			PHPProjectModel projectModel = getModelForProject(project);
			if (model.equals(projectModel))
				return project;
		}
		return null;
	}

	public IProject[] listProjects() {
		ArrayList list = new ArrayList();
		for (Iterator iter = models.keySet().iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			if (project.exists() && project.isOpen())
				list.add(project);
		}
		IProject[] projects = (IProject[]) list.toArray(new IProject[list.size()]);
		return projects;
	}

	public PHPProjectModel[] listModels() {
		int i = 0;
		PHPProjectModel[] projects = new PHPProjectModel[models.size()];
		for (Iterator iter = models.values().iterator(); iter.hasNext();) {
			projects[i++] = (PHPProjectModel) iter.next();
		}
		return projects;
	}

	public void removeModel(IProject removedProject) {
		PHPProjectModel model = getModelForProject(removedProject);
		if (model != null) {
			model.getPHPUserModel().removeModelListener(this);
			model.dispose();
		}
		models.remove(removedProject);
		fireProjectModelRemoved(removedProject);
	}

	/**
	 * @param fork use different job when building a project. (in order not to run in UI thread)
	 * by default use true, use false only if you're running from non-UI job.
	 */
	private void putModel(final IProject project, PHPProjectModel projectModel, boolean fork) {
		PHPProjectModel oldPhpProjectModel = (PHPProjectModel) models.get(project);
		models.put(project, projectModel);
		projectModel.initialize(project);
		if (oldPhpProjectModel == null) {
			projectModel.getPHPUserModel().addModelListener(this);
		} else {
			this.copyUserModelListeners(projectModel.getPHPUserModel(), oldPhpProjectModel.getPHPUserModel().getModelListenerList());
		}
		fireProjectModelAdded(project);
		if (projectModel.isBuildNeeded()) {
			if (fork) {
				WorkspaceJob buildJob = new WorkspaceJob(NLS.bind(CoreMessages.getString("PHPWorkspaceModelManager_4"), project.getName())) {
					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						buildModel(monitor, project);
						return Status.OK_STATUS;
					}
				};
				buildJob.setUser(false);
				buildJob.schedule();

			} else {
				IProgressMonitor monitor = new NullProgressMonitor();
				buildModel(monitor, project);
			}
		} else {
			if (project.exists()) {//the event should not be lunched if we're dealing with the defaultPHPProjectModel
				fireProjectModelChanged(project);
			}
		}

	}

	private void buildModel(IProgressMonitor monitor, IProject project) {
		try {
			project.accept(new FullPhpProjectBuildVisitor(monitor));
			fireProjectModelChanged(project);
		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			monitor.done();
		}

	}

	private void copyUserModelListeners(IPHPUserModel newUserModel, List modelListenerList) {
		for (Iterator modelListenerIterator = modelListenerList.iterator(); modelListenerIterator.hasNext();) {
			ModelListener modelListener = (ModelListener) modelListenerIterator.next();
			newUserModel.addModelListener(modelListener);
		}
	}

	/**
	 * Model Listeners
	 */
	public synchronized void addModelListener(ModelListener l) {
		modelListeners.add(l);
	}

	public synchronized void removeModelListener(ModelListener l) {
		modelListeners.remove(l);
	}

	private synchronized ModelListener[] getModelListenersIteratorCopy() {
		ModelListener[] iterator = new ModelListener[modelListeners.size()];
		modelListeners.toArray(iterator);
		return iterator;
	}

	public void fileDataChanged(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (ModelListener element : iterator) {
			element.fileDataChanged(fileData);
		}
	}

	public void fileDataAdded(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (ModelListener element : iterator) {
			element.fileDataAdded(fileData);
		}
	}

	public void fileDataRemoved(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (ModelListener element : iterator) {
			element.fileDataRemoved(fileData);
		}
	}

	public void dataCleared() {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (ModelListener element : iterator) {
			element.dataCleared();
		}
	}

	/**
	 * Workspace model listeners
	 */
	public void addWorkspaceModelListener(String projectName, IWorkspaceModelListener l) {
		List wlisteners = (List) workspaceModelListeners.get(projectName);
		if (wlisteners == null) {
			wlisteners = Collections.synchronizedList(new ArrayList(2));
			workspaceModelListeners.put(projectName, wlisteners);
		}
		if (!wlisteners.contains(l))
			wlisteners.add(l);
	}

	public void removeWorkspaceModelListener(String projectName, IWorkspaceModelListener l) {
		List wlisteners = (List) workspaceModelListeners.get(projectName);
		if (wlisteners == null) {
			return;
		}
		wlisteners.remove(l);
	}

	public void addFileToModel(IFile file) {
		if (!PHPModelUtil.isPhpFile(file)) {
			return;
		}

		PHPProjectModel projectModel = getModelForProject(file.getProject());
		if (projectModel != null) {
			projectModel.addFileToModel(file);
		}
	}

	public void removeFileFromModel(IFile file) {
		if (file == null) {
			return;
		}

		PHPProjectModel projectModel = getModelForProject(file.getProject());
		if (projectModel == null && !file.exists()) {
			projectModel = getDefaultPHPProjectModel();
			// distinguish between include path and external files:
			if (projectModel.getFileData(file.getFullPath().toOSString()) == null) {
				return;
			}
		}
		if (projectModel == null) {
			return;
		}
		projectModel.removeFileFromModel(file);
	}

	/**
	 * Global listeners
	 */

	/**
	 * add a global listener
	 */
	public synchronized void addWorkspaceModelListener(IWorkspaceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Error registering IWorkspaceModelListener"); //$NON-NLS-1$
		}
		globalWorkspaceModelListeners.add(listener);
	}

	/**
	 * remove a global listener
	 * @param listener
	 */
	public synchronized void removeWorkspaceModelListener(IWorkspaceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Error registering IWorkspaceModelListener"); //$NON-NLS-1$
		}
		globalWorkspaceModelListeners.remove(listener);
	}

	private synchronized IWorkspaceModelListener[] getGlobalWorkspaceModelListeners() {
		return globalWorkspaceModelListeners.toArray(new IWorkspaceModelListener[globalWorkspaceModelListeners.size()]);
	}

	/**
	 * End Global listeners
	 */

	public void fireProjectModelAdded(IProject project) {
		final IWorkspaceModelListener[] globalListeners = getGlobalWorkspaceModelListeners();
		for (IWorkspaceModelListener workspaceModelListener : globalListeners) {
			workspaceModelListener.projectModelAdded(project);
		}

		List listenersList = (List) workspaceModelListeners.get(project.getName());
		if (listenersList == null) {
			return;
		}
		Object[] listeners = listenersList.toArray();
		for (int i = 0; i < listeners.length; ++i) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) listeners[i];
			listener.projectModelAdded(project);
		}
	}

	public void fireProjectModelRemoved(IProject project) {
		final IWorkspaceModelListener[] globalListeners = getGlobalWorkspaceModelListeners();
		for (IWorkspaceModelListener workspaceModelListener : globalListeners) {
			workspaceModelListener.projectModelRemoved(project);
		}

		List listenersList = (List) workspaceModelListeners.get(project.getName());
		if (listenersList == null) {
			return;
		}
		Object[] listeners = listenersList.toArray();
		for (int i = 0; i < listeners.length; ++i) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) listeners[i];
			listener.projectModelRemoved(project);
		}
	}

	public void fireProjectModelChanged(IProject project) {
		final IWorkspaceModelListener[] globalListeners = getGlobalWorkspaceModelListeners();
		for (IWorkspaceModelListener workspaceModelListener : globalListeners) {
			workspaceModelListener.projectModelChanged(project);
		}

		List listenersList = (List) workspaceModelListeners.get(project.getName());
		if (listenersList == null) {
			return;
		}
		Object[] listeners = listenersList.toArray();
		for (int i = 0; i < listeners.length; ++i) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) listeners[i];
			listener.projectModelChanged(project);
		}
	}

}