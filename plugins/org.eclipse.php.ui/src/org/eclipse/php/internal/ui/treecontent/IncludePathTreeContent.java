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
package org.eclipse.php.internal.ui.treecontent;

import java.io.File;
import java.util.*;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.watson.ElementTree;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.IPhpProjectOptionChangeListener;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * TODO: description of class, and maybe author if you wrote it entirely from scratch
 */
public class IncludePathTreeContent implements IPHPTreeContentProvider {

	// TODO: all consts should be up here - also should be "private static final" 
	public static final String ID_INCLUDES_NODE = "org.eclipse.php.ui.treecontent.IncludesNode";
	static final String INCLUDE_MODEL_MANAGER_ID = "CompositeIncludePathModel";
	static final IPath INCLUDE_MODELS_PATH_ROOT = new Path("\0IncludePaths");

	private TreeViewer treeViewer;

	// TODO: map of <IProject, IncludesNode> , then remove the redundant castings (and there are many)
	// TODO: can be static because it is shared between all projects
	// I would document this var...	
	private final Map projectNodes = new HashMap();

	// TODO: documentation 
	private final ElementTree includePathTree = new ElementTree();

	// TODO: private methods should be located after the public ones
	// TODO: public methods - must documentation, private - only recommendation
	private Object[] getPathChildren(IPath parentPath) {
		// TODO: private methods should assert for trivial local variables, 
		// something like (parentPath != null) && includePathTree != null  

		IPath modelPath = parentPath.uptoSegment(2);

		if (!includePathTree.includes(modelPath)) {
			return NO_CHILDREN;
		}

		// TODO: you have called twice the includePathTree.getElementData(modelPath) - should be called once 
		Object element = includePathTree.getElementData(modelPath);
		if (!(element instanceof PHPIncludePathModel) && !(element instanceof PhpModelProxy)) {
			return NO_CHILDREN;
		}
		IPhpModel includePathModel = (IPhpModel) includePathTree.getElementData(modelPath);
		IPath includeLocation = PHPModelUtil.getIncludeModelLocation(includePathModel);
		// find and add missing elements:
		
		// TODO: can we changed to includePathModel.getFileDatas(String startsWith) ? it can be a long loop   
		CodeData[] fileDatas = includePathModel.getFileDatas();
		for (CodeData fileData : fileDatas) {
			// TODO: make final loop variables
			final String fileName = fileData.getName();
			
			// TODO: explain why there are 2 questions - can we optimize for one? 
			if (!fileName.startsWith(includeLocation.toOSString()) && !fileName.startsWith(includeLocation.makeAbsolute().toString())) {
				continue;
			}
			
			final IPath fileLocation = new Path(fileName);
			final IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
			if (includePathTree.includes(fileTreeLocation)) {
				continue;
			}
			// find existing directory:
			// TODO : use a while here, it is too long, the block of the for is empty...  
			IPath existingDirectoryTreeLocation;
			for (existingDirectoryTreeLocation = fileTreeLocation.removeLastSegments(1); existingDirectoryTreeLocation.segmentCount() > modelPath.segmentCount() && !includePathTree.includes(existingDirectoryTreeLocation); existingDirectoryTreeLocation = existingDirectoryTreeLocation.removeLastSegments(1)) {
			}
			
			// add missing directories:
			// TODO: use while here - from the same reasons
			for (IPath missingDirectoryTreeLocation = existingDirectoryTreeLocation.append(fileTreeLocation.segment(existingDirectoryTreeLocation.segmentCount())); missingDirectoryTreeLocation.segmentCount() < fileTreeLocation.segmentCount(); missingDirectoryTreeLocation = missingDirectoryTreeLocation
				.append(fileTreeLocation.segment(missingDirectoryTreeLocation.segmentCount()))) {
				includePathTree.createElement(missingDirectoryTreeLocation, ((Workspace) ResourcesPlugin.getWorkspace()).newResource(missingDirectoryTreeLocation, IResource.FOLDER));
			}
			includePathTree.createElement(fileTreeLocation, fileData);

		}
		// getting the children:
		IPath[] childrenPaths = includePathTree.getChildren(parentPath);
		
		// TODO: use Object[] array from the first place - then return the array 
		ArrayList childrenElements = new ArrayList(childrenPaths.length);
		// TODO: use java 5 syntax here
		for (int i = 0; i < childrenPaths.length; ++i) {
			childrenElements.add(includePathTree.getElementData(childrenPaths[i]));
		}
		return childrenElements.toArray();
	}

