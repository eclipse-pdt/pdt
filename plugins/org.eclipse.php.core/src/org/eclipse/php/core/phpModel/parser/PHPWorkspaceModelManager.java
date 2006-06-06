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
package org.eclipse.php.core.phpModel.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.core.project.build.PhpIncrementalProjectBuilder;
import org.eclipse.php.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.core.util.project.observer.ProjectRemovedObserversAttacher;

/*
 * This is a singleton object that contains a MAP of Project to PHPProjectModels. It is bootstrap by the
 * PHPCorePlugin and sets itself as a IResourceListener in order to correctly update the Model when
 * Resources are created and saved.
 */
public class PHPWorkspaceModelManager implements ModelListener {

	//this is a singleton
	protected static PHPWorkspaceModelManager instance = null;

	public static PHPWorkspaceModelManager getInstance() {

		if (instance == null)
			instance = new PHPWorkspaceModelManager();

		return instance;
	}

	protected HashMap models = new HashMap();
	private List listeners = Collections.synchronizedList(new ArrayList(2));
	private HashMap workspaceModelListeners = new HashMap();
	private List globalWorkspaceModelListeners = new ArrayList();

	private PHPWorkspaceModelManager() {

	}

	/*
	 * This call is used to populate the model from the content of the project
	 */
	public void startup() {

		initGlobalModelListeners();
		
		runBuild();

		attachProjectOpenObserver();
	}

