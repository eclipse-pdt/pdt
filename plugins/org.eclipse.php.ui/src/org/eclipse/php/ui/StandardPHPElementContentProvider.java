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
package org.eclipse.php.ui;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;

public class StandardPHPElementContentProvider implements ITreeContentProvider {

	protected static final Object[] NO_CHILDREN = new Object[0];
	protected boolean fProvideMembers;

	IPHPTreeContentProvider[] treeProviders;

	public StandardPHPElementContentProvider() {
		this(false);
	}

	public StandardPHPElementContentProvider(boolean provideMembers) {
		fProvideMembers = provideMembers;
	}

	final public Object[] getChildren(Object parentElement) {
		Object[] children = getChildrenInternal(parentElement);
		if (treeProviders != null) {
			for (int i = 0; i < treeProviders.length; i++) {
				Object[] subChildren = treeProviders[i].getChildren(parentElement);
				if (subChildren != null && subChildren.length > 0)
					children = concatenate(children, subChildren);
			}
		}
		return children;
	}

	public void setTreeProviders(IPHPTreeContentProvider[] providers) {
		this.treeProviders = providers;
	}

	protected Object[] getChildrenInternal(Object parentElement) {
		if (!exists(parentElement))
			return NO_CHILDREN;

		if (parentElement instanceof PHPWorkspaceModelManager)
			return getPHPProjects((PHPWorkspaceModelManager) parentElement);

		if (parentElement instanceof IProject)
			return getProjectChildren((IProject) parentElement);

		if (parentElement instanceof PHPProjectModel)
			return getProjectChildren((PHPProjectModel) parentElement);

		if (parentElement instanceof IContainer)
			return getFolderChildren((IContainer) parentElement, null);

		if (parentElement instanceof IFile) {
			return getFileChildren((IFile) parentElement);

		}
		if (parentElement instanceof PHPFileData)
			return getFileChildren((PHPFileData) parentElement);
		if (parentElement instanceof PHPClassData)
			return getClassChildren((PHPClassData) parentElement);

		return NO_CHILDREN;
	}

	protected Object[] getFileChildren(IFile parentElement) {
		IFile file = parentElement;
		PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (projectModel != null) {
			PHPFileData fileData = projectModel.getFileData(file.getFullPath().toString());
			if (fileData != null)
				return getFileChildren(fileData);
		}
		return NO_CHILDREN;
	}

