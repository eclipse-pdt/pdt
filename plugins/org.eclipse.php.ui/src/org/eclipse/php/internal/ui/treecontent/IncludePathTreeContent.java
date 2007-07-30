/*******************************************************************************
 * Copyright (c) 2007 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.treecontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A content provider for include paths under projects. Shows files in file-system-like tree.
 * @author seva
 */
public class IncludePathTreeContent implements IPHPTreeContentProvider {

	private static final String INCLUDE_PATHS_NODE_ID = "org.eclipse.php.ui.treecontent.IncludesNode";
	private static final String INCLUDE_PATHS_NODE_NAME = "Include Paths";

	static final IPath INCLUDE_PATHS_ROOT_PATH = new Path("\0IncludePaths");

	/**
	 * Stores tree of all the includes. Main elements are In
	 */
	static final ElementTree includePathTree = new ElementTree();

	/**
	 * Mapping of project to their main include nodes.
	 */
	private static final Map<IProject, IncludesNode> projectNodes = new HashMap();

	/**
	 * Parent tree viewer.
	 */
	private TreeViewer treeViewer;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(final Object element) {
		return element instanceof PHPTreeNode;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public synchronized Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof IProject)
			return getProjectChildren((IProject) parentElement);
		else if (parentElement instanceof PHPTreeNode && INCLUDE_PATHS_NODE_ID.equals(((PHPTreeNode) parentElement).getId()))
			return getTreeNodeChildren((PHPTreeNode) parentElement);
		else if (parentElement instanceof PHPIncludePathModel || parentElement instanceof PhpModelProxy)
			return getIncludeModelChildren((IPhpModel) parentElement);
		else if (parentElement instanceof IFolder)
			return getFolderChildren(((IFolder) parentElement));
		return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(final Object element) {
		if (includePathTree == null)
			return null;
		if (element instanceof PHPFileData) {
			return getFileParent((PHPFileData) element);
		} else if (element instanceof IFolder) {
			return getFolderParent((IFolder) element);
		} else if (element instanceof PHPIncludePathModel) {
			PHPIncludePathModel includeModel = (PHPIncludePathModel) element;
			return getIncludeModelParent(includeModel);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {

		if (viewer instanceof TreeViewer)
			treeViewer = (TreeViewer) viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		for (final Map.Entry<IProject, IncludesNode> entry : projectNodes.entrySet()) {
			final IProject project = entry.getKey();
			final IncludesNode node = entry.getValue();
			PHPWorkspaceModelManager.getInstance().removeModelListener(node);
			final PHPProjectOptions options = PHPProjectOptions.forProject(project);
			options.removeOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, node);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(final Object element) {
		if (element instanceof PHPTreeNode) {
			return getNodeText((PHPTreeNode) element);
		} else if (element instanceof PHPIncludePathModel) {
			return getIncludeModelText((PHPIncludePathModel) element);
		} else if (element instanceof PhpModelProxy) {
			final PhpModelProxy proxy = (PhpModelProxy) element;
			return getProjectModelText(proxy);
		} else if (element instanceof PHPFileData) {
			return getFileText((PHPFileData) element);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(final Object element) {
		if (element instanceof PHPTreeNode) {
			final PHPTreeNode treeNode = (PHPTreeNode) element;
			return treeNode.getImage();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(final ILabelProviderListener listener) {
		// not in use
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(final ILabelProviderListener listener) {
		// not in use
	}

	TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Create node for the file
	 * @param fileData
	 * @param fileTreeLocation
	 */
	private static void createFileNode(final CodeData fileData, final IPath fileTreeLocation) {
		includePathTree.createElement(fileTreeLocation, fileData);
	}

	/**
	 * Create Include Paths Node for the project.
	 * @param project
	 * @return the node
	 */
	private IncludesNode createIncludePathsNode(final IProject project) {
		IncludesNode treeNode;
		final Image image = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_LIBRARY);
		treeNode = new IncludesNode(INCLUDE_PATHS_NODE_NAME, image, INCLUDE_PATHS_NODE_ID, project, this);
		PHPWorkspaceModelManager.getInstance().addModelListener(treeNode);
		projectNodes.put(project, treeNode);
		final PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(project, true); // in order to create include path model manager
		final PHPProjectOptions options = PHPProjectOptions.forProject(project);
		options.addOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, treeNode);
		return treeNode;
	}

	/**
	 * Fill the tree with folders and files
	 * @param modelPath
	 * @param includePathModel
	 */
	private static void createIncludePathTree(final IPath modelPath, final IPhpModel includePathModel) {
		final IPath includeModelLocation = PHPModelUtil.getIncludeModelLocation(includePathModel);

		final CodeData[] fileDatas = includePathModel.getFileDatas();
		for (final CodeData fileData : fileDatas) {
			final String fileName = fileData.getName();

			// XXX probablly not needed:
			//			if(includePathModel instanceof PhpModelProxy) {
			//				if(!fileName.startsWith(includeModelLocation.makeAbsolute().toString()))
			//					continue;
			//				else if (!fileName.startsWith(includeModelLocation.toOSString()))
			//					continue;
			//			}

			final IPath fileLocation = new Path(fileName);
			final IPath fileTreeLocation = modelPath.append(fileLocation.removeFirstSegments(includeModelLocation.segmentCount()));

			if (includePathTree.includes(fileTreeLocation))
				continue;

			final IPath existingIncludePathFolderNode = findExistingIncludePathFolderNode(modelPath, fileTreeLocation);

			createMissingIncludePathFolderNodes(fileTreeLocation, existingIncludePathFolderNode);

			createFileNode(fileData, fileTreeLocation);
		}
	}

	/**
	 * Create missing nodes for include path folders
	 * @param fileTreeLocation
	 * @param existingIncludePathFolder
	 */
	private static void createMissingIncludePathFolderNodes(final IPath fileTreeLocation, final IPath existingIncludePathFolder) {
		IPath missingDirectoryTreeLocation = existingIncludePathFolder;
		do {
			missingDirectoryTreeLocation = missingDirectoryTreeLocation.append(fileTreeLocation.segment(missingDirectoryTreeLocation.segmentCount()));
			includePathTree.createElement(missingDirectoryTreeLocation, ((Workspace) ResourcesPlugin.getWorkspace()).newResource(missingDirectoryTreeLocation, IResource.FOLDER));
		} while (missingDirectoryTreeLocation.segmentCount() < fileTreeLocation.segmentCount());
	}

	/**
	 * Find missing folder node for a file
	 * @param modelPath
	 * @param fileTreeLocation
	 * @return last existing include path folder in the tree
	 */
	private static IPath findExistingIncludePathFolderNode(final IPath modelPath, final IPath fileTreeLocation) {
		IPath existingFolderTreeLocation = fileTreeLocation;
		do {
			existingFolderTreeLocation = existingFolderTreeLocation.removeLastSegments(1);
			if (existingFolderTreeLocation.segmentCount() <= modelPath.segmentCount())
				break;
		} while (!includePathTree.includes(existingFolderTreeLocation));
		return existingFolderTreeLocation;
	}

	/**
	 * Find include model for a file in the tree
	 * @param fileData to find model
	 * @return found model
	 */
	static IPhpModel findModel(final PHPFileData fileData) {
		validateRoot();
		final IPath[] modelPaths = includePathTree.getChildren(INCLUDE_PATHS_ROOT_PATH);
		final IResource res = PHPModelUtil.getResource(fileData);
		final IPath filePath = new Path(fileData.getName());
		String filePathString = filePath.toOSString();
		if (res != null)
			filePathString = res.getLocation().toOSString();
		for (int i = 0; i < modelPaths.length; ++i) {
			final IPhpModel model = IncludeModelPathRootConverter.from(modelPaths[i].segment(1));
			if (model == null)
				continue;
			final IPath modelLocation = PHPModelUtil.getIncludeModelLocation(model);
			if (modelLocation == null)
				continue;
			if (filePathString.startsWith(modelLocation.toOSString()) || filePath.isAbsolute() && filePath.segment(0).equals(model.getID()))
				return model;
		}
		return null;
	}

	/**
	 * Get children elements for a tree node
	 * @param parentPath
	 * @return children elements
	 */
	private static Object[] getTreeChildrenElements(final IPath parentPath) {
		final IPath[] childrenPaths = includePathTree.getChildren(parentPath);
		final Object[] childrenElements = new Object[childrenPaths.length];
		for (int i = 0; i < childrenPaths.length; ++i)
			childrenElements[i] = includePathTree.getElementData(childrenPaths[i]);
		return childrenElements;
	}

	/**
	 * Get folder children
	 * @param resource
	 * @return folder children
	 */
	private static Object[] getFolderChildren(final IFolder resource) {
		final IPath resourcePath = resource.getFullPath();
		if (includePathTree.includes(resource.getFullPath()))
			return getPathChildren(resourcePath);
		return NO_CHILDREN;
	}

	/**
	 * Get model children
	 * @param includePathModel
	 * @return model children
	 */
	private static Object[] getIncludeModelChildren(final IPhpModel includePathModel) {
		final IPath modelPath = INCLUDE_PATHS_ROOT_PATH.append(IncludeModelPathRootConverter.toString(includePathModel));
		System.out.println();
		if (!includePathTree.includes(modelPath)) {
			validateRoot();
			includePathTree.createElement(modelPath, includePathModel);
		}
		return getPathChildren(modelPath);
	}

	/**
	 * Get model parent element
	 * @param includeModel
	 * @return the parent
	 */
	private static Object getIncludeModelParent(PHPIncludePathModel includeModel) {
		final IPath modelPath = INCLUDE_PATHS_ROOT_PATH.append(IncludeModelPathRootConverter.toString(includeModel));
		if (includePathTree.includes(modelPath))
			for (final IncludesNode includePathNode : projectNodes.values()) {
				final Object[] children = getTreeNodeChildren(includePathNode);
				for (int j = 0; j < children.length; ++j)
					if (children[j] == includeModel)
						return includePathNode;
			}
		return null;
	}

	/**
	 * Get folder parent element
	 * @param folder
	 * @return parent
	 */
	private static Object getFolderParent(final IFolder folder) {
		final IPath folderPath = folder.getFullPath();
		if (includePathTree.includes(folderPath))
			return includePathTree.getElementData(folderPath.removeLastSegments(1));
		return null;
	}

	/**
	 * Get file parent element
	 * @param fileData
	 * @return
	 */
	private static Object getFileParent(final PHPFileData fileData) {
		final String fileName = fileData.getName();
		final IPath fileLocation = new Path(fileName);
		if (!includePathTree.includes(INCLUDE_PATHS_ROOT_PATH))
			return null;
		final IPath[] modelPaths = includePathTree.getChildren(INCLUDE_PATHS_ROOT_PATH);
		for (int i = 0; i < modelPaths.length; ++i) {
			final IPath includeLocation = PHPModelUtil.getIncludeModelLocation((PHPIncludePathModel) includePathTree.getElementData(modelPaths[i]));
			if (!fileName.startsWith(includeLocation.toOSString()))
				continue;
			final IPath fileTreeLocation = modelPaths[i].append(fileLocation.removeFirstSegments(includeLocation.segmentCount()));
			if (includePathTree.includes(fileTreeLocation)) {
				final IPath parentTreeLocation = fileTreeLocation.removeLastSegments(1);
				return includePathTree.getElementData(parentTreeLocation);
			}
		}
		return null;
	}

	/**
	 * @param parentPath
	 * @return
	 */
	private static Object[] getPathChildren(final IPath parentPath) {

		final IPath modelPath = parentPath.uptoSegment(2);

		if (!includePathTree.includes(modelPath))
			return NO_CHILDREN;

		final Object model = includePathTree.getElementData(modelPath);
		if (!(model instanceof PHPIncludePathModel) && !(model instanceof PhpModelProxy))
			return NO_CHILDREN;

		createIncludePathTree(modelPath, (IPhpModel) model);

		return getTreeChildrenElements(parentPath);
	}

	private Object[] getProjectChildren(final IProject project) {
		final PHPProjectOptions options = PHPProjectOptions.forProject(project);
		if (options != null)
			return new Object[] { getTreeNode(project) };
		return NO_CHILDREN;
	}

	private String getProjectModelText(final PhpModelProxy proxy) {
		return proxy.getID();
	}

	private String getNodeText(final PHPTreeNode treeNode) {
		return treeNode.getText();
	}

	private String getFileText(final PHPFileData fileData) {
		final IPath[] modelPaths = includePathTree.getChildren(includePathTree.getRoot());
		for (int i = 0; i < modelPaths.length; ++i) {
			final PHPIncludePathModel includePathModel = (PHPIncludePathModel) includePathTree.getElementData(modelPaths[i]);
			final IPath includePath = PHPModelUtil.getIncludeModelLocation(includePathModel);
			if (fileData.getName().startsWith(includePath.toOSString()))
				return new Path(fileData.getName()).lastSegment();
		}
		return null;
	}

	private String getIncludeModelText(final PHPIncludePathModel includePathModel) {
		final String id = includePathModel.getID();
		final IPath location = IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID());
		if (location != null)
			return id + " (" + IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID()).toOSString() + ")";
		return id;
	}

	private IncludesNode getTreeNode(final IProject project) {

		IncludesNode treeNode = projectNodes.get(project);

		if (treeNode == null)
			treeNode = createIncludePathsNode(project);
		// generate the tree:
		final Object[] models = getTreeNodeChildren(treeNode);
		if (models.length > 0)
			for (int i = 0; i < models.length; ++i)
				getIncludeModelChildren((IPhpModel) models[i]);
		return treeNode;
	}

	/**
	 * Get tree node children
	 * @param treeNode
	 * @return children
	 */
	private static Object[] getTreeNodeChildren(final PHPTreeNode treeNode) {
		IProject project = null;
		for (Map.Entry<IProject, IncludesNode> entry : projectNodes.entrySet()) {
			if (entry.getValue() == treeNode) {
				project = entry.getKey();
				break;
			}
		}
		if (project == null)
			return NO_CHILDREN;
		final PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		// if we force creation of the project model it hangs when creating a new project with heavy predefined include path.
		// On this stage there is no need to create the model, since we listen for include path changes.
		if (projectModel == null)
			return NO_CHILDREN;
		final PHPIncludePathModelManager includeModelManager = (PHPIncludePathModelManager) projectModel.getModel(PHPIncludePathModelManager.COMPOSITE_INCLUDE_PATH_MODEL_ID);
		if (includeModelManager == null)
			return NO_CHILDREN;
		final IPhpModel[] models = includeModelManager.listModels();
		final ArrayList<IPhpModel> filteredModels = new ArrayList(models.length);
		for (int i = 0; i < models.length; ++i) {
			final IPath modelLocation = PHPModelUtil.getIncludeModelLocation(models[i]);
			if (modelLocation != null)
				filteredModels.add(models[i]);
		}
		return filteredModels.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	/**
	 * Create root node if neccesary
	 */
	private static void validateRoot() {
		if (!includePathTree.includes(INCLUDE_PATHS_ROOT_PATH))
			includePathTree.createElement(INCLUDE_PATHS_ROOT_PATH, INCLUDE_PATHS_ROOT_PATH.segment(1));
	}
}
