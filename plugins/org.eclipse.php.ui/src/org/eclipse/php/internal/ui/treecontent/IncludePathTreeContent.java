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

public class IncludePathTreeContent implements IPHPTreeContentProvider {
	public static final String ID_INCLUDES_NODE = "org.eclipse.php.ui.treecontent.IncludesNode";
	TreeViewer treeViewer;
	HashMap projects = new HashMap();
	ElementTree includePathTree = new ElementTree();

	class IncludesNode extends PHPTreeNode implements IPhpProjectOptionChangeListener, ModelListener {
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
			
			if(hasChanges) {
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
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.to(model));
			IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
			if (includePathTree.includes(fileTreeLocation)) {
				if (deleted) {
					IPath elementToRefresh = fileTreeLocation;
					while(includePathTree.getChildCount(elementToRefresh) < 1 && elementToRefresh.segmentCount() > 1) {
						if(includePathTree.includes(elementToRefresh))
							includePathTree.deleteElement(elementToRefresh);
						elementToRefresh = elementToRefresh.removeLastSegments(1);
					}
					refresh(includePathTree.getElementData(elementToRefresh));
				} else {
					refresh(includePathTree.getElementData(fileTreeLocation));
				}
			} else {
				IPath elementToRefresh = fileTreeLocation;
				while(!includePathTree.includes(elementToRefresh) && elementToRefresh.segmentCount() > 1) {
					if(deleted)
						if(includePathTree.includes(elementToRefresh) && includePathTree.getChildCount(elementToRefresh) < 1)
							includePathTree.deleteElement(elementToRefresh);
					elementToRefresh = fileTreeLocation.removeLastSegments(1);
				};
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

	Object[] getPathChildren(IPath parentPath) {
		IPath modelPath = parentPath.uptoSegment(2);
		if (!includePathTree.includes(modelPath)) {
			return NO_CHILDREN;
		}
		Object element = includePathTree.getElementData(modelPath);
		if (!(element instanceof PHPIncludePathModel) && !(element instanceof PhpModelProxy)) {
			return NO_CHILDREN;
		}
		IPhpModel includePathModel = (IPhpModel) includePathTree.getElementData(modelPath);
		IPath includeLocation = PHPModelUtil.getIncludeModelLocation(includePathModel);
		// find and add missing elements:
		CodeData[] fileDatas = includePathModel.getFileDatas();
		for (int i = 0; i < fileDatas.length; ++i) {
			String fileName = fileDatas[i].getName();
			if (!fileName.startsWith(includeLocation.toOSString()) && !fileName.startsWith(includeLocation.makeAbsolute().toString())) {
				continue;
			}
			IPath fileLocation = new Path(fileName);
			IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
			if (includePathTree.includes(fileTreeLocation)) {
				continue;
			}
			// find existing directory:
			IPath existingDirectoryTreeLocation;
			for (existingDirectoryTreeLocation = fileTreeLocation.removeLastSegments(1); existingDirectoryTreeLocation.segmentCount() > modelPath.segmentCount() && !includePathTree.includes(existingDirectoryTreeLocation); existingDirectoryTreeLocation = existingDirectoryTreeLocation
				.removeLastSegments(1)) {
			}
			// add missing directories:
			for (IPath missingDirectoryTreeLocation = existingDirectoryTreeLocation.append(fileTreeLocation.segment(existingDirectoryTreeLocation.segmentCount())); missingDirectoryTreeLocation.segmentCount() < fileTreeLocation.segmentCount(); missingDirectoryTreeLocation = missingDirectoryTreeLocation
				.append(fileTreeLocation.segment(missingDirectoryTreeLocation.segmentCount()))) {
				includePathTree.createElement(missingDirectoryTreeLocation, ((Workspace) ResourcesPlugin.getWorkspace()).newResource(missingDirectoryTreeLocation, IResource.FOLDER));
			}
			includePathTree.createElement(fileTreeLocation, fileDatas[i]);

		}
		// getting the children:
		IPath[] childrenPaths = includePathTree.getChildren(parentPath);
		ArrayList childrenElements = new ArrayList(childrenPaths.length);
		for (int i = 0; i < childrenPaths.length; ++i) {
			childrenElements.add(includePathTree.getElementData(childrenPaths[i]));
		}
		return childrenElements.toArray();
	}

	static final String INCLUDE_MODEL_MANAGER_ID = "CompositeIncludePathModel";

	static class IncludeModelPathRootConverter {
		static public String to(IPhpModel model) {
			return String.valueOf(model.getID().replace(Path.SEPARATOR, '?').replace(File.separatorChar, '!').replace(Path.DEVICE_SEPARATOR, ';'));
		}

		static IPhpModel from(String pathRoot, IProject[] projectsToFindIn) {
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

		static public IPhpModel from(String pathRoot, IProject project) {
			return from(pathRoot, new IProject[] { project });
		}

		static public IPhpModel from(String pathRoot) {
			return from(pathRoot, ResourcesPlugin.getWorkspace().getRoot().getProjects());
		}
	}

	static final IPath INCLUDE_MODELS_PATH_ROOT = new Path("\0IncludePaths");

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
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.to(includePathModel));
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

	IncludesNode getTreeNode(IProject project) {
		IncludesNode treeNode = (IncludesNode) projects.get(project);
		if (treeNode == null) {

			Image image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_LIBRARY);
			treeNode = new IncludesNode("Include Paths", image, ID_INCLUDES_NODE, project, null);
			PHPWorkspaceModelManager.getInstance().addModelListener(treeNode);
			projects.put(project, treeNode);
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
			IPath modelPath = INCLUDE_MODELS_PATH_ROOT.append(IncludeModelPathRootConverter.to((PHPIncludePathModel) element));
			if (includePathTree.includes(modelPath)) {
				for (Iterator i = projects.values().iterator(); i.hasNext();) {
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
		if (element instanceof PHPTreeNode) {
			return true;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		for (Iterator iter = projects.keySet().iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			IncludesNode node = (IncludesNode) projects.get(project);
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
