/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - PDT port
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;
import org.eclipse.wst.jsdt.ui.ProjectLibraryRoot;

public class PHPNavigatorContentProvider extends PHPExplorerContentProvider implements IPipelinedTreeContentProvider {

	public static final String PDT_EXTENSION_ID = "org.eclipse.php.ui.phpContent"; //$NON-NLS-1$

	public PHPNavigatorContentProvider() {
		super(true);
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		IMemento memento = aConfig.getMemento();

		restoreState(memento);
		setIsFlatLayout(false);
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);
		setProvideMembers(showCUChildren);
	}

	@Override
	public Object getParent(Object element) {
		Object parent = super.getParent(element);
		if (parent instanceof IScriptModel) {
			return ((IScriptModel) parent).getWorkspace().getRoot();
		}
		if (parent instanceof IScriptProject) {
			return ((IScriptProject) parent).getProject();
		} else if (parent instanceof ISourceModule) {
			return ((ISourceModule) parent).getResource();
		}
		return parent;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IWorkspaceRoot) {
			IWorkspaceRoot root = (IWorkspaceRoot) parentElement;
			return filterResourceProjects(root.getProjects());
		} else if (parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			if (isPHPProject(project)) {
				IScriptProject scriptProject = DLTKCore.create(project);
				return super.getChildren(scriptProject);
			}
		} else if (parentElement instanceof IScriptFolder) {
			/// return customize(super.getChildren(parentElement), null);
		} else if (parentElement instanceof IScriptModel) {
			return filterResourceProjects(((IScriptModel) parentElement).getWorkspace().getRoot().getProjects());
		} else if (parentElement instanceof IFile) {
			IModelElement create = DLTKCore.create((IFile) parentElement);
			if (create != null) {
				return super.getChildren(create);
			}
		}
		return super.getChildren(parentElement);
	}

	@Override
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return ((IProject) element).isAccessible();
		} else if (element instanceof IFile) {
			IModelElement create = DLTKCore.create((IFile) element);
			if (create != null) {
				return super.hasChildren(create);
			}
		}
		return super.hasChildren(element);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, findInputElement(newInput));
	}

	private Object findInputElement(Object newInput) {
		if (newInput instanceof IWorkspaceRoot) {
			return DLTKCore.create((IWorkspaceRoot) newInput);
		}
		return newInput;
	}

	private static IProject[] filterResourceProjects(IProject[] projects) {
		List<IProject> filteredProjects = new ArrayList<IProject>(projects.length);
		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (!project.isOpen() || isPHPProject(project))
				filteredProjects.add(project);
		}
		return filteredProjects.toArray(new IProject[filteredProjects.size()]);
	}

	private static boolean isPHPProject(IProject project) {
		try {
			return project.hasNature(PHPNature.ID);
		} catch (CoreException e) {
			Logger.logException(e);
			return false;
		}
	}

	@Override
	public void getPipelinedChildren(Object parent, Set currentChildren) {
		customize(getChildren(parent), currentChildren);
	}

	@Override
	public void getPipelinedElements(Object input, Set currentElements) {
		customize(getChildren(input), currentElements);
	}

	/**
	 * Adapted from the Common Navigator Content Provider
	 * 
	 * @param phpElements
	 *            the java elements
	 * @param proposedChildren
	 *            the proposed children
	 */
	private void customize(Object[] phpElements, Set<Object> proposedChildren) {
		proposedChildren.clear();

		for (int i = 0; i < phpElements.length; i++) {
			Object element = phpElements[i];
			if (element instanceof ISourceModule) {
				ISourceModule sourceModule = (ISourceModule) element;
				IResource resource = sourceModule.getResource();
				if (resource != null) {
					proposedChildren.add(resource);
				} else {
					proposedChildren.add(sourceModule);
				}
			} else if (element instanceof IModelElement) {
				IModelElement cElement = (IModelElement) element;
				IResource resource = cElement.getResource();
				if (resource != null) {
					proposedChildren.remove(resource);
				}
				proposedChildren.add(element);
			} else if (element instanceof ProjectLibraryRoot) {
				// don't add
			} else if (element != null) {
				proposedChildren.add(element);
			}
		}
	}

	@Override
	public Object getPipelinedParent(Object anObject, Object aSuggestedParent) {
		return getParent(anObject);
	}

	@Override
	public PipelinedShapeModification interceptAdd(PipelinedShapeModification addModification) {
		Object parent = addModification.getParent();

		if (parent instanceof IScriptProject) {
			addModification.setParent(((IScriptProject) parent).getProject());
		}

		if (parent instanceof IWorkspaceRoot) {
			deconvertPHPProjects(addModification);
		}

		convertToPHPElements(addModification);
		return addModification;
	}

	@Override
	public PipelinedShapeModification interceptRemove(PipelinedShapeModification removeModification) {
		return removeModification;
	}

	@Override
	public boolean interceptRefresh(PipelinedViewerUpdate refreshSynchronization) {
		return false;

	}

	@Override
	public boolean interceptUpdate(PipelinedViewerUpdate updateSynchronization) {
		return false;
	}

	private void deconvertPHPProjects(PipelinedShapeModification modification) {
		Set<IProject> convertedChildren = new LinkedHashSet<IProject>();
		for (Iterator<IAdaptable> iterator = modification.getChildren().iterator(); iterator.hasNext();) {
			Object added = iterator.next();
			if (added instanceof IScriptProject) {
				iterator.remove();
				convertedChildren.add(((IScriptProject) added).getProject());
			}
		}
		modification.getChildren().addAll(convertedChildren);
	}

	/**
	 * Converts the shape modification to use Java elements.
	 *
	 *
	 * @param modification
	 *            the shape modification to convert
	 * @return returns true if the conversion took place
	 */
	private boolean convertToPHPElements(PipelinedShapeModification modification) {
		Object parent = modification.getParent();
		// As of 3.3, we no longer re-parent additions to IProject.
		if (parent instanceof IContainer) {
			IModelElement element = DLTKCore.create((IContainer) parent);
			if (element != null && element.exists()) {
				// we don't convert the root
				if (!(element instanceof IScriptModel) && !(element instanceof IScriptProject))
					modification.setParent(element);
				return convertToPHPElements(modification.getChildren());

			}
		}
		return false;
	}

	/**
	 * Converts the shape modification to use PHP elements.
	 *
	 *
	 * @param currentChildren
	 *            The set of current children that would be contributed or
	 *            refreshed in the viewer.
	 * @return returns true if the conversion took place
	 */
	private boolean convertToPHPElements(Set<Object> currentChildren) {
		LinkedHashSet<Object> convertedChildren = new LinkedHashSet<Object>();
		IModelElement newChild;
		for (Iterator<Object> childrenItr = currentChildren.iterator(); childrenItr.hasNext();) {
			Object child = childrenItr.next();
			// only convert IFolders
			if (child instanceof IFolder) {
				if ((newChild = DLTKCore.create((IResource) child)) != null && newChild.exists()) {
					childrenItr.remove();
					convertedChildren.add(newChild);
				}
			} else if (child instanceof IScriptProject) {
				childrenItr.remove();
				convertedChildren.add(((IScriptProject) child).getProject());
			}
		}
		if (!convertedChildren.isEmpty()) {
			currentChildren.addAll(convertedChildren);
			return true;
		}
		return false;

	}

	@Override
	protected void postRefresh(List<?> toRefreshOld, boolean updateLabels, Collection<Runnable> runnables) {
		List<Object> toRefresh = new ArrayList<>(toRefreshOld);
		int size = toRefresh.size();
		for (int i = 0; i < size; i++) {
			Object element = toRefresh.get(i);
			if (element instanceof IScriptProject) {
				toRefresh.set(i, ((IScriptProject) element).getProject());
			}
		}
		for (Iterator<Object> iter = toRefresh.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IModelElement) {
				iter.remove();
				toRefresh.add(((IModelElement) element).getModel().getWorkspace().getRoot());
				super.postRefresh(toRefresh, updateLabels, runnables);
				return;
			}
		}
		super.postRefresh(toRefresh, updateLabels, runnables);
	}

	@Override
	public void restoreState(IMemento aMemento) {

	}

	@Override
	public void saveState(IMemento aMemento) {

	}
}