	protected Object[] getFileChildren(PHPFileData fileData) {
		ArrayList list = new ArrayList();
		PHPIncludeFileData[] includes = fileData.getIncludeFiles();
		if (includes != null) {
			for (int i = 0; i < includes.length; i++) {
				list.add(includes[i]);
			}
		}
		PHPConstantData[] consts = fileData.getConstants();
		if (consts != null) {
			for (int i = 0; i < consts.length; i++) {
				list.add(consts[i]);
			}
		}
		PHPClassData[] classData = fileData.getClasses();
		if (classData != null) {
			for (int i = 0; i < classData.length; i++) {
				list.add(classData[i]);
			}
		}
		PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null) {
			for (int i = 0; i < functions.length; i++) {
				list.add(functions[i]);
			}
		}
		return list.toArray();
	}

	protected Object[] getClassChildren(PHPClassData classData) {
		ArrayList list = new ArrayList();
		PHPFunctionData[] functions = classData.getFunctions();
		if (functions != null) {
			for (int i = 0; i < functions.length; i++) {
				list.add(functions[i]);
			}
		}
		PHPVariableData[] vars = classData.getVars();
		if (vars != null) {
			for (int i = 0; i < vars.length; i++) {
				list.add(vars[i]);
			}
		}
		PHPClassConstData[] consts = classData.getConsts();
		if (consts != null) {
			for (int i = 0; i < consts.length; i++) {
				list.add(consts[i]);
			}
		}
		return list.toArray();
	}

	public final Object getParent(Object element) {
		Object parent = null;
		if (exists(element)) {
			parent = internalGetParent(element);
		}
		if (treeProviders != null) {
			for (int i = 0; i < treeProviders.length && parent == null; i++) {
				parent = treeProviders[i].getParent(element);
			}
		}
		return parent;

	}

	final public boolean hasChildren(Object element) {
		boolean areChildren = hasChildrenInternal(element);

		return areChildren;
	}

	protected boolean hasChildrenInternal(Object element) {
		if (getProvideMembers()) {
			// assume CUs and class files are never empty
			if (element instanceof PHPFileData) {
				return true;
			}
		} else {
			// don't allow to drill down into a compilation unit or class file
			if (element instanceof PHPFileData || element instanceof IFile) {
				return false;
			}
		}

		if (element instanceof PHPProjectModel) {
			PHPProjectModel sp = (PHPProjectModel) element;
			IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(sp);
			if (!project.isOpen()) {
				return false;
			}
		}

		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			return PHPModelUtil.hasChildren(codeData);
		}
		Object[] children = getChildren(element);
		return (children != null) && children.length > 0;
	}

	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		if (treeProviders != null) {
			for (int i = 0; i < treeProviders.length; i++) {
				treeProviders[i].inputChanged(viewer, oldInput, newInput);
			}
		}
	}

	protected boolean exists(Object element) {
		if (element == null) {
			return false;
		}
		if (element instanceof IResource) {
			return ((IResource) element).exists();
		}

		return true;
	}

	public boolean getProvideMembers() {
		return fProvideMembers;
	}

	protected Object[] getPHPProjects(PHPWorkspaceModelManager modelManager) {
		return modelManager.listProjects();
	}

	protected Object[] getProjectChildren(PHPProjectModel model) {
		IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(model);
		return getProjectChildren(project);
	}

	protected Object[] getProjectChildren(PHPProjectModel model, String[] filterNames) {
		IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(model);
		return getProjectChildren(project, filterNames);
	}

	protected Object[] getProjectChildren(IProject project) {
		String[] filterNames = {};
		return getProjectChildren(project, filterNames);
	}

	protected Object[] getProjectChildren(IProject project, String[] filterNames) {
		if (!project.isOpen())
			return NO_CHILDREN;

		Object[] children = getFolderChildren(project, filterNames);

		return children;
	}

	protected boolean isProjectRoot(IContainer root) {
		return (root instanceof IProject);
	}

	protected Object[] getFolderChildren(IContainer folder, String[] filterNames) {
		try {
			IResource[] members = folder.members();
			IProject project = folder.getProject();
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
			if (projectModel == null)
				return members;
			if (!project.exists())
				return members;
			ArrayList folderList = new ArrayList();
			ArrayList fileList = new ArrayList();
			for (int i = 0; i < members.length; i++) {
				IResource member = members[i];
				//				PHPFileData fileData = null;
				boolean filterOut = false;
				if (filterNames != null)
					for (int j = 0; j < filterNames.length; j++) {
						if (filterNames[j].equals(member.getName())) {
							filterOut = true;
							break;
						}
					}
				if (filterOut)
					continue;
				if (member instanceof IFolder) {
					folderList.add(member);
				} else {
					if (member instanceof IFile)
						fileList.add(members[i]);
				}
			}
			folderList.addAll(fileList);
			return folderList.toArray();
		} catch (CoreException e) {
			return NO_CHILDREN;
		}
	}

	protected Object internalGetParent(Object element) {
		return PHPModelUtil.getParent(element);
	}

	protected Object skipProjectRoot(IFolder root) {
		if (isProjectRoot(root))
			return root.getParent();
		return root;
	}

	protected static Object[] concatenate(Object[] a1, Object[] a2) {
		int a1Len = a1.length;
		int a2Len = a2.length;
		Object[] res = new Object[a1Len + a2Len];
		System.arraycopy(a1, 0, res, 0, a1Len);
		System.arraycopy(a2, 0, res, a1Len, a2Len);
		return res;
	}

	protected boolean isSourceFolderEmpty(Object element) {
		if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			try {
				if (folder.exists() && (folder.members().length == 0))
					return true;
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setProvideMembers(boolean b) {
		fProvideMembers = b;
	}

}