	private void initGlobalModelListeners() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.workspaceModelListener");
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("WorkspaceModelListener")) {
				WorkspaceModelListenerProxy modelListenerProxy = new WorkspaceModelListenerProxy(element);
				IWorkspaceModelListener listener = modelListenerProxy.getListener();
				addWorkspaceModelListener(listener);
			}
		}
		
	}

	private class WorkspaceModelListenerProxy {
		IConfigurationElement element;
		IWorkspaceModelListener listener;

		public WorkspaceModelListenerProxy(IConfigurationElement element) {
			this.element = element;
		}

		public IWorkspaceModelListener getListener() {
			if (listener == null) {
				Platform.run(new SafeRunnable("Error creation PhpModel for extension-point org.eclipse.php.core.workspaceModelListener") {
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
					IProject project = (IProject)resource;
					int eventFlags = resourceDelta.getFlags();
					if ((eventFlags & IResourceDelta.OPEN) != 0) {
						//could be an OPEN or CLOSE
						if (project.isOpen()) {
							runBuild(project);
						}
					}
				}				
			}

		});
	}

	private void runBuild(final IProject project) {
		WorkspaceJob cleanJob = new WorkspaceJob("Creating php model ...") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					project.build(PhpIncrementalProjectBuilder.CLEAN_BUILD, monitor);
					//					ResourcesPlugin.getWorkspace().getRoot().accept(new FullPhpProjectBuildVisitor());
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
		WorkspaceJob cleanJob = new WorkspaceJob("Creating php model ...") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					ResourcesPlugin.getWorkspace().build(PhpIncrementalProjectBuilder.FULL_BUILD, monitor);
					//					ResourcesPlugin.getWorkspace().getRoot().accept(new FullPhpProjectBuildVisitor());
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
		//remove as listener
		//		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		//		workspace.removeResourceChangeListener(this);

	}

	public PHPFileData getModelForFile(String filename, boolean forceCreation) {

		Path path = new Path(filename);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.segment(0));
		PHPProjectModel projModel = getModelForProject(project, forceCreation);
		if (projModel == null) {
			return null;
		}
		PHPFileData fileData = projModel.getFileData(filename);
		if (fileData == null && forceCreation) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			addFileToModel(file);
			fileData = projModel.getFileData(filename);
		}
		return fileData;
	}
	
	public PHPFileData getModelForFile(IFile file, boolean forceCreation) {

		PHPProjectModel projModel = getModelForProject(file.getProject(), forceCreation);
		if (projModel == null) {
			return null;
		}
		String filename=file.getFullPath().toString();
		PHPFileData fileData = projModel.getFileData(filename);
		if (fileData == null && forceCreation) {
			addFileToModel(file);
			fileData = projModel.getFileData(filename);
		}
		return fileData;
	}

	public PHPFileData getModelForFile(String filename) {
		return getModelForFile(filename, false);
	}

	public PHPProjectModel getModelForProject(final IProject project, boolean forceCreation) {
		PHPProjectModel projectModel = (PHPProjectModel) models.get(project);
		if (projectModel == null && forceCreation) {
			if (project.isOpen() && project.exists() && project.isAccessible()) {
				boolean hasNature;
				try {
					hasNature = project.hasNature(PHPNature.ID);
				} catch (CoreException e) {
					PHPCorePlugin.log(e);
					return null;
				}
				if (hasNature) {
					projectModel = new PHPProjectModel();
					putModel(project, projectModel);
					attachProjectCloseObserver(project);
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

	public void addModelListener(ModelListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeModelListener(ModelListener l) {
		listeners.remove(l);
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

	public void fileDataChanged(PHPFileData fileData) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ModelListener listener = (ModelListener) iter.next();
			listener.fileDataChanged(fileData);
		}
	}

	public void fileDataAdded(PHPFileData fileData) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ModelListener listener = (ModelListener) iter.next();
			listener.fileDataAdded(fileData);
		}
	}

	public void fileDataRemoved(PHPFileData fileData) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ModelListener listener = (ModelListener) iter.next();
			listener.fileDataRemoved(fileData);
		}
	}

	public void dataCleared() {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ModelListener listener = (ModelListener) iter.next();
			listener.dataCleared();
		}
	}

	public void addFileToModel(IFile file) {
		if (!this.isPhpFile(file)) {
			return;
		}

		PHPProjectModel projectModel = (PHPProjectModel) PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		projectModel.addFileToModel(file);
	}

	public void removeFileFromModel(IFile file) {
		PHPProjectModel projectModel = (PHPProjectModel) PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (projectModel == null) {
			return;
		}

		projectModel.removeFileFromModel(file);
	}

	public void addWorkspaceModelListener(String projectName, IWorkspaceModelListener l) {
		List listeners = (List) workspaceModelListeners.get(projectName);
		if (listeners == null) {
			listeners = Collections.synchronizedList(new ArrayList(2));
			workspaceModelListeners.put(projectName, listeners);
		}
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeWorkspaceModelListener(String projectName, IWorkspaceModelListener l) {
		List listeners = (List) workspaceModelListeners.get(projectName);
		if (listeners == null) {
			return;
		}
		listeners.remove(l);
	}

	public void addWorkspaceModelListener(IWorkspaceModelListener l) {
		if (!globalWorkspaceModelListeners.contains(l))
			globalWorkspaceModelListeners.add(l);
	}
	
	public void removeWorkspaceModelListener(IWorkspaceModelListener l) {
		globalWorkspaceModelListeners.remove(l);
	}
	
	public void fireProjectModelAdded(IProject project) {
		for(Iterator iter = globalWorkspaceModelListeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelAdded(project);
		}
		
		List listeners = (List) workspaceModelListeners.get(project.getName());
		if (listeners == null) {
			return;
		}
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelAdded(project);
		}
	}

	public void fireProjectModelRemoved(IProject project) {
		for (Iterator iter = globalWorkspaceModelListeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelRemoved(project);
		}
		
		List listeners = (List) workspaceModelListeners.get(project.getName());
		if (listeners == null) {
			return;
		}
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelRemoved(project);
		}
	}

	public void fireProjectModelChanged(IProject project) {
		for (Iterator iter = globalWorkspaceModelListeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelChanged(project);
		}

		List listeners = (List) workspaceModelListeners.get(project.getName());
		if (listeners == null) {
			return;
		}
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			IWorkspaceModelListener listener = (IWorkspaceModelListener) iter.next();
			listener.projectModelChanged(project);
		}
	}

	private boolean isPhpFile(IFile file) {
		IContentDescription contentDescription;
		try {
			contentDescription = file.getContentDescription();
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return false;
		}
		if (contentDescription == null) {
			if (hasPhpExtention(file)) {
				PHPCorePlugin.logErrorMessage("content description null!");
			}
			return false;
		}

		if (!ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId())) {
			return false;
		}

		return true;
	}

	private boolean hasPhpExtention(IFile file) {
		IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		String[] validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String ext = fileName.substring(index + 1);
		for (int i = 0; i < validExtensions.length; i++) {
			if (ext.equals(validExtensions[i])) {
				return true;
			}
		}
		return false;
	}

}
