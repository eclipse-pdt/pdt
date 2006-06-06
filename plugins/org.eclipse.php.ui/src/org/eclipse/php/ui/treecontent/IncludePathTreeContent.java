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

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.phpModel.parser.PHPIncludePathModel;
import org.eclipse.php.core.phpModel.parser.PHPIncludePathModelManager;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.parser.PhpModelProxy;
import org.eclipse.php.core.project.options.IPhpProjectOptionChangeListener;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class IncludePathTreeContent implements IPHPTreeContentProvider {
	public static final String ID_INCLUDES_NODE = "org.eclipse.php.ui.treecontent.IncludesNode";
	TreeViewer treeViewer;
	HashMap projects = new HashMap();

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
			return includePathModel.getFileDatas();
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
			return includePathModel.getID();
		} else if (element instanceof PhpModelProxy) {
			PhpModelProxy proxy = (PhpModelProxy) element;
			return proxy.getID();
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
