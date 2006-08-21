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
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;

public class StandardPHPElementContentProvider implements ITreeContentProvider {

	protected static final Object[] NO_CHILDREN = new Object[0];

	protected static Object[] concatenate(final Object[] a1, final Object[] a2) {
		final int a1Len = a1.length;
		final int a2Len = a2.length;
		final Object[] res = new Object[a1Len + a2Len];
		System.arraycopy(a1, 0, res, 0, a1Len);
		System.arraycopy(a2, 0, res, a1Len, a2Len);
		return res;
	}

	protected boolean fProvideMembers;

	IPHPTreeContentProvider[] treeProviders;

	public StandardPHPElementContentProvider() {
		this(false);
	}

	public StandardPHPElementContentProvider(final boolean provideMembers) {
		fProvideMembers = provideMembers;
	}

	public void dispose() {

	}

	protected boolean exists(final Object element) {
		if (element == null)
			return false;
		if (element instanceof IResource)
			return ((IResource) element).exists();

		return true;
	}

	final public Object[] getChildren(final Object parentElement) {
		Object[] children = getChildrenInternal(parentElement);
		if (treeProviders != null)
			for (int i = 0; i < treeProviders.length; i++) {
				final Object[] subChildren = treeProviders[i].getChildren(parentElement);
				if (subChildren != null && subChildren.length > 0)
					children = concatenate(children, subChildren);
			}
		return children;
	}

	protected Object[] getChildrenInternal(final Object parentElement) {
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
			final IFile file = (IFile) parentElement;
			final PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
			if (projectModel != null) {
				final PHPFileData fileData = projectModel.getFileData(file.getFullPath().toString());
				if (fileData != null)
					return getFileChildren(fileData);
			}

		}
		if (parentElement instanceof PHPFileData)
			return getFileChildren((PHPFileData) parentElement);
		if (parentElement instanceof PHPClassData)
			return getClassChildren((PHPClassData) parentElement);

		return NO_CHILDREN;
	}

	private Object[] getClassChildren(final PHPClassData classData) {
		final ArrayList list = new ArrayList();
		final PHPFunctionData[] functions = classData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				list.add(functions[i]);
		final PHPVariableData[] vars = classData.getVars();
		if (vars != null)
			for (int i = 0; i < vars.length; i++)
				list.add(vars[i]);
		final PHPClassConstData[] consts = classData.getConsts();
		if (consts != null)
			for (int i = 0; i < consts.length; i++)
				list.add(consts[i]);
		return list.toArray();
	}

	public Object[] getElements(final Object parent) {
		return getChildren(parent);
	}

	private Object[] getFileChildren(final PHPFileData fileData) {
		final ArrayList list = new ArrayList();
		final PHPClassData[] classData = fileData.getClasses();
		if (classData != null)
			for (int i = 0; i < classData.length; i++)
				list.add(classData[i]);
		final PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				list.add(functions[i]);
		final PHPConstantData[] consts = fileData.getConstants();
		if (consts != null)
			for (int i = 0; i < consts.length; i++)
				list.add(consts[i]);
		return list.toArray();
	}

	protected Object[] getFolderChildren(final IContainer folder, final String[] filterNames) {
		try {
			final IResource[] members = folder.members();
			final IProject project = folder.getProject();
			if (!project.exists())
				return members;
			final ArrayList folderList = new ArrayList();
			final ArrayList fileList = new ArrayList();
			for (int i = 0; i < members.length; i++) {
				final IResource member = members[i];
				//				PHPFileData fileData = null;
				boolean filterOut = false;
				if (filterNames != null)
					for (int j = 0; j < filterNames.length; j++)
						if (filterNames[j].equals(member.getName())) {
							filterOut = true;
							break;
						}
				if (filterOut)
					continue;
				if (member instanceof IFolder)
					folderList.add(member);
				else if (member instanceof IFile)
					//						fileData = projectModel.getFileData(member.getFullPath().toString());
					//					if (fileData != null)
					//						fileList.add(fileData);
					//					else
					fileList.add(members[i]);
			}
			folderList.addAll(fileList);
			return folderList.toArray();
		} catch (final CoreException e) {
			return NO_CHILDREN;
		}
	}

	public Object getParent(final Object element) {
		if (!exists(element))
			return null;
		return internalGetParent(element);
	}

	protected Object[] getPHPProjects(final PHPWorkspaceModelManager modelManager) {
		return modelManager.listProjects();
	}

	protected Object[] getProjectChildren(final IProject project) {
		final String[] filterNames = {};
		return getProjectChildren(project, filterNames);
	}

	protected Object[] getProjectChildren(final IProject project, final String[] filterNames) {
		if (!project.isOpen())
			return NO_CHILDREN;

		final Object[] children = getFolderChildren(project, filterNames);

		return children;
	}

	protected Object[] getProjectChildren(final PHPProjectModel model) {
		final IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(model);
		return getProjectChildren(project);
	}

	protected Object[] getProjectChildren(final PHPProjectModel model, final String[] filterNames) {
		final IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(model);
		return getProjectChildren(project, filterNames);
	}

	public boolean getProvideMembers() {
		return fProvideMembers;
	}

	final public boolean hasChildren(final Object element) {
		final boolean areChildren = hasChildrenInternal(element);

		return areChildren;
	}

	protected boolean hasChildrenInternal(final Object element) {
		if (getProvideMembers()) {
			// assume CUs and class files are never empty
			if (element instanceof PHPFileData)
				return true;
		} else // don't allow to drill down into a compilation unit or class file
		if (element instanceof PHPFileData || element instanceof IFile)
			return false;

		if (element instanceof PHPProjectModel) {
			final PHPProjectModel sp = (PHPProjectModel) element;
			final IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(sp);
			if (!project.isOpen())
				return false;
		}

		if (element instanceof PHPCodeData) {
			final PHPCodeData codeData = (PHPCodeData) element;
			return PHPModelUtil.hasChildren(codeData);
		}
		final Object[] children = getChildren(element);
		return children != null && children.length > 0;
	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {

		if (treeProviders != null)
			for (int i = 0; i < treeProviders.length; i++)
				treeProviders[i].inputChanged(viewer, oldInput, newInput);
	}

	protected Object internalGetParent(final Object element) {
		return PHPModelUtil.getParent(element);
	}

	protected boolean isProjectRoot(final IContainer root) {
		return root instanceof IProject;
	}

	protected boolean isSourceFolderEmpty(final Object element) {
		if (element instanceof IFolder) {
			final IFolder folder = (IFolder) element;
			try {
				if (folder.exists() && folder.members().length == 0)
					return true;
			} catch (final CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setProvideMembers(final boolean b) {
		fProvideMembers = b;
	}

	public void setTreeProviders(final IPHPTreeContentProvider[] providers) {
		treeProviders = providers;
	}

	protected Object skipProjectRoot(final IFolder root) {
		if (isProjectRoot(root))
			return root.getParent();
		return root;
	}
}