	// TODO: gathers all inner classes in the header or footer of the class
	// TODO: document inner classes
	// TODO: generally there is no reason to make inner class to utils 
	private static class IncludeModelPathRootConverter {
		static public String toString(IPhpModel model) {
			// TODO assert model
			return String.valueOf(model.getID().replace(Path.SEPARATOR, '?').replace(File.separatorChar, '!').replace(Path.DEVICE_SEPARATOR, ';'));
		}

		// TODO modifier
		// TODO be consistent toString and toPhpModel
		static IPhpModel toPhpModel(String pathRoot, IProject[] projectsToFindIn) {
			String id = pathRoot.replace('?', Path.SEPARATOR).replace('!', File.separatorChar).replace(';', Path.DEVICE_SEPARATOR);
			for (int i = 0; i < projectsToFindIn.length; ++i) {
				PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(projectsToFindIn[i]);
				if (projectModel == null) {
					continue;
				}
				PHPIncludePathModelManager includeModelManager = (PHPIncludePathModelManager) projectModel.getModel(INCLUDE_MODEL_MANAGER_ID);
				if (includeModelManager == null) {
					continue;
				}
				IPhpModel model = includeModelManager.getModel(id);
				if (model != null) {
					return model;
				}
			}
			return null;
		}

		// TODO toPhpModel
		static public IPhpModel from(String pathRoot, IProject project) {
			return toPhpModel(pathRoot, new IProject[] { project });
		}

		// TODO toPhpModel		
		static public IPhpModel from(String pathRoot) {
			return toPhpModel(pathRoot, ResourcesPlugin.getWorkspace().getRoot().getProjects());
		}
	}

	private class IncludesNode extends PHPTreeNode implements IPhpProjectOptionChangeListener, ModelListener {
		IncludesNode(String text, Image image, String id, Object data, Object[] children) {
			super(text, image, id, data, children);
		}

