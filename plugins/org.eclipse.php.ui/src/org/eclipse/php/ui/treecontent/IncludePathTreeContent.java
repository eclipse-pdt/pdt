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
package org.eclipse.php.ui.treecontent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.watson.ElementTree;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.phpModel.parser.PHPIncludePathModel;
import org.eclipse.php.core.phpModel.parser.PHPIncludePathModelManager;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.parser.PhpModelProxy;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.project.options.IPhpProjectOptionChangeListener;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class IncludePathTreeContent implements IPHPTreeContentProvider {
	public static final String ID_INCLUDES_NODE = "org.eclipse.php.ui.treecontent.IncludesNode";
	TreeViewer treeViewer;
	HashMap projects = new HashMap();
	ElementTree includePathTree = new ElementTree();

	class IncludesNode extends PHPTreeNode implements IPhpProjectOptionChangeListener {
		IncludesNode(String text, Image image, String id, Object data, Object[] children) {
			super(text, image, id, data, children);
		}

		public void notifyOptionChanged(Object oldOption, Object newOption) {
			treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
				public void run() {
					treeViewer.refresh(IncludesNode.this);
				}
			});
		}
	}

	Object[] getPathChildren(IPath parentPath) {
		IPath modelPath = new Path(parentPath.segment(0));
		if (!includePathTree.includes(modelPath)) {
			return NO_CHILDREN;
		}
		PHPIncludePathModel includePathModel = (PHPIncludePathModel) includePathTree.getElementData(modelPath);
		IPath includePath = IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID());
		File folder = new File(includePath.append(parentPath.removeFirstSegments(1)).toOSString());
		// remove not existing elements:
		IPath[] innerFilePaths = includePathTree.getChildren(parentPath);
		for (int i = 0; i < innerFilePaths.length; ++i) {
			Object elementData = includePathTree.getElementData(innerFilePaths[i]);
			boolean toDelete = false;
			if (elementData instanceof PHPFileData) {
				PHPFileData realFileData = includePathModel.getFileData(includePath.append(innerFilePaths[i].removeFirstSegments(1)).toOSString());
				if (realFileData == null) {
					toDelete = true;
				}
			} else {
				IResource resource = (IResource) elementData;
				PHPFileData[] fileDatas = includePathModel.getFileDatas();
				boolean startFound = false;
				for (int j = 0; j < fileDatas.length; ++j) {
					if (fileDatas[j].getName().startsWith(includePath.append(resource.getFullPath().removeFirstSegments(1)).toOSString())) {
						startFound = true;
						break;
					}
				}
				toDelete = !startFound;
			}
			if (toDelete) {
				includePathTree.deleteElement(innerFilePaths[i]);
			}
		}
		// add missing elements:
		String[] innerFileNames = folder.list();
		if (innerFileNames != null) {
			for (int i = 0; i < innerFileNames.length; ++i) {
				File innerFile = new File(folder.getPath() + File.separatorChar + innerFileNames[i]);
				PHPFileData[] fileDatas = includePathModel.getFileDatas();

				boolean startFound = false;
				PHPFileData exactMatchingFileData = null;
				for (int j = 0; j < fileDatas.length; ++j) {
					if (fileDatas[j].getName().startsWith(innerFile.getAbsolutePath())) {
						startFound = true;
						if (fileDatas[j].getName().equals(innerFile.getAbsolutePath())) {
							exactMatchingFileData = fileDatas[j];
						}
						break;
					}
				}
				if (startFound) {
					IPath innerFilePath = parentPath.append(new Path(innerFileNames[i]));
					if (!includePathTree.includes(innerFilePath)) {
						if (exactMatchingFileData != null) {
							includePathTree.createElement(innerFilePath, exactMatchingFileData);
						} else {
							includePathTree.createElement(innerFilePath, ((Workspace) ResourcesPlugin.getWorkspace()).newResource(innerFilePath, innerFile.isFile() ? IResource.FILE : IResource.FOLDER));
						}
					}
				}
			}
		}
		// getting the children:
		IPath[] childrenPaths = includePathTree.getChildren(parentPath);
		ArrayList childrenElements = new ArrayList(childrenPaths.length);
		for (int i = 0; i < childrenPaths.length; ++i) {
			childrenElements.add(includePathTree.getElementData(childrenPaths[i]));
		}
		return childrenElements.toArray();
	}

	public Object[] getChildren(Object parentElement) {
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
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject((IProject) treeNode.getData(), true);//			PHPIncludePathModel model=PHPIncludePathModelManager
			PHPIncludePathModelManager includeModelManager = (PHPIncludePathModelManager) projectModel.getModel("CompositeIncludePathModel");
			if (includeModelManager == null) {
				return new Object[0];
			}
			return includeModelManager.listModels();
		} else if (parentElement instanceof PHPIncludePathModel) {
			PHPIncludePathModel includePathModel = (PHPIncludePathModel) parentElement;
			String modelRoot = includePathModel.getID().replace(Path.SEPARATOR, '\0'); // in case it's a plain directory, not a variable
			IPath modelPath = new Path(modelRoot);
			if (!includePathTree.includes(modelPath)) {
				includePathTree.createElement(modelPath, includePathModel);
			}
			return getPathChildren(modelPath);
		} else if (parentElement instanceof IResource) {
			IPath resourcePath = ((IResource) parentElement).getFullPath();
			if (includePathTree.includes(((IResource) parentElement).getFullPath())) {
				return getPathChildren(resourcePath);
			}
		} else if (parentElement instanceof PhpModelProxy) {
			PhpModelProxy proxy = (PhpModelProxy) parentElement;
			return proxy.getPHPFilesData(null);
		}

		return NO_CHILDREN;
	}

	IncludesNode getTreeNode(IProject project) {
		IncludesNode treeNode = (IncludesNode) projects.get(project);
		if (treeNode == null) {

			Image image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_LIBRARY);
			treeNode = new IncludesNode("Include Paths", image, ID_INCLUDES_NODE, project, null);
			projects.put(project, treeNode);
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			options.addOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, treeNode);

		}
		return treeNode;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		for (Iterator iter = projects.keySet().iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			IncludesNode node = (IncludesNode) projects.get(project);
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
			return includePathModel.getID() + " (" + IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID()).toOSString() + ")";
		} else if (element instanceof PhpModelProxy) {
			PhpModelProxy proxy = (PhpModelProxy) element;
			return proxy.getID();
		} else if (element instanceof PHPFileData) {
			PHPFileData fileData = (PHPFileData) element;
			IPath[] modelPaths = includePathTree.getChildren(includePathTree.getRoot());
			for (int i = 0; i < modelPaths.length; ++i) {
				PHPIncludePathModel includePathModel = (PHPIncludePathModel) includePathTree.getElementData(modelPaths[i]);
				IPath includePath = IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID());
				if (fileData.getName().startsWith(includePath.toOSString())) {
					return new Path(fileData.getName()).lastSegment();
				}
			}
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {

	}

}
