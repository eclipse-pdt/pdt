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

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.resources.ExternalFileDecorator;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
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
	protected static PHPProjectModel defaultModel;

	/**
	 * Model listeners
	 */
	private final static Set modelListeners = Collections.synchronizedSet(new HashSet(2));
	private final static Map workspaceModelListeners = Collections.synchronizedMap(new HashMap(2));
	private final static Set<IWorkspaceModelListener> globalWorkspaceModelListeners = new HashSet<IWorkspaceModelListener>(2);

	/*
	 * This call is used to populate the model from the content of the project
	 */
	public void startup() {
		initGlobalModelListeners();

		runBuild();

		attachProjectOpenObserver();

		initLanguageModels();
	}

	private void initLanguageModels() {
		PHPLanguageManagerProvider.instance();
	}

	private void initGlobalModelListeners() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.workspaceModelListener");
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("workspaceModelListener")) {
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
				SafeRunner.run(new SafeRunnable("Error creation PhpModel for extension-point org.eclipse.php.internal.core.workspaceModelListener") {
					public void run() throws Exception {
						listener = (IWorkspaceModelListener) element.createExecutableExtension("class");
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
				for (int i = 0; i < affectedChildren.length; i++) {
					resourceDelta = affectedChildren[i];
					IResource resource = resourceDelta.getResource();
					IProject project = (IProject) resource;
					int eventFlags = resourceDelta.getFlags();
					if ((eventFlags & IResourceDelta.OPEN) != 0) {
						// could be an OPEN or CLOSE
						if (project.isOpen()) {
							runBuild(project);
						}
					}
				}
			}
		});
	}

	public void runBuild(final IProject project) {
		WorkspaceJob cleanJob = new WorkspaceJob(NLS.bind("Building PHP project: {0} ...", project.getName())) {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
					// ResourcesPlugin.getWorkspace().getRoot().accept(new FullPhpProjectBuildVisitor());
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		cleanJob.setUser(false);
		cleanJob.schedule();
	}

	private void runBuild() {
		WorkspaceJob cleanJob = new WorkspaceJob("Building PHP projects ...") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
					monitor.beginTask("Building PHP projects ...", projects.length);

					for (int i = 0; i < projects.length; i++) {
						IProject project = projects[i];
						if (!project.isOpen()) {
							continue;
						}
						boolean hasNature;
						try {
							hasNature = project.hasNature(PHPNature.ID);
						} catch (CoreException e) {
							PHPCorePlugin.log(e);
							return null;
						}
						if (hasNature) {
							project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
						}
						if (monitor.isCanceled()) {
							break;
						}
						monitor.worked(1);
					}
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		cleanJob.setUser(false);
		cleanJob.schedule();
	}

	/*
	 * Called when the workbench is shutting down in order to cache the model
	 */
	public void shutdown() {
		// serialize the Models
		IProject[] projects = listProjects();
		for (int i = 0; i < projects.length; i++) {
			// By removing each of the models we cause for a model dispose.
			// The model dispose, in its turn, will save a cache snapshot of its state.
			removeModel(projects[i]);
		}
		// remove as listener
		// IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// workspace.removeResourceChangeListener(this);

	}

	public final static PHPProjectModel getDefaultPHPProjectModel() {
		if (defaultModel == null) {
			defaultModel = new PHPProjectModel();
			defaultModel.initialize(ExternalFilesRegistry.getInstance().getExternalFilesProject());
			defaultModel.getPHPUserModel().addModelListener(PHPWorkspaceModelManager.getInstance());
			PHPWorkspaceModelManager.getInstance().fireProjectModelAdded(defaultModel.getProject());
		}
		return defaultModel;
	}

	public IProject getProjectForFileData(PHPFileData fileData, IProject defaultProject) {
		if (fileData == null)
			return null;
		IResource res = PHPModelUtil.getResource(fileData);
		IProject project;
		if (res != null) {
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
					String projectPath = projects[i].getLocation().toOSString();
					String modelFilename;
					if (filenameOS.startsWith(projectPath)) {
						modelFilename = new Path(StringUtils.replace(filenameOS, projectPath, "")).toPortableString();
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

	private synchronized PHPFileData getModelForExternalFile(IFile externalFile) {
		PHPFileData fileData = null;
		PHPProjectModel externalProjectModel = getDefaultPHPProjectModel();
		// initialize for first time
		if (externalProjectModel.getPHPUserModel() == null) {
			externalProjectModel.addFileToModel(externalFile);
		}

		// use full path to distinguish between files with the same name (same project model...)
		fileData = externalProjectModel.getFileData(externalFile.getFullPath().toString());
		if (fileData == null) {
			externalProjectModel.addFileToModel(externalFile);
			fileData = externalProjectModel.getFileData(externalFile.getFullPath().toString());
		}
		return fileData;
	}

	public PHPFileData getModelForFile(IFile file, boolean forceCreation) {
		PHPProjectModel projModel = getModelForProject(file.getProject(), forceCreation);
		if (projModel == null) {
			if (file instanceof ExternalFileDecorator && ExternalFilesRegistry.getInstance().isEntryExist(file.getFullPath().toString())) {
				return getModelForExternalFile(file);
			}
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
		if (ExternalFilesRegistry.getInstance().isEntryExist(path.toString())) {
			file = ExternalFilesRegistry.getInstance().getFileEntry(path.toString());
		} else {
			file = ExternalFileDecorator.createFile(path.toString());
		}
		PHPFileData result = null;
		result = getModelForFile(filename, false);
		if (result == null && file != null) {
			if (ExternalFilesRegistry.getInstance().isEntryExist(file.getFullPath().toString())) {
				result = getModelForExternalFile(file);
			}
		}
		return result;
	}

	public PHPProjectModel getModelForProject(final IProject project, boolean forceCreation) {
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
							putModel(project, projectModel);
							attachProjectCloseObserver(project);
						}
					} else if (!project.exists() && project.equals(ExternalFilesRegistry.getInstance().getExternalFilesProject())) {
						projectModel = getDefaultPHPProjectModel();
						putModel(project, projectModel);
						attachProjectCloseObserver(project);
					}
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
		if (model.equals(getDefaultPHPProjectModel())) {
			return ExternalFilesRegistry.getInstance().getExternalFilesProject();
		}

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
			projects[i++] = ((PHPProjectModel) iter.next());
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

	private void putModel(IProject project, PHPProjectModel projectModel) {
		PHPProjectModel oldPhpProjectModel = (PHPProjectModel) models.get(project);
		models.put(project, projectModel);
		projectModel.initialize(project);
		if (oldPhpProjectModel == null) {
			projectModel.getPHPUserModel().addModelListener(this);
		} else {
			this.copyUserModelListeners(projectModel.getPHPUserModel(), oldPhpProjectModel.getPHPUserModel().getModelListenerList());
		}
		if (oldPhpProjectModel == null) {
			fireProjectModelAdded(project);
		} else {
			fireProjectModelChanged(project);
		}
	}

	private void copyUserModelListeners(PHPUserModel newUserModel, List modelListenerList) {
		for (Iterator modelListenerIterator = modelListenerList.iterator(); modelListenerIterator.hasNext();) {
			ModelListener modelListener = (ModelListener) modelListenerIterator.next();
			newUserModel.addModelListener(modelListener);
		}
	}

	/**
	 * Model Listeners
	 */
	public void addModelListener(ModelListener l) {
		modelListeners.add(l);
	}

	public void removeModelListener(ModelListener l) {
		modelListeners.remove(l);
	}

	private ModelListener[] getModelListenersIteratorCopy() {
		synchronized (modelListeners) {
			ModelListener[] iterator = new ModelListener[modelListeners.size()];
			modelListeners.toArray(iterator);
			return iterator;
		}
	}

	public void fileDataChanged(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (int i = 0; i < iterator.length; i++) {
			iterator[i].fileDataChanged(fileData);
		}
	}

	public void fileDataAdded(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (int i = 0; i < iterator.length; i++) {
			iterator[i].fileDataAdded(fileData);
		}
	}

	public void fileDataRemoved(PHPFileData fileData) {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (int i = 0; i < iterator.length; i++) {
			iterator[i].fileDataRemoved(fileData);
		}
	}

	public void dataCleared() {
		ModelListener[] iterator = getModelListenersIteratorCopy();
		for (int i = 0; i < iterator.length; i++) {
			iterator[i].dataCleared();
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
		if ((projectModel == null) && !file.exists()) {
			projectModel = getDefaultPHPProjectModel();
			// distinguish between include path and external files:
			if (projectModel.getFileData(file.getFullPath().toString()) == null) {
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
			throw new IllegalArgumentException("Error registering IWorkspaceModelListener");
		}
		globalWorkspaceModelListeners.add(listener);
	}

	/**
	 * remove a global listener
	 * @param listener
	 */
	public synchronized void removeWorkspaceModelListener(IWorkspaceModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Error registering IWorkspaceModelListener");
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
		for (int i = 0; i < globalListeners.length; i++) {
			IWorkspaceModelListener workspaceModelListener = globalListeners[i];
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
		for (int i = 0; i < globalListeners.length; i++) {
			IWorkspaceModelListener workspaceModelListener = globalListeners[i];
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
		for (int i = 0; i < globalListeners.length; i++) {
			IWorkspaceModelListener workspaceModelListener = globalListeners[i];
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