		protected void refresh(final Object obj) {
			if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
				treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
							treeViewer.refresh(obj);
						}
					}
				});
			}
		}

		protected void refresh() {
			refresh(IncludesNode.this);
		}

		public void notifyOptionChanged(Object oldOption, Object newOption) {
			List<IIncludePathEntry> oldEntriesList = Arrays.asList((IIncludePathEntry[]) oldOption);
			List<IIncludePathEntry> newEntriesList = Arrays.asList((IIncludePathEntry[]) newOption);

			boolean hasChanges = false;

			for (IIncludePathEntry newEntry : newEntriesList) {
				if (!oldEntriesList.contains(newEntry)) {
					hasChanges = true;
				}
			}

			for (IIncludePathEntry oldEntry : oldEntriesList) {
				if (!newEntriesList.contains(oldEntry)) {
					hasChanges = true;
				}
			}

			if (hasChanges) {
				includePathTree.getDataTree().empty();
				refresh();
			}
		}

		public void dataCleared() {
		}

		private IPhpModel findModel(PHPFileData fileData) {
			validateRoot();
			IPath[] modelPaths = includePathTree.getChildren(INCLUDE_MODELS_PATH_ROOT);
			IResource res = PHPModelUtil.getResource(fileData);
			IPath filePath = new Path(fileData.getName());
			String filePathString = filePath.toOSString();
			if (res != null) {
				filePathString = res.getLocation().toOSString();
			}
			for (int i = 0; i < modelPaths.length; ++i) {
				IPhpModel model = IncludeModelPathRootConverter.from(modelPaths[i].segment(1));
				if (model == null) {
					continue;
				}
				IPath modelLocation = PHPModelUtil.getIncludeModelLocation(model);
				if (modelLocation == null) {
					continue;
				}
				if (filePathString.startsWith(modelLocation.toOSString()) || (filePath.isAbsolute() && filePath.segment(0).equals(model.getID()))) {
					return model;
				}
			}
			return null;
		}

		protected void refresh(PHPFileData fileData, boolean deleted) {
			IPhpModel model = findModel(fileData);
			if (model == null) {
				return;
			}
			IPath includeLocation = PHPModelUtil.getIncludeModelLocation(model);
			String fileName = fileData.getName();
			IPath fileLocation = new Path(fileName);
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.toString(model));
			IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
			if (includePathTree.includes(fileTreeLocation)) {
				if (deleted) {
					IPath elementToRefresh = fileTreeLocation;
					while (includePathTree.getChildCount(elementToRefresh) < 1 && elementToRefresh.segmentCount() > 1) {
						if (includePathTree.includes(elementToRefresh))
							includePathTree.deleteElement(elementToRefresh);
						elementToRefresh = elementToRefresh.removeLastSegments(1);
					}
					refresh(includePathTree.getElementData(elementToRefresh));
				} else {
					refresh(includePathTree.getElementData(fileTreeLocation));
				}
			} else {
				IPath elementToRefresh = fileTreeLocation;
				while (!includePathTree.includes(elementToRefresh) && elementToRefresh.segmentCount() > 1) {
					if (deleted)
						if (includePathTree.includes(elementToRefresh) && includePathTree.getChildCount(elementToRefresh) < 1)
							includePathTree.deleteElement(elementToRefresh);
					elementToRefresh = fileTreeLocation.removeLastSegments(1);
				}
				;
				refresh(includePathTree.getElementData(elementToRefresh));
			}
		}

		public void fileDataAdded(PHPFileData fileData) {
			refresh(fileData, false);
		}

		public void fileDataChanged(PHPFileData fileData) {
//			refresh(fileData, false);
		}

		public void fileDataRemoved(PHPFileData fileData) {
			refresh(fileData, true);
		}

	}
	
	// TODO: all IPHPTreeContentProvider methods should be placed sequentially
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public synchronized Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			if (options != null) // only for php projects
			{
				Object[] ret = { getTreeNode(project) };
				return ret;
			}
		} else if ((parentElement instanceof PHPTreeNode) && ID_INCLUDES_NODE.equals(((PHPTreeNode) parentElement).getId())) {
			PHPTreeNode treeNode = (PHPTreeNode) parentElement;
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject((IProject) treeNode.getData()/*, true*/);//			PHPIncludePathModel model=PHPIncludePathModelManager
			// if we force creation of the project model it hangs when creating a new project with heavy predefined include path.
			// On this stage there is no need to create the model, since we listen for include path changes.
			if (projectModel == null)
				return new Object[0];
			PHPIncludePathModelManager includeModelManager = (PHPIncludePathModelManager) projectModel.getModel(INCLUDE_MODEL_MANAGER_ID);
			if (includeModelManager == null) {
				return new Object[0];
			}
			IPhpModel[] models = includeModelManager.listModels();
			ArrayList filteredModels = new ArrayList(models.length);
			for (int i = 0; i < models.length; ++i) {
				IPath modelLocation = PHPModelUtil.getIncludeModelLocation(models[i]);
				if (modelLocation != null) {
					filteredModels.add(models[i]);
				}
			}
			return filteredModels.toArray();
		} else if (parentElement instanceof PHPIncludePathModel || parentElement instanceof PhpModelProxy) {
			IPhpModel includePathModel = (IPhpModel) parentElement;
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.toString(includePathModel));
			System.out.println();
			if (!includePathTree.includes(modelPath)) {
				validateRoot();
				includePathTree.createElement(modelPath, includePathModel);
			}
			return getPathChildren(modelPath);
		} else if (parentElement instanceof IResource) {
			IPath resourcePath = ((IResource) parentElement).getFullPath();
			if (includePathTree.includes(((IResource) parentElement).getFullPath())) {
				return getPathChildren(resourcePath);
			}
		}

		return NO_CHILDREN;
	}

	private void validateRoot() {
		if (!includePathTree.includes(INCLUDE_MODELS_PATH_ROOT)) {
			includePathTree.createElement(INCLUDE_MODELS_PATH_ROOT, INCLUDE_MODELS_PATH_ROOT.segment(1));
		}
	}

	/**
	 * TODO: indicate the access modifier   
	 */
	IncludesNode getTreeNode(IProject project) {
		// TODO: can you assert that project != null ?
		IncludesNode treeNode = (IncludesNode) projectNodes.get(project);
		
		if (treeNode == null) {
			Image image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_LIBRARY);
			treeNode = new IncludesNode("Include Paths", image, ID_INCLUDES_NODE, project, null);
			PHPWorkspaceModelManager.getInstance().addModelListener(treeNode);
			projectNodes.put(project, treeNode);
			PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(project, true); // in order to create include path model manager
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			options.addOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, treeNode);

		}
		// generate the tree:
		Object[] models = getChildren(treeNode);
		if (models.length > 0) {
			for (int i = 0; i < models.length; ++i) {
				getChildren(models[i]);
			}
		}
		return treeNode;
	}

	/**
	 * TODO: document public methods - this is very important one  
	 */
	public Object getParent(Object element) {
		if (includePathTree == null)
			return null;
		if (element instanceof PHPFileData) {
			PHPFileData fileData = (PHPFileData) element;
			String fileName = fileData.getName();
			IPath fileLocation = new Path(fileName);
			if (!includePathTree.includes(INCLUDE_MODELS_PATH_ROOT)) {
				return null;
			}
			IPath[] modelPaths = includePathTree.getChildren(INCLUDE_MODELS_PATH_ROOT);
			for (int i = 0; i < modelPaths.length; ++i) {
				IPath includeLocation = PHPModelUtil.getIncludeModelLocation((PHPIncludePathModel) includePathTree.getElementData(modelPaths[i]));
				if (!fileName.startsWith(includeLocation.toOSString()))
					continue;
				IPath fileTreeLocation = modelPaths[i].append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
				if (includePathTree.includes(fileTreeLocation)) {
					IPath parentTreeLocation = fileTreeLocation.removeLastSegments(1);
					return includePathTree.getElementData(parentTreeLocation);
				}
			}
		} else if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			IPath folderPath = folder.getFullPath();
			if (includePathTree.includes(folderPath)) {
				return includePathTree.getElementData(folderPath.removeLastSegments(1));
			}
		} else if (element instanceof PHPIncludePathModel) {
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.toString((PHPIncludePathModel) element));
			if (includePathTree.includes(modelPath)) {
				for (Iterator i = projectNodes.values().iterator(); i.hasNext();) {
					IncludesNode treeNode = (IncludesNode) i.next();
					Object[] children = getChildren(treeNode);
					for (int j = 0; j < children.length; ++j) {
						if (children[j] == element) {
							return treeNode;
						}
					}
				}
			}
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		// TODO: return element instanceof PHPTreeNode
		if (element instanceof PHPTreeNode) {
			return true;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		for (Iterator iter = projectNodes.keySet().iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			IncludesNode node = (IncludesNode) projectNodes.get(project);
			PHPWorkspaceModelManager.getInstance().removeModelListener(node);
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			options.removeOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, node);
		}
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		if (viewer instanceof TreeViewer) {
			treeViewer = (TreeViewer) viewer;
		}
	}

	public Image getImage(Object element) {
		if (element instanceof PHPTreeNode) {
			PHPTreeNode treeNode = (PHPTreeNode) element;
			return treeNode.getImage();
		}
		return null;
	}

	public String getText(Object element) {
		if (element instanceof PHPTreeNode) {
			PHPTreeNode treeNode = (PHPTreeNode) element;
			return treeNode.getText();
		} else if (element instanceof PHPIncludePathModel) {
			PHPIncludePathModel includePathModel = (PHPIncludePathModel) element;
			String id = includePathModel.getID();
			IPath location = IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID());
			if (location != null) {
				return id + " (" + IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID()).toOSString() + ")";
			}
			return id;
		} else if (element instanceof PhpModelProxy) {
			PhpModelProxy proxy = (PhpModelProxy) element;
			return proxy.getID();
		} else if (element instanceof PHPFileData) {
			PHPFileData fileData = (PHPFileData) element;
			IPath[] modelPaths = includePathTree.getChildren(includePathTree.getRoot());
			for (int i = 0; i < modelPaths.length; ++i) {
				PHPIncludePathModel includePathModel = (PHPIncludePathModel) includePathTree.getElementData(modelPaths[i]);
				IPath includePath = PHPModelUtil.getIncludeModelLocation(includePathModel);
				if (fileData.getName().startsWith(includePath.toOSString())) {
					return new Path(fileData.getName()).lastSegment();
				}
			}
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void removeListener(ILabelProviderListener listener) {
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return false; 
	}
}
